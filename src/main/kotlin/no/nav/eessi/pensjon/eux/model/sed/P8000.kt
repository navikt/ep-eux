package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P8000(
    @JsonProperty("sed")
    override var type: SedType = SedType.P8000,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
    @JsonProperty("pensjon")
    val p8000Pensjon: P8000Pensjon?
) : SED(type, sedGVer, sedVer, nav)