package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType
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
            SedType.P2000, SedType.P2100, SedType.P2200, SedType.P4000, SedType.P5000, SedType.P6000, SedType.P7000, SedType.P8000,
            SedType.P9000, SedType.P10000, SedType.P15000, SedType.X005, SedType.X009, SedType.X010, SedType.R005
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        private class SimpleSED(
            @JsonProperty("sed")
            val type: SedType
        )

        fun fromJsonToConcrete(json: String?): SED {
            return when (json?.let { fromSimpleJson(json) }) {
                SedType.P2000 -> mapJsonToAny<P2000>(json)
                SedType.P2100 -> mapJsonToAny<P2100>(json)
                SedType.P2200 -> mapJsonToAny<P2200>(json)
                SedType.P4000 -> mapJsonToAny<P4000>(json)
                SedType.P5000 -> mapJsonToAny<P5000>(json)
                SedType.P6000 -> mapJsonToAny<P6000>(json)
                SedType.P7000 -> mapJsonToAny<P7000>(json)
                SedType.P8000 -> mapJsonToAny<P8000>(json)
                SedType.P9000 -> mapJsonToAny<P9000>(json)
                SedType.P10000 -> mapJsonToAny<P10000>(json)
                SedType.P15000 -> mapJsonToAny<P15000>(json)
                SedType.R005 -> mapJsonToAny<R005>(json)
                SedType.X005 -> mapJsonToAny<X005>(json)
                SedType.X008 -> mapJsonToAny<X008>(json)
                SedType.X009 -> mapJsonToAny<X009>(json)
                SedType.X010 -> mapJsonToAny<X010>(json)
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
                SedType.P4000 -> (this as P4000).p4000Pensjon?.gjenlevende?.person
                SedType.P5000 -> (this as P5000).p5000Pensjon?.gjenlevende?.person
                SedType.P6000 -> (this as P6000).p6000Pensjon?.gjenlevende?.person
                SedType.P7000 -> (this as P7000).p7000Pensjon?.gjenlevende?.person
                SedType.P15000 -> (this as P15000).p15000Pensjon?.gjenlevende?.person
                else -> null
            }
        ).filterNotNull().filter { it.pin != null }

}