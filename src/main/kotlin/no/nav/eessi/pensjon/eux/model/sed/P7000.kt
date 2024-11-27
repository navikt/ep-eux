package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P7000(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_P7000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P7000Pensjon?
) : SED(type, nav = nav)

fun P7000?.hasUforePensjonType() = this?.pensjon?.samletVedtak?.tildeltepensjoner?.firstOrNull()?.pensjonType == "02"
fun P7000?.hasGjenlevPensjonType() = this?.pensjon?.samletVedtak?.tildeltepensjoner?.firstOrNull()?.pensjonType == "03"