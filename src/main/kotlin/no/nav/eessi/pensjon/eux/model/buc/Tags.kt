package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Tags(
    @JsonProperty("DMProcessId") val processId: Int? = null,
    @JsonProperty("Operation") val operation: Any? = null,
    @JsonProperty("category") val category: String? = null,
    @JsonProperty("type") val type: String? = null
)
