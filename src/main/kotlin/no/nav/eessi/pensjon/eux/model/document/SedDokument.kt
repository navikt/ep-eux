package no.nav.eessi.pensjon.eux.model.document

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class SedDokument(
    val sed: EuxDokument,
    val vedlegg: List<EuxDokument>?
) {
    fun toJson(): String = jacksonObjectMapper().writeValueAsString(this)
}

class EuxDokument(
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
