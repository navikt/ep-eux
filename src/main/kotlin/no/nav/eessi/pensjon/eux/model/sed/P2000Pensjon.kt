package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
class MeldingOmPensjonP2000(
    val melding: String?,
    val pensjon: P2000Pensjon
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class P2000Pensjon(
    val bruker: Bruker? = null,

    //P2000
    val angitidligstdato: String? = null,
    val utsettelse: List<Utsettelse>? = null,

    //P2XXX
    val ytelser: List<YtelserItem>? = null,
    val forespurtstartdato: String? = null,

    val vedtak: List<VedtakItem>? = null,
    val vedlegg: List<String> ? = null,
    val kravDato: Krav? = null, //kravDato pkt. 9.1 P2000

    val etterspurtedokumenter: String? = null,
    val ytterligeinformasjon: String? = null,
    val trekkgrunnlag: List<String> ?= null,
    val mottaker: List<String> ?= null
)
