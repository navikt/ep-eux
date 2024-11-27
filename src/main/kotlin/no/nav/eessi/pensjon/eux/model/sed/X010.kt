package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

@JsonIgnoreProperties(ignoreUnknown = true)
class X010(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_X010,
    @JsonProperty("nav")
    val xnav: XNav? = null
): SED(type)