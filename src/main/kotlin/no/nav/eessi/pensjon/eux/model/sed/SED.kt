package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
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
    open var sedVer: String? = "1",
    open var nav: Nav? = null, // TODO Mutable
    open var pensjon: Pensjon? = null, // TODO Mutable
) {
    companion object {
        @JvmStatic
        fun fromJson(sed: String): SED {
            return mapJsonToAny(sed, typeRefs(), true)
        }

        fun fromJsonToConcrete(json: String): SED {
            val sed =  mapJsonToAny(json, typeRefs<SED>())

            return when(sed.type) {
                SedType.P2000 -> mapJsonToAny(json, typeRefs<P2000>())
                SedType.P2200 -> mapJsonToAny(json, typeRefs<P2200>())
                SedType.P4000 -> mapJsonToAny(json, typeRefs<P4000>())
                SedType.P5000 -> mapJsonToAny(json, typeRefs<P5000>())
                SedType.P6000 -> mapJsonToAny(json, typeRefs<P6000>())
                SedType.P7000 -> mapJsonToAny(json, typeRefs<P7000>())
                SedType.P8000 -> mapJsonToAny(json, typeRefs<P8000>())
                SedType.P10000 -> mapJsonToAny(json, typeRefs<P10000>())
                SedType.P15000 -> mapJsonToAny(json, typeRefs<P15000>())
                SedType.X005 -> mapJsonToAny(json, typeRefs<X005>())
                SedType.R005 -> mapJsonToAny(json, typeRefs<R005>())
                else -> sed
            }
        }

    }

    @JsonIgnore
    override fun toString() : String = this.toJson()

}