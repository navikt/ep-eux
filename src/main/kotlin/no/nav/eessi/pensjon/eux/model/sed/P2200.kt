package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P2200(
    @JsonProperty("sed")
    override val type: SedType = SedType.P2200,
    override val sedGVer: String = "4",
    override val sedVer: String = "1",
    override val nav: Nav? = null,
    override val pensjon: Pensjon?
) : SED