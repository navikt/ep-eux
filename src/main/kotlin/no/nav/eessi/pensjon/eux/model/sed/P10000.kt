package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P10000(
    @JsonProperty("sed")
    override var type: SedType = SedType.P10000,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
) : SED(type, sedGVer, sedVer, nav)