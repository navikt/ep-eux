package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
@JsonIgnoreProperties(ignoreUnknown = true)
data class P2000Pensjon(
    val bruker: Bruker? = null,

    //P2000
    val angitidligstdato: String? = null,
    val utsettelse: List<Utsettelse>? = null,

    val vedtak: List<VedtakItem>? = null,
    val vedlegg: List<String> ? = null,

    val etterspurtedokumenter: String? = null,
    val ytterligeinformasjon: String? = null,

    override val ytelser: List<YtelserItem>? = null,
    override val forespurtstartdato: String? = null,
    override val kravDato: Krav? = null, //kravDato pkt. 9.1 P2000

    //P3000
    val landspesifikk: Landspesifikk? = null
) : PensjonBasis(ytelser, forespurtstartdato, kravDato)


//P2000
    data class Utsettelse(
    val institusjonsnavn: String? = null,
    val institusjonsid: String? = null,
    val land: String? = null,
    val institusjon: Institusjon? = null,
    val tildato: String? = null
)
