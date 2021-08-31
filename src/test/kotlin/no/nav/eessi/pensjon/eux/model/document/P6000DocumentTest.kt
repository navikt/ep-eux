package no.nav.eessi.pensjon.eux.model.document

import no.nav.eessi.pensjon.eux.model.sed.SedType
import no.nav.eessi.pensjon.utils.mapAnyToJson
import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.typeRefs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class P6000DocumentTest {

    @Test
    fun mapToJsonP6000Document() {
        val json = mapAnyToJson(expectedP6000doc())
        Assertions.assertEquals(expectedJson(), json)

    }

    @Test
    fun mapFromJsonP6000Document() {
        val p6000doc: P6000Dokument = mapJsonToAny(expectedJson(), typeRefs())

        Assertions.assertEquals(expectedP6000doc(), p6000doc)
    }


    fun expectedP6000doc() : P6000Dokument {
        return P6000Dokument(
            type = SedType.P6000,
            bucid = "1213213",
            documentID = "123123a21312ad23123asad23123",
            fraLand = "NO",
            sisteVersjon = "1",
            pdfUrl = "url",
            sistMottatt = LocalDate.of(2020, 10, 12),
            retning = Retning.IN
        )

    }

    fun expectedJson(): String {
        return """
            {
              "type" : "P6000",
              "bucid" : "1213213",
              "documentID" : "123123a21312ad23123asad23123",
              "fraLand" : "NO",
              "sisteVersjon" : "1",
              "pdfUrl" : "url",
              "sistMottatt" : "2020-10-12",
              "retning" : "IN"
            }
        """.trimIndent()
    }

}

