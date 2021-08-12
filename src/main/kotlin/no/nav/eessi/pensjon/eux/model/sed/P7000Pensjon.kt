package no.nav.eessi.pensjon.eux.model.sed


class P7000Pensjon(
    val bruker: Bruker? = null,
    val gjenlevende: Bruker? = null,
    val samletVedtak: SamletMeldingVedtak? = null,
)

data class SamletMeldingVedtak(
    val vedtaksammendrag: String? = null,

   val avslag: List<PensjonAvslagItem>? = null,

   val tildeltepensjoner: List<TildeltPensjonItem>? = null
)

//kap 4.?Tildelte pensjoner
data class TildeltPensjonItem(
    val pensjonType: String? = null, //4.1.2
    val ytelser: List<YtelserItem>? = null, //4
    val vedtakPensjonType: String? = null, //4.1.1
    val tildeltePensjonerLand: String? = null,   //4.1.2.1.1.
    val adressatForRevurdering: List<AdressatForRevurderingItem>? = null,   //4.1.8.2.1.
    val institusjonPensjon: PensjonsInstitusjon? = null,
    val institusjon: Institusjon? = null,
    val reduksjonsGrunn: String? = null,    // 4.1.7
    val startdatoPensjonsRettighet: String? = null,  // 4.1.5
    val datoFrist: String? = null, // timeLimitsForReview
    val revurderingtidsfrist: String? = null //  dateWhenDecisionIssuedStatedDecision
)

data class AdressatForRevurderingItem(
    val adressatforrevurdering: String? = null
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
