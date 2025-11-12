package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.Avsender
import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.eux.model.document.Retning
import no.nav.eessi.pensjon.eux.model.sed.KravType.GJENLEV
import no.nav.eessi.pensjon.eux.model.sed.KravType.UFOREP

class P6000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P6000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P6000Pensjon?,
    @JsonIgnore
    var avsender: Avsender? = null
) : SED(type, nav = nav), GjenlevPensjon, UforePensjon {
    override fun hasGjenlevPensjonType(): Boolean {
        return pensjon?.vedtak?.firstOrNull()?.type == "20"
    }

    override fun hasUforePensjonType(): Boolean {
        return pensjon?.vedtak?.firstOrNull()?.type == "30"
    }
}