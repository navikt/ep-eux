package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonProperty

class PoolGroup(
    @JsonProperty("DMProcessId") val dmProcessId: String? = null,
    @JsonProperty("DocumentId") val documentId: String? = null,
    @JsonProperty("Operation") val operation: String? = null,
    @JsonProperty("ParentDocId") val parentDocId: Any? = null,
    @JsonProperty("ParentType") val parentType: String? = null,
    @JsonProperty("Type") val type: String? = null,

    val activityInstanceId: Any? = null,
    val hasLocalClose: Boolean? = null
)