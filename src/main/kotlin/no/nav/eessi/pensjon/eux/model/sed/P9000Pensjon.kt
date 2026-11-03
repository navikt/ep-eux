package no.nav.eessi.pensjon.eux.model.sed

class P9000Pensjon(
    val merinformasjon: MerInformasjonP9000? = null,
    val tilleggsinformasjon: TilleggsinformasjonP9000? = null,
)

data class MerInformasjonP9000(
    val livdoedinfo: Livdoedinfo? = null,
    val aktivitetsinfo: List<Aktivitetsinfo>? = null,
    val bruker: Bruker? = null,
    val egenerklaering: Egenerklaering? = null,
    val infoarbeidsledig: List<Infoarbeidsledig>? = null,
    val yrkesaktiv: List<Yrkesaktiv>? = null,
    val infoinntekt: List<Infoinntekt>? = null,
    val infoskolegang: List<Infoskolegang>? = null,
    val ytelser: List<YtelserItemP9000>? = null
)

data class YtelserItemP9000(
    val ytelsesdatoer: YtelsesDatoer? = null,
    val anneninfoomytelsertekst: String? = null,
    val tilleggsytelserutbetalingitilleggtilpensjon: String? = null,
    val beloep: List<BeloepItem>? = null,
    val datotyper: DatoTyper? = null,
    val status: String? = null,
    val ytelsestype: String? = null,
    val sluttdatorettytelse: String? = null
)

data class YtelsesDatoer(
    val datotyper: DatoTyper? = null,
    val annenytelse: String? = null,
    val datoavslagpaaytelse: String? = null,
    val sluttdatorettytelse: String? = null
)

data class DatoTyper(
    val sluttdatoforstansiytelser: String? = null,
    val datoavslagpaaytelse: String? = null,
    val sluttdatoredusertytelse: String? = null,
    val datokravytelse: String? = null,
    val sluttdatoutbetaling: String? = null,
    val sluttdatorettytelse: String? = null,
    val startdatoredusertytelse: String? = null,
    val startdatoutbetaling: String? = null,
    val startdatoretttilytelser: String? = null,
    val startdatoforstansytelse: String? = null,
)
data class Infoskolegang(
    val typeskolegang: String? = null,
    val startdatoskolegang: String? = null,
    val merinfoomskolegang: String? = null,
    val sluttdatoskolegang: String? = null,
    val skolenavn: String? = null

)

data class Infoinntekt(
    val sluttdatomottakinntekt: String? = null,
    val inntektellerikke: String? = null,
    val inntektbelop: List<Inntektbelop>? = null,
    val ytterligereinfoinntekt: String? = null,
    val tilleggsytelserutbetalingeritilleggtilinntekt: String? = null,
    val startdatomottakinntekt: String? = null,
    val inntektskilde: String? = null
)

data class Inntektbelop(
    val betalingshyppighet: String? = null,
    val beloepgjeldendesiden: String? = null,
    val annenbetalingshyppighet: String? = null,
    val beloep: String? = null,
    val valuta: String? = null
)

data class Yrkesaktiv(
    val yrkesstatus: String? = null,
    val startdato: String?,
    val sluttdato: String?
)

data class Infoarbeidsledig(
    val startdato: String? = null,
    val sluttdato: String? = null,
    val arbeidsledig: String? = null
)

data class Aktivitetsinfo(
    val ansattype: String? = null,
    val antalltimer: String? = null,
    val sluttdato: String? = null,
    val sted: String? = null,
    val startdato: String? = null
)

data class Egenerklaering(
    val dato: String? = null,
    val egenerklaering: String? = null
)

data class Livdoedinfo(
    val region: String? = null,
    val land: String? = null,
    val by: String? = null,
    val lever: String? = null,
    val dodsdato: String? = null
)

data class TilleggsinformasjonP9000(
    val negativtsvar: Negativtsvar? = null,
    val yrkesaktivitet: Yrkesaktivitet? = null,
    val vedlegg: List<Vedlegg>? = null,
    val vedlegginfo: String? = null,
    val anneninformation: String? = null,
    val p8000: P8000DatoOgHenvisningPerson? = null,
    val bekreftelseSed: List<BekreftelseSed>? = null,
)

data class Vedlegg(
    val dokument: String? = null
)

data class Yrkesaktivitet(
    val tilleggsopplysning: String? = null,
    val ingenaktivtetinformasjon: String? = null
)

data class BekreftelseSed(
    val p8000ref: String? = null,
    val aarsak: String? = null,
    val info: String? = null,
    val grunn: String? = null,

)

data class Negativtsvar(
    val bekreftelseinformasjon: String? = null,
    val dokument: String? = null,
    val informasjon: String? = null,
    val sed: String? = null,
    val aarsakikkesendsed: String? = null,
    val aarsakgrunn: String? = null,
    val aarsakannen: String? = null,
    val sedsProvidedInMultipleType: String? = null,
    val annensed: String? = null
)
