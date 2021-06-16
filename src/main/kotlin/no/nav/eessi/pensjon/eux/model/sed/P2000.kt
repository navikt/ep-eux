package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

class P2000(
    @JsonProperty("sed")
    override var type: SedType = SedType.P2000,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
    override var pensjon: Pensjon?
) : SED(type, sedGVer, sedVer, nav, pensjon) {

    fun validerForKravinit() = (nav?.bruker?.person?.sivilstand != null && nav?.bruker?.person?.statsborgerskap != null)
}


