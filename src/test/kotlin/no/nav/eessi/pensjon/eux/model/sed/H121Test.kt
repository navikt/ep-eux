package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.toJson
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class SedTestX121 {


    @Test
    fun `Feil under json-mapping for R005 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/H121-NAV.json")!!.readText()

         val result = SED.fromJsonToConcrete(json).toJson()

        println(result)
        val expected = """
        {
          "sed" : "H121",
          "sedGVer" : "4",
          "sedVer" : "2",
          "nav" : {
            "eessisak" : null,
            "bruker" : {
              "mor" : null,
              "far" : null,
              "person" : {
                "pin" : [ {
                  "institusjonsnavn" : "The Norwegian Labour and Welfare Administration",
                  "institusjonsid" : "NO:889640782",
                  "sektor" : null,
                  "identifikator" : "xxx",
                  "land" : "NO",
                  "institusjon" : null
                } ],
                "pinland" : null,
                "statsborgerskap" : null,
                "etternavn" : "xxx",
                "etternavnvedfoedsel" : null,
                "fornavn" : "xxx",
                "fornavnvedfoedsel" : null,
                "tidligerefornavn" : null,
                "tidligereetternavn" : null,
                "kjoenn" : "M",
                "foedested" : {
                  "by" : "Oslo",
                  "land" : "NO",
                  "region" : null
                },
                "foedselsdato" : "1965-10-01",
                "sivilstand" : null,
                "relasjontilavdod" : null,
                "rolle" : null
              },
              "adresse" : null,
              "arbeidsforhold" : null,
              "bank" : null
            },
            "ektefelle" : null,
            "barn" : null,
            "verge" : null,
            "krav" : null,
            "annenperson" : null
          },
          "pensjon" : null
        }
        """.trimIndent()

        JSONAssert.assertEquals(expected, result, false)
    }
}