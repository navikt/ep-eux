package no.nav.eessi.pensjon.utils

import no.nav.eessi.pensjon.eux.model.sed.R005
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class JsonUtilsTest{

    @ParameterizedTest
    @CsvSource(
        "/sed/P2000-NAV.json",
        "/sed/P2200-NAV.json",
        "/sed/P4000-RINA.json",
        "/sed/P6000-NAV.json",
        "/sed/P6000-RINA.json")
    fun testValidateJSon(jsonFile : String){
        Assertions.assertTrue(validateJson(javaClass.getResource(jsonFile)!!.readText()))
    }

    @Test
    fun `Feil under json-mapping for R005 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/R005-INVALID.json").readText()

        assertThrows<JsonIllegalArgumentException> {
            mapJsonToAny<R005>(json, true)
        }
    }
}