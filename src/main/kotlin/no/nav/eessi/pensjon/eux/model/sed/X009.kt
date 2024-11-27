package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class X009(
    @JsonProperty("sed")
    override val type: SedType = SedType.X009,
    @JsonProperty("nav")
    val xnav: XNav? = null
): SED(type)