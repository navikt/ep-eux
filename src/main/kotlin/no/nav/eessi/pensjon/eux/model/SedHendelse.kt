package no.nav.eessi.pensjon.eux.model

import no.nav.eessi.pensjon.shared.person.Fodselsnummer
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.eessi.pensjon.utils.mapJsonToAny

@JsonIgnoreProperties(ignoreUnknown = true)
data class SedHendelse(
    val id: Long? = 0,
    val sedId: String? = null,
    val sektorKode: String,
    val bucType: BucType? = null,
    val rinaSakId: String,
    val avsenderId: String? = null,
    val avsenderNavn: String? = null,
    val avsenderLand: String? = null,
    val mottakerId: String? = null,
    val mottakerNavn: String? = null,
    val mottakerLand: String? = null,
    val rinaDokumentId: String,
    val rinaDokumentVersjon: String,
    val sedType: SedType? = null,
    val navBruker: Fodselsnummer? = null
) {
    companion object {

        fun fromJson(json: String): SedHendelse = mapJsonToAny(json, readUnknownAsNull = true)
    }
}