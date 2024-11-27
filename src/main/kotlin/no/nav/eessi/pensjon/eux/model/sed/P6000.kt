package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P6000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P6000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P6000Pensjon?
) : SED(type, nav = nav)

fun P6000.hasUforePensjonType() = this.pensjon?.vedtak?.firstOrNull()?.type == "30"
fun P6000.hasGjenlevPensjonType() = this.pensjon?.vedtak?.firstOrNull()?.type == "20"