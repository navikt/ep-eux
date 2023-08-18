package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P2000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P2000,
    override val nav: Nav? = null,
    override val pensjon: Pensjon?
) : SED(type, nav = nav, pensjon = pensjon)

