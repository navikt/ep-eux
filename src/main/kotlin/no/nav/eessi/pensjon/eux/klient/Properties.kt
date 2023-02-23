package no.nav.eessi.pensjon.eux.klient

/**
 * Del av respons fra eux-rina-api/cpi/rinasaker
 * Skal bare endres dersom responsen fra EUX endrer seg.
 */
class Properties(
    val importance: String? = null,
    val criticality: String? = null
)