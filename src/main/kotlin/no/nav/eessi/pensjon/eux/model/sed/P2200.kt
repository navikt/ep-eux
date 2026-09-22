package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

class P2200(
    @JsonProperty("sed")
    override val type: SedType = SedType.P2200,
    @JsonProperty("nav")
    var navP2200: NavP2200? = null,
    override val pensjon: P2200Pensjon?
) : SED(type, pensjon = pensjon) {
    fun allePersonerP2200(): List<Person> =
        listOf(
            navP2200?.bruker?.person,
            navP2200?.ektefelle?.person,
            navP2200?.verge?.person,
            navP2200?.ektefelle?.person,
            pensjon?.gjenlevende?.person
        ).plus((navP2200?.barn?.map { it.person } ?: emptyList())
        ).filterNotNull().filter { it.pin != null }
}

