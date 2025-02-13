package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P10000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P10000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    override val pensjon: P10000Pensjon? = null,
    ): SED(type, nav = nav), GjenlevPensjon, UforePensjon {
    override fun hasGjenlevPensjonType(): Boolean {
        return this.pensjon?.merinformasjon?.ytelser?.firstOrNull()?.ytelsestype == "11"
    }

    override fun hasUforePensjonType(): Boolean {
        return this.pensjon?.merinformasjon?.ytelser?.firstOrNull()?.ytelsestype == "08"
    }
}