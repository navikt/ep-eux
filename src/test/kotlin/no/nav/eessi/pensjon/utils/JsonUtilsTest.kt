package no.nav.eessi.pensjon.utils

import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.eessi.pensjon.eux.model.sed.R005
import org.jetbrains.annotations.TestOnly
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


    @Test
    fun `Gitt en feilmelding når kall til errorBody så generer gyldig json`() {
        val feilmelding = "En feilmelding"
        val body = errorBody(feilmelding)

        Assertions.assertNotNull(body)
        Assertions.assertNotEquals("", body)
        Assertions.assertTrue(isValidJson(body))
    }

    @Test
    fun `Gitt en feilmelding med feilmeldingstekst når kall til errorBody så må body inneholde error og success false`() {
        val feilmelding = "En feilmelding"
        val body = errorBody(feilmelding)

        val mapper = ObjectMapper()

        val tree = mapper.readTree(body)
        Assertions.assertEquals("En feilmelding", tree.get("error").textValue())
        Assertions.assertEquals(false, tree.get("success").booleanValue())
    }

    @Test
    fun `Gitt en feilmelding med tom feilmeldingstekst når kall til errorBody så må body inneholde error og success false`() {
        val feilmelding = ""
        val body = errorBody(feilmelding)

        val mapper = ObjectMapper()

        val tree = mapper.readTree(body)
        Assertions.assertEquals("", tree.get("error").textValue())
        Assertions.assertEquals(false, tree.get("success").booleanValue())
    }

    @Test
    fun `Gitt et kall til successBody så generer gyldig json`() {
        val body = successBody()

        Assertions.assertNotNull(body)
        Assertions.assertNotEquals("", body)
        Assertions.assertTrue(isValidJson(body))
    }

    @Test
    fun `Gitt et kall til successBody så må body inneholde success true`() {
        val body = successBody()

        val mapper = ObjectMapper()

        val tree = mapper.readTree(body)
        Assertions.assertEquals(true, tree.get("success").booleanValue())
    }

    @Test
    fun `Gitt en gyldig path når maskerer så masker kun personIdentifikator`() {
        Assertions.assertEquals("***********___PINFO", maskerPersonIdentifier("12345678910___PINFO"))
    }

    @Test
    fun `Gitt en gyldig paths når maskerer så masker kun personIdentifikator`() {
        Assertions.assertEquals(
            "***********___PINFO,***********___PINFO",
            maskerPersonIdentifier(
                listOf("12345678910___PINFO", "10987654321___PINFO")
            )
        )
    }

    @TestOnly
    fun isValidJson(json: String): Boolean {
        val mapper = ObjectMapper()
        return try{
            mapper.readTree(json)
            true
        } catch (ex: Exception) {
            false
        }
    }

/*
    @Test
    fun `Test liste med SED kun PensjonSED skal returneres`() {
        val list  = listOf("X005","P2000","P4000","H02","X06","P9000", "")

        val result = filterPensionSedAndSort(list)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals("[P2000, P4000, P9000]", result.toString())
    }

    @Test
    fun `Test av liste med SEDer der kun PensjonSEDer skal returneres`() {
        val list  = listOf("X005","P2000","P4000","H02","X06","P9000", "")

        val result = filterPensionSedAndSort(list)

        Assertions.assertEquals(3, result.size)
        Assertions.assertEquals("[ \"P2000\", \"P4000\", \"P9000\" ]", result.toJson())
    }
*/


    @Test
    fun `Test listMapToJson`() {
        val list = listOf(mapOf("Name" to "Johnnyboy", "place" to "dummy"), mapOf("Name" to "Kjent dorull", "place" to "Q2"))

        val actualjson = "[ {\n" +
                "  \"Name\" : \"Johnnyboy\",\n" +
                "  \"place\" : \"dummy\"\n" +
                "}, {\n" +
                "  \"Name\" : \"Kjent dorull\",\n" +
                "  \"place\" : \"Q2\"\n" +
                "} ]"


        println(list.toJson())
        Assertions.assertEquals(actualjson.replace("\n", ""), list.toJson().replace(System.lineSeparator(), ""))

    }
}