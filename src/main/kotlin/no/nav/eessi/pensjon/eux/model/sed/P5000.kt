package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

data class P5000(
    @JsonProperty("sed")
    override var type: SedType = SedType.P5000,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
    @JsonProperty("pensjon")
    val p5000Pensjon: P5000Pensjon? = null
) : SED(type, sedGVer, sedVer, nav)