package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P12000UtvidetTest {

    @Test
    fun mapJsonToP12000() {

        val p12000 = mapJsonToAny<P12000>(p12000Json())
        val p12000Betalingsdetaljer = p12000.pensjon?.pensjoninfo?.firstOrNull()?.betalingsdetaljer
        val p12000Opphoring = p12000.pensjon?.pensjoninfo?.firstOrNull()?.pensjonsopphoring
        val p12000Avslag = p12000.pensjon?.pensjoninfo?.firstOrNull()?.pensjonsavslag

        assertEquals("æøå", p12000Betalingsdetaljer?.belop)
        assertEquals("01", p12000Betalingsdetaljer?.pensjonstype)
        assertEquals("aarlig", p12000Betalingsdetaljer?.utbetalingshyppighet)
        assertEquals("æøå", p12000Betalingsdetaljer?.belop)
        assertEquals("2001-01-01", p12000Betalingsdetaljer?.effektueringsdato)

        assertEquals("01", p12000Opphoring?.pensjonstype)

        assertEquals("01", p12000Avslag?.pensjonstype)

        // gjenlevende skal også være en del av P12000
        assertEquals("Kari", p12000.pensjon?.gjenlevende?.mor?.person?.fornavn)

        val p12000json = p12000.toJsonSkipEmpty()
        JSONAssert.assertEquals(p12000json, p12000Json(), false)

    }

    private fun p12000Json() =
        """
          {
          "nav" : {
            "bruker" : {
              "mor" : {
                "person" : {
                  "etternavnvedfoedsel" : "æøå",
                  "fornavn" : "æøå"
                }
              },
              "person" : {
                "fornavn" : "æøå",
                "kjoenn" : "M",
                "etternavn" : "æøå",
                "kontakt" : {
                  "email" : [ {
                    "adresse" : "æøå"
                  } ],
                  "telefon" : [ {
                    "nummer" : "æøå",
                    "type" : "hjem"
                  } ]
                },
                "etternavnvedfoedsel" : "æøå",
                "foedselsdato" : "2001-01-01",
                "tidligereetternavn" : "æøå",
                "statsborgerskap" : [ {
                  "land" : "GR"
                } ],
                "pin" : [ {
                  "institusjonsnavn" : "æøå",
                  "identifikator" : "æøå",
                  "sektor" : "yrkesskade_og_yrkessykdom",
                  "land" : "GR",
                  "institusjonsid" : "æøå"
                } ],
                "foedested" : {
                  "region" : "æøå",
                  "by" : "æøå",
                  "land" : "GR"
                },
                "fornavnvedfoedsel" : "æøå",
                "tidligerefornavn" : "æøå"
              },
              "adresse" : {
                "bygning" : "æøå",
                "region" : "æøå",
                "postnummer" : "æøå",
                "by" : "æøå",
                "land" : "GR",
                "gate" : "æøå"
              },
              "far" : {
                "person" : {
                  "fornavn" : "æøå",
                  "etternavnvedfoedsel" : "æøå"
                }
              }
            },
            "eessisak" : [ {
              "institusjonsnavn" : "æøå",
              "saksnummer" : "æøå",
              "land" : "GR",
              "institusjonsid" : "æøå"
            } ]
          },
          "pensjon" : {
            "gjenlevende" : {
              "person" : {
                "kontakt" : {
                  "telefon" : [ {
                    "type" : "hjem",
                    "nummer" : "æøå"
                  } ],
                  "email" : [ {
                    "adresse" : "æøå"
                  } ]
                },
                "etternavnvedfoedsel" : "æøå",
                "foedested" : {
                  "region" : "æøå",
                  "by" : "æøå",
                  "land" : "GR"
                },
                "pin" : [ {
                  "sektor" : "yrkesskade_og_yrkessykdom",
                  "land" : "GR",
                  "institusjonsid" : "æøå",
                  "identifikator" : "æøå",
                  "institusjonsnavn" : "æøå"
                } ],
                "fornavn" : "fornavn",
                "kjoenn" : "M",
                "tidligereetternavn" : "æøå",
                "fornavnvedfoedsel" : "æøå",
                "foedselsdato" : "2001-01-01",
                "tidligerefornavn" : "æøå",
                "etternavn" : "æøå",
                "statsborgerskap" : [ {
                  "land" : "GR"
                } ]
              },
              "adresse" : {
                "postnummer" : "æøå",
                "by" : "æøå",
                "land" : "GR",
                "gate" : "æøå",
                "bygning" : "æøå",
                "region" : "æøå"
              },
              "mor" : {
                "person" : {
                  "etternavnvedfoedsel" : "æøå",
                  "fornavn" : "Kari"
                }
              },
              "far" : {
                "person" : {
                  "fornavn" : "æøå",
                  "etternavnvedfoedsel" : "æøå"
                }
              }
            },
            "merinformasjon" : {
              "ytelser" : [ {
                "tilleggsytelserutbetalingitilleggtilpensjon" : "æøå"
              } ]
            },
            "pensjoninfo" : [ {
              "betalingsdetaljer" : {
                "effektueringsdato" : "2001-01-01",
                "betaldato" : "2001-01-01",
                "belop" : "æøå",
                "valuta" : "CHF",
                "basertpaa" : "01",
                "fradato" : "2001-01-01",
                "annenutbetalingshyppighet" : "æøå",
                "utbetalingshyppighet" : "aarlig",
                "pensjonstype" : "01",
                "bosattotal" : "æøå",
                "arbeidstotal" : "æøå"
              },
              "pensjonsavslag" : {
                "tekstfelt" : "æøå",
                "pensjonstype" : "01"
              },
              "pensjonsopphoring" : {
                "pensjonstype" : "01",
                "tekstfelt" : "æøå"
              }
            } ],
            "foresporsel" : {
              "referanseTilPerson" : "01"
            },
            "ytterligeinformasjon" : "æøå",
            "anmodning13000verdi" : "1"
          },
          "sedVer" : "3",
          "sedGVer" : "4",
          "sed" : "P12000"
        }
        """.trimIndent()

}