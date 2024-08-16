package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P12000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P12000,
    override val nav: Nav? = null,
    override val pensjon: P12000Pensjon?
) : SED(type, nav = nav)


fun P12000.hasUforePensjonType(): Boolean {
    return this.pensjon?.pensjoninfo
        ?.firstOrNull()
        ?.betalingsdetaljer
        ?.pensjonstype == "02"
}
