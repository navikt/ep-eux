package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.toJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class X010Test {

    @Test
    fun `test for utfylling av X010`() {

        val x010 = X010(
            nav = Nav(
                sak = Navsak(
                    kontekst = Kontekst(
                        bruker = Bruker(
                            person = Person(
                                fornavn = "OLA",
                                etternavn = "BJELLEKLANGEN",
                                foedselsdato = "2001-02-04",
                                kjoenn = "M"
                            )
                        )
                    ),
                    paaminnelse = Paaminnelse(
                        svar = Svar(
                            informasjon = Informasjon(
                                kommersenere = listOf(
                                    KommersenereItem(
                                        type = "P5000",
                                        forventetdato = "2020-20-10",
                                        opplysninger = "Opplysningenenenenen"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )

        val json = x010.toJson()

        assertEquals(expectedJson(), json)


    }


    private fun expectedJson(): String {
        return """
{
  "sed" : "X010",
  "sedGVer" : "4",
  "sedVer" : "1",
  "nav" : {
    "eessisak" : null,
    "bruker" : null,
    "brukere" : null,
    "ektefelle" : null,
    "barn" : null,
    "verge" : null,
    "krav" : null,
    "sak" : {
      "kontekst" : {
        "bruker" : {
          "mor" : null,
          "far" : null,
          "person" : {
            "pin" : null,
            "pinland" : null,
            "statsborgerskap" : null,
            "etternavn" : "BJELLEKLANGEN",
            "fornavn" : "OLA",
            "kjoenn" : "M",
            "foedested" : null,
            "foedselsdato" : "2001-02-04",
            "sivilstand" : null,
            "relasjontilavdod" : null,
            "rolle" : null
          },
          "adresse" : null,
          "arbeidsforhold" : null,
          "bank" : null
        }
      },
      "leggtilinstitusjon" : null,
      "paaminnelse" : {
        "svar" : {
          "informasjon" : {
            "kommersenere" : [ {
              "type" : "P5000",
              "opplysninger" : "Opplysningenenenenen",
              "forventetdato" : "2020-20-10"
            } ]
          }
        }
      }
    },
    "annenperson" : null
  },
  "pensjon" : null
}
        """.trimIndent()
    }

}