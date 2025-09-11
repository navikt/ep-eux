package no.nav.eessi.pensjon.eux.model.sed

class P6000Pensjon(
    override val gjenlevende: Bruker? = null,
    val reduksjon: List<ReduksjonItem>? = null,
    val vedtak: List<VedtakItem>? = null,
    val sak: Sak? = null,
    val tilleggsinformasjon: Tilleggsinformasjon? = null,
    val ytterligeinformasjon: String? = null,
    override val kravDato: Krav? = null
) : Pensjon()
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
    val artikkel48: String? = null,    // 6.6 i P6000
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
    val artikkel54: String? = null,  // 5.1.4 i P6000
    val reduksjon: List<ReduksjonItem>? = null,
    val kravtype: List<KravtypeItem>? = null,
)

