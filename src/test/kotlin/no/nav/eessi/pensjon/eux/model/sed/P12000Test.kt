package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P12000Test {

    @Test
    fun mapJsonToP12000() {

        val p12000 = mapJsonToAny<P12000>(p12000Json())
        val p12000Betalingsdetaljer = p12000.pensjon?.pensjoninfo?.firstOrNull()?.betalingsdetaljer

        assertEquals("11111", p12000Betalingsdetaljer?.belop)
        assertEquals("01", p12000Betalingsdetaljer?.pensjonstype)
        assertEquals("maaned_12_per_aar", p12000Betalingsdetaljer?.utbetalingshyppighet)
        assertEquals("11111", p12000Betalingsdetaljer?.belop)
        assertEquals("2024-01-01", p12000Betalingsdetaljer?.effektueringsdato)

        // gjenlevende skal også være en del av P12000
        assertEquals("kari", p12000.pensjon?.gjenlevende?.mor?.person?.fornavn)

        val p12000json = p12000.toJsonSkipEmpty()
        JSONAssert.assertEquals(p12000json, p12000Json(), false)

    }

    private fun p12000Json() =
        """
        {
          "pensjon": {
            "pensjoninfo": [
              {
                "betalingsdetaljer": {
                  "pensjonstype": "01",
                  "effektueringsdato": "2024-01-01",
                  "utbetalingshyppighet": "maaned_12_per_aar",
                  "basertpaa": "01",
                  "belop": "11111",
                  "valuta": "EUR"
                }
              }
            ],
            "gjenlevende": {             
              "mor": {
                "person": {
                  "etternavnvedfoedsel": "nordmann",
                  "fornavn": "kari"
                }
              }
            }
          },
          "nav": {
            "bruker": {
              "person": {
                "kjoenn": "U",
                "etternavn": "v",
                "fornavn": "l",
                "foedselsdato": "2006-02-17"
              }
            }
          },
          "sedGVer": "4",
          "sedVer": "2",
          "sed": "P12000"
        }
        """.trimIndent()

}