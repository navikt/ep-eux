package no.nav.eessi.pensjon.eux.model.sed

class P6000Pensjon(
    val gjenlevende: Bruker? = null,
    val reduksjon: List<ReduksjonItem>? = null,
    val vedtak: List<VedtakItem>? = null,
    val sak: Sak? = null,
    val tilleggsinformasjon: Tilleggsinformasjon? = null,
    val ytterligeinformasjon: String? = null,
    val kravDato: Krav? = null
)
data class AndreinstitusjonerItem(
    val institusjonsid: String? = null,
    val institusjonsnavn: String? = null,
    val institusjonsadresse: String? = null,
    val postnummer: String? = null,
    val bygningsnavn: String? = null,
    val land: String? = null,
    val region: String? = null,
    val poststed: String? = null
)

data class Tilleggsinformasjon(
    val dato: String? = null,
    val andreinstitusjoner: List<AndreinstitusjonerItem>? = null,
    val artikkel48: String? = null,
    val opphoer: Opphoer? = null,
    val revurderingtidsfrist: String? = null,

    //2021.09.06 Legger inn grunnet mapping feil fra RINA
    val annen: AnnenItem? = null,
    val person: PersonAnnen? = null,
    val anneninformation: String? = null,
    val saksnummerAnnen: String ? = null,
    val saksnummer: String? = null
)

data class PersonAnnen(
    val pinannen: PinAnnen?  = null
)

data class PinAnnen(
    val identifikator: String? = null,
    val sektor: String? = null
)

data class DelvisstansItem(
    val indikator: String? = null,
    val utbetaling: UtbetalingItem? = null
)

data class UtbetalingItem(
    val beloepBrutto: String? = null,
    val begrunnelse: String? = null,
    val valuta: String? = null
)

//2021.09.06 Legger inn grunnet mapping feil fra RINA
data class AnnenItem(
    val institusjonsadresse: InstitusjonsadresseItem,
    val institusjonsid: String? = null,
    val institusjonsnavn: String? = null
)

data class InstitusjonsadresseItem(
    val land: String? = null,
)

data class Opphoer(
    val dato: String? = null,
    val annulleringdato: String? = null
)

data class Sak(
    val artikkel54: String? = null,
    val reduksjon: List<ReduksjonItem>? = null,
    val kravtype: List<KravtypeItem>? = null,
    val enkeltkrav: KravtypeItem? = null
)

