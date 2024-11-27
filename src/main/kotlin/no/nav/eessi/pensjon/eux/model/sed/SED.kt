package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.eux.model.SedType.*
import no.nav.eessi.pensjon.utils.mapAnyToJson
import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson

// SED class main request class to basis
// Strukturerte Elektroniske Dokumenter

@JsonIgnoreProperties(ignoreUnknown = true)
open class SED(
    @JsonProperty("sed")
    open val type: SedType,
    open val sedGVer: String? = "4",
    open var sedVer: String? = setSEDVersion("2"),
    open val nav: Nav? = null,
    open val pensjon: Pensjon? = null
) {
    companion object {
    private val logger by lazy { org.slf4j.LoggerFactory.getLogger(SED::class.java) }
        //TODO Hvorfor logges 4.3 som 4.2????
        fun setSEDVersion(sedVersion: String?): String {
            return when(sedVersion) {
                "v4.3" -> "3"
                else -> "2"
            }.also { logger.debug("SED version: v4.$sedVersion") }
        }
        private fun fromSimpleJson(sed: String): SedType {
            return mapJsonToAny<SimpleSED>(sed, true).type
        }
        fun fromJson(sed: String): SED {
            return mapJsonToAny(sed, true)
        }

        fun listSupportetConcreteClass(): List<SedType> = listOf(
            SEDTYPE_P2000, SEDTYPE_P2100, SEDTYPE_P2200, SEDTYPE_P4000, SEDTYPE_P5000, SEDTYPE_P6000, SEDTYPE_P7000, SEDTYPE_P8000,
            SEDTYPE_P9000, SEDTYPE_P10000, SEDTYPE_P15000, SEDTYPE_X005, SEDTYPE_X009, SEDTYPE_X010, SEDTYPE_R005
        )
        inline fun <reified T : SED> generateSedToClass(sed: SED): T = mapJsonToAny<T>(sed.toSkipEmptyString())
        inline fun <reified T : SED> generateJsonToClass(json: String): T = mapJsonToAny<T>(json)

        @JsonIgnoreProperties(ignoreUnknown = true)
        private class SimpleSED(
            @JsonAlias("sed")
            @JsonProperty("sed")
            val type: SedType
        )

        fun fromJsonToConcrete(json: String?): SED {
            return when (json?.let { fromSimpleJson(json) }) {
                SEDTYPE_P2000 -> mapJsonToAny<P2000>(json)
                SEDTYPE_P2100 -> mapJsonToAny<P2100>(json)
                SEDTYPE_P2200 -> mapJsonToAny<P2200>(json)
                SEDTYPE_P4000 -> mapJsonToAny<P4000>(json)
                SEDTYPE_P5000 -> mapJsonToAny<P5000>(json)
                SEDTYPE_P6000 -> mapJsonToAny<P6000>(json)
                SEDTYPE_P7000 -> mapJsonToAny<P7000>(json)
                SEDTYPE_P8000 -> mapJsonToAny<P8000>(json)
                SEDTYPE_P9000 -> mapJsonToAny<P9000>(json)
                SEDTYPE_P10000 -> mapJsonToAny<P10000>(json)
                SEDTYPE_P12000 -> mapJsonToAny<P12000>(json)
                SEDTYPE_P15000 -> mapJsonToAny<P15000>(json)
                SEDTYPE_R005 -> mapJsonToAny<R005>(json)
                SEDTYPE_X005 -> mapJsonToAny<X005>(json)
                SEDTYPE_X008 -> mapJsonToAny<X008>(json)
                SEDTYPE_X009 -> mapJsonToAny<X009>(json)
                SEDTYPE_X010 -> mapJsonToAny<X010>(json)
                else -> fromJson(json!!)
            }
        }
    }

    @JsonIgnore
    fun toSkipEmptyString() : String = mapAnyToJson(this, true)

    @JsonIgnore
    override fun toString() : String = this.toJson()

    fun allePersoner(): List<Person> =
        listOf(
            nav?.bruker?.person,
            nav?.annenperson?.person,
            nav?.ektefelle?.person,
            nav?.verge?.person,
            nav?.ektefelle?.person,
            pensjon?.gjenlevende?.person
        ).plus((nav?.barn?.map { it.person } ?: emptyList())
        ).plus(
            when (type) {
                SEDTYPE_P4000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P5000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P6000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P7000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P8000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P10000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P12000 -> pensjon?.gjenlevende?.person
                SEDTYPE_P15000 -> pensjon?.gjenlevende?.person
                else -> null
            }
        ).filterNotNull().filter { it.pin != null }

}