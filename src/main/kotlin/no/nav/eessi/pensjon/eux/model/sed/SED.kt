package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.sed.SedType.P10000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P15000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P2000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P2100
import no.nav.eessi.pensjon.eux.model.sed.SedType.P2200
import no.nav.eessi.pensjon.eux.model.sed.SedType.P4000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P5000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P6000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P7000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P8000
import no.nav.eessi.pensjon.eux.model.sed.SedType.P9000
import no.nav.eessi.pensjon.eux.model.sed.SedType.R005
import no.nav.eessi.pensjon.eux.model.sed.SedType.X005
import no.nav.eessi.pensjon.eux.model.sed.SedType.X010
import no.nav.eessi.pensjon.utils.mapAnyToJson
import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.typeRefs

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
        @JvmStatic
        fun fromJson(sed: String): SED {
            return mapJsonToAny(sed, typeRefs(), true)
        }

        fun listSupportetConcreteClass(): List<SedType> = listOf(
            P2000, P2100, P2200, P4000, P5000, P6000, P7000, P8000,
            P9000, P10000, P15000, X005, X010, R005
        )

        inline fun <reified T : SED> generateSedToClass(sed: SED): T = mapJsonToAny(sed.toJson(), typeRefs<T>())
        inline fun <reified T : SED> generateJsonToClass(json: String): T = mapJsonToAny(json, typeRefs<T>())

        fun fromJsonToConcrete(json: String?): SED {
            val sed =  json?.let { fromJson(json) }
            return when(sed!!.type) {
                P2000 -> generateJsonToClass<P2000>(json)
                P2100 -> generateJsonToClass<P2100>(json)
                P2200 -> generateJsonToClass<P2200>(json)
                P4000 -> generateJsonToClass<P4000>(json)
                P5000 -> generateJsonToClass<P5000>(json)
                P6000 -> generateJsonToClass<P6000>(json)
                P7000 -> generateJsonToClass<P7000>(json)
                P8000 -> generateJsonToClass<P8000>(json)
                P9000 -> generateJsonToClass<P9000>(json)
                P10000 -> generateJsonToClass<P10000>(json)
                P15000 -> generateJsonToClass<P15000>(json)
                R005 -> generateJsonToClass<R005>(json)
                X005 -> generateJsonToClass<X005>(json)
                X010 -> generateJsonToClass<X010>(json)
                else -> sed
            }
        }

    }

    @JsonIgnore
    fun toSkipEmptyString() : String = mapAnyToJson(this, true)

    @JsonIgnore
    override fun toString() : String = this.toJson()

}