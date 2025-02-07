package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class P8000Pensjon(
    val gjenlevende: Bruker? = null,
    val anmodning: AnmodningOmTilleggsInfo? = null,
    val ytterligeinformasjon: String? = null,
    val vedlegg: List<String> ? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AnmodningOmTilleggsInfo(
    val referanseTilPerson: String? = null,
    val seder: List<Seder>? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
class Seder {
    val sendFolgendeSEDer: List<String>? = null
}
