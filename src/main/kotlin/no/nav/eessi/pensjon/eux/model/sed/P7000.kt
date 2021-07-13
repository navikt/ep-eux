package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P7000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P7000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p7000Pensjon: P7000Pensjon?
) : SED(type, nav = nav)