package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


data class P12000Pensjon(
    val pensjoninfo:  List<Pensjoninfo>? = null,
    override val gjenlevende: Bruker? = null,
) : Pensjon()

//kap.5
@JsonIgnoreProperties(ignoreUnknown = true)
data class Pensjoninfo(
    val betalingsdetaljer: Betalingsdetaljer? = null,
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