package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P8000Frontend(
    @JsonProperty("sed")
    type: SedType = SedType.P8000,
    nav: Nav? = null,
    @JsonProperty("pensjon")
    p8000Pensjon: P8000Pensjon?,
    var options: Map<String, Any>? = null
) : P8000(type, nav, p8000Pensjon) {

}