package no.nav.eessi.pensjon.eux.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BucInfo(
    val sakTittel: String? = null,
    val sakType: String? = null,
    val internasjonalSakId: String? = null,
    val erSakseier: String? = null,
    val motparter: List<Motparter>? = null,
    val sistEndretDato: String? = null,
    val opprettetDato: String? = null,
)
