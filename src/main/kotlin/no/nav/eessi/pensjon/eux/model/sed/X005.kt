package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class X005(
    @JsonProperty("sed")
    override var type: SedType = SedType.X005,
    override val sedGVer: String,
    override val sedVer: String,
    override val nav: Nav?,
    override val pensjon: Pensjon?
) : SED