package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P15000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P15000,
    override val sedGVer: String,
    override val sedVer: String,
    override val nav: Nav? = null,
    override val pensjon: P15000Pensjon? = null
): SED