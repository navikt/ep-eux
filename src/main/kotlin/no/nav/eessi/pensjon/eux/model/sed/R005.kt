package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class R005(
    @JsonProperty("sed")
    override var type: SedType = SedType.R005,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
    override var pensjon: Pensjon?,
    val tilbakekreving: Tilbakekreving? = null

) : SED(type, sedGVer, sedVer, nav, pensjon)