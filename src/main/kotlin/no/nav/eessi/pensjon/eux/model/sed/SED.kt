package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import org.slf4j.LoggerFactory
import no.nav.eessi.pensjon.utils.JsonIllegalArgumentException
import no.nav.eessi.pensjon.utils.mapAnyToJson
import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.typeRefs

// SED class main request class to basis
// Strukturerte Elektroniske Dokumenter

open class SED(
    @JsonProperty("sed")
    open val type: SedType,
    open val sedGVer: String? = "4",
    open var sedVer: String? = "2",
    open val nav: Nav? = null,
    open val pensjon: Pensjon? = null
) {

    companion object {
        private val logger = LoggerFactory.getLogger(SED::class.java)

        private fun fromSimpleJson(sed: String): SedType {
            return mapJsonToAny(sed, typeRefs<SimpleSED>(), true).type
        }
        fun fromJson(sed: String): SED {
            return mapJsonToAny(sed, typeRefs(), true)
        }

        fun listSupportetConcreteClass(): List<SedType> = listOf(
            SedType.P2000, SedType.P2100, SedType.P2200, SedType.P4000, SedType.P5000, SedType.P6000, SedType.P7000, SedType.P8000,
            SedType.P9000, SedType.P10000, SedType.P15000, SedType.X005, SedType.X010, SedType.R005
        )

        inline fun <reified T : SED> generateSedToClass(sed: SED): T = mapJsonToAny(sed.toSkipEmptyString(), typeRefs<T>())
        inline fun <reified T : SED> generateJsonToClass(json: String): T = mapJsonToAny(json, typeRefs<T>())

        @JsonIgnoreProperties(ignoreUnknown = true)
        private class SimpleSED(
            @JsonProperty("sed")
            val type: SedType
        )

        fun fromJsonToConcrete(json: String?): SED {
            try {
                return when(json?.let { fromSimpleJson(json) }) {
                    SedType.P2000 -> generateJsonToClass<P2000>(json)
                    SedType.P2100 -> generateJsonToClass<P2100>(json)
                    SedType.P2200 -> generateJsonToClass<P2200>(json)
                    SedType.P4000 -> generateJsonToClass<P4000>(json)
                    SedType.P5000 -> generateJsonToClass<P5000>(json)
                    SedType.P6000 -> generateJsonToClass<P6000>(json)
                    SedType.P7000 -> generateJsonToClass<P7000>(json)
                    SedType.P8000 -> generateJsonToClass<P8000>(json)
                    SedType.P9000 -> generateJsonToClass<P9000>(json)
                    SedType.P10000 -> generateJsonToClass<P10000>(json)
                    SedType.P15000 -> generateJsonToClass<P15000>(json)
                    SedType.R005 -> generateJsonToClass<R005>(json)
                    SedType.X005 -> generateJsonToClass<X005>(json)
                    SedType.X010 -> generateJsonToClass<X010>(json)
                    else -> fromJson(json!!)
                }
            } catch (jpe: JsonParseException) {
                val exception = jpe.message?.substringBefore("nav", "{")
                throw JsonIllegalArgumentException("Feilet ved konvertering av jsonformat $exception", Throwable(exception))
                    .also { logger.error(it.message) }
            } catch (jme: JsonMappingException) {
                val exception = jme.message?.substringBefore("nav", "{")
                throw JsonIllegalArgumentException("Feilet ved mapping av jsonformat $exception", Throwable(exception))
                    .also { logger.error(it.message) }
            } catch (ex: Exception) {
                throw Exception("Feilet med en ukjent feil ved jsonformat").also { logger.error(it.message) }
            }
        }
    }

    @JsonIgnore
    open fun korrektPerson(): Person? {
        return this.pensjon?.gjenlevende?.person ?: this.nav?.bruker?.person
    }

    @JsonIgnore
    fun toSkipEmptyString() : String = mapAnyToJson(this, true)

    @JsonIgnore
    override fun toString() : String = this.toJson()

}