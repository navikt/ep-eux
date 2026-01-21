package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.JsonValue

class H002(
    @JsonProperty("sed")
    override var type: SedType,
    @JsonProperty("nav")
    var navH: NavH? = null,
) : SED(type)


@JsonIgnoreProperties(ignoreUnknown = true)
data class NavH(
    val eessisak: List<EessisakItem>? = null,
    @JsonProperty("bruker")
    val bruker: BrukerH? = null,
    val ektefelle: Ektefelle? = null,
    val barn: List<BarnItem>? = null, //pkt 6 og 8
    val verge: Verge? = null,
    val krav: Krav? = null,

    //P10000 hvordan få denne til å bli val?
    val annenperson: Bruker? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BrukerH(
    val mor: Foreldre? = null,
    val far: Foreldre? = null,
    val person: Person? = null,
    //@JsonProperty("bruker")
    val adresse: List<Adresse?> = emptyList(),
    val arbeidsforhold: List<ArbeidsforholdItem>? = null,
    val bank: Bank? = null,
)

