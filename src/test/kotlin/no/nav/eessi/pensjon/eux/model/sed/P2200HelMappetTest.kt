package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Field numbers checked against P2200.pdf (12 pages, printed 27 May 2019).
 * Non-SEPA numbers come from ACL field metadata because that conditional section is absent in the PDF.
 * Synthetic fixture only. Tests JSON <-> P2200, not the cross-repository runtime ACL.
 * Each version's source path is documented separately; v4.4 and v4.5 are not identical.
 * Version differences: benefit PIN, pension basis and entitlement-end-date source.
 * TODOs retain unresolved source paths; missing fields are not asserted as null.
 */
class P2200HelMappetTest {
    private fun p2200Json(): String =
        requireNotNull(javaClass.getResource("/sed/P2200-HelMappet-NAV.json")).readText()

    @Test
    fun localCaseNumbers() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val navP2200 = p2200.navP2200
        val navP2200Eessisak = navP2200?.eessisak
        val navP2200EessisakFirst = navP2200Eessisak?.getOrNull(0)
        val navP2200EessisakSecond = navP2200Eessisak?.getOrNull(1)

        // 1.1.1 country (entry 1)
        // Mapping: $nav.eessisak[x].land -> P2200.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("NO", navP2200EessisakFirst?.land, "1.1.1 nav.eessisak.0.land")
        // 1.1.1 country (entry 2)
        // Mapping: $nav.eessisak[x].land -> P2200.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("SE", navP2200EessisakSecond?.land, "1.1.1 nav.eessisak.1.land")
        // 1.1.2 case Number (entry 1)
        // Mapping: $nav.eessisak[x].saksnummer -> P2200.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 1", navP2200EessisakFirst?.saksnummer, "1.1.2 nav.eessisak.0.saksnummer")
        // 1.1.2 case Number (entry 2)
        // Mapping: $nav.eessisak[x].saksnummer -> P2200.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 2", navP2200EessisakSecond?.saksnummer, "1.1.2 nav.eessisak.1.saksnummer")
        // 1.1.3.1 institution ID (entry 1)
        // Mapping: $nav.eessisak[x].institusjonsid -> P2200.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 1", navP2200EessisakFirst?.institusjonsid, "1.1.3.1 nav.eessisak.0.institusjonsid")
        // 1.1.3.1 institution ID (entry 2)
        // Mapping: $nav.eessisak[x].institusjonsid -> P2200.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 2", navP2200EessisakSecond?.institusjonsid, "1.1.3.1 nav.eessisak.1.institusjonsid")
        // 1.1.3.2 institution Name (entry 1)
        // Mapping: $nav.eessisak[x].institusjonsnavn -> P2200.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 1", navP2200EessisakFirst?.institusjonsnavn, "1.1.3.2 nav.eessisak.0.institusjonsnavn")
        // 1.1.3.2 institution Name (entry 2)
        // Mapping: $nav.eessisak[x].institusjonsnavn -> P2200.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 2", navP2200EessisakSecond?.institusjonsnavn, "1.1.3.2 nav.eessisak.1.institusjonsnavn")
    }

    @Test
    fun insuredPerson() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val navP2200 = p2200.navP2200
        val navP2200Bruker = navP2200?.bruker
        val navP2200BrukerPerson = navP2200Bruker?.person
        val navP2200BrukerAdresse = navP2200Bruker?.adresse
        val navP2200BrukerUforhet = navP2200Bruker?.uforhet
        val navP2200BrukerPersonPin = navP2200BrukerPerson?.pin
        val navP2200BrukerPersonKontakt = navP2200BrukerPerson?.kontakt
        val navP2200BrukerPersonFoedested = navP2200BrukerPerson?.foedested
        val navP2200BrukerPersonSivilstand = navP2200BrukerPerson?.sivilstand
        val navP2200BrukerPersonKontaktTelefon = navP2200BrukerPersonKontakt?.telefon
        val navP2200BrukerPersonPinFirst = navP2200BrukerPersonPin?.getOrNull(0)
        val navP2200BrukerPersonPinSecond = navP2200BrukerPersonPin?.getOrNull(1)

        // 2.1.1 family Name (entry 1)
        // Mapping: $nav.bruker.person.etternavn -> P2200.InsuredPerson.PersonIdentification.familyName
        assertEquals("2.1.1 Test family Name 1", navP2200BrukerPerson?.etternavn, "2.1.1 nav.bruker.person.etternavn")
        // 2.1.2 forename (entry 1)
        // Mapping: $nav.bruker.person.fornavn -> P2200.InsuredPerson.PersonIdentification.forename
        assertEquals("2.1.2 Test forename 1", navP2200BrukerPerson?.fornavn, "2.1.2 nav.bruker.person.fornavn")
        // 2.1.3 date Birth (entry 1)
        // Mapping: $nav.bruker.person.foedselsdato -> P2200.InsuredPerson.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navP2200BrukerPerson?.foedselsdato, "2.1.3 nav.bruker.person.foedselsdato")
        // 2.1.4 sex (entry 1)
        // Mapping: $nav.bruker.person.kjoenn -> P2200.InsuredPerson.PersonIdentification.sex.value[x]
        assertEquals("K", navP2200BrukerPerson?.kjoenn, "2.1.4 nav.bruker.person.kjoenn")
        // 2.1.5 family Name At Birth (entry 1)
        // Mapping: $nav.bruker.person.etternavnvedfoedsel -> P2200.InsuredPerson.PersonIdentification.familyNameAtBirth
        assertEquals("2.1.5 Test family Name At Birth 1", navP2200BrukerPerson?.etternavnvedfoedsel, "2.1.5 nav.bruker.person.etternavnvedfoedsel")
        // 2.1.6 forename At Birth (entry 1)
        // Mapping: $nav.bruker.person.fornavnvedfoedsel -> P2200.InsuredPerson.PersonIdentification.forenameAtBirth
        assertEquals("2.1.6 Test forename At Birth 1", navP2200BrukerPerson?.fornavnvedfoedsel, "2.1.6 nav.bruker.person.fornavnvedfoedsel")
        // 2.1.7.1.1 country (entry 1)
        // Mapping: $nav.bruker.person.pin[x].land -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navP2200BrukerPersonPinFirst?.land, "2.1.7.1.1 nav.bruker.person.pin.0.land")
        // 2.1.7.1.1 country (entry 2)
        // Mapping: $nav.bruker.person.pin[x].land -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navP2200BrukerPersonPinSecond?.land, "2.1.7.1.1 nav.bruker.person.pin.1.land")
        // 2.1.7.1.2 personal Identification Number (entry 1)
        // Mapping: $nav.bruker.person.pin[x].identifikator -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 1", navP2200BrukerPersonPinFirst?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.0.identifikator")
        // 2.1.7.1.2 personal Identification Number (entry 2)
        // Mapping: $nav.bruker.person.pin[x].identifikator -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 2", navP2200BrukerPersonPinSecond?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.1.identifikator")
        // 2.1.7.1.3 sector (entry 1)
        // Mapping: $nav.bruker.person.pin[x].sektor -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200BrukerPersonPinFirst?.sektor, "2.1.7.1.3 nav.bruker.person.pin.0.sektor")
        // 2.1.7.1.3 sector (entry 2)
        // Mapping: $nav.bruker.person.pin[x].sektor -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200BrukerPersonPinSecond?.sektor, "2.1.7.1.3 nav.bruker.person.pin.1.sektor")
        // 2.1.7.1.4.1 institution ID (entry 1)
        // Mapping: $nav.bruker.person.pin[x].institusjon.institusjonsid -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 1", navP2200BrukerPersonPinFirst?.institusjon?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.0.institusjon.institusjonsid")
        // 2.1.7.1.4.1 institution ID (entry 2)
        // Mapping: $nav.bruker.person.pin[x].institusjon.institusjonsid -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 2", navP2200BrukerPersonPinSecond?.institusjon?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.1.institusjon.institusjonsid")
        // 2.1.7.1.4.2 institution Name (entry 1)
        // Mapping: $nav.bruker.person.pin[x].institusjon.institusjonsnavn -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 1", navP2200BrukerPersonPinFirst?.institusjon?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.0.institusjon.institusjonsnavn")
        // 2.1.7.1.4.2 institution Name (entry 2)
        // Mapping: $nav.bruker.person.pin[x].institusjon.institusjonsnavn -> P2200.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 2", navP2200BrukerPersonPinSecond?.institusjon?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.1.institusjon.institusjonsnavn")
        // 2.1.8.1.1 town (entry 1)
        // Mapping: $nav.bruker.person.foedested.by -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("2.1.8.1.1 Test town 1", navP2200BrukerPersonFoedested?.by, "2.1.8.1.1 nav.bruker.person.foedested.by")
        // 2.1.8.1.2 region (entry 1)
        // Mapping: $nav.bruker.person.foedested.region -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("2.1.8.1.2 Test region 1", navP2200BrukerPersonFoedested?.region, "2.1.8.1.2 nav.bruker.person.foedested.region")
        // 2.1.8.1.3 country (entry 1)
        // Mapping: $nav.bruker.person.foedested.land -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navP2200BrukerPersonFoedested?.land, "2.1.8.1.3 nav.bruker.person.foedested.land")
        // 2.1.8.2 father Family Name At Birth (entry 1)
        // Mapping: $nav.bruker.far.person.etternavnvedfoedsel -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("2.1.8.2 Test father Family Name At Birth 1", navP2200Bruker?.far?.person?.etternavnvedfoedsel, "2.1.8.2 nav.bruker.far.person.etternavnvedfoedsel")
        // 2.1.8.3 forename Father (entry 1)
        // Mapping: $nav.bruker.far.person.fornavn -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("2.1.8.3 Test forename Father 1", navP2200Bruker?.far?.person?.fornavn, "2.1.8.3 nav.bruker.far.person.fornavn")
        // 2.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping: $nav.bruker.mor.person.etternavnvedfoedsel -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("2.1.8.4 Test mother Family Name At Birth 1", navP2200Bruker?.mor?.person?.etternavnvedfoedsel, "2.1.8.4 nav.bruker.mor.person.etternavnvedfoedsel")
        // 2.1.8.5 forename Mother (entry 1)
        // Mapping: $nav.bruker.mor.person.fornavn -> P2200.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("2.1.8.5 Test forename Mother 1", navP2200Bruker?.mor?.person?.fornavn, "2.1.8.5 nav.bruker.mor.person.fornavn")
        // 2.2.1.1 nationality (entry 1)
        // Mapping: $nav.bruker.person.statsborgerskap[x].land -> P2200.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navP2200BrukerPerson?.statsborgerskap?.getOrNull(0)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.0.land")
        // 2.2.1.1 nationality (entry 2)
        // Mapping: $nav.bruker.person.statsborgerskap[x].land -> P2200.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navP2200BrukerPerson?.statsborgerskap?.getOrNull(1)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.1.land")
        // 2.2.1.2 previous Family Name (entry 1)
        // Mapping: $nav.bruker.person.tidligereetternavn -> P2200.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("2.2.1.2 Test previous Family Name 1", navP2200BrukerPerson?.tidligereetternavn, "2.2.1.2 nav.bruker.person.tidligereetternavn")
        // 2.2.1.3 previous Forename (entry 1)
        // Mapping: $nav.bruker.person.tidligerefornavn -> P2200.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("2.2.1.3 Test previous Forename 1", navP2200BrukerPerson?.tidligerefornavn, "2.2.1.3 nav.bruker.person.tidligerefornavn")
        // 2.2.2.1 family Status (entry 1)
        // Mapping: $nav.bruker.person.sivilstand[x].status -> P2200.InsuredPerson.AdditionalInformationPerson.FamilyStatus[x].familyStatus.value[x]
        assertEquals(SivilstandRina.gift, navP2200BrukerPersonSivilstand?.getOrNull(0)?.status, "2.2.2.1 nav.bruker.person.sivilstand.0.status")
        // 2.2.2.1 family Status (entry 2)
        // Mapping: $nav.bruker.person.sivilstand[x].status -> P2200.InsuredPerson.AdditionalInformationPerson.FamilyStatus[x].familyStatus.value[x]
        assertEquals(SivilstandRina.gift, navP2200BrukerPersonSivilstand?.getOrNull(1)?.status, "2.2.2.1 nav.bruker.person.sivilstand.1.status")
        // 2.2.2.2 family Status Date (entry 1)
        // Mapping: $nav.bruker.person.sivilstand[x].fradato -> P2200.InsuredPerson.AdditionalInformationPerson.FamilyStatus[x].familyStatusDate
        assertEquals("2020-01-02", navP2200BrukerPersonSivilstand?.getOrNull(0)?.fradato, "2.2.2.2 nav.bruker.person.sivilstand.0.fradato")
        // 2.2.2.2 family Status Date (entry 2)
        // Mapping: $nav.bruker.person.sivilstand[x].fradato -> P2200.InsuredPerson.AdditionalInformationPerson.FamilyStatus[x].familyStatusDate
        assertEquals("2021-01-02", navP2200BrukerPersonSivilstand?.getOrNull(1)?.fradato, "2.2.2.2 nav.bruker.person.sivilstand.1.fradato")
        // 2.2.3.1 street (entry 1)
        // Mapping: $nav.bruker.adresse.gate -> P2200.InsuredPerson.AdditionalInformationPerson.Address.street
        assertEquals("2.2.3.1 Test street 1", navP2200BrukerAdresse?.gate, "2.2.3.1 nav.bruker.adresse.gate")
        // 2.2.3.2 building Name (entry 1)
        // Mapping: $nav.bruker.adresse.bygning -> P2200.InsuredPerson.AdditionalInformationPerson.Address.buildingName
        assertEquals("2.2.3.2 Test building Name 1", navP2200BrukerAdresse?.bygning, "2.2.3.2 nav.bruker.adresse.bygning")
        // 2.2.3.3 town (entry 1)
        // Mapping: $nav.bruker.adresse.by -> P2200.InsuredPerson.AdditionalInformationPerson.Address.town
        assertEquals("2.2.3.3 Test town 1", navP2200BrukerAdresse?.by, "2.2.3.3 nav.bruker.adresse.by")
        // 2.2.3.4 postal Code (entry 1)
        // Mapping: $nav.bruker.adresse.postnummer -> P2200.InsuredPerson.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", navP2200BrukerAdresse?.postnummer, "2.2.3.4 nav.bruker.adresse.postnummer")
        // 2.2.3.5 region (entry 1)
        // Mapping: $nav.bruker.adresse.region -> P2200.InsuredPerson.AdditionalInformationPerson.Address.region
        assertEquals("2.2.3.5 Test region 1", navP2200BrukerAdresse?.region, "2.2.3.5 nav.bruker.adresse.region")
        // 2.2.3.6 country (entry 1)
        // Mapping: $nav.bruker.adresse.land -> P2200.InsuredPerson.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", navP2200BrukerAdresse?.land, "2.2.3.6 nav.bruker.adresse.land")
        // 2.2.4.1.1.1 type (entry 1)
        // Mapping: $nav.bruker.person.kontakt.telefon[x].type -> P2200.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", navP2200BrukerPersonKontaktTelefon?.getOrNull(0)?.type, "2.2.4.1.1.1 nav.bruker.person.kontakt.telefon.0.type")
        // 2.2.4.1.1.1 type (entry 2)
        // Mapping: $nav.bruker.person.kontakt.telefon[x].type -> P2200.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", navP2200BrukerPersonKontaktTelefon?.getOrNull(1)?.type, "2.2.4.1.1.1 nav.bruker.person.kontakt.telefon.1.type")
        // 2.2.4.1.1.2 number (entry 1)
        // Mapping: $nav.bruker.person.kontakt.telefon[x].nummer -> P2200.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("2.2.4.1.1.2 Test number 1", navP2200BrukerPersonKontaktTelefon?.getOrNull(0)?.nummer, "2.2.4.1.1.2 nav.bruker.person.kontakt.telefon.0.nummer")
        // 2.2.4.1.1.2 number (entry 2)
        // Mapping: $nav.bruker.person.kontakt.telefon[x].nummer -> P2200.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("2.2.4.1.1.2 Test number 2", navP2200BrukerPersonKontaktTelefon?.getOrNull(1)?.nummer, "2.2.4.1.1.2 nav.bruker.person.kontakt.telefon.1.nummer")
        // 2.2.4.2.1.1 email (entry 1)
        // Mapping: $nav.bruker.person.kontakt.email[x].adresse -> P2200.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-2.2.4.2.1.1-1@example.invalid", navP2200BrukerPersonKontakt?.email?.getOrNull(0)?.adresse, "2.2.4.2.1.1 nav.bruker.person.kontakt.email.0.adresse")
        // 2.2.4.2.1.1 email (entry 2)
        // Mapping: $nav.bruker.person.kontakt.email[x].adresse -> P2200.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-2.2.4.2.1.1-2@example.invalid", navP2200BrukerPersonKontakt?.email?.getOrNull(1)?.adresse, "2.2.4.2.1.1 nav.bruker.person.kontakt.email.1.adresse")
        // 2.3.1 start Date Commencement Invalidity Determined By Pension (entry 1)
        // Mapping: $nav.bruker.uforhet.startDatoPensjon -> P2200.InsuredPerson.InvalidityInformation.startDateCommencementInvalidityDeterminedByPension
        assertEquals("2020-01-02", navP2200BrukerUforhet?.startDatoPensjon, "2.3.1 nav.bruker.uforhet.startDatoPensjon")
        // 2.3.2 start Date Commencement Work Incapacity According Medical Statement (entry 1)
        // Mapping: $nav.bruker.uforhet.startdatoLege -> P2200.InsuredPerson.InvalidityInformation.startDateCommencementWorkIncapacityAccordingMedicalStatement
        assertEquals("2020-01-02", navP2200BrukerUforhet?.startdatoLege, "2.3.2 nav.bruker.uforhet.startdatoLege")
        // 2.3.3 invalidity Result Work Accident Occupational Disease Indicator (entry 1)
        // Mapping: $nav.bruker.uforhet.arbeidsUlykke -> P2200.InsuredPerson.InvalidityInformation.invalidityResultWorkAccidentOccupationalDiseaseIndicator.value[x]
        assertEquals("0", navP2200BrukerUforhet?.arbeidsUlykke, "2.3.3 nav.bruker.uforhet.arbeidsUlykke")
        // 2.3.4 invalidity Result Connected Military Service Indicator (entry 1)
        // Mapping: $nav.bruker.uforhet.militartjenesteUlykke -> P2200.InsuredPerson.InvalidityInformation.invalidityResultConnectedMilitaryServiceIndicator.value[x]
        assertEquals("0", navP2200BrukerUforhet?.militartjenesteUlykke, "2.3.4 nav.bruker.uforhet.militartjenesteUlykke")
        // 2.3.5 invalidity Caused By Insured Person Purpose Indicator (entry 1)
        // Mapping: $nav.bruker.uforhet.bevisstforsaketSoker -> P2200.InsuredPerson.InvalidityInformation.invalidityCausedByInsuredPersonPurposeIndicator.value[x]
        assertEquals("0", navP2200BrukerUforhet?.bevisstforsaketSoker, "2.3.5 nav.bruker.uforhet.bevisstforsaketSoker")
        // 2.3.6 invalidity Caused By Liable Third Party Indicator (entry 1)
        // Mapping: $nav.bruker.uforhet.ansvarligTredjepart -> P2200.InsuredPerson.InvalidityInformation.invalidityCausedByLiableThirdPartyIndicator.value[x]
        assertEquals("0", navP2200BrukerUforhet?.ansvarligTredjepart, "2.3.6 nav.bruker.uforhet.ansvarligTredjepart")
    }

    @Test
    fun employmentDetails() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val pensjon = p2200.pensjon
        val pensjonBruker = pensjon?.bruker
        val pensjonBrukerArbeidsforhold = pensjonBruker?.arbeidsforhold
        val pensjonBrukerArbeidsforholdFirst = pensjonBrukerArbeidsforhold?.getOrNull(0)
        val pensjonBrukerArbeidsforholdSecond = pensjonBrukerArbeidsforhold?.getOrNull(1)
        val pensjonBrukerArbeidsforholdFirstInntekt = pensjonBrukerArbeidsforholdFirst?.inntekt
        val pensjonBrukerArbeidsforholdSecondInntekt = pensjonBrukerArbeidsforholdSecond?.inntekt
        val pensjonBrukerArbeidsforholdFirstInntektFirst = pensjonBrukerArbeidsforholdFirstInntekt?.getOrNull(0)
        val pensjonBrukerArbeidsforholdSecondInntektFirst = pensjonBrukerArbeidsforholdSecondInntekt?.getOrNull(0)
        val pensjonBrukerArbeidsforholdFirstInntektSecond = pensjonBrukerArbeidsforholdFirstInntekt?.getOrNull(1)
        val pensjonBrukerArbeidsforholdSecondInntektSecond = pensjonBrukerArbeidsforholdSecondInntekt?.getOrNull(1)

        // 3.1.1 occupation (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].yrke -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].occupation
        assertEquals("3.1.1 Test occupation 1", pensjonBrukerArbeidsforholdFirst?.yrke, "3.1.1 pensjon.bruker.arbeidsforhold.0.yrke")
        // 3.1.1 occupation (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].yrke -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].occupation
        assertEquals("3.1.1 Test occupation 2", pensjonBrukerArbeidsforholdSecond?.yrke, "3.1.1 pensjon.bruker.arbeidsforhold.1.yrke")
        // 3.1.2 employment Self Employment (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].type -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].employmentSelfEmployment.value[x]
        assertEquals("01", pensjonBrukerArbeidsforholdFirst?.type, "3.1.2 pensjon.bruker.arbeidsforhold.0.type")
        // 3.1.2 employment Self Employment (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].type -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].employmentSelfEmployment.value[x]
        assertEquals("02", pensjonBrukerArbeidsforholdSecond?.type, "3.1.2 pensjon.bruker.arbeidsforhold.1.type")
        // 3.1.3 start Date Intended Employment Or Self Employment (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].planlagtstartdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].startDateIntendedEmploymentOrSelfEmployment
        assertEquals("2020-01-02", pensjonBrukerArbeidsforholdFirst?.planlagtstartdato, "3.1.3 pensjon.bruker.arbeidsforhold.0.planlagtstartdato")
        // 3.1.3 start Date Intended Employment Or Self Employment (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].planlagtstartdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].startDateIntendedEmploymentOrSelfEmployment
        assertEquals("2021-01-02", pensjonBrukerArbeidsforholdSecond?.planlagtstartdato, "3.1.3 pensjon.bruker.arbeidsforhold.1.planlagtstartdato")
        // 3.1.4 end Date Employment Or Self Employment (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].sluttdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].endDateEmploymentOrSelfEmployment
        assertEquals("2020-01-02", pensjonBrukerArbeidsforholdFirst?.sluttdato, "3.1.4 pensjon.bruker.arbeidsforhold.0.sluttdato")
        // 3.1.4 end Date Employment Or Self Employment (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].sluttdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].endDateEmploymentOrSelfEmployment
        assertEquals("2021-01-02", pensjonBrukerArbeidsforholdSecond?.sluttdato, "3.1.4 pensjon.bruker.arbeidsforhold.1.sluttdato")
        // 3.1.5 intended Retirement Date From Employment Or Self Employment (entry 1)
        // Mapping (ACL 4.4): $pensjon.bruker.arbeidsforhold[x].planlagtpensjoneringsdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].intendedRetirementDateFromEmploymentOrSelfEmployment
        // Mapping: $pensjon.bruker.arbeidsforhold[x].planlagtpensjoneringsdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].intendedRetirementDateFromEmploymentOrSelfEmployment
        assertEquals("2020-01-02", pensjonBrukerArbeidsforholdFirst?.planlagtpensjoneringsdato, "3.1.5 pensjon.bruker.arbeidsforhold.0.planlagtpensjoneringsdato")
        // 3.1.5 intended Retirement Date From Employment Or Self Employment (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].planlagtpensjoneringsdato -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].intendedRetirementDateFromEmploymentOrSelfEmployment
        assertEquals("2021-01-02", pensjonBrukerArbeidsforholdSecond?.planlagtpensjoneringsdato, "3.1.5 pensjon.bruker.arbeidsforhold.1.planlagtpensjoneringsdato")
        // 3.1.6 hours Per Week (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].arbeidstimerperuke -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].hoursPerWeek
        assertEquals("1000.50", pensjonBrukerArbeidsforholdFirst?.arbeidstimerperuke, "3.1.6 pensjon.bruker.arbeidsforhold.0.arbeidstimerperuke")
        // 3.1.6 hours Per Week (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].arbeidstimerperuke -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].hoursPerWeek
        assertEquals("1001.50", pensjonBrukerArbeidsforholdSecond?.arbeidstimerperuke, "3.1.6 pensjon.bruker.arbeidsforhold.1.arbeidstimerperuke")
        // 3.1.7.1 amount (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloep -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amount
        assertEquals("1000.50", pensjonBrukerArbeidsforholdFirstInntektFirst?.beloep, "3.1.7.1 pensjon.bruker.arbeidsforhold.0.inntekt.0.beloep")
        // 3.1.7.1 amount (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloep -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amount
        assertEquals("1001.50", pensjonBrukerArbeidsforholdSecondInntektFirst?.beloep, "3.1.7.1 pensjon.bruker.arbeidsforhold.1.inntekt.0.beloep")
        // 3.1.7.1 amount (entry 3)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloep -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amount
        assertEquals("1002.50", pensjonBrukerArbeidsforholdFirstInntektSecond?.beloep, "3.1.7.1 pensjon.bruker.arbeidsforhold.0.inntekt.1.beloep")
        // 3.1.7.1 amount (entry 4)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloep -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amount
        assertEquals("1003.50", pensjonBrukerArbeidsforholdSecondInntektSecond?.beloep, "3.1.7.1 pensjon.bruker.arbeidsforhold.1.inntekt.1.beloep")
        // 3.1.7.2 currency (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].valuta -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].currency.value[x]
        assertEquals("NOK", pensjonBrukerArbeidsforholdFirstInntektFirst?.valuta, "3.1.7.2 pensjon.bruker.arbeidsforhold.0.inntekt.0.valuta")
        // 3.1.7.2 currency (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].valuta -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].currency.value[x]
        assertEquals("NOK", pensjonBrukerArbeidsforholdSecondInntektFirst?.valuta, "3.1.7.2 pensjon.bruker.arbeidsforhold.1.inntekt.0.valuta")
        // 3.1.7.2 currency (entry 3)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].valuta -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].currency.value[x]
        assertEquals("NOK", pensjonBrukerArbeidsforholdFirstInntektSecond?.valuta, "3.1.7.2 pensjon.bruker.arbeidsforhold.0.inntekt.1.valuta")
        // 3.1.7.2 currency (entry 4)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].valuta -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].currency.value[x]
        assertEquals("NOK", pensjonBrukerArbeidsforholdSecondInntektSecond?.valuta, "3.1.7.2 pensjon.bruker.arbeidsforhold.1.inntekt.1.valuta")
        // 3.1.7.3 amount Effective Since (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloeputbetaltsiden -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amountEffectiveSince
        assertEquals("2020-01-02", pensjonBrukerArbeidsforholdFirstInntektFirst?.beloeputbetaltsiden, "3.1.7.3 pensjon.bruker.arbeidsforhold.0.inntekt.0.beloeputbetaltsiden")
        // 3.1.7.3 amount Effective Since (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloeputbetaltsiden -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amountEffectiveSince
        assertEquals("2021-01-02", pensjonBrukerArbeidsforholdSecondInntektFirst?.beloeputbetaltsiden, "3.1.7.3 pensjon.bruker.arbeidsforhold.1.inntekt.0.beloeputbetaltsiden")
        // 3.1.7.3 amount Effective Since (entry 3)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloeputbetaltsiden -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amountEffectiveSince
        assertEquals("2022-01-02", pensjonBrukerArbeidsforholdFirstInntektSecond?.beloeputbetaltsiden, "3.1.7.3 pensjon.bruker.arbeidsforhold.0.inntekt.1.beloeputbetaltsiden")
        // 3.1.7.3 amount Effective Since (entry 4)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloeputbetaltsiden -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amountEffectiveSince
        assertEquals("2023-01-02", pensjonBrukerArbeidsforholdSecondInntektSecond?.beloeputbetaltsiden, "3.1.7.3 pensjon.bruker.arbeidsforhold.1.inntekt.1.beloeputbetaltsiden")
        // 3.1.7.4 payment Frequency (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].betalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].paymentFrequency.value[x]
        assertEquals("99", pensjonBrukerArbeidsforholdFirstInntektFirst?.betalingshyppighetinntekt, "3.1.7.4 pensjon.bruker.arbeidsforhold.0.inntekt.0.betalingshyppighetinntekt")
        // 3.1.7.4 payment Frequency (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].betalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].paymentFrequency.value[x]
        assertEquals("99", pensjonBrukerArbeidsforholdSecondInntektFirst?.betalingshyppighetinntekt, "3.1.7.4 pensjon.bruker.arbeidsforhold.1.inntekt.0.betalingshyppighetinntekt")
        // 3.1.7.4 payment Frequency (entry 3)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].betalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].paymentFrequency.value[x]
        assertEquals("99", pensjonBrukerArbeidsforholdFirstInntektSecond?.betalingshyppighetinntekt, "3.1.7.4 pensjon.bruker.arbeidsforhold.0.inntekt.1.betalingshyppighetinntekt")
        // 3.1.7.4 payment Frequency (entry 4)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].betalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].paymentFrequency.value[x]
        assertEquals("99", pensjonBrukerArbeidsforholdSecondInntektSecond?.betalingshyppighetinntekt, "3.1.7.4 pensjon.bruker.arbeidsforhold.1.inntekt.1.betalingshyppighetinntekt")
        // 3.1.7.5.1 other Payment Frequency (entry 1)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].annenbetalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("3.1.7.5.1 Test other Payment Frequency 1", pensjonBrukerArbeidsforholdFirstInntektFirst?.annenbetalingshyppighetinntekt, "3.1.7.5.1 pensjon.bruker.arbeidsforhold.0.inntekt.0.annenbetalingshyppighetinntekt")
        // 3.1.7.5.1 other Payment Frequency (entry 2)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].annenbetalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("3.1.7.5.1 Test other Payment Frequency 2", pensjonBrukerArbeidsforholdSecondInntektFirst?.annenbetalingshyppighetinntekt, "3.1.7.5.1 pensjon.bruker.arbeidsforhold.1.inntekt.0.annenbetalingshyppighetinntekt")
        // 3.1.7.5.1 other Payment Frequency (entry 3)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].annenbetalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("3.1.7.5.1 Test other Payment Frequency 3", pensjonBrukerArbeidsforholdFirstInntektSecond?.annenbetalingshyppighetinntekt, "3.1.7.5.1 pensjon.bruker.arbeidsforhold.0.inntekt.1.annenbetalingshyppighetinntekt")
        // 3.1.7.5.1 other Payment Frequency (entry 4)
        // Mapping: $pensjon.bruker.arbeidsforhold[x].inntekt[x].annenbetalingshyppighetinntekt -> P2200.InsuredPersonEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("3.1.7.5.1 Test other Payment Frequency 4", pensjonBrukerArbeidsforholdSecondInntektSecond?.annenbetalingshyppighetinntekt, "3.1.7.5.1 pensjon.bruker.arbeidsforhold.1.inntekt.1.annenbetalingshyppighetinntekt")
    }

    @Test
    fun benefitDetails() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val pensjon = p2200.pensjon
        val pensjonYtelser = pensjon?.ytelser
        val pensjonYtelserFirst = pensjonYtelser?.getOrNull(0)
        val pensjonYtelserSecond = pensjonYtelser?.getOrNull(1)
        val pensjonYtelserFirstBeloep = pensjonYtelserFirst?.beloep
        val pensjonYtelserSecondBeloep = pensjonYtelserSecond?.beloep
        val pensjonYtelserFirstInstitusjon = pensjonYtelserFirst?.institusjon
        val pensjonYtelserSecondInstitusjon = pensjonYtelserSecond?.institusjon
        val pensjonYtelserFirstMottasbasertpaaitem = pensjonYtelserFirst?.mottasbasertpaaitem
        val pensjonYtelserSecondMottasbasertpaaitem = pensjonYtelserSecond?.mottasbasertpaaitem
        val pensjonYtelserFirstBeloepFirst = pensjonYtelserFirstBeloep?.getOrNull(0)
        val pensjonYtelserSecondBeloepFirst = pensjonYtelserSecondBeloep?.getOrNull(0)
        val pensjonYtelserFirstBeloepSecond = pensjonYtelserFirstBeloep?.getOrNull(1)
        val pensjonYtelserSecondBeloepSecond = pensjonYtelserSecondBeloep?.getOrNull(1)
        val pensjonYtelserFirstMottasbasertpaaitemFirst = pensjonYtelserFirstMottasbasertpaaitem?.getOrNull(0)
        val pensjonYtelserSecondMottasbasertpaaitemFirst = pensjonYtelserSecondMottasbasertpaaitem?.getOrNull(0)
        val pensjonYtelserFirstMottasbasertpaaitemSecond = pensjonYtelserFirstMottasbasertpaaitem?.getOrNull(1)
        val pensjonYtelserSecondMottasbasertpaaitemSecond = pensjonYtelserSecondMottasbasertpaaitem?.getOrNull(1)

        // 4.1.1 benefits (entry 1)
        // Mapping: $pensjon.ytelser[x].ytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].benefits.value[x]
        assertEquals("99", pensjonYtelserFirst?.ytelse, "4.1.1 pensjon.ytelser.0.ytelse")
        // 4.1.1 benefits (entry 2)
        // Mapping: $pensjon.ytelser[x].ytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].benefits.value[x]
        assertEquals("99", pensjonYtelserSecond?.ytelse, "4.1.1 pensjon.ytelser.1.ytelse")
        // 4.1.2.1 other Benefit (entry 1)
        // Mapping: $pensjon.ytelser[x].annenytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].PleaseFillInFollowingIfBenefitsOtherBenefits.otherBenefit
        assertEquals("4.1.2.1 Test other Benefit 1", pensjonYtelserFirst?.annenytelse, "4.1.2.1 pensjon.ytelser.0.annenytelse")
        // 4.1.2.1 other Benefit (entry 2)
        // Mapping: $pensjon.ytelser[x].annenytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].PleaseFillInFollowingIfBenefitsOtherBenefits.otherBenefit
        assertEquals("4.1.2.1 Test other Benefit 2", pensjonYtelserSecond?.annenytelse, "4.1.2.1 pensjon.ytelser.1.annenytelse")
        // 4.1.3 status (entry 1)
        // Mapping: $pensjon.ytelser[x].status -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].status.value[x]
        assertEquals("01", pensjonYtelserFirst?.status, "4.1.3 pensjon.ytelser.0.status")
        // 4.1.3 status (entry 2)
        // Mapping: $pensjon.ytelser[x].status -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].status.value[x]
        assertEquals("02", pensjonYtelserSecond?.status, "4.1.3 pensjon.ytelser.1.status")
        // 4.1.4.1.1 country (entry 1)
        // Mapping: $pensjon.ytelser[x].institusjon.land -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.country.value[x]
        assertEquals("NO", pensjonYtelserFirstInstitusjon?.land, "4.1.4.1.1 pensjon.ytelser.0.institusjon.land")
        // 4.1.4.1.1 country (entry 2)
        // Mapping: $pensjon.ytelser[x].institusjon.land -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.country.value[x]
        assertEquals("SE", pensjonYtelserSecondInstitusjon?.land, "4.1.4.1.1 pensjon.ytelser.1.institusjon.land")
        // 4.1.4.1.2 personal Identification Number (entry 1)
        assertEquals("TEST-V44-PIN-1", pensjonYtelserFirstInstitusjon?.pin, "4.1.4.1.2 pensjon.ytelser.0.institusjon.pin")
        // 4.1.4.1.2 personal Identification Number (entry 2)
        assertEquals("TEST-V44-PIN-2", pensjonYtelserSecondInstitusjon?.pin, "4.1.4.1.2 pensjon.ytelser.1.institusjon.pin")
        // 4.1.4.1.2 personal Identification Number (entry 1)
        // Mapping: $pensjon.ytelser[x].pin.identifikator -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.personalIdentificationNumber
        assertEquals("TEST-V45-PIN-1", pensjonYtelserFirst?.pin?.identifikator, "4.1.4.1.2 pensjon.ytelser.0.pin.identifikator")
        // 4.1.4.1.2 personal Identification Number (entry 2)
        // Mapping: $pensjon.ytelser[x].pin.identifikator -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.personalIdentificationNumber
        assertEquals("TEST-V45-PIN-2", pensjonYtelserSecond?.pin?.identifikator, "4.1.4.1.2 pensjon.ytelser.1.pin.identifikator")
        // 4.1.4.1.3 sector (entry 1)
        // Mapping: $pensjon.ytelser[x].institusjon.sektor -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.sector.value[x]
        assertEquals("pensjoner", pensjonYtelserFirstInstitusjon?.sektor, "4.1.4.1.3 pensjon.ytelser.0.institusjon.sektor")
        // 4.1.4.1.3 sector (entry 2)
        // Mapping: $pensjon.ytelser[x].institusjon.sektor -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.sector.value[x]
        assertEquals("pensjoner", pensjonYtelserSecondInstitusjon?.sektor, "4.1.4.1.3 pensjon.ytelser.1.institusjon.sektor")
        // 4.1.4.1.4.1 institution ID (entry 1)
        // Mapping: $pensjon.ytelser[x].institusjon.institusjonsid -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.Institution.institutionID
        assertEquals("4.1.4.1.4.1 Test institution ID 1", pensjonYtelserFirstInstitusjon?.institusjonsid, "4.1.4.1.4.1 pensjon.ytelser.0.institusjon.institusjonsid")
        // 4.1.4.1.4.1 institution ID (entry 2)
        // Mapping: $pensjon.ytelser[x].institusjon.institusjonsid -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.Institution.institutionID
        assertEquals("4.1.4.1.4.1 Test institution ID 2", pensjonYtelserSecondInstitusjon?.institusjonsid, "4.1.4.1.4.1 pensjon.ytelser.1.institusjon.institusjonsid")
        // 4.1.4.1.4.2 institution Name (entry 1)
        // Mapping: $pensjon.ytelser[x].institusjon.institusjonsnavn -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.Institution.institutionName
        assertEquals("4.1.4.1.4.2 Test institution Name 1", pensjonYtelserFirstInstitusjon?.institusjonsnavn, "4.1.4.1.4.2 pensjon.ytelser.0.institusjon.institusjonsnavn")
        // 4.1.4.1.4.2 institution Name (entry 2)
        // Mapping: $pensjon.ytelser[x].institusjon.institusjonsnavn -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.Institution.Institution.institutionName
        assertEquals("4.1.4.1.4.2 Test institution Name 2", pensjonYtelserSecondInstitusjon?.institusjonsnavn, "4.1.4.1.4.2 pensjon.ytelser.1.institusjon.institusjonsnavn")
        // 4.1.4.2 case Number (entry 1)
        // Mapping: $pensjon.ytelser[x].institusjon.saksnummer -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.caseNumber
        assertEquals("4.1.4.2 Test case Number 1", pensjonYtelserFirstInstitusjon?.saksnummer, "4.1.4.2 pensjon.ytelser.0.institusjon.saksnummer")
        // 4.1.4.2 case Number (entry 2)
        // Mapping: $pensjon.ytelser[x].institusjon.saksnummer -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Institution.caseNumber
        assertEquals("4.1.4.2 Test case Number 2", pensjonYtelserSecondInstitusjon?.saksnummer, "4.1.4.2 pensjon.ytelser.1.institusjon.saksnummer")
        // 4.1.5 start Date Benefits Payment (entry 1)
        // Mapping: $pensjon.ytelser[x].startdatoutbetaling -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].startDateBenefitsPayment
        assertEquals("2020-02-02", pensjonYtelserFirst?.startdatoutbetaling, "4.1.5 pensjon.ytelser.0.startdatoutbetaling")
        // 4.1.5 start Date Benefits Payment (entry 2)
        // Mapping: $pensjon.ytelser[x].startdatoutbetaling -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].startDateBenefitsPayment
        assertEquals("2021-02-02", pensjonYtelserSecond?.startdatoutbetaling, "4.1.5 pensjon.ytelser.1.startdatoutbetaling")
        // 4.1.6 end Date Benefits Payment (entry 1)
        // Mapping: $pensjon.ytelser[x].sluttdatoutbetaling -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].endDateBenefitsPayment
        assertEquals("2020-04-02", pensjonYtelserFirst?.sluttdatoutbetaling, "4.1.6 pensjon.ytelser.0.sluttdatoutbetaling")
        // 4.1.6 end Date Benefits Payment (entry 2)
        // Mapping: $pensjon.ytelser[x].sluttdatoutbetaling -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].endDateBenefitsPayment
        assertEquals("2021-04-02", pensjonYtelserSecond?.sluttdatoutbetaling, "4.1.6 pensjon.ytelser.1.sluttdatoutbetaling")
        // 4.1.7 start Date Entitlement Benefits (entry 1)
        // Mapping: $pensjon.ytelser[x].startdatoretttilytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].startDateEntitlementBenefits
        assertEquals("2020-01-02", pensjonYtelserFirst?.startdatoretttilytelse, "4.1.7 pensjon.ytelser.0.startdatoretttilytelse")
        // 4.1.7 start Date Entitlement Benefits (entry 2)
        // Mapping: $pensjon.ytelser[x].startdatoretttilytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].startDateEntitlementBenefits
        assertEquals("2021-01-02", pensjonYtelserSecond?.startdatoretttilytelse, "4.1.7 pensjon.ytelser.1.startdatoretttilytelse")
        // 4.1.8 End date of entitlement (entry 1)
        //TODO 4.1.8 Fix both entitlement-end-date ACL source names to sluttdatorettilutbetaling.
        // Mapping (ACL v4.4, unresolved): $pensjon.ytelser[x].sluttdatoRettTilUtbetaling -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].endDateEntitlementBenefits
        // Mapping (ACL v4.5, unresolved): $pensjon.ytelser[x].sluttdatoUtbetaling -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].endDateEntitlementBenefits
        // Model-only assertion: $pensjon.ytelser[x].sluttdatorettilutbetaling; no matching ACL source in either version.
        assertEquals("2020-03-02", pensjonYtelserFirst?.sluttdatorettilutbetaling, "4.1.8 pensjon.ytelser.0.sluttdatorettilutbetaling")
        // 4.1.8 End date of entitlement (entry 2)
        //TODO 4.1.8 Fix both entitlement-end-date ACL source names to sluttdatorettilutbetaling.
        // Model-only assertion: $pensjon.ytelser[x].sluttdatorettilutbetaling; no matching ACL source in either version.
        assertEquals("2021-03-02", pensjonYtelserSecond?.sluttdatorettilutbetaling, "4.1.8 pensjon.ytelser.1.sluttdatorettilutbetaling")
        // 4.1.9.1 amount (entry 1)
        // Mapping: $pensjon.ytelser[x].beloep[x].beloep -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("1000.50", pensjonYtelserFirstBeloepFirst?.beloep, "4.1.9.1 pensjon.ytelser.0.beloep.0.beloep")
        // 4.1.9.1 amount (entry 2)
        // Mapping: $pensjon.ytelser[x].beloep[x].beloep -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("1001.50", pensjonYtelserSecondBeloepFirst?.beloep, "4.1.9.1 pensjon.ytelser.1.beloep.0.beloep")
        // 4.1.9.1 amount (entry 3)
        // Mapping: $pensjon.ytelser[x].beloep[x].beloep -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("1002.50", pensjonYtelserFirstBeloepSecond?.beloep, "4.1.9.1 pensjon.ytelser.0.beloep.1.beloep")
        // 4.1.9.1 amount (entry 4)
        // Mapping: $pensjon.ytelser[x].beloep[x].beloep -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("1003.50", pensjonYtelserSecondBeloepSecond?.beloep, "4.1.9.1 pensjon.ytelser.1.beloep.1.beloep")
        // 4.1.9.2 currency (entry 1)
        // Mapping: $pensjon.ytelser[x].beloep[x].valuta -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", pensjonYtelserFirstBeloepFirst?.valuta, "4.1.9.2 pensjon.ytelser.0.beloep.0.valuta")
        // 4.1.9.2 currency (entry 2)
        // Mapping: $pensjon.ytelser[x].beloep[x].valuta -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", pensjonYtelserSecondBeloepFirst?.valuta, "4.1.9.2 pensjon.ytelser.1.beloep.0.valuta")
        // 4.1.9.2 currency (entry 3)
        // Mapping: $pensjon.ytelser[x].beloep[x].valuta -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", pensjonYtelserFirstBeloepSecond?.valuta, "4.1.9.2 pensjon.ytelser.0.beloep.1.valuta")
        // 4.1.9.2 currency (entry 4)
        // Mapping: $pensjon.ytelser[x].beloep[x].valuta -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", pensjonYtelserSecondBeloepSecond?.valuta, "4.1.9.2 pensjon.ytelser.1.beloep.1.valuta")
        // 4.1.9.3 amount Effective Since (entry 1)
        // Mapping: $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2020-01-02", pensjonYtelserFirstBeloepFirst?.gjeldendesiden, "4.1.9.3 pensjon.ytelser.0.beloep.0.gjeldendesiden")
        // 4.1.9.3 amount Effective Since (entry 2)
        // Mapping: $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2021-01-02", pensjonYtelserSecondBeloepFirst?.gjeldendesiden, "4.1.9.3 pensjon.ytelser.1.beloep.0.gjeldendesiden")
        // 4.1.9.3 amount Effective Since (entry 3)
        // Mapping: $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2022-01-02", pensjonYtelserFirstBeloepSecond?.gjeldendesiden, "4.1.9.3 pensjon.ytelser.0.beloep.1.gjeldendesiden")
        // 4.1.9.3 amount Effective Since (entry 4)
        // Mapping: $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2023-01-02", pensjonYtelserSecondBeloepSecond?.gjeldendesiden, "4.1.9.3 pensjon.ytelser.1.beloep.1.gjeldendesiden")
        // 4.1.9.4 payment Frequency (entry 1)
        // Mapping: $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet, pensjonYtelserFirstBeloepFirst?.betalingshyppighetytelse, "4.1.9.4 pensjon.ytelser.0.beloep.0.betalingshyppighetytelse")
        // 4.1.9.4 payment Frequency (entry 2)
        // Mapping: $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet, pensjonYtelserSecondBeloepFirst?.betalingshyppighetytelse, "4.1.9.4 pensjon.ytelser.1.beloep.0.betalingshyppighetytelse")
        // 4.1.9.4 payment Frequency (entry 3)
        // Mapping: $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet, pensjonYtelserFirstBeloepSecond?.betalingshyppighetytelse, "4.1.9.4 pensjon.ytelser.0.beloep.1.betalingshyppighetytelse")
        // 4.1.9.4 payment Frequency (entry 4)
        // Mapping: $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet, pensjonYtelserSecondBeloepSecond?.betalingshyppighetytelse, "4.1.9.4 pensjon.ytelser.1.beloep.1.betalingshyppighetytelse")
        // 4.1.9.5.1 other Payment Frequency (entry 1)
        // Mapping: $pensjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.9.5.1 Test other Payment Frequency 1", pensjonYtelserFirstBeloepFirst?.annenbetalingshyppighetytelse, "4.1.9.5.1 pensjon.ytelser.0.beloep.0.annenbetalingshyppighetytelse")
        // 4.1.9.5.1 other Payment Frequency (entry 2)
        // Mapping: $pensjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.9.5.1 Test other Payment Frequency 2", pensjonYtelserSecondBeloepFirst?.annenbetalingshyppighetytelse, "4.1.9.5.1 pensjon.ytelser.1.beloep.0.annenbetalingshyppighetytelse")
        // 4.1.9.5.1 other Payment Frequency (entry 3)
        // Mapping: $pensjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.9.5.1 Test other Payment Frequency 3", pensjonYtelserFirstBeloepSecond?.annenbetalingshyppighetytelse, "4.1.9.5.1 pensjon.ytelser.0.beloep.1.annenbetalingshyppighetytelse")
        // 4.1.9.5.1 other Payment Frequency (entry 4)
        // Mapping: $pensjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.9.5.1 Test other Payment Frequency 4", pensjonYtelserSecondBeloepSecond?.annenbetalingshyppighetytelse, "4.1.9.5.1 pensjon.ytelser.1.beloep.1.annenbetalingshyppighetytelse")
        // 4.1.10.1 the Pension Received Based On (entry 1)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaa -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].thePensionReceivedBasedOn.value[x]
        assertEquals("01", pensjonYtelserFirst?.mottasbasertpaa, "4.1.10.1 pensjon.ytelser.0.mottasbasertpaa")
        // 4.1.10.1 the Pension Received Based On (entry 2)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaa -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].thePensionReceivedBasedOn.value[x]
        assertEquals("02", pensjonYtelserSecond?.mottasbasertpaa, "4.1.10.1 pensjon.ytelser.1.mottasbasertpaa")
        // 4.1.10.1 the Pension Received Based On (entry 1)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].mottasbasertpaa -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].thePensionReceivedBasedOn.value[x]
        assertEquals("02", pensjonYtelserFirstMottasbasertpaaitemFirst?.mottasbasertpaa, "4.1.10.1 pensjon.ytelser.0.mottasbasertpaaitem.0.mottasbasertpaa")
        // 4.1.10.1 the Pension Received Based On (entry 2)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].mottasbasertpaa -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].thePensionReceivedBasedOn.value[x]
        assertEquals("01", pensjonYtelserSecondMottasbasertpaaitemFirst?.mottasbasertpaa, "4.1.10.1 pensjon.ytelser.1.mottasbasertpaaitem.0.mottasbasertpaa")
        // 4.1.10.1 the Pension Received Based On (entry 3)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].mottasbasertpaa -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].thePensionReceivedBasedOn.value[x]
        assertEquals("01", pensjonYtelserFirstMottasbasertpaaitemSecond?.mottasbasertpaa, "4.1.10.1 pensjon.ytelser.0.mottasbasertpaaitem.1.mottasbasertpaa")
        // 4.1.10.1 the Pension Received Based On (entry 4)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].mottasbasertpaa -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].thePensionReceivedBasedOn.value[x]
        assertEquals("02", pensjonYtelserSecondMottasbasertpaaitemSecond?.mottasbasertpaa, "4.1.10.1 pensjon.ytelser.1.mottasbasertpaaitem.1.mottasbasertpaa")
        // 4.1.10.2 total Gross Amount Residence Based Pension (entry 1)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloepbostedsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountResidenceBasedPension
        assertEquals("1000.50", pensjonYtelserFirstMottasbasertpaaitemFirst?.totalbruttobeloepbostedsbasert, "4.1.10.2 pensjon.ytelser.0.mottasbasertpaaitem.0.totalbruttobeloepbostedsbasert")
        // 4.1.10.2 total Gross Amount Residence Based Pension (entry 2)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloepbostedsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountResidenceBasedPension
        assertEquals("1001.50", pensjonYtelserSecondMottasbasertpaaitemFirst?.totalbruttobeloepbostedsbasert, "4.1.10.2 pensjon.ytelser.1.mottasbasertpaaitem.0.totalbruttobeloepbostedsbasert")
        // 4.1.10.2 total Gross Amount Residence Based Pension (entry 3)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloepbostedsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountResidenceBasedPension
        assertEquals("1002.50", pensjonYtelserFirstMottasbasertpaaitemSecond?.totalbruttobeloepbostedsbasert, "4.1.10.2 pensjon.ytelser.0.mottasbasertpaaitem.1.totalbruttobeloepbostedsbasert")
        // 4.1.10.2 total Gross Amount Residence Based Pension (entry 4)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloepbostedsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountResidenceBasedPension
        assertEquals("1003.50", pensjonYtelserSecondMottasbasertpaaitemSecond?.totalbruttobeloepbostedsbasert, "4.1.10.2 pensjon.ytelser.1.mottasbasertpaaitem.1.totalbruttobeloepbostedsbasert")
        // 4.1.10.3 total Gross Amount Work Related Pension (entry 1)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloeparbeidsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountWorkRelatedPension
        assertEquals("1000.50", pensjonYtelserFirstMottasbasertpaaitemFirst?.totalbruttobeloeparbeidsbasert, "4.1.10.3 pensjon.ytelser.0.mottasbasertpaaitem.0.totalbruttobeloeparbeidsbasert")
        // 4.1.10.3 total Gross Amount Work Related Pension (entry 2)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloeparbeidsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountWorkRelatedPension
        assertEquals("1001.50", pensjonYtelserSecondMottasbasertpaaitemFirst?.totalbruttobeloeparbeidsbasert, "4.1.10.3 pensjon.ytelser.1.mottasbasertpaaitem.0.totalbruttobeloeparbeidsbasert")
        // 4.1.10.3 total Gross Amount Work Related Pension (entry 3)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloeparbeidsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountWorkRelatedPension
        assertEquals("1002.50", pensjonYtelserFirstMottasbasertpaaitemSecond?.totalbruttobeloeparbeidsbasert, "4.1.10.3 pensjon.ytelser.0.mottasbasertpaaitem.1.totalbruttobeloeparbeidsbasert")
        // 4.1.10.3 total Gross Amount Work Related Pension (entry 4)
        // Mapping: $pensjon.ytelser[x].mottasbasertpaaitem[x].totalbruttobeloeparbeidsbasert -> P2200.InsuredPersonBenefitDetails.BenefitDetails[x].ThePensionReceivedBasedOn[x].totalGrossAmountWorkRelatedPension
        assertEquals("1003.50", pensjonYtelserSecondMottasbasertpaaitemSecond?.totalbruttobeloeparbeidsbasert, "4.1.10.3 pensjon.ytelser.1.mottasbasertpaaitem.1.totalbruttobeloeparbeidsbasert")
    }

    @Test
    fun spouse() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val navP2200 = p2200.navP2200
        val navP2200Ektefelle = navP2200?.ektefelle
        val navP2200EktefellePerson = navP2200Ektefelle?.person
        val navP2200EktefellePersonPin = navP2200EktefellePerson?.pin
        val navP2200EktefellePersonFoedested = navP2200EktefellePerson?.foedested
        val navP2200EktefellePersonPinFirst = navP2200EktefellePersonPin?.getOrNull(0)
        val navP2200EktefellePersonPinSecond = navP2200EktefellePersonPin?.getOrNull(1)

        // 5.1 spouse (entry 1)
        // Mapping: $nav.ektefelle.type -> P2200.Spouse.spouse.value[x]
        assertEquals("01", navP2200Ektefelle?.type, "5.1 nav.ektefelle.type")
        // 5.2.1 family Name (entry 1)
        // Mapping: $nav.ektefelle.person.etternavn -> P2200.Spouse.PersonIdentification.familyName
        assertEquals("5.2.1 Test family Name 1", navP2200EktefellePerson?.etternavn, "5.2.1 nav.ektefelle.person.etternavn")
        // 5.2.2 forename (entry 1)
        // Mapping: $nav.ektefelle.person.fornavn -> P2200.Spouse.PersonIdentification.forename
        assertEquals("5.2.2 Test forename 1", navP2200EktefellePerson?.fornavn, "5.2.2 nav.ektefelle.person.fornavn")
        // 5.2.3 date Birth (entry 1)
        // Mapping: $nav.ektefelle.person.foedselsdato -> P2200.Spouse.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navP2200EktefellePerson?.foedselsdato, "5.2.3 nav.ektefelle.person.foedselsdato")
        // 5.2.4 sex (entry 1)
        // Mapping: $nav.ektefelle.person.kjoenn -> P2200.Spouse.PersonIdentification.sex.value[x]
        assertEquals("K", navP2200EktefellePerson?.kjoenn, "5.2.4 nav.ektefelle.person.kjoenn")
        // 5.2.5 family Name At Birth (entry 1)
        // Mapping: $nav.ektefelle.person.etternavnvedfoedsel -> P2200.Spouse.PersonIdentification.familyNameAtBirth
        assertEquals("5.2.5 Test family Name At Birth 1", navP2200EktefellePerson?.etternavnvedfoedsel, "5.2.5 nav.ektefelle.person.etternavnvedfoedsel")
        // 5.2.6 forename At Birth (entry 1)
        // Mapping: $nav.ektefelle.person.fornavnvedfoedsel -> P2200.Spouse.PersonIdentification.forenameAtBirth
        assertEquals("5.2.6 Test forename At Birth 1", navP2200EktefellePerson?.fornavnvedfoedsel, "5.2.6 nav.ektefelle.person.fornavnvedfoedsel")
        // 5.2.7.1.1 country (entry 1)
        // Mapping: $nav.ektefelle.person.pin[x].land -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navP2200EktefellePersonPinFirst?.land, "5.2.7.1.1 nav.ektefelle.person.pin.0.land")
        // 5.2.7.1.1 country (entry 2)
        // Mapping: $nav.ektefelle.person.pin[x].land -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navP2200EktefellePersonPinSecond?.land, "5.2.7.1.1 nav.ektefelle.person.pin.1.land")
        // 5.2.7.1.2 personal Identification Number (entry 1)
        // Mapping: $nav.ektefelle.person.pin[x].identifikator -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("5.2.7.1.2 Test personal Identification Number 1", navP2200EktefellePersonPinFirst?.identifikator, "5.2.7.1.2 nav.ektefelle.person.pin.0.identifikator")
        // 5.2.7.1.2 personal Identification Number (entry 2)
        // Mapping: $nav.ektefelle.person.pin[x].identifikator -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("5.2.7.1.2 Test personal Identification Number 2", navP2200EktefellePersonPinSecond?.identifikator, "5.2.7.1.2 nav.ektefelle.person.pin.1.identifikator")
        // 5.2.7.1.3 sector (entry 1)
        // Mapping: $nav.ektefelle.person.pin[x].sektor -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200EktefellePersonPinFirst?.sektor, "5.2.7.1.3 nav.ektefelle.person.pin.0.sektor")
        // 5.2.7.1.3 sector (entry 2)
        // Mapping: $nav.ektefelle.person.pin[x].sektor -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200EktefellePersonPinSecond?.sektor, "5.2.7.1.3 nav.ektefelle.person.pin.1.sektor")
        // 5.2.7.1.4.1 institution ID (entry 1)
        // Mapping: $nav.ektefelle.person.pin[x].institusjon.institusjonsid -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("5.2.7.1.4.1 Test institution ID 1", navP2200EktefellePersonPinFirst?.institusjon?.institusjonsid, "5.2.7.1.4.1 nav.ektefelle.person.pin.0.institusjon.institusjonsid")
        // 5.2.7.1.4.1 institution ID (entry 2)
        // Mapping: $nav.ektefelle.person.pin[x].institusjon.institusjonsid -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("5.2.7.1.4.1 Test institution ID 2", navP2200EktefellePersonPinSecond?.institusjon?.institusjonsid, "5.2.7.1.4.1 nav.ektefelle.person.pin.1.institusjon.institusjonsid")
        // 5.2.7.1.4.2 institution Name (entry 1)
        // Mapping: $nav.ektefelle.person.pin[x].institusjon.institusjonsnavn -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("5.2.7.1.4.2 Test institution Name 1", navP2200EktefellePersonPinFirst?.institusjon?.institusjonsnavn, "5.2.7.1.4.2 nav.ektefelle.person.pin.0.institusjon.institusjonsnavn")
        // 5.2.7.1.4.2 institution Name (entry 2)
        // Mapping: $nav.ektefelle.person.pin[x].institusjon.institusjonsnavn -> P2200.Spouse.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("5.2.7.1.4.2 Test institution Name 2", navP2200EktefellePersonPinSecond?.institusjon?.institusjonsnavn, "5.2.7.1.4.2 nav.ektefelle.person.pin.1.institusjon.institusjonsnavn")
        // 5.2.8.1.1 town (entry 1)
        // Mapping: $nav.ektefelle.person.foedested.by -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("5.2.8.1.1 Test town 1", navP2200EktefellePersonFoedested?.by, "5.2.8.1.1 nav.ektefelle.person.foedested.by")
        // 5.2.8.1.2 region (entry 1)
        // Mapping: $nav.ektefelle.person.foedested.region -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("5.2.8.1.2 Test region 1", navP2200EktefellePersonFoedested?.region, "5.2.8.1.2 nav.ektefelle.person.foedested.region")
        // 5.2.8.1.3 country (entry 1)
        // Mapping: $nav.ektefelle.person.foedested.land -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navP2200EktefellePersonFoedested?.land, "5.2.8.1.3 nav.ektefelle.person.foedested.land")
        // 5.2.8.2 father Family Name At Birth (entry 1)
        // Mapping: $nav.ektefelle.far.person.etternavnvedfoedsel -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("5.2.8.2 Test father Family Name At Birth 1", navP2200Ektefelle?.far?.person?.etternavnvedfoedsel, "5.2.8.2 nav.ektefelle.far.person.etternavnvedfoedsel")
        // 5.2.8.3 forename Father (entry 1)
        // Mapping: $nav.ektefelle.far.person.fornavn -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("5.2.8.3 Test forename Father 1", navP2200Ektefelle?.far?.person?.fornavn, "5.2.8.3 nav.ektefelle.far.person.fornavn")
        // 5.2.8.4 mother Family Name At Birth (entry 1)
        // Mapping: $nav.ektefelle.mor.person.etternavnvedfoedsel -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("5.2.8.4 Test mother Family Name At Birth 1", navP2200Ektefelle?.mor?.person?.etternavnvedfoedsel, "5.2.8.4 nav.ektefelle.mor.person.etternavnvedfoedsel")
        // 5.2.8.5 forename Mother (entry 1)
        // Mapping: $nav.ektefelle.mor.person.fornavn -> P2200.Spouse.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("5.2.8.5 Test forename Mother 1", navP2200Ektefelle?.mor?.person?.fornavn, "5.2.8.5 nav.ektefelle.mor.person.fornavn")
        // 5.3.1 nationality (entry 1)
        // Mapping: $nav.ektefelle.person.statsborgerskap[x].land -> P2200.Spouse.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navP2200EktefellePerson?.statsborgerskap?.getOrNull(0)?.land, "5.3.1 nav.ektefelle.person.statsborgerskap.0.land")
        // 5.3.1 nationality (entry 2)
        // Mapping: $nav.ektefelle.person.statsborgerskap[x].land -> P2200.Spouse.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navP2200EktefellePerson?.statsborgerskap?.getOrNull(1)?.land, "5.3.1 nav.ektefelle.person.statsborgerskap.1.land")
        // 5.3.2 previous Family Name (entry 1)
        // Mapping: $nav.ektefelle.person.tidligereetternavn -> P2200.Spouse.AdditionalInformationPerson.previousFamilyName
        assertEquals("5.3.2 Test previous Family Name 1", navP2200EktefellePerson?.tidligereetternavn, "5.3.2 nav.ektefelle.person.tidligereetternavn")
        // 5.3.3 previous Forename (entry 1)
        // Mapping: $nav.ektefelle.person.tidligerefornavn -> P2200.Spouse.AdditionalInformationPerson.previousForename
        assertEquals("5.3.3 Test previous Forename 1", navP2200EktefellePerson?.tidligerefornavn, "5.3.3 nav.ektefelle.person.tidligerefornavn")
    }

    @Test
    fun children() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val navP2200 = p2200.navP2200
        val navP2200Barn = navP2200?.barn
        val navP2200BarnFirst = navP2200Barn?.getOrNull(0)
        val navP2200BarnSecond = navP2200Barn?.getOrNull(1)
        val navP2200BarnFirstPerson = navP2200BarnFirst?.person
        val navP2200BarnSecondPerson = navP2200BarnSecond?.person
        val navP2200BarnFirstPersonPin = navP2200BarnFirstPerson?.pin
        val navP2200BarnSecondPersonPin = navP2200BarnSecondPerson?.pin
        val navP2200BarnFirstPersonFoedested = navP2200BarnFirstPerson?.foedested
        val navP2200BarnSecondPersonFoedested = navP2200BarnSecondPerson?.foedested
        val navP2200BarnFirstPersonPinFirst = navP2200BarnFirstPersonPin?.getOrNull(0)
        val navP2200BarnSecondPersonPinFirst = navP2200BarnSecondPersonPin?.getOrNull(0)
        val navP2200BarnFirstPersonPinSecond = navP2200BarnFirstPersonPin?.getOrNull(1)
        val navP2200BarnSecondPersonPinSecond = navP2200BarnSecondPersonPin?.getOrNull(1)

        // 6.1.1.1 family Name (entry 1)
        // Mapping: $nav.barn[x].person.etternavn -> P2200.Children.Child[x].PersonIdentification.familyName
        assertEquals("6.1.1.1 Test family Name 1", navP2200BarnFirstPerson?.etternavn, "6.1.1.1 nav.barn.0.person.etternavn")
        // 6.1.1.1 family Name (entry 2)
        // Mapping: $nav.barn[x].person.etternavn -> P2200.Children.Child[x].PersonIdentification.familyName
        assertEquals("6.1.1.1 Test family Name 2", navP2200BarnSecondPerson?.etternavn, "6.1.1.1 nav.barn.1.person.etternavn")
        // 6.1.1.2 forename (entry 1)
        // Mapping: $nav.barn[x].person.fornavn -> P2200.Children.Child[x].PersonIdentification.forename
        assertEquals("6.1.1.2 Test forename 1", navP2200BarnFirstPerson?.fornavn, "6.1.1.2 nav.barn.0.person.fornavn")
        // 6.1.1.2 forename (entry 2)
        // Mapping: $nav.barn[x].person.fornavn -> P2200.Children.Child[x].PersonIdentification.forename
        assertEquals("6.1.1.2 Test forename 2", navP2200BarnSecondPerson?.fornavn, "6.1.1.2 nav.barn.1.person.fornavn")
        // 6.1.1.3 date Birth (entry 1)
        // Mapping: $nav.barn[x].person.foedselsdato -> P2200.Children.Child[x].PersonIdentification.dateBirth
        assertEquals("2020-01-02", navP2200BarnFirstPerson?.foedselsdato, "6.1.1.3 nav.barn.0.person.foedselsdato")
        // 6.1.1.3 date Birth (entry 2)
        // Mapping: $nav.barn[x].person.foedselsdato -> P2200.Children.Child[x].PersonIdentification.dateBirth
        assertEquals("2021-01-02", navP2200BarnSecondPerson?.foedselsdato, "6.1.1.3 nav.barn.1.person.foedselsdato")
        // 6.1.1.4 sex (entry 1)
        // Mapping: $nav.barn[x].person.kjoenn -> P2200.Children.Child[x].PersonIdentification.sex.value[x]
        assertEquals("K", navP2200BarnFirstPerson?.kjoenn, "6.1.1.4 nav.barn.0.person.kjoenn")
        // 6.1.1.4 sex (entry 2)
        // Mapping: $nav.barn[x].person.kjoenn -> P2200.Children.Child[x].PersonIdentification.sex.value[x]
        assertEquals("M", navP2200BarnSecondPerson?.kjoenn, "6.1.1.4 nav.barn.1.person.kjoenn")
        // 6.1.1.5 family Name At Birth (entry 1)
        // Mapping: $nav.barn[x].person.etternavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.familyNameAtBirth
        assertEquals("6.1.1.5 Test family Name At Birth 1", navP2200BarnFirstPerson?.etternavnvedfoedsel, "6.1.1.5 nav.barn.0.person.etternavnvedfoedsel")
        // 6.1.1.5 family Name At Birth (entry 2)
        // Mapping: $nav.barn[x].person.etternavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.familyNameAtBirth
        assertEquals("6.1.1.5 Test family Name At Birth 2", navP2200BarnSecondPerson?.etternavnvedfoedsel, "6.1.1.5 nav.barn.1.person.etternavnvedfoedsel")
        // 6.1.1.6 forename At Birth (entry 1)
        // Mapping: $nav.barn[x].person.fornavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.forenameAtBirth
        assertEquals("6.1.1.6 Test forename At Birth 1", navP2200BarnFirstPerson?.fornavnvedfoedsel, "6.1.1.6 nav.barn.0.person.fornavnvedfoedsel")
        // 6.1.1.6 forename At Birth (entry 2)
        // Mapping: $nav.barn[x].person.fornavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.forenameAtBirth
        assertEquals("6.1.1.6 Test forename At Birth 2", navP2200BarnSecondPerson?.fornavnvedfoedsel, "6.1.1.6 nav.barn.1.person.fornavnvedfoedsel")
        // 6.1.1.7.1.1 country (entry 1)
        // Mapping: $nav.barn[x].person.pin[x].land -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navP2200BarnFirstPersonPinFirst?.land, "6.1.1.7.1.1 nav.barn.0.person.pin.0.land")
        // 6.1.1.7.1.1 country (entry 2)
        // Mapping: $nav.barn[x].person.pin[x].land -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navP2200BarnSecondPersonPinFirst?.land, "6.1.1.7.1.1 nav.barn.1.person.pin.0.land")
        // 6.1.1.7.1.1 country (entry 3)
        // Mapping: $nav.barn[x].person.pin[x].land -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navP2200BarnFirstPersonPinSecond?.land, "6.1.1.7.1.1 nav.barn.0.person.pin.1.land")
        // 6.1.1.7.1.1 country (entry 4)
        // Mapping: $nav.barn[x].person.pin[x].land -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navP2200BarnSecondPersonPinSecond?.land, "6.1.1.7.1.1 nav.barn.1.person.pin.1.land")
        // 6.1.1.7.1.2 personal Identification Number (entry 1)
        // Mapping: $nav.barn[x].person.pin[x].identifikator -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("6.1.1.7.1.2 Test personal Identification Number 1", navP2200BarnFirstPersonPinFirst?.identifikator, "6.1.1.7.1.2 nav.barn.0.person.pin.0.identifikator")
        // 6.1.1.7.1.2 personal Identification Number (entry 2)
        // Mapping: $nav.barn[x].person.pin[x].identifikator -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("6.1.1.7.1.2 Test personal Identification Number 2", navP2200BarnSecondPersonPinFirst?.identifikator, "6.1.1.7.1.2 nav.barn.1.person.pin.0.identifikator")
        // 6.1.1.7.1.2 personal Identification Number (entry 3)
        // Mapping: $nav.barn[x].person.pin[x].identifikator -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("6.1.1.7.1.2 Test personal Identification Number 3", navP2200BarnFirstPersonPinSecond?.identifikator, "6.1.1.7.1.2 nav.barn.0.person.pin.1.identifikator")
        // 6.1.1.7.1.2 personal Identification Number (entry 4)
        // Mapping: $nav.barn[x].person.pin[x].identifikator -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("6.1.1.7.1.2 Test personal Identification Number 4", navP2200BarnSecondPersonPinSecond?.identifikator, "6.1.1.7.1.2 nav.barn.1.person.pin.1.identifikator")
        // 6.1.1.7.1.3 sector (entry 1)
        // Mapping: $nav.barn[x].person.pin[x].sektor -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200BarnFirstPersonPinFirst?.sektor, "6.1.1.7.1.3 nav.barn.0.person.pin.0.sektor")
        // 6.1.1.7.1.3 sector (entry 2)
        // Mapping: $nav.barn[x].person.pin[x].sektor -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200BarnSecondPersonPinFirst?.sektor, "6.1.1.7.1.3 nav.barn.1.person.pin.0.sektor")
        // 6.1.1.7.1.3 sector (entry 3)
        // Mapping: $nav.barn[x].person.pin[x].sektor -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200BarnFirstPersonPinSecond?.sektor, "6.1.1.7.1.3 nav.barn.0.person.pin.1.sektor")
        // 6.1.1.7.1.3 sector (entry 4)
        // Mapping: $nav.barn[x].person.pin[x].sektor -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navP2200BarnSecondPersonPinSecond?.sektor, "6.1.1.7.1.3 nav.barn.1.person.pin.1.sektor")
        // 6.1.1.7.1.4.1 institution ID (entry 1)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("6.1.1.7.1.4.1 Test institution ID 1", navP2200BarnFirstPersonPinFirst?.institusjon?.institusjonsid, "6.1.1.7.1.4.1 nav.barn.0.person.pin.0.institusjon.institusjonsid")
        // 6.1.1.7.1.4.1 institution ID (entry 2)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("6.1.1.7.1.4.1 Test institution ID 2", navP2200BarnSecondPersonPinFirst?.institusjon?.institusjonsid, "6.1.1.7.1.4.1 nav.barn.1.person.pin.0.institusjon.institusjonsid")
        // 6.1.1.7.1.4.1 institution ID (entry 3)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("6.1.1.7.1.4.1 Test institution ID 3", navP2200BarnFirstPersonPinSecond?.institusjon?.institusjonsid, "6.1.1.7.1.4.1 nav.barn.0.person.pin.1.institusjon.institusjonsid")
        // 6.1.1.7.1.4.1 institution ID (entry 4)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("6.1.1.7.1.4.1 Test institution ID 4", navP2200BarnSecondPersonPinSecond?.institusjon?.institusjonsid, "6.1.1.7.1.4.1 nav.barn.1.person.pin.1.institusjon.institusjonsid")
        // 6.1.1.7.1.4.2 institution Name (entry 1)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("6.1.1.7.1.4.2 Test institution Name 1", navP2200BarnFirstPersonPinFirst?.institusjon?.institusjonsnavn, "6.1.1.7.1.4.2 nav.barn.0.person.pin.0.institusjon.institusjonsnavn")
        // 6.1.1.7.1.4.2 institution Name (entry 2)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("6.1.1.7.1.4.2 Test institution Name 2", navP2200BarnSecondPersonPinFirst?.institusjon?.institusjonsnavn, "6.1.1.7.1.4.2 nav.barn.1.person.pin.0.institusjon.institusjonsnavn")
        // 6.1.1.7.1.4.2 institution Name (entry 3)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("6.1.1.7.1.4.2 Test institution Name 3", navP2200BarnFirstPersonPinSecond?.institusjon?.institusjonsnavn, "6.1.1.7.1.4.2 nav.barn.0.person.pin.1.institusjon.institusjonsnavn")
        // 6.1.1.7.1.4.2 institution Name (entry 4)
        // Mapping: $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2200.Children.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("6.1.1.7.1.4.2 Test institution Name 4", navP2200BarnSecondPersonPinSecond?.institusjon?.institusjonsnavn, "6.1.1.7.1.4.2 nav.barn.1.person.pin.1.institusjon.institusjonsnavn")
        // 6.1.1.8.1.1 town (entry 1)
        // Mapping: $nav.barn[x].person.foedested.by -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("6.1.1.8.1.1 Test town 1", navP2200BarnFirstPersonFoedested?.by, "6.1.1.8.1.1 nav.barn.0.person.foedested.by")
        // 6.1.1.8.1.1 town (entry 2)
        // Mapping: $nav.barn[x].person.foedested.by -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("6.1.1.8.1.1 Test town 2", navP2200BarnSecondPersonFoedested?.by, "6.1.1.8.1.1 nav.barn.1.person.foedested.by")
        // 6.1.1.8.1.2 region (entry 1)
        // Mapping: $nav.barn[x].person.foedested.region -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("6.1.1.8.1.2 Test region 1", navP2200BarnFirstPersonFoedested?.region, "6.1.1.8.1.2 nav.barn.0.person.foedested.region")
        // 6.1.1.8.1.2 region (entry 2)
        // Mapping: $nav.barn[x].person.foedested.region -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("6.1.1.8.1.2 Test region 2", navP2200BarnSecondPersonFoedested?.region, "6.1.1.8.1.2 nav.barn.1.person.foedested.region")
        // 6.1.1.8.1.3 country (entry 1)
        // Mapping: $nav.barn[x].person.foedested.land -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navP2200BarnFirstPersonFoedested?.land, "6.1.1.8.1.3 nav.barn.0.person.foedested.land")
        // 6.1.1.8.1.3 country (entry 2)
        // Mapping: $nav.barn[x].person.foedested.land -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("SE", navP2200BarnSecondPersonFoedested?.land, "6.1.1.8.1.3 nav.barn.1.person.foedested.land")
        // 6.1.1.8.2 father Family Name At Birth (entry 1)
        // Mapping: $nav.barn[x].far.person.etternavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("6.1.1.8.2 Test father Family Name At Birth 1", navP2200BarnFirst?.far?.person?.etternavnvedfoedsel, "6.1.1.8.2 nav.barn.0.far.person.etternavnvedfoedsel")
        // 6.1.1.8.2 father Family Name At Birth (entry 2)
        // Mapping: $nav.barn[x].far.person.etternavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("6.1.1.8.2 Test father Family Name At Birth 2", navP2200BarnSecond?.far?.person?.etternavnvedfoedsel, "6.1.1.8.2 nav.barn.1.far.person.etternavnvedfoedsel")
        // 6.1.1.8.3 forename Father (entry 1)
        // Mapping: $nav.barn[x].far.person.fornavn -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("6.1.1.8.3 Test forename Father 1", navP2200BarnFirst?.far?.person?.fornavn, "6.1.1.8.3 nav.barn.0.far.person.fornavn")
        // 6.1.1.8.3 forename Father (entry 2)
        // Mapping: $nav.barn[x].far.person.fornavn -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("6.1.1.8.3 Test forename Father 2", navP2200BarnSecond?.far?.person?.fornavn, "6.1.1.8.3 nav.barn.1.far.person.fornavn")
        // 6.1.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping: $nav.barn[x].mor.person.etternavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("6.1.1.8.4 Test mother Family Name At Birth 1", navP2200BarnFirst?.mor?.person?.etternavnvedfoedsel, "6.1.1.8.4 nav.barn.0.mor.person.etternavnvedfoedsel")
        // 6.1.1.8.4 mother Family Name At Birth (entry 2)
        // Mapping: $nav.barn[x].mor.person.etternavnvedfoedsel -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("6.1.1.8.4 Test mother Family Name At Birth 2", navP2200BarnSecond?.mor?.person?.etternavnvedfoedsel, "6.1.1.8.4 nav.barn.1.mor.person.etternavnvedfoedsel")
        // 6.1.1.8.5 forename Mother (entry 1)
        // Mapping: $nav.barn[x].mor.person.fornavn -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("6.1.1.8.5 Test forename Mother 1", navP2200BarnFirst?.mor?.person?.fornavn, "6.1.1.8.5 nav.barn.0.mor.person.fornavn")
        // 6.1.1.8.5 forename Mother (entry 2)
        // Mapping: $nav.barn[x].mor.person.fornavn -> P2200.Children.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("6.1.1.8.5 Test forename Mother 2", navP2200BarnSecond?.mor?.person?.fornavn, "6.1.1.8.5 nav.barn.1.mor.person.fornavn")
        // 6.1.2.1 nationality (entry 1)
        // Mapping: $nav.barn[x].person.statsborgerskap[x].land -> P2200.Children.Child[x].AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navP2200BarnFirstPerson?.statsborgerskap?.getOrNull(0)?.land, "6.1.2.1 nav.barn.0.person.statsborgerskap.0.land")
        // 6.1.2.1 nationality (entry 2)
        // Mapping: $nav.barn[x].person.statsborgerskap[x].land -> P2200.Children.Child[x].AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navP2200BarnSecondPerson?.statsborgerskap?.getOrNull(0)?.land, "6.1.2.1 nav.barn.1.person.statsborgerskap.0.land")
        // 6.1.2.1 nationality (entry 3)
        // Mapping: $nav.barn[x].person.statsborgerskap[x].land -> P2200.Children.Child[x].AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navP2200BarnFirstPerson?.statsborgerskap?.getOrNull(1)?.land, "6.1.2.1 nav.barn.0.person.statsborgerskap.1.land")
        // 6.1.2.1 nationality (entry 4)
        // Mapping: $nav.barn[x].person.statsborgerskap[x].land -> P2200.Children.Child[x].AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navP2200BarnSecondPerson?.statsborgerskap?.getOrNull(1)?.land, "6.1.2.1 nav.barn.1.person.statsborgerskap.1.land")
        // 6.1.2.2 relationship Insured Person (entry 1)
        // Mapping: $nav.barn[x].relasjontilbruker43 -> P2200.Children.Child[x].AdditionalInformationPerson.relationshipInsuredPerson.value[x]
        assertEquals("99", navP2200BarnFirst?.getRelasjontilbruker43(), "6.1.2.2 nav.barn.0.relasjontilbruker43")
        // 6.1.2.2 relationship Insured Person (entry 2)
        // Mapping: $nav.barn[x].relasjontilbruker43 -> P2200.Children.Child[x].AdditionalInformationPerson.relationshipInsuredPerson.value[x]
        assertEquals("99", navP2200BarnSecond?.getRelasjontilbruker43(), "6.1.2.2 nav.barn.1.relasjontilbruker43")
        // 6.1.2.3.1 specifics Other Child (entry 1)
        // Mapping: $nav.barn[x].opplysningeromannetbarn -> P2200.Children.Child[x].AdditionalInformationPerson.FillInFollowingIfRelationshipInsuredPersonIsOther.specificsOtherChild
        assertEquals("6.1.2.3.1 Test specifics Other Child 1", navP2200BarnFirst?.opplysningeromannetbarn, "6.1.2.3.1 nav.barn.0.opplysningeromannetbarn")
        // 6.1.2.3.1 specifics Other Child (entry 2)
        // Mapping: $nav.barn[x].opplysningeromannetbarn -> P2200.Children.Child[x].AdditionalInformationPerson.FillInFollowingIfRelationshipInsuredPersonIsOther.specificsOtherChild
        assertEquals("6.1.2.3.1 Test specifics Other Child 2", navP2200BarnSecond?.opplysningeromannetbarn, "6.1.2.3.1 nav.barn.1.opplysningeromannetbarn")
        // 6.1.2.4 date Death (entry 1)
        // Mapping: $nav.barn[x].person.doedsdato -> P2200.Children.Child[x].AdditionalInformationPerson.dateDeath
        assertEquals("2020-01-02", navP2200BarnFirstPerson?.doedsdato, "6.1.2.4 nav.barn.0.person.doedsdato")
        // 6.1.2.4 date Death (entry 2)
        // Mapping: $nav.barn[x].person.doedsdato -> P2200.Children.Child[x].AdditionalInformationPerson.dateDeath
        assertEquals("2021-01-02", navP2200BarnSecondPerson?.doedsdato, "6.1.2.4 nav.barn.1.person.doedsdato")
    }

    @Test
    fun representative() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val navP2200 = p2200.navP2200
        val navP2200Verge = navP2200?.verge
        val navP2200VergePerson = navP2200Verge?.person
        val navP2200VergeAdresse = navP2200Verge?.adresse
        val navP2200VergePersonKontakt = navP2200VergePerson?.kontakt
        val navP2200VergePersonKontaktTelefon = navP2200VergePersonKontakt?.telefon

        // 7.1 family Name (entry 1)
        // Mapping: $nav.verge.person.etternavn -> P2200.InformationRepresentativeLegalGuardian.familyName
        assertEquals("7.1 Test family Name 1", navP2200VergePerson?.etternavn, "7.1 nav.verge.person.etternavn")
        // 7.2 forename (entry 1)
        // Mapping ): $nav.verge.person.fornavn -> P2200.InformationRepresentativeLegalGuardian.forename
        // Mapping: $nav.verge.person.fornavn -> P2200.InformationRepresentativeLegalGuardian.forename
        assertEquals("7.2 Test forename 1", navP2200VergePerson?.fornavn, "7.2 nav.verge.person.fornavn")
        // 7.3 grounds (entry 1)
        // Mapping /n): $nav.verge.vergemaal.mandat -> P2200.InformationRepresentativeLegalGuardian.grounds
        // Mapping: $nav.verge.vergemaal.mandat -> P2200.InformationRepresentativeLegalGuardian.grounds
        assertEquals("7.3 Test grounds 1", navP2200Verge?.vergemaal?.mandat, "7.3 nav.verge.vergemaal.mandat")
        // 7.4.1 street (entry 1)
        // Mapping: $nav.verge.adresse.gate -> P2200.InformationRepresentativeLegalGuardian.Address.street
        assertEquals("7.4.1 Test street 1", navP2200VergeAdresse?.gate, "7.4.1 nav.verge.adresse.gate")
        // 7.4.2 building Name (entry 1)
        // Mapping: $nav.verge.adresse.bygning -> P2200.InformationRepresentativeLegalGuardian.Address.buildingName
        assertEquals("7.4.2 Test building Name 1", navP2200VergeAdresse?.bygning, "7.4.2 nav.verge.adresse.bygning")
        // 7.4.3 town (entry 1)
        // Mapping: $nav.verge.adresse.by -> P2200.InformationRepresentativeLegalGuardian.Address.town
        assertEquals("7.4.3 Test town 1", navP2200VergeAdresse?.by, "7.4.3 nav.verge.adresse.by")
        // 7.4.4 postal Code (entry 1)
        // Mapping: $nav.verge.adresse.postnummer -> P2200.InformationRepresentativeLegalGuardian.Address.postalCode
        assertEquals("0123", navP2200VergeAdresse?.postnummer, "7.4.4 nav.verge.adresse.postnummer")
        // 7.4.5 region (entry 1)
        // Mapping: $nav.verge.adresse.region -> P2200.InformationRepresentativeLegalGuardian.Address.region
        assertEquals("7.4.5 Test region 1", navP2200VergeAdresse?.region, "7.4.5 nav.verge.adresse.region")
        // 7.4.6 country (entry 1)
        // Mapping: $nav.verge.adresse.land -> P2200.InformationRepresentativeLegalGuardian.Address.country.value[x]
        assertEquals("NO", navP2200VergeAdresse?.land, "7.4.6 nav.verge.adresse.land")
        // 7.5.1.1.1 type (entry 1)
        // Mapping: $nav.verge.person.kontakt.telefon[x].type -> P2200.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", navP2200VergePersonKontaktTelefon?.getOrNull(0)?.type, "7.5.1.1.1 nav.verge.person.kontakt.telefon.0.type")
        // 7.5.1.1.1 type (entry 2)
        // Mapping: $nav.verge.person.kontakt.telefon[x].type -> P2200.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", navP2200VergePersonKontaktTelefon?.getOrNull(1)?.type, "7.5.1.1.1 nav.verge.person.kontakt.telefon.1.type")
        // 7.5.1.1.2 number (entry 1)
        // Mapping: $nav.verge.person.kontakt.telefon[x].nummer -> P2200.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("7.5.1.1.2 Test number 1", navP2200VergePersonKontaktTelefon?.getOrNull(0)?.nummer, "7.5.1.1.2 nav.verge.person.kontakt.telefon.0.nummer")
        // 7.5.1.1.2 number (entry 2)
        // Mapping: $nav.verge.person.kontakt.telefon[x].nummer -> P2200.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("7.5.1.1.2 Test number 2", navP2200VergePersonKontaktTelefon?.getOrNull(1)?.nummer, "7.5.1.1.2 nav.verge.person.kontakt.telefon.1.nummer")
        // 7.5.2.1.1 email (entry 1)
        // Mapping: $nav.verge.person.kontakt.email[x].adresse -> P2200.InformationRepresentativeLegalGuardian.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-7.5.2.1.1-1@example.invalid", navP2200VergePersonKontakt?.email?.getOrNull(0)?.adresse, "7.5.2.1.1 nav.verge.person.kontakt.email.0.adresse")
        // 7.5.2.1.1 email (entry 2)
        // Mapping: $nav.verge.person.kontakt.email[x].adresse -> P2200.InformationRepresentativeLegalGuardian.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-7.5.2.1.1-2@example.invalid", navP2200VergePersonKontakt?.email?.getOrNull(1)?.adresse, "7.5.2.1.1 nav.verge.person.kontakt.email.1.adresse")
    }

    @Test
    fun paymentInformation() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val navP2200 = p2200.navP2200
        val navP2200Bruker = navP2200?.bruker
        val navP2200BrukerBank = navP2200Bruker?.bank
        val navP2200BrukerBankKonto = navP2200BrukerBank?.konto
        val navP2200BrukerBankAdresse = navP2200BrukerBank?.adresse

        // 8.1 payment To (entry 1)
        // Mapping: $nav.bruker.bank.konto.innehaver.rolle -> P2200.InformationPayment.paymentTo.value[x]
        assertEquals("01", navP2200BrukerBankKonto?.innehaver?.rolle, "8.1 nav.bruker.bank.konto.innehaver.rolle")
        // 8.2.1 account Holder Name (entry 1)
        // Mapping: $nav.bruker.bank.konto.innehaver.navn -> P2200.InformationPayment.BankInformation.accountHolderName
        assertEquals("8.2.1 Test account Holder Name 1", navP2200BrukerBankKonto?.innehaver?.navn, "8.2.1 nav.bruker.bank.konto.innehaver.navn")
        // 8.2.2.1 IBAN (entry 1)
        // Mapping: $nav.bruker.bank.konto.sepa.iban -> P2200.InformationPayment.BankInformation.SEPAAccount.IBAN
        assertEquals("TEST-IBAN-NOT-A-REAL-ACCOUNT", navP2200BrukerBankKonto?.sepa?.iban, "8.2.2.1 nav.bruker.bank.konto.sepa.iban")
        // 8.2.2.2 BICSWIFT (entry 1)
        // Mapping: $nav.bruker.bank.konto.sepa.swift -> P2200.InformationPayment.BankInformation.SEPAAccount.BICSWIFT
        assertEquals("TEST-NOT-A-REAL-BIC", navP2200BrukerBankKonto?.sepa?.swift, "8.2.2.2 nav.bruker.bank.konto.sepa.swift")
        // 8.2.3.1 account Number (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.konto.kontonr -> P2200.InformationPayment.BankInformation.NonSEPAAccount.accountNumber
        assertEquals("8.2.3.1 Test account Number 1", navP2200BrukerBankKonto?.kontonr, "8.2.3.1 nav.bruker.bank.konto.kontonr")
        // 8.2.3.2 BICSWIFT (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.konto.ikkesepa.swift -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BICSWIFT
        assertEquals("TEST-NOT-A-REAL-BIC", navP2200BrukerBankKonto?.ikkesepa?.swift, "8.2.3.2 nav.bruker.bank.konto.ikkesepa.swift")
        // 8.2.3.3 bank Name (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.navn -> P2200.InformationPayment.BankInformation.NonSEPAAccount.bankName
        assertEquals("8.2.3.3 Test bank Name 1", navP2200BrukerBank?.navn, "8.2.3.3 nav.bruker.bank.navn")
        // 8.2.3.4.1 street (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.adresse.gate -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.street
        assertEquals("8.2.3.4.1 Test street 1", navP2200BrukerBankAdresse?.gate, "8.2.3.4.1 nav.bruker.bank.adresse.gate")
        // 8.2.3.4.2 building Name (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.adresse.bygning -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.buildingName
        assertEquals("8.2.3.4.2 Test building Name 1", navP2200BrukerBankAdresse?.bygning, "8.2.3.4.2 nav.bruker.bank.adresse.bygning")
        // 8.2.3.4.3 town (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.adresse.by -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.town
        assertEquals("8.2.3.4.3 Test town 1", navP2200BrukerBankAdresse?.by, "8.2.3.4.3 nav.bruker.bank.adresse.by")
        // 8.2.3.4.4 postal Code (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.adresse.postkode -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.postalCode
        assertEquals("0123", navP2200BrukerBankAdresse?.postkode, "8.2.3.4.4 nav.bruker.bank.adresse.postkode")
        // 8.2.3.4.5 region (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.adresse.region -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.region
        assertEquals("8.2.3.4.5 Test region 1", navP2200BrukerBankAdresse?.region, "8.2.3.4.5 nav.bruker.bank.adresse.region")
        // 8.2.3.4.6 country (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping: $nav.bruker.bank.adresse.land -> P2200.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.country.value[x]
        assertEquals("NO", navP2200BrukerBankAdresse?.land, "8.2.3.4.6 nav.bruker.bank.adresse.land")
    }

    @Test
    fun miscellaneous() {
        val p2200 = mapJsonToAny<P2200>(p2200Json())
        val pensjon = p2200.pensjon

        // 9.1 date Claim (entry 1)
        // Mapping: $nav.krav.dato -> P2200.Miscellaneous.dateClaim
        assertEquals("2020-01-02", p2200.navP2200?.krav?.dato, "9.1 nav.krav.dato")
        // 9.2 recipient Decision (entry 1)
        // Mapping: $pensjon.mottaker[x] -> P2200.Miscellaneous.recipientDecision.value[x]
        assertEquals("01", pensjon?.mottaker?.getOrNull(0), "9.2 pensjon.mottaker.0")
        // 9.2 recipient Decision (entry 2)
        // Mapping: $pensjon.mottaker[x] -> P2200.Miscellaneous.recipientDecision.value[x]
        assertEquals("02", pensjon?.mottaker?.getOrNull(1), "9.2 pensjon.mottaker.1")
        // 9.3 deductions Grounds (entry 1)
        // Mapping: $pensjon.trekkgrunnlag[x] -> P2200.Miscellaneous.deductionsGrounds.value[x]
        assertEquals("01", pensjon?.trekkgrunnlag?.getOrNull(0), "9.3 pensjon.trekkgrunnlag.0")
        // 9.3 deductions Grounds (entry 2)
        // Mapping: $pensjon.trekkgrunnlag[x] -> P2200.Miscellaneous.deductionsGrounds.value[x]
        assertEquals("02", pensjon?.trekkgrunnlag?.getOrNull(1), "9.3 pensjon.trekkgrunnlag.1")
        // 9.4 attachments (entry 1)
        // Mapping: $pensjon.vedlegg[x] -> P2200.Miscellaneous.attachments.value[x]
        assertEquals("99", pensjon?.vedlegg?.getOrNull(0), "9.4 pensjon.vedlegg.0")
        // 9.4 attachments (entry 2)
        // Mapping: $pensjon.vedlegg[x] -> P2200.Miscellaneous.attachments.value[x]
        assertEquals("99", pensjon?.vedlegg?.getOrNull(1), "9.4 pensjon.vedlegg.1")
        // 9.5.1 other Attachment (entry 1)
        // Mapping: $pensjon.vedleggandre -> P2200.Miscellaneous.PleaseFillInFollowingIfAttachmentsOther.otherAttachment
        assertEquals("9.5.1 Test other Attachment 1", pensjon?.vedleggandre, "9.5.1 pensjon.vedleggandre")
        // 9.6 requested Documents (entry 1)
        // Mapping: $pensjon.etterspurtedokumenter -> P2200.Miscellaneous.requestedDocuments
        assertEquals("9.6 Test requested Documents 1", pensjon?.etterspurtedokumenter, "9.6 pensjon.etterspurtedokumenter")
        // 9.7 additional Information (entry 1)
        // Mapping: $pensjon.ytterligeinformasjon -> P2200.Miscellaneous.additionalInformation
        assertEquals("9.7 Test additional Information 1", pensjon?.ytterligeinformasjon, "9.7 pensjon.ytterligeinformasjon")
    }

    @Test
    fun supportedFieldsSurviveJsonRoundTrip() {
        val expected = p2200Json()
        val p2200 = mapJsonToAny<P2200>(expected)

        // 1-9 All populated supported fields, including collection sizes and order.
        JSONAssert.assertEquals(expected, p2200.toJsonSkipEmpty(), true)
    }
}
