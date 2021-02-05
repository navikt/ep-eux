package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnore

class Tags(
    val DMProcessId: String? = null,
    val operation: Any? = null,
    val category: String? = null,
    val type: String? = null,
    @JsonIgnore
    val dmprocessId: Any? = null
)