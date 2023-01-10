package no.nav.eessi.pensjon.eux.model

import no.nav.eessi.pensjon.eux.model.buc.BucType
import no.nav.eessi.pensjon.personoppslag.Fodselsnummer

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
)