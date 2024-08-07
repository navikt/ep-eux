package no.nav.eessi.pensjon.eux.model.sed


class P12000Pensjon(
    val pensjoninfo:  List<Pensjoninfo>? = null
)

//kap.5
data class Pensjoninfo(
    val betalingsdetaljer: Betalingsdetaljer? = null,
)

data class Betalingsdetaljer(
    val fradato: String? = null,
    val belop: String? = null,
    val effektueringsdato: String? = null,
    val annenutbetalingshyppighet: String? = null,
    val valuta: String? = null,
    val utbetalingshyppighet: String? = null,
    var pensjonstype: String? = null,  //5.1.1
    val basertpaa: String? = null,
    val bosattotal: String? = null,
    val arbeidstotal: String? = null,
    val betaldato: String? = null,
)