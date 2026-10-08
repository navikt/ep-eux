package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P2200Test {

    @Test
    fun mapJsonToP2200() {

        val p2200 = mapJsonToAny<P2200>(p2200Json())

        val bruker = p2200.navP2200?.bruker
        val forsikret = p2200.navP2200?.bruker?.person
        val brukerMor = p2200.navP2200?.bruker?.mor?.person
        val uforhet = p2200.navP2200?.bruker?.uforhet

        val p2200json = p2200.toJsonSkipEmpty()
        JSONAssert.assertEquals(p2200json, p2200Json(), true)
        JSONAssert.assertEquals(p2200Json(), p2200json, true)

        //1.1.1  Country
        assertEquals("AT", p2200.navP2200?.eessisak?.firstOrNull()?.land)
        //1.1.2 Case number
        assertEquals("123", p2200.navP2200?.eessisak?.firstOrNull()?.saksnummer)
        //1.1.3.1 Institution ID
        assertEquals(null, p2200.navP2200?.eessisak?.firstOrNull()?.institusjonsid)
        //1.1.3.2 Institution Name
        assertEquals(null, p2200.navP2200?.eessisak?.firstOrNull()?.institusjonsnavn)

        /** 2. Insured person */
        //2.1.1 Family name(s)
        assertEquals("kompis", forsikret?.etternavn)
        //2.1.2 Forename(s)
        assertEquals("sur", forsikret?.fornavn)
        //2.1.3 Date of birth
        assertEquals("2004-02-04", forsikret?.foedselsdato)
        //2.1.4. Sex
        assertEquals("K", forsikret?.kjoenn)
        //2.1.5. Family name(s) at birth
        assertEquals("venn", forsikret?.etternavnvedfoedsel)
        //2.1.6. Forename(s) at birth
        assertEquals("blid", forsikret?.fornavnvedfoedsel)

        // Personal Identification Number(s)*
        //2.1.7.1.1. Country
        assertEquals("AT", forsikret?.pin?.firstOrNull()?.land)
        //2.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("123456789", forsikret?.pin?.firstOrNull()?.identifikator)
        //2.1.7.1.3. Sector
        assertEquals("pensjoner", forsikret?.pin?.firstOrNull()?.sektor)
        //2.1.7.1.4.1. Institution ID
        assertEquals("NO:NAVAT01", forsikret?.pin?.firstOrNull()?.institusjon?.institusjonsid)
        //2.1.7.1.4.2. Institution Name
        assertEquals("NAV ACC 01", forsikret?.pin?.firstOrNull()?.institusjon?.institusjonsnavn)
        //2.1.8.1.1. Town
        assertEquals("Bergen", forsikret?.foedested?.by)
        //2.1.8.1.2. Region
        assertEquals("Vestland", forsikret?.foedested?.region)
        //2.1.8.1.3. Country
        assertEquals("NO", forsikret?.foedested?.land)
        //2.1.8.2. Father's family name at birth
        assertEquals("venn", forsikret?.etternavnvedfoedsel)
        //2.1.8.3. Forename of father
        assertEquals("God", bruker?.far?.person?.fornavn)
        //2.1.8.4. Mother's family name at birth
        assertEquals("Forbilde", brukerMor?.etternavnvedfoedsel)
        //2.1.8.5. Forename of mother
        assertEquals("dårlig", brukerMor?.fornavn)

        //2.2.1.1. Nationality
        //2.2.1.2. Previous family name(s)
        assertEquals("Sykkel", forsikret?.tidligereetternavn)
        //2.2.1.3. Previous forename(s)
        assertEquals("Jente", forsikret?.tidligerefornavn)
        //2.2.2.1. Family Status (rina enum 02 Married)
        assertEquals(SivilstandRina.gift, forsikret?.sivilstand?.firstOrNull()?.status)
        //2.2.2.2. Family status date
        assertEquals("2024-05-07", forsikret?.sivilstand?.firstOrNull()?.fradato)
        //2.2.3. Address
        //2.2.3.1. Street
        assertEquals("Storgata 4", bruker?.adresse?.gate)
        //2.2.3.2. Building Name
        assertEquals("Høyblokka", bruker?.adresse?.bygning)
        //2.2.3.3. Town
        assertEquals("Bergen", bruker?.adresse?.by)
        //2.2.3.4. Postal Code
        assertEquals("5000", bruker?.adresse?.postnummer)
        //2.2.3.5. Region
        assertEquals("Vestland", bruker?.adresse?.region)
        //2.2.3.6. Country
        assertEquals("CY", bruker?.adresse?.land)
        //2.2.4.1.1.1. Type
        assertEquals("mobil", forsikret?.kontakt?.telefon?.firstOrNull()?.type)
        //2.2.4.1.1.2. Number
        assertEquals("99999999", forsikret?.kontakt?.telefon?.firstOrNull()?.nummer)
        //2.2.4.2.1.1. Email Address
        assertEquals("hei@post.no", forsikret?.kontakt?.email?.firstOrNull()?.adresse)

        /** 2.3. Invalidity information */
        //2.3.1. Start date of commencement of invalidity (determined by pension institution)
        assertEquals("2025-06-01", uforhet?.startDatoPensjon)
        //2.3.2. Start date of commencement of work incapacity (according to medical statement)
        assertEquals("2025-05-22", uforhet?.startdatoLege)
        //2.3.3. Invalidity result of work accident - occupational disease
        assertEquals("0", uforhet?.arbeidsUlykke)
        //2.3.4. Invalidity result of injuries/accidents connected to military service
        assertEquals("1", uforhet?.militartjenesteUlykke)
        //2.3.5. Invalidity caused by insured person on purpose
        assertEquals("0", uforhet?.bevisstforsaketSoker)
        //2.3.6. Invalidity caused by liable third party
        assertEquals("0", uforhet?.ansvarligTredjepart)

        val pensjonP2200 = p2200.pensjon
        /** 3. Insured person's employment and self-employment details */
        //3.1.1. Occupation mappingfelt: occupation
        assertEquals("Bødker", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.yrke)
        //3.1.2. Employment and self-employment mappingsfelt: employmentSelfEmployment
        assertEquals("forsikrede_driver_fortsatt_selvstendig_naerigsvirksomhet", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.type)
        //3.1.3. Start date of intended employment or self-employment mappingsfelt: startDateIntendedEmploymentOrSelfEmployment
        assertEquals("2025-06-05", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.planlagtstartdato)
        //3.1.4. End date of employment or self-employment mappingfelt: endDateEmploymentOrSelfEmployment
        assertEquals("2025-12-14", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.sluttdato)
        //3.1.5. Intended retirement date from employment or self-employment mappingsfelt: intendedRetirementDateFromEmploymentOrSelfEmployment
        assertEquals("2025-11-13", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.planlagtpensjoneringsdato)
        //3.1.6. Hours per week mappingsfelt: hoursPerWeek
        assertEquals("12", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.arbeidstimerperuke)

        //3.1.7.1. Amount mappingfelt: amount
        assertEquals("123", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.beloep)
        //3.1.7.2. Currency mappingsfelt: currency
        assertEquals("EUR", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.valuta)
        //3.1.7.3. Amount effective since mappingsfelt: amountEffectiveSince
        assertEquals("2025-06-04", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.beloeputbetaltsiden)
        //3.1.7.4. Payment frequency mappingsfelt: paymentFrequency (rina enum mapping)
        assertEquals("03", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.betalingshyppighetinntekt)
        //3.1.7.5.1. Other payment frequency mappingsfelt: otherPaymentFrequency
//        assertEquals("timelønn", pensjonP2200?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.annenbetalingshyppighetinntekt)
        assertEquals("timelønn", pensjonP2200?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.annenbetalingshyppighetytelse)

        /** 4 Insured person's benefit details */
        //4.1.1. Benefits mappingsfelt: benefits (rina enum mapping)
        assertEquals("08", pensjonP2200?.ytelser?.firstOrNull()?.ytelse)
        //4.1.2.1. Other benefit mappingsfelt: otherBenefit
        assertEquals(null, pensjonP2200?.ytelser?.firstOrNull()?.annenytelse)
        //4.1.3. Status mappingsfelt:status? (rina enum)
        assertEquals("02", pensjonP2200?.ytelser?.firstOrNull()?.status)
        //4.1.4.1.1. Country
        assertEquals("AT", pensjonP2200?.ytelser?.firstOrNull()?.institusjon?.land)
        //4.1.4.1.3. Sector sector mappingsfelt: sector ($nav.bruker.person.pin[x].sektor)
        assertEquals("pensjoner", bruker?.person?.pin?.firstOrNull()?.sektor)
        //4.1.4.1.4.1. Institution ID
        assertEquals("NO:NAVAT01", pensjonP2200?.ytelser?.firstOrNull()?.institusjon?.institusjonsid)
        //4.1.4.1.4.2. Institution Name
        assertEquals("NAV ACC 01", pensjonP2200?.ytelser?.firstOrNull()?.institusjon?.institusjonsnavn)
        //4.1.4.2. Case Number
        assertEquals("154", pensjonP2200?.ytelser?.firstOrNull()?.institusjon?.saksnummer)
        //4.1.5. Start date of benefits payment mappingsfelt: startDateBenefitsPayment ($pensjon.ytelser[x].startdatoutbetaling)
        assertEquals("2025-06-04", pensjonP2200?.ytelser?.firstOrNull()?.startdatoutbetaling)
        //4.1.6. End date of benefits payment
        assertEquals("2025-07-11", pensjonP2200?.ytelser?.firstOrNull()?.sluttdatoRettTilUtbetaling)
        //4.1.7. Start date of entitlement to benefits
        assertEquals("2025-03-07", pensjonP2200?.ytelser?.firstOrNull()?.startdatoretttilytelse)
        //4.1.8. End date of entitlement to benefits
        assertEquals("2025-11-21", pensjonP2200?.ytelser?.firstOrNull()?.sluttdatoutbetaling)
        //4.1.9.1. Amount
        assertEquals("55", pensjonP2200?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.beloep)
        //4.1.9.2. Currency
        assertEquals("EUR", pensjonP2200?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.valuta)
        //4.1.9.3. Amount effective since
        assertEquals("2025-07-11", pensjonP2200?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.gjeldendesiden)
        //4.1.9.4. Payment frequency mappingsfelt: (rina enum: 02 Quarterly)
        assertEquals(Betalingshyppighet.kvartalsvis, pensjonP2200?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.betalingshyppighetytelse)
        //4.1.9.5.1. Other payment frequency
        assertEquals("fritekstfelt", pensjonP2200?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.utbetalingshyppighetAnnen)
        //4.1.10.1. The pension received is based on
        assertEquals("45", pensjonP2200?.ytelser?.firstOrNull()?.mottasbasertpaaitem?.firstOrNull()?.totalbruttobeloepbostedsbasert)
        //4.1.10.2. Total gross amount of residence - based pension (rina enum 01 tilsvarer Residence)
        assertEquals("01", pensjonP2200?.ytelser?.firstOrNull()?.mottasbasertpaaitem?.firstOrNull()?.verdi)
        //4.1.10.3. Total gross amount of work related pension
        assertEquals("54", pensjonP2200?.ytelser?.firstOrNull()?.mottasbasertpaaitem?.firstOrNull()?.totalbruttobeloeparbeidsbasert)

        /** 5 Spouse */
        //5.1. Spouse type
        assertEquals("ektefelle", p2200.navP2200?.ektefelle?.type )
        //5.2.1. Family name(s)
        assertEquals("regn", p2200.navP2200?.ektefelle?.person?.etternavn )
        //5.2.2. Forename(s)
        assertEquals("heftig", p2200.navP2200?.ektefelle?.person?.fornavn )
        //5.2.3. Date of birth
        assertEquals("2004-02-04", p2200.navP2200?.ektefelle?.person?.foedselsdato )
        //5.2.4. Sex (rina enum 01 Male)
        assertEquals("M", p2200.navP2200?.ektefelle?.person?.kjoenn )
        //5.2.5. Family name(s) at birth
        assertEquals("kjele", p2200.navP2200?.ektefelle?.person?.etternavnvedfoedsel )
        //5.2.6. Forename(s) at birth
        assertEquals("liten", p2200.navP2200?.ektefelle?.person?.fornavnvedfoedsel )
        //5.2.7.1.1. Country
        assertEquals("BE", p2200.navP2200?.ektefelle?.person?.pin?.firstOrNull()?.land )
        //5.2.7.1.2. Personal Identification Number (PIN)
        assertEquals("1122", p2200.navP2200?.ektefelle?.person?.pin?.firstOrNull()?.identifikator )
        //5.2.7.1.3. Sector (rina enum 04 Pensions)
        assertEquals("pensjoner", p2200.navP2200?.ektefelle?.person?.pin?.firstOrNull()?.sektor )
        //5.2.7.1.4.1. Institution ID
        assertEquals("NO:NAVAT05", p2200.navP2200?.ektefelle?.person?.pin?.firstOrNull()?.institusjon?.institusjonsid)
        //5.2.7.1.4.2. Institution Name
        assertEquals("NAV ACC 05", p2200.navP2200?.ektefelle?.person?.pin?.firstOrNull()?.institusjon?.institusjonsnavn)
        //5.2.8.1.1. Town
        assertEquals("moss", p2200.navP2200?.ektefelle?.person?.foedested?.by)
        //5.2.8.1.2. Region
        assertEquals("østfold", p2200.navP2200?.ektefelle?.person?.foedested?.region)
        //5.2.8.1.3. Country
        assertEquals("NO", p2200.navP2200?.ektefelle?.person?.foedested?.land)
        //5.2.8.2. Father's family name at birth
        assertEquals("ert", p2200.navP2200?.ektefelle?.far?.person?.etternavnvedfoedsel)
        //5.2.8.3. Forename of father
        assertEquals("grønn", p2200.navP2200?.ektefelle?.far?.person?.fornavn)
        //5.2.8.4. Mother's family name at birth
        assertEquals("kommode", p2200.navP2200?.ektefelle?.mor?.person?.etternavnvedfoedsel)
        //5.2.8.5. Forename of mother
        assertEquals("brun", p2200.navP2200?.ektefelle?.mor?.person?.fornavn)
        //5.3.1. Nationality
        assertEquals("DZ", p2200.navP2200?.ektefelle?.person?.statsborgerskap?.firstOrNull()?.land)
        //5.3.2. Previous family name(s)
        assertEquals("båt", p2200.navP2200?.barn?.firstOrNull()?.far?.person?.etternavnvedfoedsel)
        //5.3.3. Previous forename(s)
        assertEquals("liten", p2200.navP2200?.barn?.firstOrNull()?.far?.person?.fornavn)

        /** 6. Children */
        //6.1.1.1. Family name(s)
        assertEquals("ballong", p2200.navP2200?.barn?.firstOrNull()?.person?.etternavn)
        //6.1.1.2. Forename(s)
        assertEquals("blå", p2200.navP2200?.barn?.firstOrNull()?.person?.fornavn)
        //6.1.1.3. Date of birth
        assertEquals("2022-12-25", p2200.navP2200?.barn?.firstOrNull()?.person?.foedselsdato)
        //6.1.1.4. Sex  (rina enum 01 Male)
        assertEquals("M", p2200.navP2200?.barn?.firstOrNull()?.person?.kjoenn)
        //6.1.1.5. Family name(s) at birth
        assertEquals("grå", p2200.navP2200?.barn?.firstOrNull()?.person?.etternavnvedfoedsel)
        //6.1.1.6. Forename(s) at birth
        assertEquals("bil", p2200.navP2200?.barn?.firstOrNull()?.person?.fornavnvedfoedsel)
        //6.1.1.7.1.1. Country
        assertEquals("BE", p2200.navP2200?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.land)
        //6.1.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("125", p2200.navP2200?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.identifikator)
        //6.1.1.7.1.3. Sector (rina enum 04 Pensions)
        assertEquals("pensjoner", p2200.navP2200?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.sektor)
        //6.1.1.7.1.4.1. Institution ID
        assertEquals("NO:NAVAT05", p2200.navP2200?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.institusjon?.institusjonsid)
        //6.1.1.7.1.4.2. Institution Name
        assertEquals("NAV ACC 05", p2200.navP2200?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.institusjon?.institusjonsnavn)
        //6.1.1.8.1.1. Town
        assertEquals("Bø", p2200.navP2200?.barn?.firstOrNull()?.person?.foedested?.by)
        //6.1.1.8.1.2. Region
        assertEquals("Nordland", p2200.navP2200?.barn?.firstOrNull()?.person?.foedested?.region)
        //6.1.1.8.1.3. Country
        assertEquals("NO", p2200.navP2200?.barn?.firstOrNull()?.person?.foedested?.land)
        //6.1.1.8.2. Father's family name at birth
        assertEquals("båt", p2200.navP2200?.barn?.firstOrNull()?.far?.person?.etternavnvedfoedsel)
        //6.1.1.8.3. Forename of father
        assertEquals("liten", p2200.navP2200?.barn?.firstOrNull()?.far?.person?.fornavn)
        //6.1.1.8.4. Mother's family name at birth
        assertEquals("ball", p2200.navP2200?.barn?.firstOrNull()?.mor?.person?.etternavnvedfoedsel)
        //6.1.1.8.5. Forename of mother
        assertEquals("stor", p2200.navP2200?.barn?.firstOrNull()?.mor?.person?.fornavn)
        //6.1.2.1. Nationality
        assertEquals("BG", p2200.navP2200?.barn?.firstOrNull()?.person?.statsborgerskap?.firstOrNull()?.land)
        //6.1.2.2. Relationship to the insured person (rina enum 01 Own Child)
        assertEquals("eget_barn", p2200.navP2200?.barn?.firstOrNull()?.getRelasjontilbruker43())
        //6.1.2.3.1. Specifics on "Other child"
        assertEquals("opplysningeromannetbarn", p2200.navP2200?.barn?.firstOrNull()?.opplysningeromannetbarn)
        //6.1.2.4. Date of death

        /** 7. Information on representative/legal guardian */
        //7.1. Family name
        assertEquals("x", p2200.navP2200?.verge?.person?.etternavn)
        //7.2. Forename
        assertEquals("y", p2200.navP2200?.verge?.person?.fornavn)
        //7.3. Grounds
        assertEquals("kjennelse", p2200.navP2200?.verge?.vergemaal?.mandat)

        //7.4.1. Street
        assertEquals("h", p2200.navP2200?.verge?.adresse?.gate)
        //7.4.2. Building Name
        assertEquals("i", p2200.navP2200?.verge?.adresse?.bygning)
        //7.4.3. Town
        assertEquals("b", p2200.navP2200?.verge?.adresse?.by)
        //7.4.4. Postal Code
        assertEquals("3200", p2200.navP2200?.verge?.adresse?.postnummer)
        //7.4.5. Region
        assertEquals("ff", p2200.navP2200?.verge?.adresse?.region)
        //7.4.6. Country
        assertEquals("AT", p2200.navP2200?.verge?.adresse?.land)
        //7.5.1.1.1. Type (rina enum 02 Mobile)
        assertEquals("mobil", p2200.navP2200?.verge?.person?.kontakt?.telefon?.firstOrNull()?.type)
        //7.5.1.1.2. Number mappingsfelt: number
        assertEquals("888888888", p2200.navP2200?.verge?.person?.kontakt?.telefon?.firstOrNull()?.nummer)
        //7.5.2.1.1. Email Address mappingsfelt: EmailAddress
        assertEquals("gh@post.no", p2200.navP2200?.verge?.person?.kontakt?.email?.firstOrNull()?.adresse)

        /** 8. Information on payment */
        //8.1. Payment to mappingsfelt: recipientDecision
        assertEquals("forsikret_person", pensjonP2200?.vedtak?.firstOrNull()?.mottaker?.firstOrNull())
        //8.2.1. Account holder name mappingsfelt: accountHolderName
        assertEquals("gg", p2200.navP2200?.bruker?.bank?.konto?.innehaver?.navn)
        //8.2.2.1. IBAN mappingsfelt: IBAN
        assertEquals("no9386011117947", p2200.navP2200?.bruker?.bank?.konto?.sepa?.iban)
        //8.2.2.2. BIC-SWIFT mappingsfelt: BICSWIFT
        assertEquals("dnbanokxxxx", p2200.navP2200?.bruker?.bank?.konto?.sepa?.swift)

        /** 9. Miscellaneous */
        //9.1. Date of claim mappingsfelt: dateClaim
        assertEquals("2026-07-02", p2200.navP2200?.krav?.dato)
        //9.2. Recipient of the decision mappingsfelt: recipientDecision
        assertEquals("forsikret_person", pensjonP2200?.vedtak?.firstOrNull()?.mottaker?.firstOrNull())
        //9.3. Deductions grounds mappingsfelt: deductionsGrounds
        assertEquals("987_2009_Art_72_1", pensjonP2200?.vedtak?.firstOrNull()?.trekkgrunnlag?.firstOrNull())
        //9.4. Attachments mappingsfelt: attachments
        assertEquals("utførlig_medisinsk_rapport", pensjonP2200?.vedlegg?.firstOrNull())
        //9.5.1. Other attachment mappingsfelt: otherAttachment
        assertEquals("pass", pensjonP2200?.vedleggandre)
        //9.6. Requested documents mappingsfelt: requestedDocuments
        assertEquals("p5000", pensjonP2200?.etterspurtedokumenter)
        //9.7. Additional information mappingsfelt: additionalInformation
        assertEquals("ingen", pensjonP2200?.ytterligeinformasjon)

    }

    private fun p2200Json() =
        """
        {
          "nav": {
            "bruker": {
              "mor": {
                "person": {
                  "etternavnvedfoedsel": "Forbilde",
                  "fornavn": "dårlig"
                }
              },
              "person": {
                "fornavn": "sur",
                "sivilstand": [
                  {
                    "status": "gift",
                    "fradato": "2024-05-07"
                  }
                ],
                "kjoenn": "K",
                "etternavn": "kompis",
                "kontakt": {
                  "email": [
                    {
                      "adresse": "hei@post.no"
                    }
                  ],
                  "telefon": [
                    {
                      "nummer": "99999999",
                      "type": "mobil"
                    }
                  ]
                },
                "foedselsdato": "2004-02-04",
                "tidligereetternavn": "Sykkel",
                "statsborgerskap": [
                  {
                    "land": "HR"
                  }
                ],
                "pin": [
                  {
                    "sektor": "pensjoner",
                    "identifikator": "123456789",
                    "institusjon": {
                      "institusjonsid": "NO:NAVAT01",
                      "institusjonsnavn": "NAV ACC 01"
                    },
                    "land": "AT"
                  }
                ],
                "foedested": {
                  "region": "Vestland",
                  "land": "NO",
                  "by": "Bergen"
                },
                "fornavnvedfoedsel": "blid",
                "tidligerefornavn": "Jente",
                "etternavnvedfoedsel": "venn"
              },
              "uforhet": {
                "arbeidsUlykke": "0",
                "startDatoPensjon": "2025-06-01",
                "startdatoLege": "2025-05-22",
                "militartjenesteUlykke": "1",
                "ansvarligTredjepart": "0",
                "bevisstforsaketSoker": "0"
              },
              "adresse": {
                "region": "Vestland",
                "land": "CY",
                "gate": "Storgata 4",
                "bygning": "Høyblokka",
                "postnummer": "5000",
                "by": "Bergen"
              },
              "bank": {
                "konto": {
                  "innehaver": {
                    "navn": "gg",
                    "rolle": "forsikret_person"
                  },
                  "sepa": {
                    "swift": "dnbanokxxxx",
                    "iban": "no9386011117947"
                  }
                }
              },
              "far": {
                "person": {
                  "fornavn": "God",
                  "etternavnvedfoedsel": "Venn"
                }
              }
            },
            "barn": [
              {
                "opplysningeromannetbarn": "opplysningeromannetbarn",
                "relasjontilbruker43": "eget_barn",
                "far": {
                  "person": {
                    "etternavnvedfoedsel": "båt",
                    "fornavn": "liten"
                  }
                },
                "person": {
                  "pin": [
                    {
                      "identifikator": "125",
                      "institusjon": {
                        "institusjonsnavn": "NAV ACC 05",
                        "institusjonsid": "NO:NAVAT05"
                      },
                      "sektor": "pensjoner",
                      "land": "BE"
                    }
                  ],
                  "foedested": {
                    "by": "Bø",
                    "land": "NO",
                    "region": "Nordland"
                  },
                  "foedselsdato": "2022-12-25",
                  "kjoenn": "M",
                  "fornavn": "blå",
                  "statsborgerskap": [
                    {
                      "land": "BG"
                    }
                  ],
                  "etternavn": "ballong",
                  "doedsdato": "2026-09-17",
                  "etternavnvedfoedsel": "grå",
                  "fornavnvedfoedsel": "bil"
                },
                "mor": {
                  "person": {
                    "fornavn": "stor",
                    "etternavnvedfoedsel": "ball"
                  }
                }
              }
            ],
            "verge": {
              "person": {
                "kontakt": {
                  "telefon": [
                    {
                      "nummer": "888888888",
                      "type": "mobil"
                    }
                  ],
                  "email": [
                    {
                      "adresse": "gh@post.no"
                    }
                  ]
                },
                "fornavn": "y",
                "etternavn": "x"
              },
              "vergemaal": {
                "mandat": "kjennelse"
              },
              "adresse": {
                "by": "b",
                "postnummer": "3200",
                "gate": "h",
                "land": "AT",
                "region": "ff",
                "bygning": "i"
              }
            },
            "krav": {
              "dato": "2026-07-02"
            },
            "ektefelle": {
              "mor": {
                "person": {
                  "etternavnvedfoedsel": "kommode",
                  "fornavn": "brun"
                }
              },
              "person": {
                "pin": [
                  {
                    "land": "BE",
                    "institusjon": {
                      "institusjonsnavn": "NAV ACC 05",
                      "institusjonsid": "NO:NAVAT05"
                    },
                    "identifikator": "1122",
                    "sektor": "pensjoner"
                  }
                ],
                "statsborgerskap": [
                  {
                    "land": "DZ"
                  }
                ],
                "etternavnvedfoedsel": "kjele",
                "fornavn": "heftig",
                "tidligereetternavn": "båt",
                "foedested": {
                  "region": "østfold",
                  "land": "NO",
                  "by": "moss"
                },
                "etternavn": "regn",
                "foedselsdato": "2004-02-04",
                "tidligerefornavn": "liten",
                "kjoenn": "M",
                "fornavnvedfoedsel": "liten"
              },
              "type": "ektefelle",
              "far": {
                "person": {
                  "fornavn": "grønn",
                  "etternavnvedfoedsel": "ert"
                }
              }
            },
            "eessisak": [
              {
                "saksnummer": "123",
                "land": "AT"
              }
            ]
          },
          "pensjon": {
            "ytelser": [
              {
                "mottasbasertpaaitem": [
                  {
                    "totalbruttobeloeparbeidsbasert": "54",
                    "verdi": "01",
                    "totalbruttobeloepbostedsbasert": "45"
                  }
                ],
                "status": "02",
                "startdatoutbetaling": "2025-06-04",
                "sluttdatoRettTilUtbetaling": "2025-07-11",
                "institusjon": {
                  "saksnummer": "154",
                  "land": "AT",
                  "institusjonsnavn": "NAV ACC 01",
                  "sektor": "pensjoner",
                  "institusjonsid": "NO:NAVAT01"
                },
                "beloep": [
                  {
                    "gjeldendesiden": "2025-07-11",
                    "beloep": "55",
                    "valuta": "EUR",
                    "betalingshyppighetytelse": "02",
                    "utbetalingshyppighetAnnen": "fritekstfelt"
                  }
                ],
                "startdatoretttilytelse": "2025-03-07",
                "sluttdatoutbetaling": "2025-11-21",
                "pin": {
                  "identifikator": "123"
                },
                "ytelse": "08"
              }
            ],
            "ytterligeinformasjon": "ingen",
            "bruker": {
              "arbeidsforhold": [
                {
                  "yrke": "Bødker",
                  "planlagtpensjoneringsdato": "2025-11-13",
                  "inntekt": [
                    {
                      "beloeputbetaltsiden": "2025-06-04",
                      "annenbetalingshyppighetinntekt": "timelønn",
                      "beloep": "123",
                      "valuta": "EUR",
                      "betalingshyppighetinntekt": "03"
                    }
                  ],
                  "type": "forsikrede_driver_fortsatt_selvstendig_naerigsvirksomhet",
                  "arbeidstimerperuke": "12",
                  "sluttdato": "2025-12-14",
                  "planlagtstartdato": "2025-06-05"
                }
              ]
            },
            "etterspurtedokumenter": "p5000",
            "vedtak": [
              {
                "mottaker": [
                  "forsikret_person"
                ],
                "trekkgrunnlag": [
                  "987_2009_Art_72_1"
                ]
              }
            ],
            "vedlegg": [
              "utførlig_medisinsk_rapport"
            ],
            "vedleggandre": "pass"
          },
          "sedVer": "4",
          "sedGVer": "4",
          "sed": "P2200"
        }
        """.trimIndent()
}