package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

data class P5000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P5000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p5000Pensjon: P5000Pensjon? = null
) : SED(type, nav = nav)