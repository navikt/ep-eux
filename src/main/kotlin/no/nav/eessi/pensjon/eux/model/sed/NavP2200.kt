package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

class NavP2200 (
    val eessisak: List<EessisakItem>? = null,
    val bruker: BrukerP2200? = null,
    val ektefelle: EktefelleP2200? = null,
    val barn: List<BarnItem>? = null,
    val verge: Verge? = null,
    val krav: Krav? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BrukerP2200(
    val mor: Foreldre? = null,
    val far: Foreldre? = null,
    val person: Person? = null,
    val adresse: Adresse? = null,
    val arbeidsforhold: List<ArbeidsforholdItem>? = null,
    val bank: Bank? = null,
    val uforhet: Uforhet? = null,
)

data class Uforhet(
    val arbeidsUlykke: String? = null,
    val startDatoPensjon: String? = null,
    val startdatoLege: String? = null,
    val militartjenesteUlykke: String? = null,
    val ansvarligTredjepart: String? = null,
    val bevisstforsaketSoker: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class EktefelleP2200(
    val person: Person? = null,
    val type: String? = null,
    val far: Foreldre? = null,
    val mor: Foreldre? = null
    )
