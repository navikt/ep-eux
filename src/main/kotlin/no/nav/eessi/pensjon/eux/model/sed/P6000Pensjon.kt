package no.nav.eessi.pensjon.eux.model.sed

class P6000Pensjon(
    val bruker: Bruker? = null,
    val gjenlevende: Bruker? = null,
    val reduksjon: List<ReduksjonItem>? = null,
    val vedtak: List<VedtakItem>? = null,
    val sak: Sak? = null,
    val tilleggsinformasjon: Tilleggsinformasjon? = null,
    val ytterligeinformasjon: String? = null,
    val kravDato: Krav? = null
)