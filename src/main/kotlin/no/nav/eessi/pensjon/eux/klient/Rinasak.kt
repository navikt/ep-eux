package no.nav.eessi.pensjon.eux.klient

/**
 * Respons fra eux-rina-api/cpi/rinasaker
 * Skal bare endres dersom responsen fra EUX endrer seg.
 */
class Rinasak(
    val id: String? = null,
    val processDefinitionId: String? = null,
    val traits: Traits? = null,
    val applicationRoleId: String? = null,
    val properties: Properties? = null,
    val status: String? = null
)