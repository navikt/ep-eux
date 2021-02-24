package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Institusjon(
    val id: String,
    val navn: String? = null,
    val akronym: String? = null,
    val landkode: String,
    val tilegnetBucs: List<TilegnetBuc> = emptyList()
)

data class TilegnetBuc(
    val bucType: BucType?,
    val institusjonsrolle: String,
    val gyldigStartDato: String,
    val eessiklar: Boolean
)
