package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P15000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P15000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p15000Pensjon: P15000Pensjon?
) : SED(type, nav = nav)