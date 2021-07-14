package no.nav.eessi.pensjon.eux.model.sed


class P7000Pensjon(
    val bruker: Bruker? = null,
    val gjenlevende: Bruker? = null,
    val samletVedtak: SamletMeldingVedtak? = null,
)

data class SamletMeldingVedtak(
    val avslag: List<PensjonAvslagItem>? = null,
    val vedtaksammendrag: String? = null,
    val tildeltepensjoner: TildeltePensjoner? = null,
    val startdatoPensjonsRettighet: String? = null,  // 4.1.5
    val reduksjonsGrunn: String? = null    // 4.1.7
)

//kap 4.?Tildelte pensjoner
data class TildeltePensjoner(
    val pensjonType: String? = null, //4.1.2
    val vedtakPensjonType: String? = null, //4.1.1
    val tildeltePensjonerLand: String? = null,   //4.1.2.1.1.
    val addressatForRevurdering: String? = null,   //4.1.8.2.1.
    val institusjonPensjon: PensjonsInstitusjon? = null,
    val institusjon: Institusjon? = null
)

data class PensjonsInstitusjon(
    val sektor: String? = null
)

//kap.5??
data class PensjonAvslagItem(
    val pensjonType: String? = null,
    val begrunnelse: String? = null, //5.1
    val dato: String? = null,   //5.2
    val datoFrist: String? = null,
    val pin : PinItem? = null,
    val adresse: String? = null
)
