package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.eux.model.sed.KravType.GJENLEV
import no.nav.eessi.pensjon.eux.model.sed.KravType.UFOREP

class P15000(
    @JsonProperty("sed")
    override val type: SedType = SedType.SEDTYPE_P15000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P15000Pensjon?
) : SED(type, nav = nav)

fun P15000.hasUforePensjonType() = this.nav?.krav?.type == UFOREP
fun P15000.hasGjenlevendePensjonType() = this.nav?.krav?.type == GJENLEV