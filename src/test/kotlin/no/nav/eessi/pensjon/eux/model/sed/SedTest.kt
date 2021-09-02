package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.JsonIllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SedTest {

    @Test
    fun `Feil under json-mapping skal kaste en exception`() {
        val json = javaClass.getResource("/sed/P2000-INVALID.json").readText()
        val exception = assertThrows<Exception> {
            SED.fromJsonToConcrete(json)
        }
        assertEquals(
            "Feilet ved konvertering av jsonformat Unexpected character ('\"' (code 34)): was expecting comma to separate Object entries\n" +
                    " at [Source: (String)\"{\n" +
                    "  \"sed\": \"P2000\",\n" +
                    "  \"sedGVer\": \"4\",\n" +
                    "  \"sedVer\": \"2\",\n" +
                    "  \"", exception.message)
    }

    @Test
    fun `Feil under json-mapping for R005 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/R005-INVALID.json").readText()
        val exception = assertThrows<JsonIllegalArgumentException> {
            SED.fromJsonToConcrete(json)
        }
        assertEquals(
            "Feilet ved mapping av jsonformat Unrecognized field \"personFEIL\" (class no.", exception.message)
    }
}