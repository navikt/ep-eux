package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P1200Test {

    @Test
    fun mapJsonToP2200() {

        val p1200 = mapJsonToAny<P12000>(p1200Json())

        val bruker = p1200.nav?.bruker
        val forsikret = p1200.nav?.bruker?.person
        val brukerMor = p1200.nav?.bruker?.mor?.person

        val p1200json = p1200.toJson()
        JSONAssert.assertEquals(p1200json, p1200Json(), true)
        JSONAssert.assertEquals(p1200Json(), p1200json, false)

        /** 1. Local case numbers*/
        //1.1.1  Country
        assertEquals("NO", p1200.nav?.eessisak?.firstOrNull()?.land)
        //1.1.2 Case number
        assertEquals("22975052", p1200.nav?.eessisak?.firstOrNull()?.saksnummer)
        //1.1.3.1 Institution ID
        assertEquals("NO:NAVAT07", p1200.nav?.eessisak?.firstOrNull()?.institusjonsid)
        //1.1.3.2 Institution Name
        assertEquals("NAV ACCEPTANCE TEST 07", p1200.nav?.eessisak?.firstOrNull()?.institusjonsnavn)

        /** 2. Insured person */
        //2.1.1 Family name(s)
        assertEquals("KORRIDOR", forsikret?.etternavn)
        //2.1.2 Forename(s)
        assertEquals("ARITMETISK", forsikret?.fornavn)
        //2.1.3 Date of birth
        assertEquals("1962-04-16", forsikret?.foedselsdato)
        //2.1.4. Sex
        assertEquals("M", forsikret?.kjoenn)
        //2.1.5. Family name(s) at birth
        assertEquals("Gang", forsikret?.etternavnvedfoedsel)
        //2.1.6. Forename(s) at birth
        assertEquals("trang", forsikret?.fornavnvedfoedsel)

        // Personal Identification Number(s)*
        //2.1.7.1.1. Country
        assertEquals("NO", forsikret?.pin?.firstOrNull()?.land)
        //2.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("16446208164", forsikret?.pin?.firstOrNull()?.identifikator)
        //2.1.7.1.3. Sector
        assertEquals("pensjoner", forsikret?.pin?.firstOrNull()?.sektor)
        //2.1.7.1.4.1. Institution ID
        assertEquals("NO:NAVAT07", p1200.nav?.bruker?.person?.pin?.firstOrNull()?.institusjonsid)
        //2.1.7.1.4.2. Institution Name
        assertEquals("NAV ACCEPTANCE TEST 07", p1200.nav?.bruker?.person?.pin?.firstOrNull()?.institusjonsnavn)
        //2.1.7.1.1. Country
        assertEquals("BE", p1200.nav?.bruker?.person?.pin?.last()?.land)
        //2.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("160462-587-45", p1200.nav?.bruker?.person?.pin?.last()?.identifikator)
        //2.1.7.1.3. Sector
        assertEquals(null, p1200.nav?.bruker?.person?.pin?.last()?.sektor)
        //2.1.7.1.4.1. Institution ID
        assertEquals(null, p1200.nav?.bruker?.person?.pin?.last()?.institusjonsid)
        //2.1.7.1.4.2. Institution Name
        assertEquals(null, p1200.nav?.bruker?.person?.pin?.last()?.institusjonsnavn)

        //2.1.8.1.1. Town
        assertEquals("Bergen", forsikret?.foedested?.by)
        //2.1.8.1.2. Region
        assertEquals("vestland", forsikret?.foedested?.region)
        //2.1.8.1.3. Country
        assertEquals("NO", forsikret?.foedested?.land)
        //2.1.8.2. Father's family name at birth
        assertEquals("kano", p1200.nav?.bruker?.far?.person?.etternavnvedfoedsel)
        //2.1.8.3. Forename of father
        assertEquals("grønn", bruker?.far?.person?.fornavn)
        //2.1.8.4. Mother's family name at birth
        assertEquals("kajakk", brukerMor?.etternavnvedfoedsel)
        //2.1.8.5. Forename of mother
        assertEquals("rød", brukerMor?.fornavn)

        //2.2.1.1. Nationality
        assertEquals("NO", forsikret?.statsborgerskap?.firstOrNull()?.land)
        assertEquals("SE", forsikret?.statsborgerskap?.last()?.land)
        //2.2.1.2. Previous family name(s)
        assertEquals("bil", forsikret?.tidligereetternavn)
        //2.2.1.3. Previous forename(s)
        assertEquals("skitten", forsikret?.tidligerefornavn)
        //2.2.2. Address
        //2.2.2.1. Street
        assertEquals("1KOLEJOWA 6/5", bruker?.adresse?.gate)
        //2.2.2.2. Building Name
        assertEquals("utsikten", bruker?.adresse?.bygning)
        //2.2.2.3. Town
        assertEquals("CAPITAL WEST", bruker?.adresse?.by)
        //2.2.2.4. Postal Code
        assertEquals("3000", bruker?.adresse?.postnummer)
        //2.2.2.5. Region
        assertEquals("Bretagne", bruker?.adresse?.region)
        //2.2.2.6. Country
        assertEquals("FR", bruker?.adresse?.land)
        //2.2.3.1. Telephone Numbers
        //2.2.4.1.1.1. Type (rina enum 02 Mobile)
        assertEquals("mobil", forsikret?.kontakt?.telefon?.firstOrNull()?.type)
        //2.2.4.1.1.2. Number
        assertEquals("99999999", forsikret?.kontakt?.telefon?.firstOrNull()?.nummer)

        //2.2.3.2. Email Addresses
        //2.2.3.2.1.1. Email Address
        assertEquals("post@noreply.no", forsikret?.kontakt?.email?.firstOrNull()?.adresse)


        val pensjonP1200 = p1200.pensjonP12000
        /** 3. Recipient of survivor's pension */
        //3.1.1. Family name(s)
        println("REZZ: ${p1200.pensjonP12000?.gjenlevende?.person}")
        assertEquals("MOTTAKER", pensjonP1200?.gjenlevende?.person?.etternavn)
        //3.1.2. Forename(s)
        assertEquals("GJENLEVENDE", pensjonP1200?.gjenlevende?.person?.fornavn)
        //3.1.3. Date of birth
        assertEquals("1978-02-13", pensjonP1200?.gjenlevende?.person?.foedselsdato)
        //3.1.4. Sex (rina enum 02 Female)
        assertEquals("K", pensjonP1200?.gjenlevende?.person?.kjoenn)
        //3.1.5. Family name(s) at birth
        assertEquals("fisk", pensjonP1200?.gjenlevende?.person?.etternavnvedfoedsel)
        //3.1.6. Forename(s) at birth
        assertEquals("død", pensjonP1200?.gjenlevende?.person?.fornavnvedfoedsel)


        //3.1.7.1 Personal Identification Number(s)*
        //3.1.7.1.1. Country
        assertEquals("PL", pensjonP1200?.gjenlevende?.person?.pin?.firstOrNull()?.land)
        //3.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("BE-235-1334", pensjonP1200?.gjenlevende?.person?.pin?.firstOrNull()?.identifikator)
        //3.1.7.1.3. Sector (rina enum 04 Pensions)
        assertEquals("pensjoner", pensjonP1200?.gjenlevende?.person?.pin?.first()?.sektor)
        //3.1.7.1.4.1. Institution ID
        assertEquals("PL:WF16S", pensjonP1200?.gjenlevende?.person?.pin?.firstOrNull()?.institusjonsid)
        //3.1.7.1.4.2. Institution Name
        assertEquals("ACC_Zachodniopomorski Regional Branch of NHF", pensjonP1200?.gjenlevende?.person?.pin?.firstOrNull()?.institusjonsnavn)
        //3.1.8.1. Place of birth
        //3.1.8.1.1. Town
        assertEquals("Oslo", pensjonP1200?.gjenlevende?.person?.foedested?.by)
        //3.1.8.1.2. Region
        assertEquals("Oslo", pensjonP1200?.gjenlevende?.person?.foedested?.region)
        //3.1.8.1.3. Country
        assertEquals("NO", pensjonP1200?.gjenlevende?.person?.foedested?.land)
        //3.1.8.2. Father's family name at birth
        assertEquals("FAR", pensjonP1200?.gjenlevende?.far?.person?.etternavnvedfoedsel)
        //3.1.8.3. Forename of father
        assertEquals("FAR", pensjonP1200?.gjenlevende?.far?.person?.fornavn)
        //3.1.8.4. Mother's family name at birth
        assertEquals("MOR", pensjonP1200?.gjenlevende?.mor?.person?.etternavnvedfoedsel)
        //3.1.8.5. Forename of mother
        assertEquals("MOR", pensjonP1200?.gjenlevende?.mor?.person?.fornavn)

        //3.2.1. Additional information on the person
        //3.2.1.1. Nationality
        assertEquals("NO", pensjonP1200?.gjenlevende?.person?.statsborgerskap?.firstOrNull()?.land)
        //3.2.1.2. Previous family name(s)
        assertEquals("MORFAR", pensjonP1200?.gjenlevende?.person?.tidligereetternavn)
        //3.2.1.2. Previous forename(s)
        assertEquals("GJENLEVENDE", pensjonP1200?.gjenlevende?.person?.fornavn)
        //3.2.2. Address
        //3.2.2.1. Street
        assertEquals("VEIEN", pensjonP1200?.gjenlevende?.adresse?.gate)
        //3.2.2.2. Building Name
        assertEquals("B", pensjonP1200?.gjenlevende?.adresse?.bygning)
        //3.2.2.3. Town
        assertEquals("Oslo", pensjonP1200?.gjenlevende?.adresse?.by)
        //3.2.2.4. Postal Code
        assertEquals("0889", pensjonP1200?.gjenlevende?.adresse?.postnummer)
        //3.2.2.5. Region
        assertEquals("Oslo", pensjonP1200?.gjenlevende?.adresse?.region)
        //3.2.2.6. Country
        assertEquals("NO", pensjonP1200?.gjenlevende?.adresse?.land)
        //3.2.3.1.1 Telephone Number
        //3.2.3.1.1.1. Type
        assertEquals("hjem", pensjonP1200?.gjenlevende?.person?.kontakt?.telefon?.firstOrNull()?.type)
        //3.2.3.1.1.2. Number
        assertEquals("22191817", pensjonP1200?.gjenlevende?.person?.kontakt?.telefon?.firstOrNull()?.nummer)
        //3.2.3.2.1.1. Email Address
        assertEquals("mail@mail.no", pensjonP1200?.gjenlevende?.person?.kontakt?.email?.firstOrNull()?.adresse)

        /** 4. Reference to the person */
        //4.1. This reply is made with reference to the person named in
        assertEquals("01", pensjonP1200?.foresporsel?.referanseTilPerson)

        /** 5. Information on pension */
        //5.1 Details of payment
        //5.1.1. Type of pension in the sending institution (rina enum: [02] Invalidity)
        assertEquals("02", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.pensjonstype)
        //5.1.2. Payable from
        assertEquals("2021-02-10", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.fradato)
        //5.1.3. Payable to
        assertEquals("2026-01-05", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.betaldato)
        //5.1.4.1. Amount
        assertEquals("1999", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.belop)
        //5.1.4.2. Currency
        assertEquals("EUR", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.valuta)
        //5.1.4.3. Amount effective since
        assertEquals("2026-01-01", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.effektueringsdato)
        //5.1.4.4. Payment frequency mappingsfelt: (rina enum: 05 Monthly(14/year))
        assertEquals(Betalingshyppighet.maaned_14_per_aar.toString(), pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.utbetalingshyppighet)
        //5.1.4.5.1. Other payment frequency (fritekstfelt)
        assertEquals("daglig", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.annenutbetalingshyppighet)
        //5.1.5.1. The pension received is based on (rine enum: [02] Working)
        assertEquals("02", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.basertpaa)
        //5.1.5.2. Total amount of residence - based pension
        assertEquals("999", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.bosattotal)
        //5.1.5.3. Total amount of work related pension
        assertEquals("1000", pensjonP1200?.pensjoninfo?.betalingsdetaljer?.firstOrNull()?.arbeidstotal)
        //5.2. Supplements and additional frequent payments to the pension
        assertEquals("Trygd og ytelser", pensjonP1200?.pensjoninfo?.tilleggsytelserutbetalingitilleggtilpensjon)
        //5.3.1. Type of pension in the sending institution (rina mapping: [02] Invalidity
        assertEquals("02", pensjonP1200?.pensjoninfo?.pensjonsopphoring?.firstOrNull()?.pensjonstype)
        //5.3.1. Type of pension in the sending institution
        assertEquals("Feil informasjon oppgitt", pensjonP1200?.pensjoninfo?.pensjonsopphoring?.firstOrNull()?.begrunnelse)
        //5.4.1. Type of pension in the sending institution (rina mapping: [02] Invalidity
        assertEquals("02", pensjonP1200?.pensjoninfo?.pensjonsavslag?.firstOrNull()?.pensjonstype)
        //5.4.2. Reason for pension rejection
        assertEquals("Ikke gammel nok", pensjonP1200?.pensjoninfo?.pensjonsavslag?.firstOrNull()?.begrunnelse)

        /**6. Request for P13000 */
        //6.1. Please send us P13000
        assertEquals("1", pensjonP1200?.anmodning13000verdi)

        /**7. Additional information */
        //7.1. Additional information
        assertEquals("Her kan man legge inn ytterligere informasjon og litt til", pensjonP1200?.ytterligereInformasjon)

    }

    private fun p1200Json() =
        """
      {
          "sed" : "P12000",
          "nav" : {
            "eessisak" : [ {
              "institusjonsid" : "NO:NAVAT07",
              "institusjonsnavn" : "NAV ACCEPTANCE TEST 07",
              "saksnummer" : "22975052",
              "land" : "NO"
            } ],
            "bruker" : {
              "mor" : {
                "person" : {
                  "pin" : null,
                  "pinland" : null,
                  "statsborgerskap" : null,
                  "etternavn" : null,
                  "etternavnvedfoedsel" : "kajakk",
                  "fornavn" : "rød",
                  "fornavnvedfoedsel" : null,
                  "tidligerefornavn" : null,
                  "tidligereetternavn" : null,
                  "kjoenn" : null,
                  "foedested" : null,
                  "foedselsdato" : null,
                  "sivilstand" : null,
                  "relasjontilavdod" : null,
                  "rolle" : null,
                  "kontakt" : null,
                  "doedsdato" : null
                }
              },
              "far" : {
                "person" : {
                  "pin" : null,
                  "pinland" : null,
                  "statsborgerskap" : null,
                  "etternavn" : null,
                  "etternavnvedfoedsel" : "kano",
                  "fornavn" : "grønn",
                  "fornavnvedfoedsel" : null,
                  "tidligerefornavn" : null,
                  "tidligereetternavn" : null,
                  "kjoenn" : null,
                  "foedested" : null,
                  "foedselsdato" : null,
                  "sivilstand" : null,
                  "relasjontilavdod" : null,
                  "rolle" : null,
                  "kontakt" : null,
                  "doedsdato" : null
                }
              },
              "person" : {
                "pin" : [ {
                  "institusjonsnavn" : "NAV ACCEPTANCE TEST 07",
                  "institusjonsid" : "NO:NAVAT07",
                  "sektor" : "pensjoner",
                  "identifikator" : "16446208164",
                  "land" : "NO",
                  "institusjon" : null
                }, {
                  "institusjonsnavn" : null,
                  "institusjonsid" : null,
                  "sektor" : null,
                  "identifikator" : "160462-587-45",
                  "land" : "BE",
                  "institusjon" : null
                } ],
                "pinland" : null,
                "statsborgerskap" : [ {
                  "land" : "NO"
                }, {
                  "land" : "BE"
                }, {
                  "land" : "DK"
                }, {
                  "land" : "SE"
                } ],
                "etternavn" : "KORRIDOR",
                "etternavnvedfoedsel" : "Gang",
                "fornavn" : "ARITMETISK",
                "fornavnvedfoedsel" : "trang",
                "tidligerefornavn" : "skitten",
                "tidligereetternavn" : "bil",
                "kjoenn" : "M",
                "foedested" : {
                  "by" : "Bergen",
                  "land" : "NO",
                  "region" : "vestland"
                },
                "foedselsdato" : "1962-04-16",
                "sivilstand" : null,
                "relasjontilavdod" : null,
                "rolle" : null,
                "kontakt" : {
                  "telefon" : [ {
                    "type" : "mobil",
                    "nummer" : "99999999"
                  } ],
                  "email" : [ {
                    "adresse" : "post@noreply.no"
                  } ]
                },
                "doedsdato" : null
              },
              "adresse" : {
                "gate" : "1KOLEJOWA 6/5",
                "bygning" : "utsikten",
                "by" : "CAPITAL WEST",
                "postnummer" : "3000",
                "postkode" : null,
                "region" : "Bretagne",
                "land" : "FR",
                "kontaktpersonadresse" : null,
                "datoforadresseendring" : null,
                "postadresse" : null,
                "startdato" : null,
                "type" : null,
                "annen" : null
              },
              "arbeidsforhold" : null,
              "bank" : null
            },
            "ektefelle" : null,
            "barn" : null,
            "verge" : null,
            "krav" : null,
            "annenperson" : null
          },
          "pensjon" : {
            "pensjoninfo" : {
              "betalingsdetaljer" : [ {
                "fradato" : "2021-02-10",
                "belop" : "1999",
                "effektueringsdato" : "2026-01-01",
                "annenutbetalingshyppighet" : "daglig",
                "valuta" : "EUR",
                "utbetalingshyppighet" : "maaned_14_per_aar",
                "pensjonstype" : "02",
                "basertpaa" : "02",
                "bosattotal" : "999",
                "arbeidstotal" : "1000",
                "betaldato" : "2026-01-05"
              } ],
              "pensjonsavslag" : [ {
                "begrunnelse" : "Ikke gammel nok",
                "pensjonstype" : "02"
              } ],
              "pensjonsopphoring" : [ {
                "begrunnelse" : "Feil informasjon oppgitt",
                "pensjonstype" : "02"
              } ],
              "tilleggsytelserutbetalingitilleggtilpensjon" : "Trygd og ytelser"
            },
            "ytterligereInformasjon" : "Her kan man legge inn ytterligere informasjon og litt til",
            "foresporsel" : {
              "referanseTilPerson" : "01"
            },
            "anmodning13000verdi" : "1",
            "gjenlevende" : {
              "mor" : {
                "person" : {
                  "pin" : null,
                  "pinland" : null,
                  "statsborgerskap" : null,
                  "etternavn" : null,
                  "etternavnvedfoedsel" : "MOR",
                  "fornavn" : "MOR",
                  "fornavnvedfoedsel" : null,
                  "tidligerefornavn" : null,
                  "tidligereetternavn" : null,
                  "kjoenn" : null,
                  "foedested" : null,
                  "foedselsdato" : null,
                  "sivilstand" : null,
                  "relasjontilavdod" : null,
                  "rolle" : null,
                  "kontakt" : null,
                  "doedsdato" : null
                }
              },
              "far" : {
                "person" : {
                  "pin" : null,
                  "pinland" : null,
                  "statsborgerskap" : null,
                  "etternavn" : null,
                  "etternavnvedfoedsel" : "FAR",
                  "fornavn" : "FAR",
                  "fornavnvedfoedsel" : null,
                  "tidligerefornavn" : null,
                  "tidligereetternavn" : null,
                  "kjoenn" : null,
                  "foedested" : null,
                  "foedselsdato" : null,
                  "sivilstand" : null,
                  "relasjontilavdod" : null,
                  "rolle" : null,
                  "kontakt" : null,
                  "doedsdato" : null
                }
              },
              "person" : {
                "pin" : [ {
                  "institusjonsnavn" : "ACC_Zachodniopomorski Regional Branch of NHF",
                  "institusjonsid" : "PL:WF16S",
                  "sektor" : "pensjoner",
                  "identifikator" : "BE-235-1334",
                  "land" : "PL",
                  "institusjon" : null
                }, {
                  "institusjonsnavn" : "ACC_PROVINCIAL HEAD OFFICE OF NAVARRA",
                  "institusjonsid" : "ES:3117",
                  "sektor" : "pensjoner",
                  "identifikator" : "ES-4567",
                  "land" : "ES",
                  "institusjon" : null
                } ],
                "pinland" : null,
                "statsborgerskap" : [ {
                  "land" : "NO"
                } ],
                "etternavn" : "MOTTAKER",
                "etternavnvedfoedsel" : "fisk",
                "fornavn" : "GJENLEVENDE",
                "fornavnvedfoedsel" : "død",
                "tidligerefornavn" : "GJENLEVENDE",
                "tidligereetternavn" : "MORFAR",
                "kjoenn" : "K",
                "foedested" : {
                  "by" : "Oslo",
                  "land" : "NO",
                  "region" : "Oslo"
                },
                "foedselsdato" : "1978-02-13",
                "sivilstand" : null,
                "relasjontilavdod" : null,
                "rolle" : null,
                "kontakt" : {
                  "telefon" : [ {
                    "type" : "hjem",
                    "nummer" : "22191817"
                  }, {
                    "type" : "mobil",
                    "nummer" : "98765423"
                  } ],
                  "email" : [ {
                    "adresse" : "mail@mail.no"
                  } ]
                },
                "doedsdato" : null
              },
              "adresse" : {
                "gate" : "VEIEN",
                "bygning" : "B",
                "by" : "Oslo",
                "postnummer" : "0889",
                "postkode" : null,
                "region" : "Oslo",
                "land" : "NO",
                "kontaktpersonadresse" : null,
                "datoforadresseendring" : null,
                "postadresse" : null,
                "startdato" : null,
                "type" : null,
                "annen" : null
              }
            }
          },
          "sedGVer" : "4",
          "sedVer" : "4"
        }
        """.trimIndent()
}