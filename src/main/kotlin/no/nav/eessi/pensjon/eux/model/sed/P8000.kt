package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P8000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P8000,
    override val sedGVer: String,
    override val sedVer: String,
    override val nav: Nav? = null,
    override val pensjon: P8000Pensjon? = null
) : SED