package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Properties(
    val additionalProp1: String? = null,
    val additionalProp2: String? = null,
    val additionalProp3: String? = null
)