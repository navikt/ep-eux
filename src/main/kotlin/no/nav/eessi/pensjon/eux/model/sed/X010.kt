package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class X010(
    @JsonProperty("sed")
    override val type: SedType = SedType.X010,
    @JsonProperty("nav")
    val xnav: XNav? = null
): SED(type)