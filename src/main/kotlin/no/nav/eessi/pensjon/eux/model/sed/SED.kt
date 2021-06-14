package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.typeRefs

@JsonIgnoreProperties(ignoreUnknown = true)
interface SED {

    val type: SedType
    val sedGVer: String
    val sedVer: String
    val nav: Nav?
    val pensjon: IPensjon?

    companion object {
        inline fun <reified T : SED> fromJsonToClass(json: String): T {
           return mapJsonToAny(json, typeRefs())
        }

        fun fromJson(json: String): SED {
            val sed = json.let { mapJsonToAny(it, typeRefs<GenericSED>()) }
            return when(sed.type) {
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

        @JsonIgnore
        override fun toString(): String {
            return this.toJson()
        }

    }

}

inline fun <reified T : SED> SED.toClass(): T {
    return this as T
}