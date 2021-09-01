package no.nav.eessi.pensjon.utils

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


inline fun <reified T : Any> typeRefs(): TypeReference<T> = object : TypeReference<T>() {}

inline fun <reified T : Any> mapJsonToAny(json: String, typeRef: TypeReference<T>, failonunknown: Boolean = false): T {
    return jacksonObjectMapper()
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failonunknown)
        .readValue(json, typeRef)
}

fun mapAnyToJson(data: Any, nonempty: Boolean = false): String {
    return if (nonempty) {
        jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .setDefaultPropertyInclusion(JsonInclude.Include.NON_EMPTY)
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(data)
    } else {
        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(data)
    }
}

fun Any.toJsonSkipEmpty() = mapAnyToJson(this, true)
fun Any.toJson() = mapAnyToJson(this)

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
class JsonException(message: String?, cause: Throwable?) : RuntimeException(message, cause)

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class JsonIllegalArgumentException(message: String?, cause: Throwable?) : IllegalArgumentException(message, cause)
