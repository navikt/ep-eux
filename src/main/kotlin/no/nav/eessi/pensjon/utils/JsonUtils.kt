package no.nav.eessi.pensjon.utils

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.StreamReadFeature
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.ResponseStatusException


inline fun <reified T : Any> typeRefs(): TypeReference<T> = object : TypeReference<T>() {}

inline fun <reified T : Any> mapJsonToAny(json: String, failonunknown: Boolean = false, readUnknownAsNull: Boolean = false): T {
    return try {
        jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setFilterProvider(SimpleFilterProvider().apply {
                addFilter("propertyFilter", SimpleBeanPropertyFilter.serializeAll())
            })
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failonunknown)
            .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, readUnknownAsNull)
            .readValue(json, typeRefs<T>())
    } catch (jpe: JsonParseException) {
        jpe.printStackTrace()
        throw JsonException("Feilet ved konvertering av jsonformat, ${jpe.message}", jpe)
    } catch (jme: JsonMappingException) {
        jme.printStackTrace()
        throw JsonIllegalArgumentException("Feilet ved mapping av jsonformat, ${jme.message}", jme)
    } catch (ex: Exception) {
        ex.printStackTrace()
        throw JsonException("Feilet med en ukjent feil ved jsonformat, ${ex.message}", ex)
    }
}

fun mapAnyToJson(data: Any, nonempty: Boolean = false): String {
    return if (nonempty) {
        jacksonObjectMapper()
            .setFilterProvider(SimpleFilterProvider().apply {
                addFilter("propertyFilter", SimpleBeanPropertyFilter.serializeAll())
            })
            .registerModule(JavaTimeModule())
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(data)
    } else {
        return jacksonObjectMapper()
            .setFilterProvider(SimpleFilterProvider().apply {
                addFilter("propertyFilter", SimpleBeanPropertyFilter.serializeAll())
            })
            .registerModule(JavaTimeModule())
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(data)
    }
}


fun mapAnyToJsonWithoutSensitiveData(json: Any, ignoredProperties: List<String>): String {
    return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setFilterProvider(SimpleFilterProvider().apply {
                ignoredProperties.forEach {
                    addFilter("propertyFilter", SimpleBeanPropertyFilter.serializeAllExcept(it))
                }
            })
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(json)
    }
fun validateJson(json: String): Boolean {
    return try {
        jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setFilterProvider(SimpleFilterProvider().apply {
                addFilter("propertyFilter", SimpleBeanPropertyFilter.serializeAll())
            })
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .readTree(json)
        true
    } catch (ex: Exception) {
        ex.printStackTrace()
        false
    }
}

fun errorBody(error: String?, uuid: String = "no-uuid"): String {
    return "{\"success\": false, \n \"error\": \"$error\", \"uuid\": \"$uuid\"}"
}

fun successBody(): String {
    return "{\"success\": true}"
}

inline fun eessiRequire(value: Boolean, status: HttpStatus = HttpStatus.BAD_REQUEST,  lazyMessage: () -> String) {
    if (!value) {
        val message = lazyMessage()
        throw ResponseStatusException(status, message)
    }
}

fun Any.toJsonSkipEmpty() = mapAnyToJson(this, true)
fun Any.toJson() = mapAnyToJson(this)

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class JsonException(message: String?, cause: Throwable?) : RuntimeException(message, cause)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class JsonIllegalArgumentException(message: String?, cause: Throwable?) : IllegalArgumentException(message, cause)
