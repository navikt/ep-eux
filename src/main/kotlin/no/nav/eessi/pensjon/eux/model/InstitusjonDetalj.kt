package no.nav.eessi.pensjon.eux.model

class InstitusjonDetalj (
    val landkode: String,
    val akronym: String,
    val navn: String,
    val id: String,
    val tilegnetBucs: List<TilegnetBucsItem>
)