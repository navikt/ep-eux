package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

data class P5000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P5000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P5000Pensjon? = null
) : SED(type, nav = nav)

fun P5000.hasUforePensjonType() = this.pensjon?.medlemskapboarbeid?.enkeltkrav?.krav == "30"
fun P5000.hasGjenlevPensjonType() = this.pensjon?.medlemskapboarbeid?.enkeltkrav?.krav == "20"