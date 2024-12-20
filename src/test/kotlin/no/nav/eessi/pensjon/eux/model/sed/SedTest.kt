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

        assert(exception.message!!.contains("Feilet ved mapping av jsonformat"))
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
        assertEquals(3, p7000.allePersoner().size)
    }


    @Test
    fun `Gitt en P5000 med personer når vi henter personer fra sed så returneres alle spesifikke personer med pin`() {
        val p5000 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P5000-NAV-personer.json")!!.readText())
        assertEquals(1, p5000.allePersoner().size)
    }

    @Test
    fun `Gitt en P15000 med personer når vi henter personer fra sed så returneres alle spesifikke personer med pin`() {
        val p15000 = SED.fromJsonToConcrete(javaClass.getResource("/sed/P15000-NAV-personer.json")!!.readText())
        println("AllePersoner: ${p15000.allePersoner().filter { it.pin != null }}")
        assertEquals(3, p15000.allePersoner().size)
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

    @Test
    fun `Serialiseringstest for P2000 med verge`() {
        val p2000 = """
                    {
           "sed":"P2000",
           "nav":{
              "eessisak":null,
              "bruker":{
                 "mor":null,
                 "far":null,
                 "person":{
                    "pin":[
                       {
                          "institusjonsnavn":null,
                          "institusjonsid":null,
                          "sektor":null,
                          "identifikator":"11128221249",
                          "land":"NO",
                          "institusjon":null
                       },
                       {
                          "institusjonsnavn":null,
                          "institusjonsid":null,
                          "sektor":null,
                          "identifikator":"821112-5464",
                          "land":"SE",
                          "institusjon":null
                       }
                    ],
                    "pinland":null,
                    "statsborgerskap":null,
                    "etternavn":"d",
                    "etternavnvedfoedsel":null,
                    "fornavn":"d",
                    "fornavnvedfoedsel":null,
                    "tidligerefornavn":null,
                    "tidligereetternavn":null,
                    "kjoenn":"K",
                    "foedested":null,
                    "foedselsdato":"1982-12-11",
                    "sivilstand":null,
                    "relasjontilavdod":null,
                    "rolle":null,
                    "kontakt":null
                 },
                 "adresse":null,
                 "arbeidsforhold":null,
                 "bank":null
              },
              "ektefelle":null,
              "barn" : [ {
                  "mor" : {
                    "person" : {
                      "fornavn" : "SELVHJULPEN"
                    }
                  },
                  "person" : {
                    "pin" : [ {
                      "institusjonsnavn" : "NAV ACCEPTANCE TEST 07",
                      "institusjonsid" : "NO:NAVAT07",
                      "identifikator" : "06422075303",
                      "land" : "NO"
                    }, {
                      "identifikator" : "200206-5465",
                      "land" : "SE"
                    } ],
                    "statsborgerskap" : [ {
                      "land" : "NO"
                    } ],
                    "etternavn" : "HALVMETER",
                    "fornavn" : "KREATIV",
                    "kjoenn" : "M",
                    "foedested" : {
                      "by" : "Unknown",
                      "land" : "NO"
                    },
                    "foedselsdato" : "2020-02-06"
                  },
                  "far" : {
                    "person" : {
                      "fornavn" : "REKTANGULÆR"
                    }
                  },
                  "relasjontilbruker" : "eget_barn"
                } ],
              "verge":{
                 "person":{
                    "etternavn":"Pettersen",
                    "fornavn":"Jacob",
                    "kontakt":{
                       "telefon":[
                          {
                             "nummer":"98798798",
                             "type":"arbeid"
                          },
                          {
                             "nummer":"55464445",
                             "type":"arbeid"
                          }
                       ],
                       "email":[
                          {
                             "adresse":"jp@gemail.com"
                          }
                       ]
                    }
                 },
                 "vergemaal":{
                    "mandat":"Demens"
                 },
                 "adresse":{
                    "gate":"Løpeveien",
                    "postnummer":"5478",
                    "by":"Bergen",
                    "region":"Vestland",
                    "land":"NO",
                       "currencies":[
                          "NOK"
                       ],            
                       "ioc":"NOR",
                       "languages":[
                          "nor"
                       ],
                       "name":"Norway",
                       "status":"assigned",
                       "label":"Norge",
                       "value":"NO",
                       "value3":"NOR"
                    }
                 }
              },
              "krav":{
                 "dato":"2023-03-09",
                 "type":null
              },
              "annenperson":null
           },
           "pensjon":null,
           "sedGVer":"4",
           "sedVer":"2"
        }
        """.trimIndent()
        val sed  = SED.fromJsonToConcrete(p2000)
        assertEquals(sed.nav?.barn?.firstOrNull()?.relasjontilbruker, "eget_barn")
//        assertEquals(sed.nav?.barn?.firstOrNull()?.relasjontilbruker43, "eget_barn")
    }

}