package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


data class P12000Pensjon(
    val pensjoninfo:  Pensjoninfo? = null,
    val ytterligereInformasjon: String? = null,
    val foresporsel: ReferanseTilPerson? = null,
    val anmodning13000verdi:  String? = null,
    val gjenlevende: BrukerP12000? = null
)

//kap.5
@JsonIgnoreProperties(ignoreUnknown = true)
data class Pensjoninfo(
    val betalingsdetaljer: List<Betalingsdetaljer>? = null,
    val pensjonsavslag: List<OpphoringEllerAvslag>? = null,
    val pensjonsopphoring: List<OpphoringEllerAvslag>? = null,
    val tilleggsytelserutbetalingitilleggtilpensjon: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Betalingsdetaljer(
    val fradato: String? = null,
    val belop: String? = null,
    val effektueringsdato: String? = null,
    val annenutbetalingshyppighet: String? = null,
    val valuta: String? = null,
    val utbetalingshyppighet: String? = null,
    val pensjonstype: String? = null,  //5.1.1
    val basertpaa: String? = null,
    val bosattotal: String? = null,
    val arbeidstotal: String? = null,
    val betaldato: String? = null,
)

data class BrukerP12000(
    val mor: Foreldre? = null,
    val far: Foreldre? = null,
    val person: Person? = null,
    val adresse: Adresse? = null,
)

data class OpphoringEllerAvslag(
    val begrunnelse: String? = null,
    val pensjonstype: String? = null
)

data class ReferanseTilPerson(
    val referanseTilPerson: String? = null
)