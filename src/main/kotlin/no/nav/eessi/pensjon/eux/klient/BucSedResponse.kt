package no.nav.eessi.pensjon.eux.klient

/**
 * eux-rina-api returnerer documentId ved oppretting av SED
 * Vi legger ved caseId (rinasaksnr).
 *
 * Skal bare endres dersom responsen fra EUX endrer seg.
 */
data class BucSedResponse(
    val caseId: String,
    val documentId: String
)