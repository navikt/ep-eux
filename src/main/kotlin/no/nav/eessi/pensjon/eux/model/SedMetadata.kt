package no.nav.eessi.pensjon.eux.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SedMetadata(
    val sedTittel: String? = null,
    val sedType: String? = null,
    val sedId: String? = null,
    val status: String? = null,
    val avsender: Avsender? = null,
    val sistEndretDato: String? = null,
    val opprettetDato: String? = null,
)


data class Avsender(
    val formatertNavn: String? = null,
    val id: String? = null,
    val navn: String? = null,
    val landkode: String? = null,  //landkode er 3-bokstavs
    val land: String? = null       //land er 2-bokstavs
){
    companion object {
        fun erNorsk(avsender: Avsender?): Boolean = avsender?.land == "NO"
    }
}