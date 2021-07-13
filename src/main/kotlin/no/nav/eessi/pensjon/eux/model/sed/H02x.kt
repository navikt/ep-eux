package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class H02x(
    @JsonProperty("sed")
    override var type: SedType,
    override var nav: Nav? = null,
) : SED(type, nav = nav)