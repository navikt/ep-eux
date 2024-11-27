package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

@JsonIgnoreProperties(ignoreUnknown = true)
class X005(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_X005,
    @JsonProperty("nav")
    val xnav: XNav? = null
): SED(type)