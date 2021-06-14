package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class P15000test {

    @Test
    fun testP15000() {

        val json = """
             {
                   "sed" : "P15000",
                   "sedGVer" : "4",
                   "sedVer" : "2",
                   "nav" : {
                     "eessisak" : [ {
                       "institusjonsid" : "NO:889640782",
                       "institusjonsnavn" : "The Norwegian Labour and Welfare Administration",
                       "saksnummer" : "234233534534",
                       "land" : "NO"
                     } ],
                     "bruker" : {
                       "mor" : null,
                       "far" : null,
                       "person" : {
                         "pin" : [ {
                           "institusjonsnavn" : "Welfare Administration",
                           "institusjonsid" : "889640782",
                           "sektor" : null,
                           "identifikator" : "11067122781",
                           "land" : "NO",
                           "institusjon" : null
                         }, {
                           "institusjonsnavn" : "Social",
                           "institusjonsid" : "PL390050ER",
                           "sektor" : null,
                           "identifikator" : "11067122781",
                           "land" : "PL",
                           "institusjon" : null
                         } ],
                         "pinland" : null,
                         "statsborgerskap" : null,
                         "etternavn" : "VEGGPRYD",
                         "fornavn" : "KRAFTIG",
                         "kjoenn" : "M",
                         "foedested" : null,
                         "foedselsdato" : "1971-06-11",
                         "sivilstand" : null,
                         "relasjontilavdod" : null,
                         "rolle" : null
                       },
                       "adresse" : null,
                       "arbeidsforhold" : null,
                       "bank" : null
                     },
                     "brukere" : null,
                     "ektefelle" : null,
                     "barn" : null,
                     "verge" : null,
                     "krav" : {
                       "dato" : "2020-09-24",
                       "type" : "02"
                     },
                     "sak" : null,
                     "annenperson" : null
                   },
                   "pensjon" : {
                     "gjenlevende" : {
                       "mor" : null,
                       "far" : null,
                       "person" : {
                         "pin" : [ {
                           "institusjonsnavn" : "The Norwegian Labour and Welfare Administration",
                           "institusjonsid" : "NO:889640782",
                           "sektor" : null,
                           "identifikator" : "22117320034",
                           "land" : "NO",
                           "institusjon" : null
                         } ],
                         "pinland" : null,
                         "statsborgerskap" : null,
                         "etternavn" : "VEGGPRYD",
                         "fornavn" : "LEALAUS",
                         "kjoenn" : "K",
                         "foedested" : null,
                         "foedselsdato" : "1973-11-22",
                         "sivilstand" : null,
                         "relasjontilavdod" : {
                           "relasjon" : "01"
                         },
                         "rolle" : null
                       },
                       "adresse" : null,
                       "arbeidsforhold" : null,
                       "bank" : null
                     }
                   }
                 }
        """.trimIndent()

        val p15000 = SED.fromJsonToClass<P15000>(json)

        assertEquals("VEGGPRYD", p15000.nav?.bruker?.person?.etternavn)
        assertEquals("VEGGPRYD", p15000.pensjon?.gjenlevende?.person?.etternavn)

        val sed = SED.fromJson(json)

        assertEquals("VEGGPRYD",sed.nav?.bruker?.person?.etternavn)
        assertEquals("VEGGPRYD", (sed.pensjon as P15000Pensjon).gjenlevende?.person?.etternavn)


    }


}