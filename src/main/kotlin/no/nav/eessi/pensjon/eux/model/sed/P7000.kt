package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P7000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P7000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P7000Pensjon?
) : SED(type, nav = nav)