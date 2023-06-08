package no.nav.eessi.pensjon.eux.model.document

import no.nav.eessi.pensjon.utils.mapAnyToJson
import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SedDokumentfilerTest{

    @Test
    fun mapToJson() {
        val sedToJson = mapAnyToJson(expectedSedDukumentFiler())
        val jsonToSed = mapJsonToAny<SedDokumentfiler>(sedToJson)
        assertEquals(jsonToSed.sed.filnavn, "Sak_163373_.pdf")
        assertEquals(jsonToSed.sed.mimeType, MimeType.PDF)
        assertEquals(jsonToSed.sed.mimeType?.type, "application/pdf")
        assertEquals(jsonToSed.sed.innhold, "JVBERi0xLjQKJeLj")
    }

    @Test
    fun mapFromJson() {
        val jsonToSed = mapJsonToAny<SedDokumentfiler>(expected())
        assertEquals(jsonToSed.sed.filnavn, "Sak_163373_.pdf")
        assertEquals(jsonToSed.sed.mimeType, MimeType.PDF)
        assertEquals(jsonToSed.sed.mimeType?.type, "application/pdf")
        assertEquals(jsonToSed.sed.innhold, "JVBERi0xLjQKJeLj")
    }

    fun expectedSedDukumentFiler() : SedDokumentfiler{
        return SedDokumentfiler(
            sed = SedVedlegg("Sak_163373_.pdf", MimeType.PDF, innhold = "JVBERi0xLjQKJeLj"),
            vedlegg = listOf(
                SedVedlegg("png.png", MimeType.PNG, innhold = "hEUgAAAAUAAAAFCAYAAACggg=="),
            )
        )
    }
    fun expected() : String{
        return """
            {
              "sed": {
                "filnavn": "Sak_163373_.pdf",
                "mimeType": "application/pdf",
                "innhold": "JVBERi0xLjQKJeLj"
              },
              "vedlegg": [
                {
                  "filnavn": "image/png",
                  "mimeType": "image/png",
                  "innhold": "hEUgAAAAUAAAAFCAYAAACggg=="
                }
              ]
            }
        """.trimIndent()
    }
}