package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.eux.model.SedType.*
import no.nav.eessi.pensjon.utils.mapAnyToJson
import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import org.slf4j.LoggerFactory

// SED class main request class to basis
// Strukturerte Elektroniske Dokumenter

@JsonIgnoreProperties(ignoreUnknown = true)
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
            return mapJsonToAny<SimpleSED>(sed, true).type
        }
        fun fromJson(sed: String): SED {
            return mapJsonToAny(sed, true)
        }

        fun listSupportetConcreteClass(): List<SedType> = listOf(
            P2000, P2100, P2200, P4000, P5000, P6000, P7000, P8000,
            P9000, P10000, P15000, X005, X009, X010, R005
        )
        inline fun <reified T : SED> generateSedToClass(sed: SED): T = mapJsonToAny<T>(sed.toSkipEmptyString())
        inline fun <reified T : SED> generateJsonToClass(json: String): T = mapJsonToAny<T>(json)

        @JsonIgnoreProperties(ignoreUnknown = true)
        private class SimpleSED(
            @JsonProperty("sed")
            val type: SedType
        )

        fun fromJsonToConcrete(json: String?): SED {
            return when (json?.let { fromSimpleJson(json) }) {
                P2000 -> mapJsonToAny<P2000>(json)
                P2100 -> mapJsonToAny<P2100>(json)
                P2200 -> mapJsonToAny<P2200>(json)
                P4000 -> mapJsonToAny<P4000>(json)
                P5000 -> mapJsonToAny<P5000>(json)
                P6000 -> mapJsonToAny<P6000>(json)
                P7000 -> mapJsonToAny<P7000>(json)
                P8000 -> mapJsonToAny<P8000>(json)
                P9000 -> mapJsonToAny<P9000>(json)
                P10000 -> mapJsonToAny<P10000>(json)
                P15000 -> mapJsonToAny<P15000>(json)
                R005 -> mapJsonToAny<R005>(json)
                X005 -> mapJsonToAny<X005>(json)
                X008 -> mapJsonToAny<X008>(json)
                X009 -> mapJsonToAny<X009>(json)
                X010 -> mapJsonToAny<X010>(json)
                else -> fromJson(json!!)
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
                P4000 -> (this as P4000).p4000Pensjon?.gjenlevende?.person
                P5000 -> (this as P5000).p5000Pensjon?.gjenlevende?.person
                P6000 -> (this as P6000).p6000Pensjon?.gjenlevende?.person
                P7000 -> (this as P7000).p7000Pensjon?.gjenlevende?.person
                P15000 -> (this as P15000).p15000Pensjon?.gjenlevende?.person
                else -> null
            }
        ).filterNotNull().filter { it.pin != null }

}