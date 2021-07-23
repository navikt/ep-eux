package no.nav.eessi.pensjon.eux.model.document

import com.fasterxml.jackson.annotation.JsonFormat
import no.nav.eessi.pensjon.eux.model.sed.SedType
import java.time.LocalDate

data class P6000Dokument(
    val type: SedType,
    val bucid: String,
    val documentID: String,
    val fraLand: String,
    val sisteVersjon: String,
    val pdfUrl: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "no_NO")
    val sistMottatt: LocalDate
)
