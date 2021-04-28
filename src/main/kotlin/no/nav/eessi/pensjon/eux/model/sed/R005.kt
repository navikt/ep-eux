package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class R005(
    @JsonProperty("sed")
    override var type: SedType = SedType.R005,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
    @JsonProperty("pensjon")
    val r005Pensjon: R005Pensjon,
    val tilbakekreving: Tilbakekreving? = null

) : SED(type, sedGVer, sedVer, nav)