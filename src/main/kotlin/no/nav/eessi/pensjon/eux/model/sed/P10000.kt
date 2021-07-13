package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P10000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P10000,
    override val nav: Nav? = null
): SED(type, nav = nav)

