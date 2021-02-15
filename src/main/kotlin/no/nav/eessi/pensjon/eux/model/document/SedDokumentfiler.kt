package no.nav.eessi.pensjon.eux.model.document

import com.fasterxml.jackson.annotation.JsonCreator

data class SedDokumentfiler(
    val sed: SedVedlegg,
    val vedlegg: List<SedVedlegg>?
)

data class SedVedlegg(
    val filnavn: String?,
    val mimeType: MimeType?,
    val innhold: String
)

enum class MimeType(val type: String) {
    PDF("application/pdf"),
    PDFA("application/pdfa"),
    JPG("image/jpg"),
    JPEG("image/jpeg"),
    TIFF("image/tiff"),
    TIF("image/tif"),
    PNG("image/png");

    companion object {
        @JvmStatic
        @JsonCreator
        fun from(value: String): MimeType? = values().firstOrNull { it.type == value }
    }
}
