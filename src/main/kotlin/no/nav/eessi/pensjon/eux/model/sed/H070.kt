package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class H070(
    @JsonProperty("sed")
    override var type: SedType,
    @JsonProperty("nav")
    var hnav: HNav? = null,
) : SED(type)