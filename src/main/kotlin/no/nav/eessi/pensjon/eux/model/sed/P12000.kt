package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P12000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P12000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p12000Pensjon: P12000Pensjon?
) : SED(type, nav = nav)