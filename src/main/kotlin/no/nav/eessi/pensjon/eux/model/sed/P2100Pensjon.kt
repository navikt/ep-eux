package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class P2100Pensjon(
    override val gjenlevende: Bruker? = null,
    override val ytelser: List<YtelserItem>? = null,
    override val forespurtstartdato: String? = null,
    val bruker: Bruker? = null,
    val vedtak: List<P2100Vedtak>? = null,
    val vedlegg: List<String>? = null,
    val vedleggandre: String? = null,
    val etterspurtedokumenter: String? = null,
    val ytterligeinformasjon: String? = null,
    val antallSokereKjent: String? = null
) : Pensjon()

data class P2100Vedtak(
    val mottaker: List<String>? = null,
    val trekkgrunnlag: List<String>? = null
)
