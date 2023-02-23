package no.nav.eessi.pensjon.eux.klient

/**
 * Del av respons fra eux-rina-api/cpi/rinasaker
 * Skal bare endres dersom responsen fra EUX endrer seg.
 */
class Traits(
    val birthday: String? = null,
    val localPin: String? = null,
    val surname: String? = null,
    val caseId: String? = null,
    val name: String? = null,
    val flowType: String? = null,
    val status: String? = null
)