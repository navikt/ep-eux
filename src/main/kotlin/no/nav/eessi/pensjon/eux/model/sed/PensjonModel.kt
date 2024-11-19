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
enum class Betalingshyppighet {
	aarlig,
	kvartalsvis,
	maaned_12_per_aar,
	maaned_13_per_aar,
	maaned_14_per_aar,
	ukentlig,
	annet;
}

enum class YtelseType(@JsonValue val kode: String) {
	fortsatt_lønnsutbetaling_ved_sykdom("01"),
	sykepenger_ved_arbeidsuførhet("02"),
	kortsiktig_kontantytelse_ved_arbeidsulykke_eller_yrkessykdom("03"),
	rehabiliteringspenger("04"),
	familieytelse("05"),
	dagpenger("06"),
	førtidspensjon_før("07"),
	uførepensjon("08"),
	førtidspensjon_tidlig("09"),
	alderspensjon("10"),
	etterlattepensjon("11"),
	pensjon_pga_arbeidsulykke_eller_yrkessykdom("12"),
	pensjonslignende_ytelse_som_utbetales_under_obligatorisk_trafikkforsikring("13"),
	andre_ytelse("14");
}

enum class StatusType {
	søkt,
	innvilget,
	avslått,
	foreløpig;
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

