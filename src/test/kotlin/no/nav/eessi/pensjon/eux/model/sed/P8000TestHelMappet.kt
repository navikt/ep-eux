package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P8000TestHelMappet {

    //Testsed i rina q2: P2200Test
    //rinasak = 1457540
    @Test
    fun mapJsonToP8000() {

        val p8000 = mapJsonToAny<P8000>(p8000Json())

        val bruker = p8000.nav?.bruker
        val forsikret = p8000.nav?.bruker?.person
        val annenperson = p8000.nav?.annenperson
        val pensjon = p8000.p8000Pensjon

        val p8000json = p8000.toJsonSkipEmpty()
        JSONAssert.assertEquals(p8000json, p8000Json(), true)
        JSONAssert.assertEquals(p8000Json(), p8000json, true)

        //1.1.1  Country
        assertEquals("NO", p8000.nav?.eessisak?.firstOrNull()?.land)
        //1.1.2 Case number
        assertEquals("26420471", p8000.nav?.eessisak?.firstOrNull()?.saksnummer)
        //1.1.3.1 Institution ID
        assertEquals("NO:NAVAT07", p8000.nav?.eessisak?.firstOrNull()?.institusjonsid)
        //1.1.3.2 Institution Name
        assertEquals("NAV ACCEPTANCE TEST 07", p8000.nav?.eessisak?.firstOrNull()?.institusjonsnavn)

        /** 2. Insured person */
        //2.1.1 Family name(s)
        assertEquals("BRANDE", forsikret?.etternavn)
        //2.1.2 Forename(s)
        assertEquals("SART", forsikret?.fornavn)
        //2.1.3 Date of birth
        assertEquals("1962-06-27", forsikret?.foedselsdato)
        //2.1.4. Sex (rina enum: [01] Male)
        assertEquals("M", forsikret?.kjoenn)
        //2.1.5. Family name(s) at birth
        assertEquals("Ball", forsikret?.etternavnvedfoedsel)
        //2.1.6. Forename(s) at birth
        assertEquals("Bolle", forsikret?.fornavnvedfoedsel)

        // Personal Identification Number(s)*
        //2.1.7.1.1. Country
        assertEquals("NO", forsikret?.pin?.firstOrNull()?.land)
        //2.1.7.1.2. Personal Identification Number (PIN)
        assertEquals("27466237197", forsikret?.pin?.firstOrNull()?.identifikator)
        //2.1.7.1.3. Sector
        assertEquals("familieytelser", forsikret?.pin?.firstOrNull()?.institusjon?.sektor)
        //2.1.7.1.4.1. Institution ID
        assertEquals("NO:NAVAT01", forsikret?.pin?.firstOrNull()?.institusjon?.institusjonsid)
        //2.1.7.1.4.2. Institution Name
        assertEquals("NAV ACC 01", forsikret?.pin?.firstOrNull()?.institusjon?.institusjonsnavn)
        //2.1.8.1.1. Town
        assertEquals("Trondheim", forsikret?.foedested?.by)
        //2.1.8.1.2. Region
        assertEquals("Tron", forsikret?.foedested?.region)
        //2.1.8.1.3. Country
        assertEquals("BE", forsikret?.foedested?.land)
        //2.1.8.2. Father's family name at birth
        assertEquals("farsetternavnvedfoedsel", bruker?.far?.person?.etternavnvedfoedsel)
        //2.1.8.3. Forename of father
        assertEquals("farsfornavn", bruker?.far?.person?.fornavn)
        //2.1.8.4. Mother's family name at birth
        assertEquals("morsetternavnvedfoedsel", bruker?.mor?.person?.etternavnvedfoedsel)
        //2.1.8.5. Forename of mother
        assertEquals("morsfornavn", bruker?.mor?.person?.fornavn)

        //2.2.1.1. Nationality
        assertEquals("BG", forsikret?.statsborgerskap?.firstOrNull()?.land)
        //2.2.1.2. Previous family name(s)
        assertEquals("Bulger", forsikret?.tidligereetternavn)
        //2.2.1.3. Previous forename(s)
        assertEquals("Bulgarier", forsikret?.tidligerefornavn)
        //2.2.2. Address
        //2.2.2.1. Street
        assertEquals("Gangstøvegen 48", bruker?.adresse?.gate)
        //2.2.3.2. Building Name
        assertEquals("bygningsnavn", bruker?.adresse?.bygning)
        //2.2.3.3. Town
        assertEquals("ALVERSUND", bruker?.adresse?.by)
        //2.2.3.4. Postal Code
        assertEquals("5911", bruker?.adresse?.postnummer)
        //2.2.3.5. Region
        assertEquals("region2225", bruker?.adresse?.region)
        //2.2.3.6. Country
        assertEquals("NO", bruker?.adresse?.land)
        //2.2.4.1.1.1. Type
        assertEquals("mobil", forsikret?.kontakt?.telefon?.firstOrNull()?.type)
        //2.2.4.1.1.2. Number
        assertEquals("+4799999999", forsikret?.kontakt?.telefon?.firstOrNull()?.nummer)
        //2.2.4.2.1.1. Email Address
        assertEquals("noreply@nav.no", forsikret?.kontakt?.email?.firstOrNull()?.adresse)

        /** 3. Other person */
        //3.1. 3.1. Role of the person
        assertEquals("03", annenperson?.person?.rolle)
        //3.2.1. Family name(s)
        assertEquals("familienavnAnnenPerson", annenperson?.person?.etternavn)
        //3.2.2. Forename(s)
        assertEquals("fornavnAnnenPerson", annenperson?.person?.fornavn)
        //3.2.3. Date of birth
        assertEquals("2004-01-05", annenperson?.person?.foedselsdato)
        //3.2.4. Sex
        assertEquals("M", annenperson?.person?.kjoenn)
        //3.2.5. Family name(s) at birth
        assertEquals("familienavnAnnenPersonVedFoedsel", annenperson?.person?.etternavnvedfoedsel)
        //2.3.6. Invalidity caused by liable third party
        assertEquals("fornavnAnnenPersonVedFoedsel", annenperson?.person?.fornavnvedfoedsel)

        //3.2.7.1 Personal Identification Number(s)*
        //3.2.7.1.1. Country
        assertEquals("HR", annenperson?.person?.pin?.firstOrNull()?.land)
        //3.2.7.1.2. Personal Identification Number (PIN)
        assertEquals("3549646413", annenperson?.person?.pin?.firstOrNull()?.identifikator)
        //3.2.7.1.3. Sector (rina enum [04] Pensions
        assertEquals("pensjoner", annenperson?.person?.pin?.firstOrNull()?.sektor)
        //3.2.7.1.4. Institution
        //3.2.7.1.4.1. Institution ID
        assertEquals("LT:188783981", annenperson?.person?.pin?.firstOrNull()?.institusjonsid)
        //3.2.7.1.4.2. Institution Name
        assertEquals("Klaipeda Territorial Health Insurance Fund", annenperson?.person?.pin?.firstOrNull()?.institusjonsnavn)
        //3.2.8.1. Place of birth
        //3.2.8.1.1. Town
        assertEquals("32811Town", annenperson?.person?.foedested?.by)
        //3.2.8.1.2. Region
        assertEquals("32812Region", annenperson?.person?.foedested?.region)
        //3.2.8.1.3. Country
        assertEquals("CY", annenperson?.person?.foedested?.land)
        //3.2.8.2. Father's family name at birth
        assertEquals("FathersFamilyNameAtBirth3282", annenperson?.far?.person?.etternavnvedfoedsel)
        //3.2.8.3. Forename of father
        assertEquals("ForenameFather3283", annenperson?.far?.person?.fornavnvedfoedsel)
        //3.2.8.4. Mother's family name at birth
        assertEquals("MotherFamilyNameAtBirth3284", annenperson?.mor?.person?.etternavnvedfoedsel)
        //3.2.8.5. Forename of mother
        assertEquals("ForenameOfMother3285", annenperson?.mor?.person?.fornavnvedfoedsel)
        //3.3.1. Additional information on the person
        //3.3.1.1. Nationality
        assertEquals("DK", annenperson?.person?.statsborgerskap?.firstOrNull()?.land)
        //3.3.1.2. Previous family name(s)
        assertEquals("PreviousFamilyName3312", annenperson?.person?.tidligereetternavn)
        //3.3.1.3. Previous forename(s)
        assertEquals("PreviousForename3313", annenperson?.person?.tidligerefornavn)

        //3.3.2.1. Street
        assertEquals("Street3321", annenperson?.adresse?.gate)
        //3.3.2.2. Building Name
        assertEquals("BuildingName3322", annenperson?.adresse?.bygning)
        //3.3.2.3. Town
        assertEquals("Town3323", annenperson?.adresse?.by)
        //3.3.2.4. Postal Code
        assertEquals("PostalCode", annenperson?.adresse?.postnummer)
        //3.3.2.5. Region
        assertEquals("Region3325", annenperson?.adresse?.region)
        //3.3.2.6. Country
        assertEquals("EE", annenperson?.adresse?.land)
        //3.3.3.1.1.1. Type (rina enum [01] Home)
        assertEquals("hjem", annenperson?.person?.kontakt?.telefon?.firstOrNull()?.type)
        //3.3.3.1.1.2. Number mappingsfelt: number
        assertEquals("92333112", annenperson?.person?.kontakt?.telefon?.firstOrNull()?.nummer)
        //3.3.3.2.1.1. Email Address
        assertEquals("epost@norge.no", annenperson?.person?.kontakt?.email?.firstOrNull()?.adresse)

        /** 4. Relationship of the Dependant / Family Member or child to the insured person */
        //4.1.1. Relationship to the insured person
        assertEquals("01", pensjon?.anmodning?.relasjonTilForsikretPerson)
        //4.2.1. Description of other relative
        assertEquals("DescriptionOfOtheRelative421", pensjon?.anmodning?.beskrivelseAnnenSlektning)

        /** 5. Reference to the person */
        //5.1. This request is made with reference to the person named in
        assertEquals("02", pensjon?.anmodning?.referanseTilPerson)

        /** 6. Request for document(s) */
        //6.1. Please provide us with following documents:
        assertEquals("[utførlig_medisinsk_rapport]", pensjon?.vedlegg.toString())
        //6.2.1. Additional information on document(s)
        assertEquals("AdditionalInformationOnDocument621", pensjon?.anmodning?.ytterligereInfoOmDokumenter)
        //6.3. Reason for the request
        assertEquals("ReasonForTheRequest63", pensjon?.anmodning?.begrunnKrav)

        /** 7. Request for SED(s) */
        //7.1.1. Please provide us with following SEDs:
        assertEquals("[p1000]", pensjon?.anmodning?.seder?.firstOrNull()?.sendFolgendeSEDer.toString())
        //7.1.2.1. Other requested SED(s)
        assertEquals("7.1.2.1. Other requested SED(s)", pensjon?.anmodning?.seder?.firstOrNull()?.andreEtterspurteSEDer)
        //7.1.2.2. Reason for the request
        assertEquals("ReasonForTheRequest7122", pensjon?.anmodning?.seder?.firstOrNull()?.begrunnelse)

        /** 8. Request for information */
        //8.1. General information
        assertEquals("02", pensjon?.anmodning?.informasjon?.generellPersonInfo)
        //8.2.1. Please inform us if the person is/was claiming; is/was receiving; is/was rejected for the following benefit(s)
        assertEquals("01", pensjon?.anmodning?.informasjon?.infoOmPersonYtelse?.firstOrNull()?.sendInfoOm?.firstOrNull()?.sendInfoOm)
        //8.2.2.1. Other benefit(s)
        assertEquals("OtherBenefits8221", pensjon?.anmodning?.informasjon?.infoOmPersonYtelse?.firstOrNull()?.annenYtelse)
        //8.2.3.1. Please send us information on (rina enum: [01] Date of the claim for benefit)
        assertEquals("01", pensjon?.anmodning?.informasjon?.infoOmPersonYtelse?.firstOrNull()?.sendInfoOm?.firstOrNull()?.sendInfoOm)
        //8.2.3.2.1. Other information on benefit(s)
        assertEquals("OtherInformationOnBenefits82321", pensjon?.anmodning?.informasjon?.infoOmPersonYtelse?.firstOrNull()?.sendInfoOm?.firstOrNull()?.annenInfoOmYtelser)
        //8.3.1. Please inform us about the person's activity as (checkbox: [01] Employed)
        assertEquals("01", pensjon?.anmodning?.personAktivitetSom?.firstOrNull()?.persAktivitetSom)
        //8.3.2. Please inform
        assertEquals("01", pensjon?.anmodning?.personAktivitet?.firstOrNull()?.persAktivitet)
        //8.4.1. Please inform us about the person's income (checkbox: [01] Source of income)
        assertEquals("01", pensjon?.anmodning?.personInntekt?.firstOrNull()?.inntekt)
        //8.4.2. Please state income effective since
        assertEquals("2021-06-01", pensjon?.anmodning?.personInntekt?.firstOrNull()?.oppgiInntektFOM)
        //8.5. Other requested information
        assertEquals("OtherRequestedInformation85", pensjon?.anmodning?.informasjon?.annenEtterspurtInformasjon)
        //8.6. Reason for the request
        assertEquals("ReasonForTheRequest86", pensjon?.anmodning?.informasjon?.begrunnelseKrav)

        /** 9. Request for confirmation of information */
        //9.1. Please confirm the following information
        assertEquals("PleaseConfirmTheFollowingInformation91", pensjon?.anmodning?.anmodningOmBekreftelse?.bekreftelseInfo)
        //9.2. Reason for the request
        assertEquals("ReasonForTheRequest92", pensjon?.anmodning?.anmodningOmBekreftelse?.bekreftelsesGrunn)

        /** 10. Request for person's statement */
        //10.1. Please provide the following statement of the person
        assertEquals("PleaseProvideTheFollowingStatementOfThePerson101", pensjon?.anmodning?.anmodningPerson?.egenerklaering)
        //10.2. Reason for the request
        assertEquals("ReasonForTheRequest102", pensjon?.anmodning?.anmodningPerson?.begrunnelseKrav)

        /** 11. Additional information */
        //11.1. Additional information
        assertEquals("AdditionalInformation111", pensjon?.ytterligeinformasjon)

    }

    private fun p8000Json() =
        """
       {
         "pensjon": {
           "anmodning": {
             "informasjon": {
               "infoOmPersonYtelse": [
                 {
                   "sendInfoOm": [
                     {
                       "sendInfoOm": "01",
                       "annenInfoOmYtelser": "OtherInformationOnBenefits82321"
                     }
                   ],
                   "annenYtelse": "OtherBenefits8221",
                   "informerOmPersonHarFremsattKravEllerMottattYtelse": "01"
                 }
               ],
               "begrunnelseKrav": "ReasonForTheRequest86",
               "annenEtterspurtInformasjon": "OtherRequestedInformation85",
               "generellPersonInfo": "02"
             },
             "seder": [
               {
                 "sendFolgendeSEDer": [
                   "p1000"
                 ],
                 "andreEtterspurteSEDer": "7.1.2.1. Other requested SED(s)",
                 "begrunnelse": "ReasonForTheRequest7122"
               }
             ],
             "begrunnKrav": "ReasonForTheRequest63",
             "anmodningPerson": {
               "egenerklaering": "PleaseProvideTheFollowingStatementOfThePerson101",
               "begrunnelseKrav": "ReasonForTheRequest102"
             },
             "beskrivelseAnnenSlektning": "DescriptionOfOtheRelative421",
             "relasjonTilForsikretPerson": "01",
             "personInntekt": [
               {
                 "oppgiInntektFOM": "2021-06-01",
                 "inntekt": "01"
               }
             ],
             "ytterligereInfoOmDokumenter": "AdditionalInformationOnDocument621",
             "personAktivitetSom": [
               {
                 "persAktivitetSom": "01"
               }
             ],
             "referanseTilPerson": "02",
             "anmodningOmBekreftelse": {
               "bekreftelsesGrunn": "ReasonForTheRequest92",
               "bekreftelseInfo": "PleaseConfirmTheFollowingInformation91"
             },
             "personAktivitet": [
               {
                 "persAktivitet": "01"
               }
             ]
           },
           "ytterligeinformasjon": "AdditionalInformation111",
           "vedlegg": [
             "utførlig_medisinsk_rapport"
           ]
         },
         "nav": {
           "annenperson": {
             "adresse": {
               "region": "Region3325",
               "postnummer": "PostalCode",
               "bygning": "BuildingName3322",
               "land": "EE",
               "by": "Town3323",
               "gate": "Street3321"
             },
             "person": {
               "kjoenn": "M",
               "kontakt": {
                 "telefon": [
                   {
                     "type": "hjem",
                     "nummer": "92333112"
                   }
                 ],
                 "email": [
                   {
                     "adresse": "epost@norge.no"
                   }
                 ]
               },
               "tidligerefornavn": "PreviousForename3313",
               "foedested": {
                 "region": "32812Region",
                 "by": "32811Town",
                 "land": "CY"
               },
               "fornavn": "fornavnAnnenPerson",
               "etternavn": "familienavnAnnenPerson",
               "pin": [
                 {
                   "sektor": "pensjoner",
                   "institusjonsid": "LT:188783981",
                   "identifikator": "3549646413",
                   "land": "HR",
                   "institusjonsnavn": "Klaipeda Territorial Health Insurance Fund"
                 }
               ],
               "fornavnvedfoedsel": "fornavnAnnenPersonVedFoedsel",
               "tidligereetternavn": "PreviousFamilyName3312",
               "rolle": "03",
               "etternavnvedfoedsel": "familienavnAnnenPersonVedFoedsel",
               "statsborgerskap": [
                 {
                   "land": "DK"
                 }
               ],
               "foedselsdato": "2004-01-05"
             },
             "mor": {
               "person": {
                 "fornavnvedfoedsel": "ForenameOfMother3285",
                 "etternavnvedfoedsel": "MotherFamilyNameAtBirth3284"
               }
             },
             "far": {
               "person": {
                 "etternavnvedfoedsel": "FathersFamilyNameAtBirth3282",
                 "fornavnvedfoedsel": "ForenameFather3283"
               }
             }
           },
           "bruker": {
               "mor" : {
                "person" : {
                  "etternavnvedfoedsel" : "morsetternavnvedfoedsel",
                  "fornavn" : "morsfornavn"
                }
              },
              "far" : {
                "person" : {
                  "etternavnvedfoedsel" : "farsetternavnvedfoedsel",
                  "fornavn" : "farsfornavn"
                }
              },
             "person": {
               "fornavn": "SART",
               "kjoenn": "M",
               "etternavn": "BRANDE",
               "kontakt": {
                 "email": [
                   {
                     "adresse": "noreply@nav.no"
                   }
                 ],
                 "telefon": [
                   {
                     "nummer": "+4799999999",
                     "type": "mobil"
                   }
                 ]
               },
               "etternavnvedfoedsel": "Ball",
               "foedselsdato": "1962-06-27",
               "tidligereetternavn": "Bulger",
               "statsborgerskap": [
                 {
                   "land": "BG"
                 }
               ],
               "pin": [
                 {
                   "identifikator": "27466237197",
                   "institusjon": {
                     "institusjonsid": "NO:NAVAT01",
                     "sektor": "familieytelser",
                     "institusjonsnavn": "NAV ACC 01"
                   },
                   "land": "NO"
                 }
               ],
               "foedested": {
                 "region": "Tron",
                 "by": "Trondheim",
                 "land": "BE"
               },
               "fornavnvedfoedsel": "Bolle",
               "tidligerefornavn": "Bulgarier"
             },
             "adresse": {
               "bygning": "bygningsnavn",
               "region": "region2225",
               "postnummer": "5911",
               "by": "ALVERSUND",
               "land": "NO",
               "gate": "Gangstøvegen 48"
             }
           },
           "eessisak": [
             {
               "institusjonsnavn": "NAV ACCEPTANCE TEST 07",
               "saksnummer": "26420471",
               "land": "NO",
               "institusjonsid": "NO:NAVAT07"
             }
           ],
           "ektefelle": {
           }
         },
         "sedVer": "4",
         "sedGVer": "4",
         "sed": "P8000"
       }
        """.trimIndent()
}