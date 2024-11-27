package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P2100(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_P2100,
    override val nav: Nav? = null,
    override val pensjon: Pensjon?
) : SED(type = SedType.SEDTYPE_P2100, nav =  nav, pensjon = pensjon)

