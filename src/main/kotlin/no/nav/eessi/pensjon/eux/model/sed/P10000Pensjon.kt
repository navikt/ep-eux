package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


data class P10000Pensjon(
    override val gjenlevende: Bruker? = null,
    val merinformasjon:  MerInformasjon? = null
    ) : Pensjon()

//kap.5
@JsonIgnoreProperties(ignoreUnknown = true)
data class MerInformasjon(
    val ytelser: List<YtelseItem>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class YtelseItem(
    val ytelsestype: String? = null // 14.1.2. Følgende ytelse
)