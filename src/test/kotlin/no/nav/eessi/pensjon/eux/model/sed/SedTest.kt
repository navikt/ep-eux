package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.JsonIllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SedTest {


    @Test
    fun `Feil under json-mapping for R005 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/R005-INVALID.json").readText()

        val exception = assertThrows<JsonIllegalArgumentException> {
            SED.fromJsonToConcrete(json)
        }
        val expected = """
Feilet ved mapping av jsonformat com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "personFEIL" (class no.nav.eessi.pensjon.eux.model.sed.Brukere), not marked as ignorable (7 known properties: "mor", "arbeidsforhold", "person", "far", "tilbakekreving", "bank", "adresse"])
 at [Source: (String)"{
  "nav": {
    "brukere": [
      {
        "person": {
          "fornavn": "LUR",
          "pin": [
            {
              "identifikator": "04117922400",
              "land": "NO"
            }
          ],
          "foedselsdato": "1979-11-04",
          "etternavn": "TRANE",
          "kjoenn": "K"
        },
        "tilbakekreving": {
          "status": {
            "type": "debitor"
          }
        }
      },
      {
        "personFEIL": {
          "fornavn": "LURIFAX","[truncated 581 chars]; line: 41, column: 8] (through reference chain: no.nav.eessi.pensjon.eux.model.sed.R005["nav"]->no.nav.eessi.pensjon.eux.model.sed.RNav["brukere"]->java.util.ArrayList[1]->no.nav.eessi.pensjon.eux.model.sed.Brukere["personFEIL"])
        """.trimIndent()

        assertEquals(expected, exception.message)
    }
}