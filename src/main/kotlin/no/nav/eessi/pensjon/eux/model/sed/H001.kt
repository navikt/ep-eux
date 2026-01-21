package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class H001(
    @JsonProperty("sed")
    override var type: SedType,
    @JsonProperty("nav")
    var navH: NavH? = null,
) : SED(type)
