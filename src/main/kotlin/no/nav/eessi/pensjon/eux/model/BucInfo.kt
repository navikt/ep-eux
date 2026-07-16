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
) {
    fun normalisert() = copy(
        sakTittel = sakTittel?.trim(),
        sakType = sakType?.trim(),
        internasjonalSakId = internasjonalSakId?.trim(),
        erSakseier = erSakseier?.trim(),
        motparter = motparter?.map { it.normalisert() },
        sistEndretDato = sistEndretDato?.trim(),
        opprettetDato = opprettetDato?.trim(),
    )
}