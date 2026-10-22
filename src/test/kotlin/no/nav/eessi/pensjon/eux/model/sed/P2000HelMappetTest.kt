package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P2000HelMappetTest {

    @Test
    fun mapJsonToP2000() {

        //Rinasak i q2: 1457786 Dokid:cc84e6a9f62642248b147e63f88695dd
        val p2000 = mapJsonToAny<P2000>(p2000Json())

        val bruker = p2000.nav?.bruker
        val forsikret = p2000.nav?.bruker?.person
        val brukerMor = p2000.nav?.bruker?.mor?.person
        val pensjon = p2000.p2000pensjon

        val p2000SkipNullJson = p2000.toJsonSkipEmpty()
        JSONAssert.assertEquals(p2000SkipNullJson, p2000Json(), true)
//        JSONAssert.assertEquals(p2000Json(), p2000SkipNullJson, true)

        //1.1.1  Country
        assertEquals("AT", p2000.nav?.eessisak?.firstOrNull()?.land)
        //1.1.2 Case number
        assertEquals("112Saksnummer", p2000.nav?.eessisak?.firstOrNull()?.saksnummer)
        //1.1.3.1 Institution ID
        assertEquals("ES:3117", p2000.nav?.eessisak?.firstOrNull()?.institusjonsid)
        //1.1.3.2 Institution Name
        assertEquals("ACC_PROVINCIAL HEAD OFFICE OF NAVARRA", p2000.nav?.eessisak?.firstOrNull()?.institusjonsnavn)

        /** 2. Insured person */
        //2.1.1 Family name(s)
        assertEquals("211Etternavn", forsikret?.etternavn)
        //2.1.2 Forename(s)
        assertEquals("212Fornavn", forsikret?.fornavn)
        //2.1.3 Date of birth
        assertEquals("2008-06-11", forsikret?.foedselsdato)
        //2.1.4. Sex
        assertEquals("M", forsikret?.kjoenn)
        //2.1.5. Family name(s) at birth
        assertEquals("215EtternavnVedFodsel", forsikret?.etternavnvedfoedsel)
        //2.1.6. Forename(s) at birth
        assertEquals("216FornavnVedFodsel", forsikret?.fornavnvedfoedsel)

        // Personal Identification Number(s)*
        //2.1.7.1.1. Country
        assertEquals("BE", forsikret?.pin?.firstOrNull()?.land)
        //2.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("12345698732", forsikret?.pin?.firstOrNull()?.identifikator)
        //2.1.7.1.3. Sector
        assertEquals("familieytelser", forsikret?.pin?.firstOrNull()?.sektor)
        //2.1.7.1.4.1. Institution ID
        assertEquals("ES:3120", p2000.nav?.bruker?.person?.pin?.firstOrNull()?.institusjonsid)
        //2.1.7.1.4.2. Institution Name
        assertEquals("ACC_GENERAL TREASURY OF THE SOCIAL SECURITY. PROVINCIAL HEAD OFFICE OF NAVARRA",
            p2000.nav?.bruker?.person?.pin?.firstOrNull()?.institusjonsnavn)
        //2.1.8.1.1. Town
        assertEquals("21811By", forsikret?.foedested?.by)
        //2.1.8.1.2. Region
        assertEquals("21812Region", forsikret?.foedested?.region)
        //2.1.7.1.1. Country
        assertEquals("BG", forsikret?.foedested?.land)
        //2.1.8.2. Father's family name at birth
        assertEquals("2182FarsEtternavnVedFodsel", bruker?.far?.person?.etternavnvedfoedsel)
        //2.1.8.3. Forename of father
        assertEquals("2213TidligereFornavn", bruker?.person?.tidligerefornavn)
        //2.1.8.4. Mother's family name at birth
        assertEquals("2184MorsEtternavnVedFodsel", brukerMor?.etternavnvedfoedsel)
        //2.1.8.5. Forename of mother
        assertEquals("2185MorsFornavn", brukerMor?.fornavn)

        //2.2.1.1. Nationality
        assertEquals("HR", bruker?.person?.statsborgerskap?.firstOrNull()?.land)
        //2.2.1.2. Previous family name(s)
        assertEquals("2212TidligereEtternavn", bruker?.person?.tidligereetternavn)
        //2.2.1.3. Previous forename(s)
        assertEquals("2213TidligereFornavn", bruker?.person?.tidligerefornavn)
        //2.2.2.1. Family Status (rina enum 02 Married)
        assertEquals(SivilstandRina.gift, forsikret?.sivilstand?.firstOrNull()?.status)
        //2.2.2.2. Family status date
        assertEquals("2016-01-05", forsikret?.sivilstand?.firstOrNull()?.fradato)
        //2.2.3. Address
        //2.2.3.1. Street
        assertEquals("2.2.3.1. Gate", bruker?.adresse?.gate)
        //2.2.3.2. Building Name
        assertEquals("2.2.3.2. Bygningens navn", bruker?.adresse?.bygning)
        //2.2.3.3. Town
        assertEquals("2.2.3.3. By", bruker?.adresse?.by)
        //2.2.3.4. Postal Code
        assertEquals("2.2.3.4. Postnummer", bruker?.adresse?.postnummer)
        //2.2.3.5. Region
        assertEquals("2.2.3.5. Region", bruker?.adresse?.region)
        //2.2.3.6. Country
        assertEquals("CY", bruker?.adresse?.land)
        //2.2.4.1.1.1. Type (rina enum: [02] Mobile)
        assertEquals("mobil", forsikret?.kontakt?.telefon?.firstOrNull()?.type)
        //2.2.4.1.1.2. Number
        assertEquals("22411200", forsikret?.kontakt?.telefon?.firstOrNull()?.nummer)
        //2.2.4.2.1.1. Email Address
        assertEquals("224211Epostadresse@mail.no", forsikret?.kontakt?.email?.firstOrNull()?.adresse)


        val pensjonP2200 = p2000.pensjon
        /** 3. Insured person's employment and self-employment details */
        //3.1.1. Occupation mappingfelt: occupation
        assertEquals("3.1.1. Yrke", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.yrke)
        //3.1.2. Employment and self-employment mappingsfelt: employmentSelfEmployment
        assertEquals("forsikrede_driver_fortsatt_selvstendig_naerigsvirksomhet", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.type)
        //3.1.3. Start date of intended employment or self-employment mappingsfelt: startDateIntendedEmploymentOrSelfEmployment
        assertEquals("2000-01-01", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.planlagtstartdato)
        //3.1.4. End date of employment or self-employment mappingfelt: endDateEmploymentOrSelfEmployment
        assertEquals("2017-02-03", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.sluttdato)
        //3.1.5. Intended retirement date from employment or self-employment mappingsfelt: intendedRetirementDateFromEmploymentOrSelfEmployment
        assertEquals("2022-04-16", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.planlagtpensjoneringsdato)
        //3.1.6. Hours per week mappingsfelt: hoursPerWeek
        assertEquals("316", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.arbeidstimerperuke)

        //3.1.7.1. Amount mappingfelt: amount
        assertEquals("3171", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.beloep)
        //3.1.7.2. Currency mappingsfelt: currency
        assertEquals("EUR", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.valuta)
        //3.1.7.3. Amount effective since mappingsfelt: amountEffectiveSince
        assertEquals("2020-05-05", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.beloeputbetaltsiden)
        //3.1.7.4. Payment frequency mappingsfelt: paymentFrequency (rina enum mapping)
        assertEquals("02", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.betalingshyppighetinntekt)
        //3.1.7.5.1. Other payment frequency mappingsfelt: otherPaymentFrequency
        assertEquals("31751AnnenBetalingshyppighet", pensjon?.bruker?.arbeidsforhold?.firstOrNull()?.inntekt?.firstOrNull()?.annenbetalingshyppighetinntekt)

        /** 4 Insured person's benefit details */
        //4.1.1. Benefits mappingsfelt: benefits (rina enum mapping)
        assertEquals("02", pensjon?.ytelser?.firstOrNull()?.ytelse)
        //4.1.2.1. Other benefit mappingsfelt: otherBenefit
        assertEquals("4121AnnenYtelse", pensjon?.ytelser?.firstOrNull()?.annenytelse)
        //4.1.3. Status mappingsfelt:status? (rina enum [02] Granted)
        assertEquals("02", pensjon?.ytelser?.firstOrNull()?.status)
        //4.1.4.1.1. Country
        assertEquals("DK", pensjon?.ytelser?.firstOrNull()?.institusjon?.land)
        //4.1.4.1.2. Personal Identification Number (PIN)
        assertEquals("98765432198", pensjon?.ytelser?.firstOrNull()?.institusjon?.pin)
        //4.1.4.1.3. Sector sector mappingsfelt: sector ($nav.bruker.person.pin[x].sektor)
        assertEquals("pensjoner", pensjon?.ytelser?.firstOrNull()?.institusjon?.sektor)
        //4.1.4.1.4.1. Institution ID
        assertEquals("DK:AD73", pensjon?.ytelser?.firstOrNull()?.institusjon?.institusjonsid)
        //4.1.4.1.4.2. Institution Name
        assertEquals("ACC_KRIFA - Christian Unemployment Insurance Fund", pensjon?.ytelser?.firstOrNull()?.institusjon?.institusjonsnavn)
        //4.1.4.2. Case Number
        assertEquals("4142Saksnummer", pensjon?.ytelser?.firstOrNull()?.institusjon?.saksnummer)
        //4.1.5. Start date of benefits payment mappingsfelt: startDateBenefitsPayment ($pensjon.ytelser[x].startdatoutbetaling)
        assertEquals("2018-07-07", pensjon?.ytelser?.firstOrNull()?.startdatoutbetaling)
        //4.1.6. End date of benefits payment
        assertEquals("2021-06-10", pensjon?.ytelser?.firstOrNull()?.sluttdatoutbetaling)
        //4.1.7. Start date of entitlement to benefits
        assertEquals("2021-08-13", pensjon?.ytelser?.firstOrNull()?.startdatoretttilytelse)
        //4.1.8. End date of entitlement to benefits
        assertEquals("2025-06-18", pensjon?.ytelser?.firstOrNull()?.sluttdatorettilutbetaling)
        //4.1.9.1. Amount
        assertEquals("4191", pensjon?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.beloep)
        //4.1.9.2. Currency
        assertEquals("DKK", pensjon?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.valuta)
        //4.1.9.3. Amount effective since
        assertEquals("2021-02-03", pensjon?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.gjeldendesiden)
        //4.1.9.4. Payment frequency mappingsfelt: (rina enum: 02 Quarterly)
        assertEquals(Betalingshyppighet.maaned_12_per_aar, pensjon?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.betalingshyppighetytelse)
        //4.1.9.5.1. Other payment frequency
        assertEquals("41951AnnenBetalingshyppighet", pensjon?.ytelser?.firstOrNull()?.beloep?.firstOrNull()?.annenbetalingshyppighetytelse)
        //4.1.10.1. The pension received is based on
        assertEquals("botid", pensjon?.ytelser?.firstOrNull()?.mottasbasertpaa)
        //4.1.10.2. Total gross amount of residence - based pension (rina enum 01 tilsvarer Residence)
        assertEquals("41102", pensjon?.ytelser?.firstOrNull()?.totalbruttobeloepbostedsbasert)
        //4.1.10.3. Total gross amount of work related pension
        assertEquals("41103", pensjon?.ytelser?.firstOrNull()?.totalbruttobeloeparbeidsbasert)

        /** 5 Spouse */
        //5.1. Spouse type
        assertEquals("ektefelle", p2000.nav?.ektefelle?.type )
        //5.2.1. Family name(s)
        assertEquals("521Etternavn", p2000.nav?.ektefelle?.person?.etternavn )
        //5.2.2. Forename(s)
        assertEquals("522Fornavn", p2000.nav?.ektefelle?.person?.fornavn )
        //5.2.3. Date of birth
        assertEquals("1984-02-08", p2000.nav?.ektefelle?.person?.foedselsdato )
        //5.2.4. Sex (rina enum [02] Female)
        assertEquals("K", p2000.nav?.ektefelle?.person?.kjoenn )
        //5.2.5. Family name(s) at birth
        assertEquals("525EtternavnVedFodsel", p2000.nav?.ektefelle?.person?.etternavnvedfoedsel )
        //5.2.6. Forename(s) at birth
        assertEquals("526FornavnVedFodsel", p2000.nav?.ektefelle?.person?.fornavnvedfoedsel )
        //5.2.7.1.1. Country
        assertEquals("EE", p2000.nav?.ektefelle?.person?.pin?.firstOrNull()?.land )
        //5.2.7.1.2. Personal Identification Number (PIN)
        assertEquals("35195746821", p2000.nav?.ektefelle?.person?.pin?.firstOrNull()?.identifikator )
        //5.2.7.1.3. Sector (rina enum [01] AWOD)
        assertEquals("yrkesskade_og_yrkessykdom", p2000.nav?.ektefelle?.person?.pin?.firstOrNull()?.sektor )
        //5.2.7.1.4.1. Institution ID
        assertEquals("CH:4ALK60622", p2000.nav?.ektefelle?.person?.pin?.firstOrNull()?.institusjonsid)
        //5.2.7.1.4.2. Institution Name
        assertEquals("ACC_Nationale Arbeitslosenkasse UNIA Baden", p2000.nav?.ektefelle?.person?.pin?.firstOrNull()?.institusjonsnavn)
        //5.2.8.1.1. Town
        assertEquals("52811By", p2000.nav?.ektefelle?.person?.foedested?.by)
        //5.2.8.1.2. Region
        assertEquals("52811Region", p2000.nav?.ektefelle?.person?.foedested?.region)
        //5.2.8.1.3. Country
        assertEquals("FI", p2000.nav?.ektefelle?.person?.foedested?.land)
        //5.2.8.2. Father's family name at birth
        assertEquals("5282FarsEtternavnVedFodsel", p2000.nav?.ektefelle?.far?.etternavnvedfoedsel)
        //5.2.8.3. Forename of father
        assertEquals("5283FarsFornavn", p2000.nav?.ektefelle?.far?.fornavn)
        //5.2.8.4. Mother's family name at birth
        assertEquals("5284MorsEtternavnVedFodsel", p2000.nav?.ektefelle?.mor?.etternavnvedfoedsel)
        //5.2.8.5. Forename of mother
        assertEquals("5285MorsFornavn", p2000.nav?.ektefelle?.mor?.fornavn)
        //5.3.1. Nationality
        assertEquals("FI", p2000.nav?.ektefelle?.person?.statsborgerskap?.firstOrNull()?.land)
        //5.3.2. Previous family name(s)
        assertEquals("532TidligereEtternavn", p2000.nav?.ektefelle?.person?.tidligereetternavn)
        //5.3.3. Previous forename(s)
        assertEquals("533TidligereFornavn", p2000.nav?.ektefelle?.person?.tidligerefornavn)

        /** 6. Children */
        //6.1.1.1. Family name(s)
        assertEquals("6111Etternavn", p2000.nav?.barn?.firstOrNull()?.person?.etternavn)
        //6.1.1.2. Forename(s)
        assertEquals("6112Fornavn", p2000.nav?.barn?.firstOrNull()?.person?.fornavn)
        //6.1.1.3. Date of birth
        assertEquals("2001-06-13", p2000.nav?.barn?.firstOrNull()?.person?.foedselsdato)
        //6.1.1.4. Sex  (rina enum 01 Male)
        assertEquals("K", p2000.nav?.barn?.firstOrNull()?.person?.kjoenn)
        //6.1.1.5. Family name(s) at birth
        assertEquals("6115EtternavnVedFodsel", p2000.nav?.barn?.firstOrNull()?.person?.etternavnvedfoedsel)
        //6.1.1.6. Forename(s) at birth
        assertEquals("6116FornavnVedFodsel", p2000.nav?.barn?.firstOrNull()?.person?.fornavnvedfoedsel)
        //6.1.1.7.1.1. Country
        assertEquals("DE", p2000.nav?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.land)
        //6.1.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("94681532791", p2000.nav?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.identifikator)
        //6.1.1.7.1.3. Sector (rina enum 04 Pensions)
        assertEquals("alle", p2000.nav?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.sektor)
        //6.1.1.7.1.4.1. Institution ID
        assertEquals("IT:2024000", p2000.nav?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.institusjonsid)
        //6.1.1.7.1.4.2. Institution Name
        assertEquals("ACC_INAIL-National Institute for Insurance against Work Accidents - DIREZIONE REGIONALE LAZIO", p2000.nav?.barn?.firstOrNull()?.person?.pin?.firstOrNull()?.institusjonsnavn)
        //6.1.1.8.1.1. Town
        assertEquals("611811By", p2000.nav?.barn?.firstOrNull()?.person?.foedested?.by)
        //6.1.1.8.1.2. Region
        assertEquals("611812Region", p2000.nav?.barn?.firstOrNull()?.person?.foedested?.region)
        //6.1.1.8.1.3. Country
        assertEquals("IS", p2000.nav?.barn?.firstOrNull()?.person?.foedested?.land)
        //6.1.1.8.2. Father's family name at birth
        assertEquals("61182FarsEtternavnVedFodsel", p2000.nav?.barn?.firstOrNull()?.far?.person?.etternavnvedfoedsel)
        //6.1.1.8.3. Forename of father
        assertEquals("61183FarsFornavn", p2000.nav?.barn?.firstOrNull()?.far?.person?.fornavn)
        //6.1.1.8.4. Mother's family name at birth
        assertEquals("61184MorsEtternavnVedFodsel", p2000.nav?.barn?.firstOrNull()?.mor?.person?.etternavnvedfoedsel)
        //6.1.1.8.5. Forename of mother
        assertEquals("61185MorsFornavn", p2000.nav?.barn?.firstOrNull()?.mor?.person?.fornavn)
        //6.1.2.1. Nationality
        assertEquals("HU", p2000.nav?.barn?.firstOrNull()?.person?.statsborgerskap?.firstOrNull()?.land)
        //6.1.2.2. Relationship to the insured person (rina enum 01 Own Child)
        assertEquals("eget_barn", p2000.nav?.barn?.firstOrNull()?.getRelasjontilbruker43())
        //6.1.2.3.1. Specifics on "Other child"
        assertEquals("61231OpplysningerOmAnnetBarn", p2000.nav?.barn?.firstOrNull()?.opplysningeromannetbarn)
        //6.1.2.4. Date of death
        assertEquals("2187-07-13", p2000.nav?.barn?.firstOrNull()?.person?.doedsdato)

        /** 7. Information on representative/legal guardian */
        //7.1. Family name
        assertEquals("71Etternavn", p2000.nav?.verge?.person?.etternavn)
        //7.2. Forename
        assertEquals("72Fornavn", p2000.nav?.verge?.person?.fornavn)
        //7.3. Grounds
        assertEquals("73Grunner", p2000.nav?.verge?.vergemaal?.mandat)

        //7.4.1. Street
        assertEquals("741Gate", p2000.nav?.verge?.adresse?.gate)
        //7.4.2. Building Name
        assertEquals("742BygningensNavn", p2000.nav?.verge?.adresse?.bygning)
        //7.4.3. Town
        assertEquals("743By", p2000.nav?.verge?.adresse?.by)
        //7.4.4. Postal Code
        assertEquals("744Postnummer", p2000.nav?.verge?.adresse?.postnummer)
        //7.4.5. Region
        assertEquals("745Region", p2000.nav?.verge?.adresse?.region)
        //7.4.6. Country
        assertEquals("IT", p2000.nav?.verge?.adresse?.land)
        //7.5.1.1.1. Type (rina enum 02 Mobile)
        assertEquals("arbeid", p2000.nav?.verge?.person?.kontakt?.telefon?.firstOrNull()?.type)
        //7.5.1.1.2. Number mappingsfelt: number
        assertEquals("22556981", p2000.nav?.verge?.person?.kontakt?.telefon?.firstOrNull()?.nummer)
        //7.5.2.1.1. Email Address mappingsfelt: EmailAddress
        assertEquals("75211Epostadresse@epost.no", p2000.nav?.verge?.person?.kontakt?.email?.firstOrNull()?.adresse)

        /** 8. Information on payment */
        //8.1. Payment to mappingsfelt: recipientDecision
        assertEquals("forsikret_person", bruker?.bank?.konto?.innehaver?.rolle)
        //8.2.1. Account holder name mappingsfelt: accountHolderName
        assertEquals("821KontohaversNavn", p2000.nav?.bruker?.bank?.konto?.innehaver?.navn)
        //8.2.2.1. IBAN mappingsfelt: IBAN
        assertEquals("no9386011117947", p2000.nav?.bruker?.bank?.konto?.sepa?.iban)
        //8.2.2.2. BIC-SWIFT mappingsfelt: BICSWIFT
        assertEquals("bankITA9987", p2000.nav?.bruker?.bank?.konto?.sepa?.swift)

        /** 9. Miscellaneous */
        //9.1. Date of claim mappingsfelt: dateClaim
        assertEquals("2008-10-22", p2000.nav?.krav?.dato)
        //9.2. Start date of pension payment requested by insured person in contact institution
        assertEquals("2025-05-21", pensjon?.forespurtstartdato)
        //9.3. From the earliest date of eligibility
        assertEquals("nei", pensjon?.angitidligstdato)
        //9.4.1. Deferment country
        assertEquals("IE", pensjon?.utsettelse?.firstOrNull()?.land)
        //9.4.2. Deferment institution name
        assertEquals("942NavnInstitusjonenUtsetter", pensjon?.utsettelse?.firstOrNull()?.institusjonsnavn)
        //9.4.3. Deferment institution code
        assertEquals("943KodeTilInstitusjon", pensjon?.utsettelse?.firstOrNull()?.institusjonsid)
        //9.4.4. Deferment time (until which date)
        assertEquals("2027-08-28", pensjon?.utsettelse?.firstOrNull()?.tildato)
        //9.5. Recipient of the decision
        assertEquals("forsikret_person", pensjon?.mottaker?.firstOrNull())
        //9.6. Deductions grounds
        assertEquals("987_2009_Art_72_1", pensjon?.trekkgrunnlag?.firstOrNull())
        //9.7.1. Institution where the pension is not claimed yet (only P5000 needed)
        assertEquals("971InstDerDetEnnåIkkeErSoktOmPensjon(kun P5000 trengs)", pensjon?.institusjonennaaikkesoektompensjon?.firstOrNull())
        //9.8. Attachments
        assertEquals("utførlig_medisinsk_rapport", pensjon?.vedlegg?.firstOrNull())
        //9.9.1. Other attachments
        assertEquals("991AndreVedlegg", pensjon?.vedleggandre)
        //9.10. Requested documents
        assertEquals("910EtterspurteDokumenter", pensjon?.etterspurtedokumenter)
        //9.11. Additional information
        assertEquals("911YtterligereInformasjon", pensjon?.ytterligeinformasjon)

    }

    private fun p2000Json() =
        """
       {
          "nav": {
            "bruker": {
              "mor": {
                "person": {
                  "etternavnvedfoedsel": "2184MorsEtternavnVedFodsel",
                  "fornavn": "2185MorsFornavn"
                }
              },
              "person": {
                "fornavn": "212Fornavn",
                "sivilstand": [
                  {
                    "status": "gift",
                    "fradato": "2016-01-05"
                  }
                ],
                "kjoenn": "M",
                "etternavn": "211Etternavn",
                "kontakt": {
                  "email": [
                    {
                      "adresse": "224211Epostadresse@mail.no"
                    }
                  ],
                  "telefon": [
                    {
                      "nummer": "22411200",
                      "type": "mobil"
                    }
                  ]
                },
                "foedselsdato": "2008-06-11",
                "tidligereetternavn": "2212TidligereEtternavn",
                "statsborgerskap": [
                  {
                    "land": "HR"
                  }
                ],
                "pin": [
                  {
                    "sektor": "familieytelser",
                    "institusjonsnavn": "ACC_GENERAL TREASURY OF THE SOCIAL SECURITY. PROVINCIAL HEAD OFFICE OF NAVARRA",
                    "identifikator": "12345698732",
                    "land": "BE",
                    "institusjonsid": "ES:3120"
                  }
                ],
                "foedested": {
                  "region": "21812Region",
                  "land": "BG",
                  "by": "21811By"
                },
                "fornavnvedfoedsel": "216FornavnVedFodsel",
                "tidligerefornavn": "2213TidligereFornavn",
                "etternavnvedfoedsel": "215EtternavnVedFodsel"
              },
              "adresse": {
                "region": "2.2.3.5. Region",
                "land": "CY",
                "gate": "2.2.3.1. Gate",
                "bygning": "2.2.3.2. Bygningens navn",
                "postnummer": "2.2.3.4. Postnummer",
                "by": "2.2.3.3. By"
              },
              "bank": {
                "konto": {
                  "innehaver": {
                    "navn": "821KontohaversNavn",
                    "rolle": "forsikret_person"
                  },
                  "sepa": {
                    "swift": "bankITA9987",
                    "iban": "no9386011117947"
                  }
                }
              },
              "far": {
                "person": {
                  "fornavn": "2183FarsFornavn",
                  "etternavnvedfoedsel": "2182FarsEtternavnVedFodsel"
                }
              }
            },
            "barn": [
              {
                "relasjontilbruker43": "eget_barn",
                "far": {
                  "person": {
                    "etternavnvedfoedsel": "61182FarsEtternavnVedFodsel",
                    "fornavn": "61183FarsFornavn"
                  }
                },
                "person": {
                  "pin": [
                    {
                      "identifikator": "94681532791",
                      "sektor": "alle",
                      "institusjonsid": "IT:2024000",
                      "land": "DE",
                      "institusjonsnavn": "ACC_INAIL-National Institute for Insurance against Work Accidents - DIREZIONE REGIONALE LAZIO"
                    }
                  ],
                  "foedested": {
                    "by": "611811By",
                    "land": "IS",
                    "region": "611812Region"
                  },
                  "foedselsdato": "2001-06-13",
                  "kjoenn": "K",
                  "fornavn": "6112Fornavn",
                  "statsborgerskap": [
                    {
                      "land": "HU"
                    }
                  ],
                  "etternavn": "6111Etternavn",
                  "doedsdato": "2187-07-13",
                  "etternavnvedfoedsel": "6115EtternavnVedFodsel",
                  "fornavnvedfoedsel": "6116FornavnVedFodsel"
                },
                "opplysningeromannetbarn": "61231OpplysningerOmAnnetBarn",
                "mor": {
                  "person": {
                    "fornavn": "61185MorsFornavn",
                    "etternavnvedfoedsel": "61184MorsEtternavnVedFodsel"
                  }
                }
              }
            ],
            "verge": {
              "person": {
                "kontakt": {
                  "telefon": [
                    {
                      "nummer": "22556981",
                      "type": "arbeid"
                    }
                  ],
                  "email": [
                    {
                      "adresse": "75211Epostadresse@epost.no"
                    }
                  ]
                },
                "fornavn": "72Fornavn",
                "etternavn": "71Etternavn"
              },
              "vergemaal": {
                "mandat": "73Grunner"
              },
              "adresse": {
                "by": "743By",
                "postnummer": "744Postnummer",
                "gate": "741Gate",
                "land": "IT",
                "region": "745Region",
                "bygning": "742BygningensNavn"
              }
            },
            "krav": {
              "dato": "2008-10-22"
            },
            "ektefelle": {
              "mor": {
                "etternavnvedfoedsel": "5284MorsEtternavnVedFodsel",
                "fornavn": "5285MorsFornavn"
              },
              "person": {
                "pin": [
                  {
                    "land": "EE",
                    "identifikator": "35195746821",
                    "sektor": "yrkesskade_og_yrkessykdom",
                    "institusjonsnavn": "ACC_Nationale Arbeitslosenkasse UNIA Baden",
                    "institusjonsid": "CH:4ALK60622"
                  }
                ],
                "statsborgerskap": [
                  {
                    "land": "FI"
                  },
                  {
                    "land": "CZ"
                  }
                ],
                "etternavnvedfoedsel": "525EtternavnVedFodsel",
                "fornavn": "522Fornavn",
                "tidligereetternavn": "532TidligereEtternavn",
                "foedested": {
                  "region": "52811Region",
                  "land": "FI",
                  "by": "52811By"
                },
                "etternavn": "521Etternavn",
                "foedselsdato": "1984-02-08",
                "tidligerefornavn": "533TidligereFornavn",
                "kjoenn": "K",
                "fornavnvedfoedsel": "526FornavnVedFodsel"
              },
              "type": "ektefelle",
              "far": {
                "fornavn": "5283FarsFornavn",
                "etternavnvedfoedsel": "5282FarsEtternavnVedFodsel"
              }
            },
            "eessisak": [
              {
                "institusjonsid": "ES:3117",
                "institusjonsnavn": "ACC_PROVINCIAL HEAD OFFICE OF NAVARRA",
                "saksnummer": "112Saksnummer",
                "land": "AT"
              }
            ]
          },
          "pensjon": {
            "utsettelse": [
              {
                "land": "IE",
                "institusjonsid": "943KodeTilInstitusjon",
                "institusjonsnavn": "942NavnInstitusjonenUtsetter",
                "tildato": "2027-08-28"
              }
            ],
            "ytelser": [
              {
                "status": "02",
                "totalbruttobeloeparbeidsbasert": "41103",
                "startdatoutbetaling": "2018-07-07",
                "beloep": [
                  {
                    "annenbetalingshyppighetytelse": "41951AnnenBetalingshyppighet",
                    "gjeldendesiden": "2021-02-03",
                    "beloep": "4191",
                    "valuta": "DKK",
                    "betalingshyppighetytelse": "03"
                  }
                ],
                "sluttdatoutbetaling": "2021-06-10",
                "annenytelse": "4121AnnenYtelse",
                "totalbruttobeloepbostedsbasert": "41102",
                "institusjon": {
                  "saksnummer": "4142Saksnummer",
                  "land": "DK",
                  "pin": "98765432198",
                  "institusjonsnavn": "ACC_KRIFA - Christian Unemployment Insurance Fund",
                  "sektor": "pensjoner",
                  "institusjonsid": "DK:AD73"
                },
                "mottasbasertpaa": "botid",
                "startdatoretttilytelse": "2021-08-13",
                "sluttdatorettilutbetaling": "2025-06-18",
                "ytelse": "02"
              }
            ],
            "ytterligeinformasjon": "911YtterligereInformasjon",
            "bruker": {
              "arbeidsforhold": [
                {
                  "yrke": "3.1.1. Yrke",
                  "planlagtpensjoneringsdato": "2022-04-16",
                  "inntekt": [
                    {
                      "beloeputbetaltsiden": "2020-05-05",
                      "annenbetalingshyppighetinntekt": "31751AnnenBetalingshyppighet",
                      "beloep": "3171",
                      "valuta": "EUR",
                      "betalingshyppighetinntekt": "02"
                    }
                  ],
                  "type": "forsikrede_driver_fortsatt_selvstendig_naerigsvirksomhet",
                  "arbeidstimerperuke": "316",
                  "sluttdato": "2017-02-03",
                  "planlagtstartdato": "2000-01-01"
                }
              ]
            },
            "etterspurtedokumenter": "910EtterspurteDokumenter",
            "vedlegg": [
              "utførlig_medisinsk_rapport"
            ],
            "mottaker": [
              "forsikret_person"
            ],
            "institusjonennaaikkesoektompensjon": [
              "971InstDerDetEnnåIkkeErSoktOmPensjon(kun P5000 trengs)"
            ],
            "vedleggandre": "991AndreVedlegg",
            "trekkgrunnlag": [
              "987_2009_Art_72_1"
            ],
            "forespurtstartdato": "2025-05-21",
            "angitidligstdato": "nei"
          },
          "sedVer": "4",
          "sedGVer": "4",
          "sed": "P2000"
        }
        """.trimIndent()
}