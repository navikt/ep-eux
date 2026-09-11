package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


data class P12000Pensjon(
    val pensjoninfo:  List<Pensjoninfo>? = null,
    val merinformasjon: MerInformasjonP12000? = null,
    val ytterligereInformasjon: String? = null,
    val foresporsel: ReferanseTilPerson? = null,
    val anmodning13000verdi:  String? = null,
    override val gjenlevende: Bruker? = null
) : Pensjon()

//kap.5
@JsonIgnoreProperties(ignoreUnknown = true)
data class Pensjoninfo(
    val betalingsdetaljer: Betalingsdetaljer? = null,
    val pensjonsavslag: OpphoringEllerAvslag? = null,
    val pensjonsopphoring: OpphoringEllerAvslag? = null,
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

data class OpphoringEllerAvslag(
    val begrunnelse: String? = null,
    val pensjonstype: String? = null
)

data class ReferanseTilPerson(
    val referanseTilPerson: String? = null
)

data class MerInformasjonP12000(
    val ytelser: List<Tilleggsytelserutbetalingitilleggtilpensjon>? = null
)

data class Tilleggsytelserutbetalingitilleggtilpensjon(
    val tilleggsytelserutbetalingitilleggtilpensjon: String? = null
)
