package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue

class MeldingOmPensjon(
		val melding: String?,
		val pensjon: Pensjon
)

@JsonIgnoreProperties(ignoreUnknown = true)
open class Pensjon(
	open val gjenlevende: Bruker? = null, // Brukes fleres steder

		//P2000
	val angitidligstdato: String? = null,
	val utsettelse: List<Utsettelse>? = null,

		//P2XXX
	val ytelser: List<YtelserItem>? = null,
	val forespurtstartdato: String? = null,
	open val kravDato: Krav? = null, //kravDato pkt. 9.1 P2000

		//P3000
	val landspesifikk: Landspesifikk? = null
)

//P2000
data class Utsettelse(
	val institusjonsnavn: String? = null,
	val institusjonsid: String? = null,
	val land: String? = null,
	val institusjon: Institusjon? = null,
	val tildato: String? = null
)

//Institusjon
@JsonIgnoreProperties(ignoreUnknown = true)
data class Institusjon(
        val institusjonsid: String? = null,
        val institusjonsnavn: String? = null,
        val saksnummer: String? = null,
        val sektor: String? = null,
        val land: String? = null,
		val personNr: String? = null,
        val utstedelsesDato: String? = null,  //4.1.4.
        val startdatoPensjonsRettighet: String? = null  //4.1.5
)

//P2000 - P2200 - P7000
@JsonIgnoreProperties(ignoreUnknown = true)
data class YtelserItem(
	val totalbruttobeloeparbeidsbasert: String? = null,
	val institusjon: Institusjon? = null,
	val pin: PinItem? = null,
	val startdatoutbetaling: String? = null,
	val mottasbasertpaa: BasertPaa? = null,
	val ytelse: String? = null,
	val startdatoretttilytelse: String? = null,
	val sluttdatoUtbetaling: String? = null,
//	val beloep: String? = null,
	val beloep: List<BeloepItem>? = null,
	val status: StatusType? = null,
	val annenbetalingshyppighetytelse: String? = null,
	val totalbruttobeloepbostedsbasert: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BeloepItem(
        val betalingshyppighetytelse: Betalingshyppighet? = null,
        val valuta: String? = null,
        val beloep: String? = null,
		val beloepBrutto: String? = null,
        val gjeldendesiden: String? = null,
        val utbetalingshyppighetAnnen: String? = null
)

enum class BasertPaa(@JsonValue val kode: String) {
	basert_på_botid("01"),
	basert_på_arbeid("02"),
	annet("99")
}
enum class Betalingshyppighet(@JsonValue val kode: String) {
	aarlig("01"),
	kvartalsvis("02"),
	maaned_12_per_aar("03"),
	maaned_13_per_aar("04"),
	maaned_14_per_aar("05"),
	ukentlig("06"),
	annet("99");
}

enum class StatusType(@JsonValue val kode: String) {
	TIL_BEHANDLING("01"),
	INNV("02"),
	AVSL("03"),
	OPPHOER("");

}

data class KravtypeItem(
        val datoFrist: String? = null,
        val krav: String? = null
)

data class VedtakItem(
	val grunnlag: Grunnlag? = null,
	val virkningsdato: String? = null,
	val ukjent: Ukjent? = null,
	val type: String? = null,  //felt 4.1 Kontekst for overføring (Type pensjon)
	val resultat: String? = null,
	val beregning: List<BeregningItem>? = null,
	val avslagbegrunnelse: List<AvslagbegrunnelseItem>? = null,
	val basertPaa: BasertPaa? = null,
	val artikkel: String? = null,
	val basertPaaAnnen: String? = null,
	val delvisstans: DelvisstansItem? = null, //2021.09.06, lagt inn grunnet mapping
	val kjoeringsdato: String? = null,
	val begrunnelseAnnen: String ? = null
)

data class Ukjent(
        val beloepBrutto: BeloepBrutto? = null
)

data class ReduksjonItem(
	val type: String? = null,
	val virkningsdato: List<VirkningsdatoItem>? = null,
	val aarsak: Arsak? = null,
	val artikkeltype: String? = null
)

data class VirkningsdatoItem(
        val startdato: String? = null,
        val sluttdato: String? = null
)

data class Arsak(
        val inntektAnnen: String? = null,
        val annenytelseellerinntekt: String? = null
)

data class Grunnlag(
	val medlemskap: String? = null,
	val opptjening: Opptjening? = null,
	val framtidigtrygdetid: String? = null
)

data class Opptjening(
        val forsikredeAnnen: String? = null
)

data class AvslagbegrunnelseItem(
        val begrunnelse: String? = null,
        val annenbegrunnelse: String? = null
)

data class BeregningItem(
	val valuta: String? = null,
	val beloepBrutto: BeloepBrutto? = null,
	val beloepNetto: BeloepBrutto? = null,
	val periode: Periode? = null,
	val utbetalingshyppighet: String? = null,
	val utbetalingshyppighetAnnen: String? = null
)

data class BeloepBrutto(
        val ytelseskomponentTilleggspensjon: String? = null,
        val beloep: String? = null,
        val ytelseskomponentGrunnpensjon: String? = null,
        val ytelseskomponentAnnen: String? = null
)

data class Periode(
        val fom: String? = null,
        val tom: String? = null,
		val extra: String? = null
)

