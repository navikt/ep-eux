package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P2000(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_P2000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p2000pensjon: P2000Pensjon? = null
) : SED(type, nav = nav)

