package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P9000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P9000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p9000Pensjon: P9000Pensjon?
) : SED(type, nav = nav)

