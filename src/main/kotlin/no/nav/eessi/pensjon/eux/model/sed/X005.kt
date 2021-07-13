package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class X005(
    @JsonProperty("sed")
    override val type: SedType = SedType.X005,
    @JsonProperty("nav")
    val xnav: XNav? = null
): SED(type)