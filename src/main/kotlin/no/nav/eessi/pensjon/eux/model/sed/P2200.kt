package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P2200(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_P2200,
    override val nav: Nav? = null,
    override val pensjon: Pensjon?
) : SED(type, nav =  nav, pensjon = pensjon)