package no.nav.eessi.pensjon.eux.model.buc

class VersionsItem(
    val date: Any? = null,
    val id: String? = null,
    val user: CaseHandler? = null
)

class CaseHandler(
    val id: String? = null,
    val name: String? = null
)

class VersionsItemNoUser(
    val date: Any? = null,
    val id: String? = null
)
