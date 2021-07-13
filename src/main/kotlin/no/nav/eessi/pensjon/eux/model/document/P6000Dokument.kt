package no.nav.eessi.pensjon.eux.model.document

import no.nav.eessi.pensjon.eux.model.sed.SedType

data class P6000Dokument(
    val type: SedType,
    val bucid: String,
    val documentID: String,
    val fraLand: String?,
    val sisteVersjon: String?
)
