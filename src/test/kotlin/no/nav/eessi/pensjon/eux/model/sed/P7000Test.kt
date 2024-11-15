package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P7000Test {

    @Test
    fun mapJsonToP7000() {

        val p7000 = mapJsonToAny<P7000>(validNavjson())
        println("@@@@@@@@ $p7000")

        Assertions.assertEquals(2, p7000.pensjon?.samletVedtak?.tildeltepensjoner?.size)
        Assertions.assertEquals(1, p7000.pensjon?.samletVedtak?.avslag?.size)

        val noTildelt = p7000.pensjon?.samletVedtak?.tildeltepensjoner?.firstOrNull { it.institusjon?.land == "NO" }
        val seTildelt = p7000.pensjon?.samletVedtak?.tildeltepensjoner?.firstOrNull { it.institusjon?.land == "SE" }
        val dkAvslag = p7000.pensjon?.samletVedtak?.avslag?.firstOrNull { it.pin?.land == "DK" }

        Assertions.assertEquals("2021-07-20", noTildelt?.revurderingtidsfrist)
        Assertions.assertEquals("123123123123", noTildelt?.ytelser?.first()?.beloepsListe?.first()?.beloepBrutto)
        Assertions.assertEquals(
            "4.1.8.2 revjrflkasdfjasdf asdfasd fadfg",
            noTildelt?.adressatForRevurdering?.first()?.adressatforrevurdering
        )
        Assertions.assertEquals("01", noTildelt?.pensjonType)

        Assertions.assertEquals("2021-07-20", seTildelt?.revurderingtidsfrist)
        Assertions.assertEquals("41", seTildelt?.ytelser?.first()?.beloepsListe?.first()?.beloepBrutto)
        Assertions.assertEquals("4.1.8.2.1", seTildelt?.adressatForRevurdering?.first()?.adressatforrevurdering)
        Assertions.assertEquals("01", seTildelt?.pensjonType)

        Assertions.assertEquals("2021-07-20", dkAvslag?.dato)
        Assertions.assertEquals("5.1.5.1 tid", dkAvslag?.tidsfristForRevurdering)
        Assertions.assertEquals("06", dkAvslag?.begrunnelse)
        Assertions.assertEquals("01", dkAvslag?.pensjonType)

        val p7000json = p7000.toJsonSkipEmpty()
        println("@@@@@@@@ $p7000json")
        JSONAssert.assertEquals(p7000json, validNavjson(), false)

    }

    private fun validNavjson() : String {

        return """
        {
          "pensjon" : {
            "samletVedtak" : {
              "tildeltepensjoner" : [ {
                "adressatForRevurdering" : [ {
                  "adressatforrevurdering" : "4.1.8.2 revjrflkasdfjasdf asdfasd fadfg"
                } ],
                "revurderingtidsfrist" : "2021-07-20",
                "pensjonType" : "01",
                "ytelser" : [ {
                  "beloepsListe" : [ {
                    "valuta" : "NOK",
                    "beloepBrutto" : "123123123123",
                    "betalingshyppighetytelse" : "maaned_12_per_aar"
                  } ],
                  "annenbetalingshyppighetytelse" : "4.1.6.6.1 annen",
                  "startdatoutbetaling" : "2021-07-20",
                  "sluttdatoretttilytelse" : "2021-07-20"
                } ],
                "startdatoPensjonsRettighet" : "2020-05-01",
                "reduksjonsGrunn" : "02",
                "institusjon" : {
                  "saksnummer" : "23123",
                  "sektor" : "alle",
                  "personNr" : "29065325959",
                  "land" : "NO",
                  "institusjonsid" : "NO:NAVAT01",
                  "institusjonsnavn" : "NAV ACCEPTANCE TEST 01"
                }
              }, {
                "institusjon" : {
                  "institusjonsid" : "SE:ACC2001",
                  "sektor" : "alle",
                  "institusjonsnavn" : "The Swedish Pensions Agency",
                  "personNr" : "21317212",
                  "saksnummer" : "32142342134234234234234",
                  "land" : "SE"
                },
                "ytelser" : [ {
                  "annenbetalingshyppighetytelse" : "4.1.6.6.1",
                  "startdatoutbetaling" : "2021-07-20",
                  "beloepsListe" : [ {
                    "beloepBrutto" : "41",
                    "betalingshyppighetytelse" : "maaned_12_per_aar",
                    "valuta" : "SEK"
                  } ],
                  "sluttdatoretttilytelse" : "2021-07-20"
                } ],
                "revurderingtidsfrist" : "2021-07-20",
                "pensjonType" : "01",
                "adressatForRevurdering" : [ {
                  "adressatforrevurdering" : "4.1.8.2.1"
                } ],
                "startdatoPensjonsRettighet" : "2021-07-20",
                "reduksjonsGrunn" : "01"
              } ],
              "avslag" : [ {
                "pin" : {
                  "identifikator" : "34234'1234",
                  "land" : "DK",
                  "institusjon" : {
                    "institusjonsnavn" : "ACC_The Danish Debt Collection Agency",
                    "institusjonsid" : "DK:10429",
                    "saksnummer" : "2342341234513452456"
                  },
                  "sektor" : "alle"
                },
                "dato" : "2021-07-20",
                "begrunnelse" : "06",
                "tidsfristForRevurdering" : "5.1.5.1 tid",
                "pensjonType" : "01"
              } ]
            },
            "gjenlevende" : {
              "person" : {
                "fornavn" : "LUGUBER",
                "kjoenn" : "M",
                "pin" : [ {
                  "land" : "NO",
                  "identifikator" : "14115224590"
                } ],
                "foedselsdato" : "1952-11-14",
                "etternavn" : "VEGGPRYD"
              }
            }
          },
          "sedVer" : "1",
          "nav" : {
            "bruker" : {
              "person" : {
                "fornavn" : "æøå",
                "kjoenn" : "K",
                "etternavn" : "æøå",
                "etternavnvedfoedsel" : "Avdød",
                "foedselsdato" : "1952-12-07",
                "pin" : [ {
                  "identifikator" : "07125224002",
                  "land" : "NO"
                } ]
              }
            },
            "eessisak" : [ {
              "institusjonsnavn" : "NAV ACCEPTANCE TEST 07",
              "saksnummer" : "22919987",
              "land" : "NO",
              "institusjonsid" : "NO:NAVAT07"
            } ]
          },
          "sedGVer" : "4",
          "sed" : "P7000"
        }
        """.trimIndent()
    }

}