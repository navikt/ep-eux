package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.utils.JsonIllegalArgumentException
import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SedTest {


    @Test
    fun `Feil under json-mapping for R005 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/R005-INVALID.json").readText()

        val exception = assertThrows<JsonIllegalArgumentException> {
            mapJsonToAny<R005>(json, true)
        }
        val expected = """
            Feilet ved mapping av jsonformat, Unrecognized field "personFEIL" (class no.nav.eessi.pensjon.eux.model.sed.Brukere), not marked as ignorable (7 known properties: "mor", "arbeidsforhold", "person", "far", "tilbakekreving", "bank", "adresse"])
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

    @Test
    fun `Gitt at vi skal hente alle personer fra en sed så returnerer vi en liste over personer`() {
        val sed = SED.fromJsonToConcrete(javaClass.getResource("/sed/P2100-PinDK-NAV.json")!!.readText())
        assertEquals(3, sed.allePersoner().size)
    }

    @Test
    fun `Gitt at vi skal hente alle personer fra en tom sed så returnerer vi en tom liste`() {
        assertEquals(0, SED(SedType.P2100).allePersoner().size)
    }

    @Test
    fun `Gitt en P7000 med personer når vi henter personer fra sed så returneres alle spesifikke personer med pin`() {
        val p7000 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P7000-NAV-personer.json")!!.readText())
        assertEquals(2, p7000.allePersoner().size)
    }


    @Test
    fun `Gitt en P5000 med personer når vi henter personer fra sed så returneres alle spesifikke personer med pin`() {
        val p5000 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P5000-NAV-personer.json")!!.readText())
        assertEquals(1, p5000.allePersoner().size)
    }

    @Test
    fun `Gitt en P15000 med personer når vi henter personer fra sed så returneres alle spesifikke personer med pin`() {
        val p15000 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P15000-NAV-personer.json")!!.readText())
        assertEquals(2, p15000.allePersoner().size)
    }

    @Test
    fun `Gitt en P15000 med personer uten pin når vi henter personer fra sed så returneres tom liste`() {
        val p15000 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P15000-UtenPin-NAV.json")!!.readText())
        assertEquals(0, p15000.allePersoner().size)
    }

    @Test
    fun `Gitt en P2200 med personer inklusive barn når vi henter personer fra sed så returneres alle spesifikke personer med pin`() {
        val p2200 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P2200-MedFamilie-NAV.json")!!.readText())
        assertEquals(5, p2200.allePersoner().size)
    }
}