package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class HNav(
    val bruker: HBruker? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class HBruker(
    val doedsfall: Doedsfall? = null,
    val person: Person,

)

data class Doedsfall(
    val doedsdato: LocalDate? = null,
)
