package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class H02x(
    @JsonProperty("sed")
    override var type: SedType,
    override var nav: Nav? = null,
) : SED(type, nav = nav)