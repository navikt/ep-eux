package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Field numbers checked against P5000.pdf (9 pages, printed 27 May 2019).
 * Synthetic fixture only. Tests JSON <-> P5000, not the cross-repository runtime ACL.
 * Both ACL templates currently use the same source paths; mapping comments show v4.5.
 * Source paths resolve to model properties; runtime ACL behavior is not verified by this test.
 */
class P5000HelMappetTest {
    private fun p5000Json(): String =
        requireNotNull(javaClass.getResource("/sed/P5000-HelMappet-NAV.json")).readText()

    @Test
    fun localCaseNumbers() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())
        val nav = p5000.nav
        val navEessisak = nav?.eessisak
        val navEessisakFirst = navEessisak?.getOrNull(0)
        val navEessisakSecond = navEessisak?.getOrNull(1)

        // 1.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].land -> P5000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("NO", navEessisakFirst?.land, "1.1.1 nav.eessisak.0.land")
        // 1.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].land -> P5000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("SE", navEessisakSecond?.land, "1.1.1 nav.eessisak.1.land")
        // 1.1.2 case Number (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].saksnummer -> P5000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 1", navEessisakFirst?.saksnummer, "1.1.2 nav.eessisak.0.saksnummer")
        // 1.1.2 case Number (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].saksnummer -> P5000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 2", navEessisakSecond?.saksnummer, "1.1.2 nav.eessisak.1.saksnummer")
        // 1.1.3.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsid -> P5000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 1", navEessisakFirst?.institusjonsid, "1.1.3.1 nav.eessisak.0.institusjonsid")
        // 1.1.3.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsid -> P5000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 2", navEessisakSecond?.institusjonsid, "1.1.3.1 nav.eessisak.1.institusjonsid")
        // 1.1.3.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsnavn -> P5000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 1", navEessisakFirst?.institusjonsnavn, "1.1.3.2 nav.eessisak.0.institusjonsnavn")
        // 1.1.3.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsnavn -> P5000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 2", navEessisakSecond?.institusjonsnavn, "1.1.3.2 nav.eessisak.1.institusjonsnavn")
    }

    @Test
    fun insuredPerson() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())
        val nav = p5000.nav
        val navBruker = nav?.bruker
        val navBrukerPerson = navBruker?.person
        val navBrukerAdresse = navBruker?.adresse
        val navBrukerPersonPin = navBrukerPerson?.pin
        val navBrukerPersonFoedested = navBrukerPerson?.foedested
        val navBrukerPersonPinFirst = navBrukerPersonPin?.getOrNull(0)
        val navBrukerPersonPinSecond = navBrukerPersonPin?.getOrNull(1)

        // 2.1.1 family Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.etternavn -> P5000.InsuredPerson.PersonIdentification.familyName
        assertEquals("2.1.1 Test family Name 1", navBrukerPerson?.etternavn, "2.1.1 nav.bruker.person.etternavn")
        // 2.1.2 forename (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.fornavn -> P5000.InsuredPerson.PersonIdentification.forename
        assertEquals("2.1.2 Test forename 1", navBrukerPerson?.fornavn, "2.1.2 nav.bruker.person.fornavn")
        // 2.1.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedselsdato -> P5000.InsuredPerson.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navBrukerPerson?.foedselsdato, "2.1.3 nav.bruker.person.foedselsdato")
        // 2.1.4 sex (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.kjoenn -> P5000.InsuredPerson.PersonIdentification.sex.value[x]
        assertEquals("K", navBrukerPerson?.kjoenn, "2.1.4 nav.bruker.person.kjoenn")
        // 2.1.5 family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.etternavnvedfoedsel -> P5000.InsuredPerson.PersonIdentification.familyNameAtBirth
        assertEquals("2.1.5 Test family Name At Birth 1", navBrukerPerson?.etternavnvedfoedsel, "2.1.5 nav.bruker.person.etternavnvedfoedsel")
        // 2.1.6 forename At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.fornavnvedfoedsel -> P5000.InsuredPerson.PersonIdentification.forenameAtBirth
        assertEquals("2.1.6 Test forename At Birth 1", navBrukerPerson?.fornavnvedfoedsel, "2.1.6 nav.bruker.person.fornavnvedfoedsel")
        // 2.1.7.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].land -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navBrukerPersonPinFirst?.land, "2.1.7.1.1 nav.bruker.person.pin.0.land")
        // 2.1.7.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].land -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navBrukerPersonPinSecond?.land, "2.1.7.1.1 nav.bruker.person.pin.1.land")
        // 2.1.7.1.2 personal Identification Number (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].identifikator -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 1", navBrukerPersonPinFirst?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.0.identifikator")
        // 2.1.7.1.2 personal Identification Number (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].identifikator -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 2", navBrukerPersonPinSecond?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.1.identifikator")
        // 2.1.7.1.3 sector (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].sektor -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPinFirst?.sektor, "2.1.7.1.3 nav.bruker.person.pin.0.sektor")
        // 2.1.7.1.3 sector (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].sektor -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPinSecond?.sektor, "2.1.7.1.3 nav.bruker.person.pin.1.sektor")
        // 2.1.7.1.4.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsid -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 1", navBrukerPersonPinFirst?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.0.institusjonsid")
        // 2.1.7.1.4.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsid -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 2", navBrukerPersonPinSecond?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.1.institusjonsid")
        // 2.1.7.1.4.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsnavn -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 1", navBrukerPersonPinFirst?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.0.institusjonsnavn")
        // 2.1.7.1.4.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsnavn -> P5000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 2", navBrukerPersonPinSecond?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.1.institusjonsnavn")
        // 2.1.8.1.1 town (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.by -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("2.1.8.1.1 Test town 1", navBrukerPersonFoedested?.by, "2.1.8.1.1 nav.bruker.person.foedested.by")
        // 2.1.8.1.2 region (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.region -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("2.1.8.1.2 Test region 1", navBrukerPersonFoedested?.region, "2.1.8.1.2 nav.bruker.person.foedested.region")
        // 2.1.8.1.3 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.land -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navBrukerPersonFoedested?.land, "2.1.8.1.3 nav.bruker.person.foedested.land")
        // 2.1.8.2 father Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.far.person.etternavnvedfoedsel -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("2.1.8.2 Test father Family Name At Birth 1", navBruker?.far?.person?.etternavnvedfoedsel, "2.1.8.2 nav.bruker.far.person.etternavnvedfoedsel")
        // 2.1.8.3 forename Father (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.far.person.fornavn -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("2.1.8.3 Test forename Father 1", navBruker?.far?.person?.fornavn, "2.1.8.3 nav.bruker.far.person.fornavn")
        // 2.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.mor.person.etternavnvedfoedsel -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("2.1.8.4 Test mother Family Name At Birth 1", navBruker?.mor?.person?.etternavnvedfoedsel, "2.1.8.4 nav.bruker.mor.person.etternavnvedfoedsel")
        // 2.1.8.5 forename Mother (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.mor.person.fornavn -> P5000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("2.1.8.5 Test forename Mother 1", navBruker?.mor?.person?.fornavn, "2.1.8.5 nav.bruker.mor.person.fornavn")
        // 2.2.1.1 nationality (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.statsborgerskap[x].land -> P5000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navBrukerPerson?.statsborgerskap?.getOrNull(0)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.0.land")
        // 2.2.1.1 nationality (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.statsborgerskap[x].land -> P5000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navBrukerPerson?.statsborgerskap?.getOrNull(1)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.1.land")
        // 2.2.1.2 previous Family Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.tidligereetternavn -> P5000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("2.2.1.2 Test previous Family Name 1", navBrukerPerson?.tidligereetternavn, "2.2.1.2 nav.bruker.person.tidligereetternavn")
        // 2.2.1.3 previous Forename (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.tidligerefornavn -> P5000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("2.2.1.3 Test previous Forename 1", navBrukerPerson?.tidligerefornavn, "2.2.1.3 nav.bruker.person.tidligerefornavn")
        // 2.2.2.1 street (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.gate -> P5000.InsuredPerson.AdditionalInformationPerson.Address.street
        assertEquals("2.2.2.1 Test street 1", navBrukerAdresse?.gate, "2.2.2.1 nav.bruker.adresse.gate")
        // 2.2.2.2 building Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.bygning -> P5000.InsuredPerson.AdditionalInformationPerson.Address.buildingName
        assertEquals("2.2.2.2 Test building Name 1", navBrukerAdresse?.bygning, "2.2.2.2 nav.bruker.adresse.bygning")
        // 2.2.2.3 town (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.by -> P5000.InsuredPerson.AdditionalInformationPerson.Address.town
        assertEquals("2.2.2.3 Test town 1", navBrukerAdresse?.by, "2.2.2.3 nav.bruker.adresse.by")
        // 2.2.2.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.postnummer -> P5000.InsuredPerson.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", navBrukerAdresse?.postnummer, "2.2.2.4 nav.bruker.adresse.postnummer")
        // 2.2.2.5 region (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.region -> P5000.InsuredPerson.AdditionalInformationPerson.Address.region
        assertEquals("2.2.2.5 Test region 1", navBrukerAdresse?.region, "2.2.2.5 nav.bruker.adresse.region")
        // 2.2.2.6 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.land -> P5000.InsuredPerson.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", navBrukerAdresse?.land, "2.2.2.6 nav.bruker.adresse.land")
    }

    @Test
    fun survivorClaimant() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())
        val pensjon = p5000.pensjon
        val pensjonGjenlevende = pensjon?.gjenlevende
        val pensjonGjenlevendePerson = pensjonGjenlevende?.person
        val pensjonGjenlevendeAdresse = pensjonGjenlevende?.adresse
        val pensjonGjenlevendePersonPin = pensjonGjenlevendePerson?.pin
        val pensjonGjenlevendePersonFoedested = pensjonGjenlevendePerson?.foedested
        val pensjonGjenlevendePersonPinFirst = pensjonGjenlevendePersonPin?.getOrNull(0)
        val pensjonGjenlevendePersonPinSecond = pensjonGjenlevendePersonPin?.getOrNull(1)

        // 3.1.1 family Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.etternavn -> P5000.ClaimantSurvivorPension.PersonIdentification.familyName
        assertEquals("3.1.1 Test family Name 1", pensjonGjenlevendePerson?.etternavn, "3.1.1 pensjon.gjenlevende.person.etternavn")
        // 3.1.2 forename (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.fornavn -> P5000.ClaimantSurvivorPension.PersonIdentification.forename
        assertEquals("3.1.2 Test forename 1", pensjonGjenlevendePerson?.fornavn, "3.1.2 pensjon.gjenlevende.person.fornavn")
        // 3.1.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedselsdato -> P5000.ClaimantSurvivorPension.PersonIdentification.dateBirth
        assertEquals("2020-01-02", pensjonGjenlevendePerson?.foedselsdato, "3.1.3 pensjon.gjenlevende.person.foedselsdato")
        // 3.1.4 sex (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.kjoenn -> P5000.ClaimantSurvivorPension.PersonIdentification.sex.value[x]
        assertEquals("K", pensjonGjenlevendePerson?.kjoenn, "3.1.4 pensjon.gjenlevende.person.kjoenn")
        // 3.1.5 family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.etternavnvedfoedsel -> P5000.ClaimantSurvivorPension.PersonIdentification.familyNameAtBirth
        assertEquals("3.1.5 Test family Name At Birth 1", pensjonGjenlevendePerson?.etternavnvedfoedsel, "3.1.5 pensjon.gjenlevende.person.etternavnvedfoedsel")
        // 3.1.6 Forename at birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.fornavnvedfoedsel -> P5000.ClaimantSurvivorPension.PersonIdentification.forenameAtBirth
        assertEquals("3.1.6 Test Forename at birth 1", pensjonGjenlevendePerson?.fornavnvedfoedsel, "3.1.6 pensjon.gjenlevende.person.fornavnvedfoedsel")
        // 3.1.7.1.1 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].land -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", pensjonGjenlevendePersonPinFirst?.land, "3.1.7.1.1 pensjon.gjenlevende.person.pin.0.land")
        // 3.1.7.1.1 country (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].land -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", pensjonGjenlevendePersonPinSecond?.land, "3.1.7.1.1 pensjon.gjenlevende.person.pin.1.land")
        // 3.1.7.1.2 personal Identification Number (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].identifikator -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.1.7.1.2 Test personal Identification Number 1", pensjonGjenlevendePersonPinFirst?.identifikator, "3.1.7.1.2 pensjon.gjenlevende.person.pin.0.identifikator")
        // 3.1.7.1.2 personal Identification Number (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].identifikator -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.1.7.1.2 Test personal Identification Number 2", pensjonGjenlevendePersonPinSecond?.identifikator, "3.1.7.1.2 pensjon.gjenlevende.person.pin.1.identifikator")
        // 3.1.7.1.3 sector (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].sektor -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", pensjonGjenlevendePersonPinFirst?.sektor, "3.1.7.1.3 pensjon.gjenlevende.person.pin.0.sektor")
        // 3.1.7.1.3 sector (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].sektor -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", pensjonGjenlevendePersonPinSecond?.sektor, "3.1.7.1.3 pensjon.gjenlevende.person.pin.1.sektor")
        // 3.1.7.1.4.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsid -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.1.7.1.4.1 Test institution ID 1", pensjonGjenlevendePersonPinFirst?.institusjonsid, "3.1.7.1.4.1 pensjon.gjenlevende.person.pin.0.institusjonsid")
        // 3.1.7.1.4.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsid -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.1.7.1.4.1 Test institution ID 2", pensjonGjenlevendePersonPinSecond?.institusjonsid, "3.1.7.1.4.1 pensjon.gjenlevende.person.pin.1.institusjonsid")
        // 3.1.7.1.4.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.1.7.1.4.2 Test institution Name 1", pensjonGjenlevendePersonPinFirst?.institusjonsnavn, "3.1.7.1.4.2 pensjon.gjenlevende.person.pin.0.institusjonsnavn")
        // 3.1.7.1.4.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P5000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.1.7.1.4.2 Test institution Name 2", pensjonGjenlevendePersonPinSecond?.institusjonsnavn, "3.1.7.1.4.2 pensjon.gjenlevende.person.pin.1.institusjonsnavn")
        // 3.1.8.1.1 town (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedested.by -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("3.1.8.1.1 Test town 1", pensjonGjenlevendePersonFoedested?.by, "3.1.8.1.1 pensjon.gjenlevende.person.foedested.by")
        // 3.1.8.1.2 region (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedested.region -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("3.1.8.1.2 Test region 1", pensjonGjenlevendePersonFoedested?.region, "3.1.8.1.2 pensjon.gjenlevende.person.foedested.region")
        // 3.1.8.1.3 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedested.land -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", pensjonGjenlevendePersonFoedested?.land, "3.1.8.1.3 pensjon.gjenlevende.person.foedested.land")
        // 3.1.8.2 father Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.far.person.etternavnvedfoedsel -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("3.1.8.2 Test father Family Name At Birth 1", pensjonGjenlevende?.far?.person?.etternavnvedfoedsel, "3.1.8.2 pensjon.gjenlevende.far.person.etternavnvedfoedsel")
        // 3.1.8.3 forename Father (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.far.person.fornavn -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("3.1.8.3 Test forename Father 1", pensjonGjenlevende?.far?.person?.fornavn, "3.1.8.3 pensjon.gjenlevende.far.person.fornavn")
        // 3.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.mor.person.etternavnvedfoedsel -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("3.1.8.4 Test mother Family Name At Birth 1", pensjonGjenlevende?.mor?.person?.etternavnvedfoedsel, "3.1.8.4 pensjon.gjenlevende.mor.person.etternavnvedfoedsel")
        // 3.1.8.5 forename Mother (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.mor.person.fornavn -> P5000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("3.1.8.5 Test forename Mother 1", pensjonGjenlevende?.mor?.person?.fornavn, "3.1.8.5 pensjon.gjenlevende.mor.person.fornavn")
        // 3.2.1.1 nationality (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.statsborgerskap[x].land -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", pensjonGjenlevendePerson?.statsborgerskap?.getOrNull(0)?.land, "3.2.1.1 pensjon.gjenlevende.person.statsborgerskap.0.land")
        // 3.2.1.1 nationality (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.statsborgerskap[x].land -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", pensjonGjenlevendePerson?.statsborgerskap?.getOrNull(1)?.land, "3.2.1.1 pensjon.gjenlevende.person.statsborgerskap.1.land")
        // 3.2.1.2 previous Family Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.tidligereetternavn -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("3.2.1.2 Test previous Family Name 1", pensjonGjenlevendePerson?.tidligereetternavn, "3.2.1.2 pensjon.gjenlevende.person.tidligereetternavn")
        // 3.2.1.3 previous Forename (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.tidligerefornavn -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("3.2.1.3 Test previous Forename 1", pensjonGjenlevendePerson?.tidligerefornavn, "3.2.1.3 pensjon.gjenlevende.person.tidligerefornavn")
        // 3.2.2.1 street (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.gate -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.street
        assertEquals("3.2.2.1 Test street 1", pensjonGjenlevendeAdresse?.gate, "3.2.2.1 pensjon.gjenlevende.adresse.gate")
        // 3.2.2.2 building Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.bygning -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.buildingName
        assertEquals("3.2.2.2 Test building Name 1", pensjonGjenlevendeAdresse?.bygning, "3.2.2.2 pensjon.gjenlevende.adresse.bygning")
        // 3.2.2.3 town (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.by -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.town
        assertEquals("3.2.2.3 Test town 1", pensjonGjenlevendeAdresse?.by, "3.2.2.3 pensjon.gjenlevende.adresse.by")
        // 3.2.2.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.postnummer -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", pensjonGjenlevendeAdresse?.postnummer, "3.2.2.4 pensjon.gjenlevende.adresse.postnummer")
        // 3.2.2.5 region (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.region -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.region
        assertEquals("3.2.2.5 Test region 1", pensjonGjenlevendeAdresse?.region, "3.2.2.5 pensjon.gjenlevende.adresse.region")
        // 3.2.2.6 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.land -> P5000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", pensjonGjenlevendeAdresse?.land, "3.2.2.6 pensjon.gjenlevende.adresse.land")
    }

    @Test
    fun insuranceAndResidencePeriods() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())
        val pensjon = p5000.pensjon
        val pensjonMedlemskapAnnen = pensjon?.medlemskapAnnen
        val pensjonMedlemskapboarbeid = pensjon?.medlemskapboarbeid
        val pensjonMedlemskapAnnenFirst = pensjonMedlemskapAnnen?.getOrNull(0)
        val pensjonMedlemskapAnnenSecond = pensjonMedlemskapAnnen?.getOrNull(1)
        val pensjonMedlemskapboarbeidMedlemskap = pensjonMedlemskapboarbeid?.medlemskap
        val pensjonMedlemskapAnnenFirstSum = pensjonMedlemskapAnnenFirst?.sum
        val pensjonMedlemskapAnnenSecondSum = pensjonMedlemskapAnnenSecond?.sum
        val pensjonMedlemskapboarbeidMedlemskapFirst = pensjonMedlemskapboarbeidMedlemskap?.getOrNull(0)
        val pensjonMedlemskapboarbeidMedlemskapSecond = pensjonMedlemskapboarbeidMedlemskap?.getOrNull(1)
        val pensjonMedlemskapboarbeidMedlemskapFirstSum = pensjonMedlemskapboarbeidMedlemskapFirst?.sum
        val pensjonMedlemskapboarbeidMedlemskapSecondSum = pensjonMedlemskapboarbeidMedlemskapSecond?.sum

        // 4.1 context Transmission (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.enkeltkrav.krav -> P5000.CertificationInsuranceResidencePeriodsInsured.contextTransmission.value[x]
        assertEquals("10", pensjonMedlemskapboarbeid?.enkeltkrav?.krav, "4.1 pensjon.medlemskapboarbeid.enkeltkrav.krav")
        // 4.2 insurance Residence Periods Indicator (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.gyldigperiode -> P5000.CertificationInsuranceResidencePeriodsInsured.insuranceResidencePeriodsIndicator.value[x]
        assertEquals("0", pensjonMedlemskapboarbeid?.gyldigperiode, "4.2 pensjon.medlemskapboarbeid.gyldigperiode")
        // 4.3.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].periode.fom -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].Period.startDate
        assertEquals("2020-01-02", pensjonMedlemskapboarbeidMedlemskapFirst?.periode?.fom, "4.3.1.1.1.1 pensjon.medlemskapboarbeid.medlemskap.0.periode.fom")
        // 4.3.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].periode.fom -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].Period.startDate
        assertEquals("2021-01-02", pensjonMedlemskapboarbeidMedlemskapSecond?.periode?.fom, "4.3.1.1.1.1 pensjon.medlemskapboarbeid.medlemskap.1.periode.fom")
        // 4.3.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].periode.tom -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].Period.endDate
        assertEquals("2020-02-02", pensjonMedlemskapboarbeidMedlemskapFirst?.periode?.tom, "4.3.1.1.1.2 pensjon.medlemskapboarbeid.medlemskap.0.periode.tom")
        // 4.3.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].periode.tom -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].Period.endDate
        assertEquals("2021-02-02", pensjonMedlemskapboarbeidMedlemskapSecond?.periode?.tom, "4.3.1.1.1.2 pensjon.medlemskapboarbeid.medlemskap.1.periode.tom")
        // 4.3.1.1.2.1 years (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.aar -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.years
        assertEquals("10", pensjonMedlemskapboarbeidMedlemskapFirstSum?.aar, "4.3.1.1.2.1 pensjon.medlemskapboarbeid.medlemskap.0.sum.aar")
        // 4.3.1.1.2.1 years (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.aar -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.years
        assertEquals("11", pensjonMedlemskapboarbeidMedlemskapSecondSum?.aar, "4.3.1.1.2.1 pensjon.medlemskapboarbeid.medlemskap.1.sum.aar")
        // 4.3.1.1.2.2 quarters (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.kvartal -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.quarters
        assertEquals("1", pensjonMedlemskapboarbeidMedlemskapFirstSum?.kvartal, "4.3.1.1.2.2 pensjon.medlemskapboarbeid.medlemskap.0.sum.kvartal")
        // 4.3.1.1.2.2 quarters (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.kvartal -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.quarters
        assertEquals("2", pensjonMedlemskapboarbeidMedlemskapSecondSum?.kvartal, "4.3.1.1.2.2 pensjon.medlemskapboarbeid.medlemskap.1.sum.kvartal")
        // 4.3.1.1.2.3 months (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.maaneder -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.months
        assertEquals("2", pensjonMedlemskapboarbeidMedlemskapFirstSum?.maaneder, "4.3.1.1.2.3 pensjon.medlemskapboarbeid.medlemskap.0.sum.maaneder")
        // 4.3.1.1.2.3 months (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.maaneder -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.months
        assertEquals("3", pensjonMedlemskapboarbeidMedlemskapSecondSum?.maaneder, "4.3.1.1.2.3 pensjon.medlemskapboarbeid.medlemskap.1.sum.maaneder")
        // 4.3.1.1.2.4 weeks (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.uker -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.weeks
        assertEquals("3", pensjonMedlemskapboarbeidMedlemskapFirstSum?.uker, "4.3.1.1.2.4 pensjon.medlemskapboarbeid.medlemskap.0.sum.uker")
        // 4.3.1.1.2.4 weeks (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.uker -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.weeks
        assertEquals("4", pensjonMedlemskapboarbeidMedlemskapSecondSum?.uker, "4.3.1.1.2.4 pensjon.medlemskapboarbeid.medlemskap.1.sum.uker")
        // 4.3.1.1.2.5.1 no (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.dager.nr -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.Days.no
        assertEquals("4", pensjonMedlemskapboarbeidMedlemskapFirstSum?.dager?.nr, "4.3.1.1.2.5.1 pensjon.medlemskapboarbeid.medlemskap.0.sum.dager.nr")
        // 4.3.1.1.2.5.1 no (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.dager.nr -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.Days.no
        assertEquals("5", pensjonMedlemskapboarbeidMedlemskapSecondSum?.dager?.nr, "4.3.1.1.2.5.1 pensjon.medlemskapboarbeid.medlemskap.1.sum.dager.nr")
        // 4.3.1.1.2.5.2 unit (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.dager.type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.Days.unit.value[x]
        assertEquals("5", pensjonMedlemskapboarbeidMedlemskapFirstSum?.dager?.type, "4.3.1.1.2.5.2 pensjon.medlemskapboarbeid.medlemskap.0.sum.dager.type")
        // 4.3.1.1.2.5.2 unit (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].sum.dager.type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].TotalNumber.Days.unit.value[x]
        assertEquals("6", pensjonMedlemskapboarbeidMedlemskapSecondSum?.dager?.type, "4.3.1.1.2.5.2 pensjon.medlemskapboarbeid.medlemskap.1.sum.dager.type")
        // 4.3.1.1.3 type (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].type.value[x]
        assertEquals("10", pensjonMedlemskapboarbeidMedlemskapFirst?.type, "4.3.1.1.3 pensjon.medlemskapboarbeid.medlemskap.0.type")
        // 4.3.1.1.3 type (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].type.value[x]
        assertEquals("11", pensjonMedlemskapboarbeidMedlemskapSecond?.type, "4.3.1.1.3 pensjon.medlemskapboarbeid.medlemskap.1.type")
        // 4.3.1.1.4 contingency Relevance (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].relevans -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].contingencyRelevance.value[x]
        assertEquals("100", pensjonMedlemskapboarbeidMedlemskapFirst?.relevans, "4.3.1.1.4 pensjon.medlemskapboarbeid.medlemskap.0.relevans")
        // 4.3.1.1.4 contingency Relevance (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].relevans -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].contingencyRelevance.value[x]
        assertEquals("101", pensjonMedlemskapboarbeidMedlemskapSecond?.relevans, "4.3.1.1.4 pensjon.medlemskapboarbeid.medlemskap.1.relevans")
        // 4.3.1.1.5 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].land -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].country.value[x]
        assertEquals("NO", pensjonMedlemskapboarbeidMedlemskapFirst?.land, "4.3.1.1.5 pensjon.medlemskapboarbeid.medlemskap.0.land")
        // 4.3.1.1.5 country (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].land -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].country.value[x]
        assertEquals("SE", pensjonMedlemskapboarbeidMedlemskapSecond?.land, "4.3.1.1.5 pensjon.medlemskapboarbeid.medlemskap.1.land")
        // 4.3.1.1.6 scheme (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].ordning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].scheme.value[x]
        assertEquals("99", pensjonMedlemskapboarbeidMedlemskapFirst?.ordning, "4.3.1.1.6 pensjon.medlemskapboarbeid.medlemskap.0.ordning")
        // 4.3.1.1.6 scheme (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].ordning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].scheme.value[x]
        assertEquals("99", pensjonMedlemskapboarbeidMedlemskapSecond?.ordning, "4.3.1.1.6 pensjon.medlemskapboarbeid.medlemskap.1.ordning")
        // 4.3.1.1.7 occupation (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].yrke -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].occupation.value[x]
        assertEquals("1111", pensjonMedlemskapboarbeidMedlemskapFirst?.yrke, "4.3.1.1.7 pensjon.medlemskapboarbeid.medlemskap.0.yrke")
        // 4.3.1.1.7 occupation (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].yrke -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].occupation.value[x]
        assertEquals("1112", pensjonMedlemskapboarbeidMedlemskapSecond?.yrke, "4.3.1.1.7 pensjon.medlemskapboarbeid.medlemskap.1.yrke")
        // 4.3.1.1.8 information Calculation (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].beregning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].informationCalculation.value[x]
        assertEquals("100", pensjonMedlemskapboarbeidMedlemskapFirst?.beregning, "4.3.1.1.8 pensjon.medlemskapboarbeid.medlemskap.0.beregning")
        // 4.3.1.1.8 information Calculation (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapboarbeid.medlemskap[x].beregning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.Periods.PeriodWithStartEndDate[x].informationCalculation.value[x]
        assertEquals("101", pensjonMedlemskapboarbeidMedlemskapSecond?.beregning, "4.3.1.1.8 pensjon.medlemskapboarbeid.medlemskap.1.beregning")
        // 4.3.2.1.1.1 years (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.aar -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.years
        assertEquals("10", pensjonMedlemskapAnnenFirstSum?.aar, "4.3.2.1.1.1 pensjon.medlemskapAnnen.0.sum.aar")
        // 4.3.2.1.1.1 years (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.aar -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.years
        assertEquals("11", pensjonMedlemskapAnnenSecondSum?.aar, "4.3.2.1.1.1 pensjon.medlemskapAnnen.1.sum.aar")
        // 4.3.2.1.1.2 quarters (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.kvartal -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.quarters
        assertEquals("1", pensjonMedlemskapAnnenFirstSum?.kvartal, "4.3.2.1.1.2 pensjon.medlemskapAnnen.0.sum.kvartal")
        // 4.3.2.1.1.2 quarters (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.kvartal -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.quarters
        assertEquals("2", pensjonMedlemskapAnnenSecondSum?.kvartal, "4.3.2.1.1.2 pensjon.medlemskapAnnen.1.sum.kvartal")
        // 4.3.2.1.1.3 months (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.maaneder -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.months
        assertEquals("2", pensjonMedlemskapAnnenFirstSum?.maaneder, "4.3.2.1.1.3 pensjon.medlemskapAnnen.0.sum.maaneder")
        // 4.3.2.1.1.3 months (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.maaneder -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.months
        assertEquals("3", pensjonMedlemskapAnnenSecondSum?.maaneder, "4.3.2.1.1.3 pensjon.medlemskapAnnen.1.sum.maaneder")
        // 4.3.2.1.1.4 weeks (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.uker -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.weeks
        assertEquals("3", pensjonMedlemskapAnnenFirstSum?.uker, "4.3.2.1.1.4 pensjon.medlemskapAnnen.0.sum.uker")
        // 4.3.2.1.1.4 weeks (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.uker -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.weeks
        assertEquals("4", pensjonMedlemskapAnnenSecondSum?.uker, "4.3.2.1.1.4 pensjon.medlemskapAnnen.1.sum.uker")
        // 4.3.2.1.1.5.1 no (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.dager.nr -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.Days.no
        assertEquals("4", pensjonMedlemskapAnnenFirstSum?.dager?.nr, "4.3.2.1.1.5.1 pensjon.medlemskapAnnen.0.sum.dager.nr")
        // 4.3.2.1.1.5.1 no (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.dager.nr -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.Days.no
        assertEquals("5", pensjonMedlemskapAnnenSecondSum?.dager?.nr, "4.3.2.1.1.5.1 pensjon.medlemskapAnnen.1.sum.dager.nr")
        // 4.3.2.1.1.5.2 unit (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.dager.type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.Days.unit.value[x]
        assertEquals("5", pensjonMedlemskapAnnenFirstSum?.dager?.type, "4.3.2.1.1.5.2 pensjon.medlemskapAnnen.0.sum.dager.type")
        // 4.3.2.1.1.5.2 unit (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].sum.dager.type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].TotalNumber.Days.unit.value[x]
        assertEquals("6", pensjonMedlemskapAnnenSecondSum?.dager?.type, "4.3.2.1.1.5.2 pensjon.medlemskapAnnen.1.sum.dager.type")
        // 4.3.2.1.2 type (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].type.value[x]
        assertEquals("10", pensjonMedlemskapAnnenFirst?.type, "4.3.2.1.2 pensjon.medlemskapAnnen.0.type")
        // 4.3.2.1.2 type (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].type -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].type.value[x]
        assertEquals("11", pensjonMedlemskapAnnenSecond?.type, "4.3.2.1.2 pensjon.medlemskapAnnen.1.type")
        // 4.3.2.1.3 contingency Relevance (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].relevans -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].contingencyRelevance.value[x]
        assertEquals("100", pensjonMedlemskapAnnenFirst?.relevans, "4.3.2.1.3 pensjon.medlemskapAnnen.0.relevans")
        // 4.3.2.1.3 contingency Relevance (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].relevans -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].contingencyRelevance.value[x]
        assertEquals("101", pensjonMedlemskapAnnenSecond?.relevans, "4.3.2.1.3 pensjon.medlemskapAnnen.1.relevans")
        // 4.3.2.1.4 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].land -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].country.value[x]
        assertEquals("NO", pensjonMedlemskapAnnenFirst?.land, "4.3.2.1.4 pensjon.medlemskapAnnen.0.land")
        // 4.3.2.1.4 country (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].land -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].country.value[x]
        assertEquals("SE", pensjonMedlemskapAnnenSecond?.land, "4.3.2.1.4 pensjon.medlemskapAnnen.1.land")
        // 4.3.2.1.5 scheme (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].ordning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].scheme.value[x]
        assertEquals("99", pensjonMedlemskapAnnenFirst?.ordning, "4.3.2.1.5 pensjon.medlemskapAnnen.0.ordning")
        // 4.3.2.1.5 scheme (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].ordning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].scheme.value[x]
        assertEquals("99", pensjonMedlemskapAnnenSecond?.ordning, "4.3.2.1.5 pensjon.medlemskapAnnen.1.ordning")
        // 4.3.2.1.6 occupation (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].yrke -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].occupation.value[x]
        assertEquals("1111", pensjonMedlemskapAnnenFirst?.yrke, "4.3.2.1.6 pensjon.medlemskapAnnen.0.yrke")
        // 4.3.2.1.6 occupation (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].yrke -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].occupation.value[x]
        assertEquals("1112", pensjonMedlemskapAnnenSecond?.yrke, "4.3.2.1.6 pensjon.medlemskapAnnen.1.yrke")
        // 4.3.2.1.7 information Calculation (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].beregning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].informationCalculation.value[x]
        assertEquals("100", pensjonMedlemskapAnnenFirst?.beregning, "4.3.2.1.7 pensjon.medlemskapAnnen.0.beregning")
        // 4.3.2.1.7 information Calculation (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapAnnen[x].beregning -> P5000.CertificationInsuranceResidencePeriodsInsured.InsuranceResidencePeriods.AdditionalPeriodsWithoutStartEndDate.AdditionalPeriod[x].informationCalculation.value[x]
        assertEquals("101", pensjonMedlemskapAnnenSecond?.beregning, "4.3.2.1.7 pensjon.medlemskapAnnen.1.beregning")
    }

    @Test
    fun totalPeriodLengths() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())
        val pensjon = p5000.pensjon
        val pensjonTrygdetid = pensjon?.trygdetid
        val pensjonMedlemskapTotal = pensjon?.medlemskapTotal
        val pensjonTrygdetidFirst = pensjonTrygdetid?.getOrNull(0)
        val pensjonTrygdetidSecond = pensjonTrygdetid?.getOrNull(1)
        val pensjonTrygdetidFirstSum = pensjonTrygdetidFirst?.sum
        val pensjonTrygdetidSecondSum = pensjonTrygdetidSecond?.sum
        val pensjonMedlemskapTotalFirst = pensjonMedlemskapTotal?.getOrNull(0)
        val pensjonMedlemskapTotalSecond = pensjonMedlemskapTotal?.getOrNull(1)
        val pensjonMedlemskapTotalFirstSum = pensjonMedlemskapTotalFirst?.sum
        val pensjonMedlemskapTotalSecondSum = pensjonMedlemskapTotalSecond?.sum

        // 5.1.1.1 type (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].type -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].type.value[x]
        assertEquals("10", pensjonMedlemskapTotalFirst?.type, "5.1.1.1 pensjon.medlemskapTotal.0.type")
        // 5.1.1.1 type (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].type -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].type.value[x]
        assertEquals("11", pensjonMedlemskapTotalSecond?.type, "5.1.1.1 pensjon.medlemskapTotal.1.type")
        // 5.1.1.2 contingency Relevance (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].relevans -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].contingencyRelevance.value[x]
        assertEquals("100", pensjonMedlemskapTotalFirst?.relevans, "5.1.1.2 pensjon.medlemskapTotal.0.relevans")
        // 5.1.1.2 contingency Relevance (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].relevans -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].contingencyRelevance.value[x]
        assertEquals("101", pensjonMedlemskapTotalSecond?.relevans, "5.1.1.2 pensjon.medlemskapTotal.1.relevans")
        // 5.1.1.3.1 years (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.aar -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.years
        assertEquals("10", pensjonMedlemskapTotalFirstSum?.aar, "5.1.1.3.1 pensjon.medlemskapTotal.0.sum.aar")
        // 5.1.1.3.1 years (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.aar -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.years
        assertEquals("11", pensjonMedlemskapTotalSecondSum?.aar, "5.1.1.3.1 pensjon.medlemskapTotal.1.sum.aar")
        // 5.1.1.3.2 quarters (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.kvartal -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.quarters
        assertEquals("1", pensjonMedlemskapTotalFirstSum?.kvartal, "5.1.1.3.2 pensjon.medlemskapTotal.0.sum.kvartal")
        // 5.1.1.3.2 quarters (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.kvartal -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.quarters
        assertEquals("2", pensjonMedlemskapTotalSecondSum?.kvartal, "5.1.1.3.2 pensjon.medlemskapTotal.1.sum.kvartal")
        // 5.1.1.3.3 months (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.maaneder -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.months
        assertEquals("2", pensjonMedlemskapTotalFirstSum?.maaneder, "5.1.1.3.3 pensjon.medlemskapTotal.0.sum.maaneder")
        // 5.1.1.3.3 months (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.maaneder -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.months
        assertEquals("3", pensjonMedlemskapTotalSecondSum?.maaneder, "5.1.1.3.3 pensjon.medlemskapTotal.1.sum.maaneder")
        // 5.1.1.3.4 weeks (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.uker -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.weeks
        assertEquals("3", pensjonMedlemskapTotalFirstSum?.uker, "5.1.1.3.4 pensjon.medlemskapTotal.0.sum.uker")
        // 5.1.1.3.4 weeks (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.uker -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.weeks
        assertEquals("4", pensjonMedlemskapTotalSecondSum?.uker, "5.1.1.3.4 pensjon.medlemskapTotal.1.sum.uker")
        // 5.1.1.3.5.1 no (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.dager.nr -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.Days.no
        assertEquals("4", pensjonMedlemskapTotalFirstSum?.dager?.nr, "5.1.1.3.5.1 pensjon.medlemskapTotal.0.sum.dager.nr")
        // 5.1.1.3.5.1 no (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.dager.nr -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.Days.no
        assertEquals("5", pensjonMedlemskapTotalSecondSum?.dager?.nr, "5.1.1.3.5.1 pensjon.medlemskapTotal.1.sum.dager.nr")
        // 5.1.1.3.5.2 unit (entry 1)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.dager.type -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.Days.unit.value[x]
        assertEquals("5", pensjonMedlemskapTotalFirstSum?.dager?.type, "5.1.1.3.5.2 pensjon.medlemskapTotal.0.sum.dager.type")
        // 5.1.1.3.5.2 unit (entry 2)
        // Mapping (ACL v4.5): $pensjon.medlemskapTotal[x].sum.dager.type -> P5000.TotalLengthPeriodsInsuranceResidence.ContingencyRelevance.ContingencyRelevance[x].TotalNumber.Days.unit.value[x]
        assertEquals("6", pensjonMedlemskapTotalSecondSum?.dager?.type, "5.1.1.3.5.2 pensjon.medlemskapTotal.1.sum.dager.type")
        // 5.2.1.1 type (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].type -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].type.value[x]
        assertEquals("10", pensjonTrygdetidFirst?.type, "5.2.1.1 pensjon.trygdetid.0.type")
        // 5.2.1.1 type (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].type -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].type.value[x]
        assertEquals("11", pensjonTrygdetidSecond?.type, "5.2.1.1 pensjon.trygdetid.1.type")
        // 5.2.1.2 information Calculation (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].beregning -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].informationCalculation.value[x]
        assertEquals("100", pensjonTrygdetidFirst?.beregning, "5.2.1.2 pensjon.trygdetid.0.beregning")
        // 5.2.1.2 information Calculation (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].beregning -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].informationCalculation.value[x]
        assertEquals("101", pensjonTrygdetidSecond?.beregning, "5.2.1.2 pensjon.trygdetid.1.beregning")
        // 5.2.1.3.1 years (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.aar -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.years
        assertEquals("10", pensjonTrygdetidFirstSum?.aar, "5.2.1.3.1 pensjon.trygdetid.0.sum.aar")
        // 5.2.1.3.1 years (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.aar -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.years
        assertEquals("11", pensjonTrygdetidSecondSum?.aar, "5.2.1.3.1 pensjon.trygdetid.1.sum.aar")
        // 5.2.1.3.2 quarters (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.kvartal -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.quarters
        assertEquals("1", pensjonTrygdetidFirstSum?.kvartal, "5.2.1.3.2 pensjon.trygdetid.0.sum.kvartal")
        // 5.2.1.3.2 quarters (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.kvartal -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.quarters
        assertEquals("2", pensjonTrygdetidSecondSum?.kvartal, "5.2.1.3.2 pensjon.trygdetid.1.sum.kvartal")
        // 5.2.1.3.3 months (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.maaneder -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.months
        assertEquals("2", pensjonTrygdetidFirstSum?.maaneder, "5.2.1.3.3 pensjon.trygdetid.0.sum.maaneder")
        // 5.2.1.3.3 months (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.maaneder -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.months
        assertEquals("3", pensjonTrygdetidSecondSum?.maaneder, "5.2.1.3.3 pensjon.trygdetid.1.sum.maaneder")
        // 5.2.1.3.4 weeks (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.uker -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.weeks
        assertEquals("3", pensjonTrygdetidFirstSum?.uker, "5.2.1.3.4 pensjon.trygdetid.0.sum.uker")
        // 5.2.1.3.4 weeks (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.uker -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.weeks
        assertEquals("4", pensjonTrygdetidSecondSum?.uker, "5.2.1.3.4 pensjon.trygdetid.1.sum.uker")
        // 5.2.1.3.5.1 no (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.dager.nr -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.Days.no
        assertEquals("4", pensjonTrygdetidFirstSum?.dager?.nr, "5.2.1.3.5.1 pensjon.trygdetid.0.sum.dager.nr")
        // 5.2.1.3.5.1 no (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.dager.nr -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.Days.no
        assertEquals("5", pensjonTrygdetidSecondSum?.dager?.nr, "5.2.1.3.5.1 pensjon.trygdetid.1.sum.dager.nr")
        // 5.2.1.3.5.2 unit (entry 1)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.dager.type -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.Days.unit.value[x]
        assertEquals("5", pensjonTrygdetidFirstSum?.dager?.type, "5.2.1.3.5.2 pensjon.trygdetid.0.sum.dager.type")
        // 5.2.1.3.5.2 unit (entry 2)
        // Mapping (ACL v4.5): $pensjon.trygdetid[x].sum.dager.type -> P5000.TotalLengthPeriodsInsuranceResidence.InformationCalculation.InformationCalculation[x].TotalNumber.Days.unit.value[x]
        assertEquals("6", pensjonTrygdetidSecondSum?.dager?.type, "5.2.1.3.5.2 pensjon.trygdetid.1.sum.dager.type")
    }

    @Test
    fun nationalEntitlementUnderArticle57() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())

        // 6.1 Entitlement under national law with an insurance period of less than one year
        // Mapping (ACL v4.5): $pensjon.art578832004.ignore -> P5000.AnInsuredPersonShowingProofHeSheHasCompletedAn.insuredLessThan1YearReceivePensionUnderNationalLawIndicator.value[x]
        assertEquals("1", p5000.pensjon?.art578832004?.ignore, "6.1 pensjon.art578832004.ignore")
    }

    @Test
    fun furtherP5000Information() {
        val p5000 = mapJsonToAny<P5000>(p5000Json())


        // 7.1 p5000Will Send By Other Institution Further Insurance Indicator (entry 1)
        // Mapping (ACL v4.5): $pensjon.separatP5000sendes -> P5000.InformationFurtherP5000.p5000WillSendByOtherInstitutionFurtherInsuranceIndicator.value[x]
        assertEquals("0", p5000.pensjon?.separatP5000sendes, "7.1 pensjon.separatP5000sendes")
    }

    @Test
    fun supportedFieldsSurviveJsonRoundTrip() {
        val expected = p5000Json()
        val p5000 = mapJsonToAny<P5000>(expected)

        // 1-7 All populated supported fields, including collection sizes and order.
        JSONAssert.assertEquals(expected, p5000.toJsonSkipEmpty(), true)
    }
}
