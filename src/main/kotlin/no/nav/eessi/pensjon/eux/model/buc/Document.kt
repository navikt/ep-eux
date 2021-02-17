package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.eessi.pensjon.eux.model.sed.SedType

@JsonIgnoreProperties(ignoreUnknown = true)
class Document(
    val id: String? = null,
    val name: Any? = null,
    val lastUpdate: String? = null,
    val creationDate: String? = null,
    val displayName: String? = null,
    val mimeType: String? = null,
    val type: SedType? = null,
    val direction: String? = null, // IN or OUT ?
    val creator: Creator? = null,
    val status: String? = null
)
