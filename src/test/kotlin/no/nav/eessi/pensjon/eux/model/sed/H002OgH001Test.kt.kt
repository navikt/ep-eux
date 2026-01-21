package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.toJson
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

internal class H002Test {


    @Test
    fun `Feil under json-mapping for H002 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/H002-NAV.json")!!.readText()

         val result = SED.fromJsonToConcrete(json).toJson()

        println(result)
        val expected = """
        {
          "sed" : "H002",
          "nav" : {
            "eessisak" : null,
            "bruker" : {
              "mor" : null,
              "far" : null,
              "person" : {
                "pin" : [ {
                  "institusjonsnavn" : "Centrale de Pensjon",
                  "institusjonsid" : "CH:4534534",
                  "sektor" : "pensjoner",
                  "identifikator" : "53453443534534",
                  "land" : "CH",
                  "institusjon" : null
                } ],
                "pinland" : null,
                "statsborgerskap" : [ {
                  "land" : "SE"
                } ],
                "etternavn" : "Tilly",
                "etternavnvedfoedsel" : null,
                "fornavn" : "Olaf",
                "fornavnvedfoedsel" : null,
                "tidligerefornavn" : null,
                "tidligereetternavn" : null,
                "kjoenn" : "M",
                "foedested" : null,
                "foedselsdato" : "1976-10-14",
                "sivilstand" : null,
                "relasjontilavdod" : null,
                "rolle" : null,
                "kontakt" : null,
                "doedsdato" : null
              },
              "adresse" : [ {
                "gate" : null,
                "bygning" : null,
                "by" : null,
                "postnummer" : null,
                "postkode" : null,
                "region" : null,
                "land" : null,
                "kontaktpersonadresse" : null,
                "datoforadresseendring" : null,
                "postadresse" : null,
                "startdato" : null,
                "type" : "bosted",
                "annen" : null
              } ],
              "arbeidsforhold" : null,
              "bank" : null
            },
            "ektefelle" : null,
            "barn" : null,
            "verge" : null,
            "krav" : null,
            "annenperson" : null
          },
          "sedGVer" : "4",
          "sedVer" : "3",
          "pensjon" : null
        }
        """.trimIndent()

        JSONAssert.assertEquals(expected, result, false)
    }

    @Test
    fun `H001 json-mapping for H001 skal kaste en exception`() {
        val json = javaClass.getResource("/sed/H001-NAV.json")!!.readText()

        val result = SED.fromJsonToConcrete(json).toJson()

        println(result)
        val expected = """
        {
          "sed" : "H001",
          "nav" : {
            "eessisak" : null,
            "bruker" : {
              "mor" : null,
              "far" : null,
              "person" : {
                "pin" : [ {
                  "institusjonsnavn" : "Centrale de Pensjon",
                  "institusjonsid" : "CH:4534534",
                  "sektor" : "pensjoner",
                  "identifikator" : "53453443534534",
                  "land" : "CH",
                  "institusjon" : null
                } ],
                "pinland" : null,
                "statsborgerskap" : [ {
                  "land" : "SE"
                } ],
                "etternavn" : "Tilly",
                "etternavnvedfoedsel" : null,
                "fornavn" : "Olaf",
                "fornavnvedfoedsel" : null,
                "tidligerefornavn" : null,
                "tidligereetternavn" : null,
                "kjoenn" : "M",
                "foedested" : null,
                "foedselsdato" : "1976-10-14",
                "sivilstand" : null,
                "relasjontilavdod" : null,
                "rolle" : null,
                "kontakt" : null,
                "doedsdato" : null
              },
              "adresse" : [ {
                "gate" : null,
                "bygning" : null,
                "by" : null,
                "postnummer" : null,
                "postkode" : null,
                "region" : null,
                "land" : null,
                "kontaktpersonadresse" : null,
                "datoforadresseendring" : null,
                "postadresse" : null,
                "startdato" : null,
                "type" : "bosted",
                "annen" : null
              } ],
              "arbeidsforhold" : null,
              "bank" : null
            },
            "ektefelle" : null,
            "barn" : null,
            "verge" : null,
            "krav" : null,
            "annenperson" : null
          },
          "sedGVer" : "4",
          "sedVer" : "3",
          "pensjon" : null
        }
        """.trimIndent()

        JSONAssert.assertEquals(expected, result, false)
    }

}