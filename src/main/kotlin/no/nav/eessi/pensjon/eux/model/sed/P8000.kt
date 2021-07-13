package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P8000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P8000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p8000Pensjon: P8000Pensjon?
) : SED(type, nav = nav)