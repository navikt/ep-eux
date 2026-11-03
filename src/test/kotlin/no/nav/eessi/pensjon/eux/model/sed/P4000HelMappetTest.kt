package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Field numbers checked against P4000.pdf (12 pages, printed 27 May 2019).
 * Conditional open-period numbers come from ACL metadata where absent in the supplied PDF.
 * Synthetic fixture only. Tests JSON <-> P4000, not the cross-repository runtime ACL.
 * Both ACL templates currently use the same source paths; mapping comments show v4.5.
 * Source paths resolve to model properties; runtime ACL behavior is not verified by this test.
 */
class P4000HelMappetTest {
    private fun p4000Json(): String =
        requireNotNull(javaClass.getResource("/sed/P4000-HelMappet-NAV.json")).readText()

    @Test
    fun localCaseNumbers() {
        val p4000 = mapJsonToAny<P4000>(p4000Json())
        val nav = p4000.nav
        val navEessisak = nav?.eessisak
        val navEessisakFirst = navEessisak?.getOrNull(0)
        val navEessisakSecond = navEessisak?.getOrNull(1)

        // 1.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].land -> P4000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("NO", navEessisakFirst?.land, "1.1.1 nav.eessisak.0.land")
        // 1.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].land -> P4000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("SE", navEessisakSecond?.land, "1.1.1 nav.eessisak.1.land")
        // 1.1.2 case Number (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].saksnummer -> P4000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 1", navEessisakFirst?.saksnummer, "1.1.2 nav.eessisak.0.saksnummer")
        // 1.1.2 case Number (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].saksnummer -> P4000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 2", navEessisakSecond?.saksnummer, "1.1.2 nav.eessisak.1.saksnummer")
        // 1.1.3.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsid -> P4000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 1", navEessisakFirst?.institusjonsid, "1.1.3.1 nav.eessisak.0.institusjonsid")
        // 1.1.3.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsid -> P4000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 2", navEessisakSecond?.institusjonsid, "1.1.3.1 nav.eessisak.1.institusjonsid")
        // 1.1.3.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsnavn -> P4000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 1", navEessisakFirst?.institusjonsnavn, "1.1.3.2 nav.eessisak.0.institusjonsnavn")
        // 1.1.3.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsnavn -> P4000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 2", navEessisakSecond?.institusjonsnavn, "1.1.3.2 nav.eessisak.1.institusjonsnavn")
    }

    @Test
    fun insuredPerson() {
        val p4000 = mapJsonToAny<P4000>(p4000Json())
        val nav = p4000.nav
        val navBruker = nav?.bruker
        val navBrukerPerson = navBruker?.person
        val navBrukerAdresse = navBruker?.adresse
        val navBrukerPersonPin = navBrukerPerson?.pin
        val navBrukerPersonFoedested = navBrukerPerson?.foedested
        val navBrukerPersonPinFirst = navBrukerPersonPin?.getOrNull(0)
        val navBrukerPersonPinSecond = navBrukerPersonPin?.getOrNull(1)

        // 2.1.1 family Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.etternavn -> P4000.InsuredPerson.PersonIdentification.familyName
        assertEquals("2.1.1 Test family Name 1", navBrukerPerson?.etternavn, "2.1.1 nav.bruker.person.etternavn")
        // 2.1.2 forename (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.fornavn -> P4000.InsuredPerson.PersonIdentification.forename
        assertEquals("2.1.2 Test forename 1", navBrukerPerson?.fornavn, "2.1.2 nav.bruker.person.fornavn")
        // 2.1.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedselsdato -> P4000.InsuredPerson.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navBrukerPerson?.foedselsdato, "2.1.3 nav.bruker.person.foedselsdato")
        // 2.1.4 sex (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.kjoenn -> P4000.InsuredPerson.PersonIdentification.sex.value[x]
        assertEquals("K", navBrukerPerson?.kjoenn, "2.1.4 nav.bruker.person.kjoenn")
        // 2.1.5 family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.etternavnvedfoedsel -> P4000.InsuredPerson.PersonIdentification.familyNameAtBirth
        assertEquals("2.1.5 Test family Name At Birth 1", navBrukerPerson?.etternavnvedfoedsel, "2.1.5 nav.bruker.person.etternavnvedfoedsel")
        // 2.1.6 forename At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.fornavnvedfoedsel -> P4000.InsuredPerson.PersonIdentification.forenameAtBirth
        assertEquals("2.1.6 Test forename At Birth 1", navBrukerPerson?.fornavnvedfoedsel, "2.1.6 nav.bruker.person.fornavnvedfoedsel")
        // 2.1.7.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].land -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navBrukerPersonPinFirst?.land, "2.1.7.1.1 nav.bruker.person.pin.0.land")
        // 2.1.7.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].land -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navBrukerPersonPinSecond?.land, "2.1.7.1.1 nav.bruker.person.pin.1.land")
        // 2.1.7.1.2 personal Identification Number (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].identifikator -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 1", navBrukerPersonPinFirst?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.0.identifikator")
        // 2.1.7.1.2 personal Identification Number (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].identifikator -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 2", navBrukerPersonPinSecond?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.1.identifikator")
        // 2.1.7.1.3 sector (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].sektor -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPinFirst?.sektor, "2.1.7.1.3 nav.bruker.person.pin.0.sektor")
        // 2.1.7.1.3 sector (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].sektor -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPinSecond?.sektor, "2.1.7.1.3 nav.bruker.person.pin.1.sektor")
        // 2.1.7.1.4.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsid -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 1", navBrukerPersonPinFirst?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.0.institusjonsid")
        // 2.1.7.1.4.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsid -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 2", navBrukerPersonPinSecond?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.1.institusjonsid")
        // 2.1.7.1.4.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsnavn -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 1", navBrukerPersonPinFirst?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.0.institusjonsnavn")
        // 2.1.7.1.4.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsnavn -> P4000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 2", navBrukerPersonPinSecond?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.1.institusjonsnavn")
        // 2.1.8.1.1 town (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.by -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("2.1.8.1.1 Test town 1", navBrukerPersonFoedested?.by, "2.1.8.1.1 nav.bruker.person.foedested.by")
        // 2.1.8.1.2 region (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.region -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("2.1.8.1.2 Test region 1", navBrukerPersonFoedested?.region, "2.1.8.1.2 nav.bruker.person.foedested.region")
        // 2.1.8.1.3 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.land -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navBrukerPersonFoedested?.land, "2.1.8.1.3 nav.bruker.person.foedested.land")
        // 2.1.8.2 father Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.far.person.etternavnvedfoedsel -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("2.1.8.2 Test father Family Name At Birth 1", navBruker?.far?.person?.etternavnvedfoedsel, "2.1.8.2 nav.bruker.far.person.etternavnvedfoedsel")
        // 2.1.8.3 forename Father (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.far.person.fornavn -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("2.1.8.3 Test forename Father 1", navBruker?.far?.person?.fornavn, "2.1.8.3 nav.bruker.far.person.fornavn")
        // 2.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.mor.person.etternavnvedfoedsel -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("2.1.8.4 Test mother Family Name At Birth 1", navBruker?.mor?.person?.etternavnvedfoedsel, "2.1.8.4 nav.bruker.mor.person.etternavnvedfoedsel")
        // 2.1.8.5 forename Mother (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.mor.person.fornavn -> P4000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("2.1.8.5 Test forename Mother 1", navBruker?.mor?.person?.fornavn, "2.1.8.5 nav.bruker.mor.person.fornavn")
        // 2.2.1.1 nationality (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.statsborgerskap[x].land -> P4000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navBrukerPerson?.statsborgerskap?.getOrNull(0)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.0.land")
        // 2.2.1.1 nationality (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.statsborgerskap[x].land -> P4000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navBrukerPerson?.statsborgerskap?.getOrNull(1)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.1.land")
        // 2.2.1.2 previous Family Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.tidligereetternavn -> P4000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("2.2.1.2 Test previous Family Name 1", navBrukerPerson?.tidligereetternavn, "2.2.1.2 nav.bruker.person.tidligereetternavn")
        // 2.2.1.3 previous Forename (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.tidligerefornavn -> P4000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("2.2.1.3 Test previous Forename 1", navBrukerPerson?.tidligerefornavn, "2.2.1.3 nav.bruker.person.tidligerefornavn")
        // 2.2.2.1 street (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.gate -> P4000.InsuredPerson.AdditionalInformationPerson.Address.street
        assertEquals("2.2.2.1 Test street 1", navBrukerAdresse?.gate, "2.2.2.1 nav.bruker.adresse.gate")
        // 2.2.2.2 building Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.bygning -> P4000.InsuredPerson.AdditionalInformationPerson.Address.buildingName
        assertEquals("2.2.2.2 Test building Name 1", navBrukerAdresse?.bygning, "2.2.2.2 nav.bruker.adresse.bygning")
        // 2.2.2.3 town (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.by -> P4000.InsuredPerson.AdditionalInformationPerson.Address.town
        assertEquals("2.2.2.3 Test town 1", navBrukerAdresse?.by, "2.2.2.3 nav.bruker.adresse.by")
        // 2.2.2.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.postnummer -> P4000.InsuredPerson.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", navBrukerAdresse?.postnummer, "2.2.2.4 nav.bruker.adresse.postnummer")
        // 2.2.2.5 region (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.region -> P4000.InsuredPerson.AdditionalInformationPerson.Address.region
        assertEquals("2.2.2.5 Test region 1", navBrukerAdresse?.region, "2.2.2.5 nav.bruker.adresse.region")
        // 2.2.2.6 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.land -> P4000.InsuredPerson.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", navBrukerAdresse?.land, "2.2.2.6 nav.bruker.adresse.land")
    }

    @Test
    fun survivorClaimant() {
        val p4000 = mapJsonToAny<P4000>(p4000Json())
        val p4000Pensjon = p4000.p4000Pensjon
        val p4000PensjonGjenlevende = p4000Pensjon?.gjenlevende
        val p4000PensjonGjenlevendePerson = p4000PensjonGjenlevende?.person
        val p4000PensjonGjenlevendeAdresse = p4000PensjonGjenlevende?.adresse
        val p4000PensjonGjenlevendePersonPin = p4000PensjonGjenlevendePerson?.pin
        val p4000PensjonGjenlevendePersonFoedested = p4000PensjonGjenlevendePerson?.foedested
        val p4000PensjonGjenlevendePersonPinFirst = p4000PensjonGjenlevendePersonPin?.getOrNull(0)
        val p4000PensjonGjenlevendePersonPinSecond = p4000PensjonGjenlevendePersonPin?.getOrNull(1)

        // 3.1.1 family Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.etternavn -> P4000.ClaimantSurvivorPension.PersonIdentification.familyName
        assertEquals("3.1.1 Test family Name 1", p4000PensjonGjenlevendePerson?.etternavn, "3.1.1 pensjon.gjenlevende.person.etternavn")
        // 3.1.2 forename (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.fornavn -> P4000.ClaimantSurvivorPension.PersonIdentification.forename
        assertEquals("3.1.2 Test forename 1", p4000PensjonGjenlevendePerson?.fornavn, "3.1.2 pensjon.gjenlevende.person.fornavn")
        // 3.1.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedselsdato -> P4000.ClaimantSurvivorPension.PersonIdentification.dateBirth
        assertEquals("2020-01-02", p4000PensjonGjenlevendePerson?.foedselsdato, "3.1.3 pensjon.gjenlevende.person.foedselsdato")
        // 3.1.4 sex (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.kjoenn -> P4000.ClaimantSurvivorPension.PersonIdentification.sex.value[x]
        assertEquals("K", p4000PensjonGjenlevendePerson?.kjoenn, "3.1.4 pensjon.gjenlevende.person.kjoenn")
        // 3.1.5 family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.etternavnvedfoedsel -> P4000.ClaimantSurvivorPension.PersonIdentification.familyNameAtBirth
        assertEquals("3.1.5 Test family Name At Birth 1", p4000PensjonGjenlevendePerson?.etternavnvedfoedsel, "3.1.5 pensjon.gjenlevende.person.etternavnvedfoedsel")
        // 3.1.6 forename At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.fornavnvedfoedsel -> P4000.ClaimantSurvivorPension.PersonIdentification.forenameAtBirth
        assertEquals("3.1.6 Test forename At Birth 1", p4000PensjonGjenlevendePerson?.fornavnvedfoedsel, "3.1.6 pensjon.gjenlevende.person.fornavnvedfoedsel")

        // 3.1.7.1.1 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].land -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", p4000PensjonGjenlevendePersonPinFirst?.land, "3.1.7.1.1 pensjon.gjenlevende.person.pin.0.land")
        // 3.1.7.1.1 country (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].land -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", p4000PensjonGjenlevendePersonPinSecond?.land, "3.1.7.1.1 pensjon.gjenlevende.person.pin.1.land")
        // 3.1.7.1.2 personal Identification Number (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].identifikator -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.1.7.1.2 Test personal Identification Number 1", p4000PensjonGjenlevendePersonPinFirst?.identifikator, "3.1.7.1.2 pensjon.gjenlevende.person.pin.0.identifikator")
        // 3.1.7.1.2 personal Identification Number (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].identifikator -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.1.7.1.2 Test personal Identification Number 2", p4000PensjonGjenlevendePersonPinSecond?.identifikator, "3.1.7.1.2 pensjon.gjenlevende.person.pin.1.identifikator")
        // 3.1.7.1.3 sector (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].sektor -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", p4000PensjonGjenlevendePersonPinFirst?.sektor, "3.1.7.1.3 pensjon.gjenlevende.person.pin.0.sektor")
        // 3.1.7.1.3 sector (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].sektor -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", p4000PensjonGjenlevendePersonPinSecond?.sektor, "3.1.7.1.3 pensjon.gjenlevende.person.pin.1.sektor")
        // 3.1.7.1.4.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsid -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.1.7.1.4.1 Test institution ID 1", p4000PensjonGjenlevendePersonPinFirst?.institusjonsid, "3.1.7.1.4.1 pensjon.gjenlevende.person.pin.0.institusjonsid")
        // 3.1.7.1.4.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsid -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.1.7.1.4.1 Test institution ID 2", p4000PensjonGjenlevendePersonPinSecond?.institusjonsid, "3.1.7.1.4.1 pensjon.gjenlevende.person.pin.1.institusjonsid")
        // 3.1.7.1.4.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.1.7.1.4.2 Test institution Name 1", p4000PensjonGjenlevendePersonPinFirst?.institusjonsnavn, "3.1.7.1.4.2 pensjon.gjenlevende.person.pin.0.institusjonsnavn")
        // 3.1.7.1.4.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P4000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.1.7.1.4.2 Test institution Name 2", p4000PensjonGjenlevendePersonPinSecond?.institusjonsnavn, "3.1.7.1.4.2 pensjon.gjenlevende.person.pin.1.institusjonsnavn")
        // 3.1.8.1.1 town (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedested.by -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("3.1.8.1.1 Test town 1", p4000PensjonGjenlevendePersonFoedested?.by, "3.1.8.1.1 pensjon.gjenlevende.person.foedested.by")
        // 3.1.8.1.2 region (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedested.region -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("3.1.8.1.2 Test region 1", p4000PensjonGjenlevendePersonFoedested?.region, "3.1.8.1.2 pensjon.gjenlevende.person.foedested.region")
        // 3.1.8.1.3 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.foedested.land -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", p4000PensjonGjenlevendePersonFoedested?.land, "3.1.8.1.3 pensjon.gjenlevende.person.foedested.land")
        // 3.1.8.2 father Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.far.person.etternavnvedfoedsel -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("3.1.8.2 Test father Family Name At Birth 1", p4000PensjonGjenlevende?.far?.person?.etternavnvedfoedsel, "3.1.8.2 pensjon.gjenlevende.far.person.etternavnvedfoedsel")
        // 3.1.8.3 forename Father (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.far.person.fornavn -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("3.1.8.3 Test forename Father 1", p4000PensjonGjenlevende?.far?.person?.fornavn, "3.1.8.3 pensjon.gjenlevende.far.person.fornavn")
        // 3.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.mor.person.etternavnvedfoedsel -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("3.1.8.4 Test mother Family Name At Birth 1", p4000PensjonGjenlevende?.mor?.person?.etternavnvedfoedsel, "3.1.8.4 pensjon.gjenlevende.mor.person.etternavnvedfoedsel")
        // 3.1.8.5 forename Mother (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.mor.person.fornavn -> P4000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("3.1.8.5 Test forename Mother 1", p4000PensjonGjenlevende?.mor?.person?.fornavn, "3.1.8.5 pensjon.gjenlevende.mor.person.fornavn")
        // 3.2.1.1 nationality (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.statsborgerskap[x].land -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", p4000PensjonGjenlevendePerson?.statsborgerskap?.getOrNull(0)?.land, "3.2.1.1 pensjon.gjenlevende.person.statsborgerskap.0.land")
        // 3.2.1.1 nationality (entry 2)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.statsborgerskap[x].land -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", p4000PensjonGjenlevendePerson?.statsborgerskap?.getOrNull(1)?.land, "3.2.1.1 pensjon.gjenlevende.person.statsborgerskap.1.land")
        // 3.2.1.2 previous Family Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.tidligereetternavn -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("3.2.1.2 Test previous Family Name 1", p4000PensjonGjenlevendePerson?.tidligereetternavn, "3.2.1.2 pensjon.gjenlevende.person.tidligereetternavn")
        // 3.2.1.3 previous Forename (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.person.tidligerefornavn -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("3.2.1.3 Test previous Forename 1", p4000PensjonGjenlevendePerson?.tidligerefornavn, "3.2.1.3 pensjon.gjenlevende.person.tidligerefornavn")
        // 3.2.2.1 street (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.gate -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.street
        assertEquals("3.2.2.1 Test street 1", p4000PensjonGjenlevendeAdresse?.gate, "3.2.2.1 pensjon.gjenlevende.adresse.gate")
        // 3.2.2.2 building Name (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.bygning -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.buildingName
        assertEquals("3.2.2.2 Test building Name 1", p4000PensjonGjenlevendeAdresse?.bygning, "3.2.2.2 pensjon.gjenlevende.adresse.bygning")
        // 3.2.2.3 town (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.by -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.town
        assertEquals("3.2.2.3 Test town 1", p4000PensjonGjenlevendeAdresse?.by, "3.2.2.3 pensjon.gjenlevende.adresse.by")
        // 3.2.2.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.postnummer -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", p4000PensjonGjenlevendeAdresse?.postnummer, "3.2.2.4 pensjon.gjenlevende.adresse.postnummer")
        // 3.2.2.5 region (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.region -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.region
        assertEquals("3.2.2.5 Test region 1", p4000PensjonGjenlevendeAdresse?.region, "3.2.2.5 pensjon.gjenlevende.adresse.region")
        // 3.2.2.6 country (entry 1)
        // Mapping (ACL v4.5): $pensjon.gjenlevende.adresse.land -> P4000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", p4000PensjonGjenlevendeAdresse?.land, "3.2.2.6 pensjon.gjenlevende.adresse.land")
    }

    @Test
    fun insuranceHistory() {
        val p4000 = mapJsonToAny<P4000>(p4000Json())
        val trygdetid = p4000.trygdetid
        val trygdetidBoPerioder = trygdetid?.boPerioder
        val trygdetidSykePerioder = trygdetid?.sykePerioder
        val trygdetidAndrePerioder = trygdetid?.andrePerioder
        val trygdetidBarnepassPerioder = trygdetid?.barnepassPerioder
        val trygdetidFrivilligPerioder = trygdetid?.frivilligPerioder
        val trygdetidOpplaeringPerioder = trygdetid?.opplaeringPerioder
        val trygdetidArbeidsledigPerioder = trygdetid?.arbeidsledigPerioder
        val trygdetidForsvartjenestePerioder = trygdetid?.forsvartjenestePerioder
        val trygdetidBoPerioderFirst = trygdetidBoPerioder?.getOrNull(0)
        val trygdetidBoPerioderSecond = trygdetidBoPerioder?.getOrNull(1)
        val trygdetidAnsattSelvstendigPerioder = trygdetid?.ansattSelvstendigPerioder
        val trygdetidFoedselspermisjonPerioder = trygdetid?.foedselspermisjonPerioder
        val trygdetidSykePerioderFirst = trygdetidSykePerioder?.getOrNull(0)
        val trygdetidSykePerioderSecond = trygdetidSykePerioder?.getOrNull(1)
        val trygdetidAndrePerioderFirst = trygdetidAndrePerioder?.getOrNull(0)
        val trygdetidAndrePerioderSecond = trygdetidAndrePerioder?.getOrNull(1)
        val trygdetidBarnepassPerioderFirst = trygdetidBarnepassPerioder?.getOrNull(0)
        val trygdetidBarnepassPerioderSecond = trygdetidBarnepassPerioder?.getOrNull(1)
        val trygdetidFrivilligPerioderFirst = trygdetidFrivilligPerioder?.getOrNull(0)
        val trygdetidFrivilligPerioderSecond = trygdetidFrivilligPerioder?.getOrNull(1)
        val trygdetidOpplaeringPerioderFirst = trygdetidOpplaeringPerioder?.getOrNull(0)
        val trygdetidOpplaeringPerioderSecond = trygdetidOpplaeringPerioder?.getOrNull(1)
        val trygdetidBoPerioderFirstPeriode = trygdetidBoPerioderFirst?.periode
        val trygdetidBoPerioderSecondPeriode = trygdetidBoPerioderSecond?.periode
        val trygdetidArbeidsledigPerioderFirst = trygdetidArbeidsledigPerioder?.getOrNull(0)
        val trygdetidArbeidsledigPerioderSecond = trygdetidArbeidsledigPerioder?.getOrNull(1)
        val trygdetidSykePerioderFirstPeriode = trygdetidSykePerioderFirst?.periode
        val trygdetidSykePerioderSecondPeriode = trygdetidSykePerioderSecond?.periode
        val trygdetidAndrePerioderFirstPeriode = trygdetidAndrePerioderFirst?.periode
        val trygdetidAndrePerioderSecondPeriode = trygdetidAndrePerioderSecond?.periode
        val trygdetidForsvartjenestePerioderFirst = trygdetidForsvartjenestePerioder?.getOrNull(0)
        val trygdetidForsvartjenestePerioderSecond = trygdetidForsvartjenestePerioder?.getOrNull(1)
        val trygdetidAnsattSelvstendigPerioderFirst = trygdetidAnsattSelvstendigPerioder?.getOrNull(0)
        val trygdetidAnsattSelvstendigPerioderSecond = trygdetidAnsattSelvstendigPerioder?.getOrNull(1)
        val trygdetidFoedselspermisjonPerioderFirst = trygdetidFoedselspermisjonPerioder?.getOrNull(0)
        val trygdetidFoedselspermisjonPerioderSecond = trygdetidFoedselspermisjonPerioder?.getOrNull(1)
        val trygdetidBarnepassPerioderFirstPeriode = trygdetidBarnepassPerioderFirst?.periode
        val trygdetidBarnepassPerioderSecondPeriode = trygdetidBarnepassPerioderSecond?.periode
        val trygdetidFrivilligPerioderFirstPeriode = trygdetidFrivilligPerioderFirst?.periode
        val trygdetidFrivilligPerioderSecondPeriode = trygdetidFrivilligPerioderSecond?.periode
        val trygdetidOpplaeringPerioderFirstPeriode = trygdetidOpplaeringPerioderFirst?.periode
        val trygdetidOpplaeringPerioderSecondPeriode = trygdetidOpplaeringPerioderSecond?.periode
        val trygdetidArbeidsledigPerioderFirstPeriode = trygdetidArbeidsledigPerioderFirst?.periode
        val trygdetidArbeidsledigPerioderSecondPeriode = trygdetidArbeidsledigPerioderSecond?.periode
        val trygdetidForsvartjenestePerioderFirstPeriode = trygdetidForsvartjenestePerioderFirst?.periode
        val trygdetidForsvartjenestePerioderSecondPeriode = trygdetidForsvartjenestePerioderSecond?.periode
        val trygdetidAnsattSelvstendigPerioderFirstPeriode = trygdetidAnsattSelvstendigPerioderFirst?.periode
        val trygdetidAnsattSelvstendigPerioderSecondPeriode = trygdetidAnsattSelvstendigPerioderSecond?.periode
        val trygdetidBarnepassPerioderFirstInformasjonBarn = trygdetidBarnepassPerioderFirst?.informasjonBarn
        val trygdetidBarnepassPerioderSecondInformasjonBarn = trygdetidBarnepassPerioderSecond?.informasjonBarn
        val trygdetidFoedselspermisjonPerioderFirstPeriode = trygdetidFoedselspermisjonPerioderFirst?.periode
        val trygdetidFoedselspermisjonPerioderSecondPeriode = trygdetidFoedselspermisjonPerioderSecond?.periode
        val trygdetidAnsattSelvstendigPerioderFirstAdresseFirma = trygdetidAnsattSelvstendigPerioderFirst?.adresseFirma
        val trygdetidAnsattSelvstendigPerioderSecondAdresseFirma = trygdetidAnsattSelvstendigPerioderSecond?.adresseFirma

        // 4.1.1.1 type Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].typePeriode -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].typePeriod.value[x]
        assertEquals("01", trygdetidAnsattSelvstendigPerioderFirst?.typePeriode, "4.1.1.1 trygdetid.ansattSelvstendigPerioder.0.typePeriode")
        // 4.1.1.1 type Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].typePeriode -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].typePeriod.value[x]
        assertEquals("02", trygdetidAnsattSelvstendigPerioderSecond?.typePeriode, "4.1.1.1 trygdetid.ansattSelvstendigPerioder.1.typePeriode")
        // 4.1.1.2.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidAnsattSelvstendigPerioderFirstPeriode?.lukketPeriode?.fom, "4.1.1.2.1.1 trygdetid.ansattSelvstendigPerioder.0.periode.lukketPeriode.fom")
        // 4.1.1.2.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidAnsattSelvstendigPerioderSecondPeriode?.lukketPeriode?.fom, "4.1.1.2.1.1 trygdetid.ansattSelvstendigPerioder.1.periode.lukketPeriode.fom")
        // 4.1.1.2.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidAnsattSelvstendigPerioderFirstPeriode?.lukketPeriode?.tom, "4.1.1.2.1.2 trygdetid.ansattSelvstendigPerioder.0.periode.lukketPeriode.tom")
        // 4.1.1.2.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidAnsattSelvstendigPerioderSecondPeriode?.lukketPeriode?.tom, "4.1.1.2.1.2 trygdetid.ansattSelvstendigPerioder.1.periode.lukketPeriode.tom")

        // 4.1.1.2.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidAnsattSelvstendigPerioderFirstPeriode?.openPeriode?.type, "4.1.1.2.2.1 trygdetid.ansattSelvstendigPerioder.0.periode.openPeriode.type")
        // 4.1.1.2.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidAnsattSelvstendigPerioderSecondPeriode?.openPeriode?.type, "4.1.1.2.2.1 trygdetid.ansattSelvstendigPerioder.1.periode.openPeriode.type")
        // 4.1.1.2.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidAnsattSelvstendigPerioderFirstPeriode?.openPeriode?.fom, "4.1.1.2.2.2 trygdetid.ansattSelvstendigPerioder.0.periode.openPeriode.fom")
        // 4.1.1.2.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidAnsattSelvstendigPerioderSecondPeriode?.openPeriode?.fom, "4.1.1.2.2.2 trygdetid.ansattSelvstendigPerioder.1.periode.openPeriode.fom")
        // 4.1.1.3 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidAnsattSelvstendigPerioderFirst?.usikkerDatoIndikator, "4.1.1.3 trygdetid.ansattSelvstendigPerioder.0.usikkerDatoIndikator")
        // 4.1.1.3 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidAnsattSelvstendigPerioderSecond?.usikkerDatoIndikator, "4.1.1.3 trygdetid.ansattSelvstendigPerioder.1.usikkerDatoIndikator")
        // 4.1.1.4 occupation During Employment Or Self Employment (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].jobbUnderAnsattEllerSelvstendig -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].occupationDuringEmploymentOrSelfEmployment
        assertEquals("4.1.1.4 Test occupation During Employment Or Self Employment 1", trygdetidAnsattSelvstendigPerioderFirst?.jobbUnderAnsattEllerSelvstendig, "4.1.1.4 trygdetid.ansattSelvstendigPerioder.0.jobbUnderAnsattEllerSelvstendig")
        // 4.1.1.4 occupation During Employment Or Self Employment (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].jobbUnderAnsattEllerSelvstendig -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].occupationDuringEmploymentOrSelfEmployment
        assertEquals("4.1.1.4 Test occupation During Employment Or Self Employment 2", trygdetidAnsattSelvstendigPerioderSecond?.jobbUnderAnsattEllerSelvstendig, "4.1.1.4 trygdetid.ansattSelvstendigPerioder.1.jobbUnderAnsattEllerSelvstendig")
        // 4.1.1.5 insurance Or Registration Number (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].forsikkringEllerRegistreringNr -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].insuranceOrRegistrationNumber
        assertEquals("4.1.1.5 Test insurance Or Registration Number 1", trygdetidAnsattSelvstendigPerioderFirst?.forsikkringEllerRegistreringNr, "4.1.1.5 trygdetid.ansattSelvstendigPerioder.0.forsikkringEllerRegistreringNr")
        // 4.1.1.5 insurance Or Registration Number (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].forsikkringEllerRegistreringNr -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].insuranceOrRegistrationNumber
        assertEquals("4.1.1.5 Test insurance Or Registration Number 2", trygdetidAnsattSelvstendigPerioderSecond?.forsikkringEllerRegistreringNr, "4.1.1.5 trygdetid.ansattSelvstendigPerioder.1.forsikkringEllerRegistreringNr")
        // 4.1.1.6 name Employer Company (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].navnFirma -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].nameEmployerCompany
        assertEquals("4.1.1.6 Test name Employer Company 1", trygdetidAnsattSelvstendigPerioderFirst?.navnFirma, "4.1.1.6 trygdetid.ansattSelvstendigPerioder.0.navnFirma")
        // 4.1.1.6 name Employer Company (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].navnFirma -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].nameEmployerCompany
        assertEquals("4.1.1.6 Test name Employer Company 2", trygdetidAnsattSelvstendigPerioderSecond?.navnFirma, "4.1.1.6 trygdetid.ansattSelvstendigPerioder.1.navnFirma")
        // 4.1.1.7.1 street (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.gate -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.street
        assertEquals("4.1.1.7.1 Test street 1", trygdetidAnsattSelvstendigPerioderFirstAdresseFirma?.gate, "4.1.1.7.1 trygdetid.ansattSelvstendigPerioder.0.adresseFirma.gate")
        // 4.1.1.7.1 street (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.gate -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.street
        assertEquals("4.1.1.7.1 Test street 2", trygdetidAnsattSelvstendigPerioderSecondAdresseFirma?.gate, "4.1.1.7.1 trygdetid.ansattSelvstendigPerioder.1.adresseFirma.gate")
        // 4.1.1.7.2 building Name (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.bygning -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.buildingName
        assertEquals("4.1.1.7.2 Test building Name 1", trygdetidAnsattSelvstendigPerioderFirstAdresseFirma?.bygning, "4.1.1.7.2 trygdetid.ansattSelvstendigPerioder.0.adresseFirma.bygning")
        // 4.1.1.7.2 building Name (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.bygning -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.buildingName
        assertEquals("4.1.1.7.2 Test building Name 2", trygdetidAnsattSelvstendigPerioderSecondAdresseFirma?.bygning, "4.1.1.7.2 trygdetid.ansattSelvstendigPerioder.1.adresseFirma.bygning")
        // 4.1.1.7.3 town (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.by -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.town
        assertEquals("4.1.1.7.3 Test town 1", trygdetidAnsattSelvstendigPerioderFirstAdresseFirma?.by, "4.1.1.7.3 trygdetid.ansattSelvstendigPerioder.0.adresseFirma.by")
        // 4.1.1.7.3 town (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.by -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.town
        assertEquals("4.1.1.7.3 Test town 2", trygdetidAnsattSelvstendigPerioderSecondAdresseFirma?.by, "4.1.1.7.3 trygdetid.ansattSelvstendigPerioder.1.adresseFirma.by")
        // 4.1.1.7.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.postnummer -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.postalCode
        assertEquals("0123", trygdetidAnsattSelvstendigPerioderFirstAdresseFirma?.postnummer, "4.1.1.7.4 trygdetid.ansattSelvstendigPerioder.0.adresseFirma.postnummer")
        // 4.1.1.7.4 postal Code (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.postnummer -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.postalCode
        assertEquals("0123", trygdetidAnsattSelvstendigPerioderSecondAdresseFirma?.postnummer, "4.1.1.7.4 trygdetid.ansattSelvstendigPerioder.1.adresseFirma.postnummer")
        // 4.1.1.7.5 region (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.region -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.region
        assertEquals("4.1.1.7.5 Test region 1", trygdetidAnsattSelvstendigPerioderFirstAdresseFirma?.region, "4.1.1.7.5 trygdetid.ansattSelvstendigPerioder.0.adresseFirma.region")
        // 4.1.1.7.5 region (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.region -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.region
        assertEquals("4.1.1.7.5 Test region 2", trygdetidAnsattSelvstendigPerioderSecondAdresseFirma?.region, "4.1.1.7.5 trygdetid.ansattSelvstendigPerioder.1.adresseFirma.region")
        // 4.1.1.7.6 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.land -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.country.value[x]
        assertEquals("NO", trygdetidAnsattSelvstendigPerioderFirstAdresseFirma?.land, "4.1.1.7.6 trygdetid.ansattSelvstendigPerioder.0.adresseFirma.land")
        // 4.1.1.7.6 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].adresseFirma.land -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].AddressEmployerCompany.country.value[x]
        assertEquals("SE", trygdetidAnsattSelvstendigPerioderSecondAdresseFirma?.land, "4.1.1.7.6 trygdetid.ansattSelvstendigPerioder.1.adresseFirma.land")
        // 4.1.1.8 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].additionalInformationConcerningPeriod
        assertEquals("4.1.1.8 Test additional Information Concerning Period 1", trygdetidAnsattSelvstendigPerioderFirst?.annenInformasjon, "4.1.1.8 trygdetid.ansattSelvstendigPerioder.0.annenInformasjon")
        // 4.1.1.8 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.ansattSelvstendigPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.EmploymentSelfEmploymentPeriods.EmploymentSelfEmployment[x].additionalInformationConcerningPeriod
        assertEquals("4.1.1.8 Test additional Information Concerning Period 2", trygdetidAnsattSelvstendigPerioderSecond?.annenInformasjon, "4.1.1.8 trygdetid.ansattSelvstendigPerioder.1.annenInformasjon")
        // 4.2.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidBoPerioderFirstPeriode?.lukketPeriode?.fom, "4.2.1.1.1.1 trygdetid.boPerioder.0.periode.lukketPeriode.fom")
        // 4.2.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidBoPerioderSecondPeriode?.lukketPeriode?.fom, "4.2.1.1.1.1 trygdetid.boPerioder.1.periode.lukketPeriode.fom")
        // 4.2.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidBoPerioderFirstPeriode?.lukketPeriode?.tom, "4.2.1.1.1.2 trygdetid.boPerioder.0.periode.lukketPeriode.tom")
        // 4.2.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidBoPerioderSecondPeriode?.lukketPeriode?.tom, "4.2.1.1.1.2 trygdetid.boPerioder.1.periode.lukketPeriode.tom")

        // 4.2.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidBoPerioderFirstPeriode?.openPeriode?.type, "4.2.1.1.2.1 trygdetid.boPerioder.0.periode.openPeriode.type")
        // 4.2.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidBoPerioderSecondPeriode?.openPeriode?.type, "4.2.1.1.2.1 trygdetid.boPerioder.1.periode.openPeriode.type")
        // 4.2.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidBoPerioderFirstPeriode?.openPeriode?.fom, "4.2.1.1.2.2 trygdetid.boPerioder.0.periode.openPeriode.fom")
        // 4.2.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidBoPerioderSecondPeriode?.openPeriode?.fom, "4.2.1.1.2.2 trygdetid.boPerioder.1.periode.openPeriode.fom")
        // 4.2.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidBoPerioderFirst?.usikkerDatoIndikator, "4.2.1.2 trygdetid.boPerioder.0.usikkerDatoIndikator")
        // 4.2.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidBoPerioderSecond?.usikkerDatoIndikator, "4.2.1.2 trygdetid.boPerioder.1.usikkerDatoIndikator")
        // 4.2.1.3 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].country.value[x]
        assertEquals("NO", trygdetidBoPerioderFirst?.land, "4.2.1.3 trygdetid.boPerioder.0.land")
        // 4.2.1.3 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].country.value[x]
        assertEquals("SE", trygdetidBoPerioderSecond?.land, "4.2.1.3 trygdetid.boPerioder.1.land")
        // 4.2.1.4 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].additionalInformationConcerningPeriod
        assertEquals("4.2.1.4 Test additional Information Concerning Period 1", trygdetidBoPerioderFirst?.annenInformasjon, "4.2.1.4 trygdetid.boPerioder.0.annenInformasjon")
        // 4.2.1.4 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.boPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.ResidencePeriods.Residence[x].additionalInformationConcerningPeriod
        assertEquals("4.2.1.4 Test additional Information Concerning Period 2", trygdetidBoPerioderSecond?.annenInformasjon, "4.2.1.4 trygdetid.boPerioder.1.annenInformasjon")
        // 4.3.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidBarnepassPerioderFirstPeriode?.lukketPeriode?.fom, "4.3.1.1.1.1 trygdetid.barnepassPerioder.0.periode.lukketPeriode.fom")
        // 4.3.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidBarnepassPerioderSecondPeriode?.lukketPeriode?.fom, "4.3.1.1.1.1 trygdetid.barnepassPerioder.1.periode.lukketPeriode.fom")
        // 4.3.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidBarnepassPerioderFirstPeriode?.lukketPeriode?.tom, "4.3.1.1.1.2 trygdetid.barnepassPerioder.0.periode.lukketPeriode.tom")
        // 4.3.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidBarnepassPerioderSecondPeriode?.lukketPeriode?.tom, "4.3.1.1.1.2 trygdetid.barnepassPerioder.1.periode.lukketPeriode.tom")

        // 4.3.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidBarnepassPerioderFirstPeriode?.openPeriode?.type, "4.3.1.1.2.1 trygdetid.barnepassPerioder.0.periode.openPeriode.type")
        // 4.3.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidBarnepassPerioderSecondPeriode?.openPeriode?.type, "4.3.1.1.2.1 trygdetid.barnepassPerioder.1.periode.openPeriode.type")
        // 4.3.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidBarnepassPerioderFirstPeriode?.openPeriode?.fom, "4.3.1.1.2.2 trygdetid.barnepassPerioder.0.periode.openPeriode.fom")
        // 4.3.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidBarnepassPerioderSecondPeriode?.openPeriode?.fom, "4.3.1.1.2.2 trygdetid.barnepassPerioder.1.periode.openPeriode.fom")
        // 4.3.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidBarnepassPerioderFirst?.usikkerDatoIndikator, "4.3.1.2 trygdetid.barnepassPerioder.0.usikkerDatoIndikator")
        // 4.3.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidBarnepassPerioderSecond?.usikkerDatoIndikator, "4.3.1.2 trygdetid.barnepassPerioder.1.usikkerDatoIndikator")
        // 4.3.1.3.1 family Name (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.etternavn -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.familyName
        assertEquals("4.3.1.3.1 Test family Name 1", trygdetidBarnepassPerioderFirstInformasjonBarn?.etternavn, "4.3.1.3.1 trygdetid.barnepassPerioder.0.informasjonBarn.etternavn")
        // 4.3.1.3.1 family Name (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.etternavn -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.familyName
        assertEquals("4.3.1.3.1 Test family Name 2", trygdetidBarnepassPerioderSecondInformasjonBarn?.etternavn, "4.3.1.3.1 trygdetid.barnepassPerioder.1.informasjonBarn.etternavn")
        // 4.3.1.3.2 forename (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.fornavn -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.forename
        assertEquals("4.3.1.3.2 Test forename 1", trygdetidBarnepassPerioderFirstInformasjonBarn?.fornavn, "4.3.1.3.2 trygdetid.barnepassPerioder.0.informasjonBarn.fornavn")
        // 4.3.1.3.2 forename (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.fornavn -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.forename
        assertEquals("4.3.1.3.2 Test forename 2", trygdetidBarnepassPerioderSecondInformasjonBarn?.fornavn, "4.3.1.3.2 trygdetid.barnepassPerioder.1.informasjonBarn.fornavn")
        // 4.3.1.3.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.foedseldato -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.dateBirth
        assertEquals("2020-01-02", trygdetidBarnepassPerioderFirstInformasjonBarn?.foedseldato, "4.3.1.3.3 trygdetid.barnepassPerioder.0.informasjonBarn.foedseldato")
        // 4.3.1.3.3 date Birth (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.foedseldato -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.dateBirth
        assertEquals("2021-01-02", trygdetidBarnepassPerioderSecondInformasjonBarn?.foedseldato, "4.3.1.3.3 trygdetid.barnepassPerioder.1.informasjonBarn.foedseldato")
        // 4.3.1.3.4 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.land -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.country.value[x]
        assertEquals("NO", trygdetidBarnepassPerioderFirstInformasjonBarn?.land, "4.3.1.3.4 trygdetid.barnepassPerioder.0.informasjonBarn.land")
        // 4.3.1.3.4 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].informasjonBarn.land -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].InformationChild.country.value[x]
        assertEquals("SE", trygdetidBarnepassPerioderSecondInformasjonBarn?.land, "4.3.1.3.4 trygdetid.barnepassPerioder.1.informasjonBarn.land")
        // 4.3.1.4 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].additionalInformationConcerningPeriod
        assertEquals("4.3.1.4 Test additional Information Concerning Period 1", trygdetidBarnepassPerioderFirst?.annenInformasjon, "4.3.1.4 trygdetid.barnepassPerioder.0.annenInformasjon")
        // 4.3.1.4 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.barnepassPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.ChildRaisingPeriods.ChildRaising[x].additionalInformationConcerningPeriod
        assertEquals("4.3.1.4 Test additional Information Concerning Period 2", trygdetidBarnepassPerioderSecond?.annenInformasjon, "4.3.1.4 trygdetid.barnepassPerioder.1.annenInformasjon")
        // 4.4.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidFrivilligPerioderFirstPeriode?.lukketPeriode?.fom, "4.4.1.1.1.1 trygdetid.frivilligPerioder.0.periode.lukketPeriode.fom")
        // 4.4.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidFrivilligPerioderSecondPeriode?.lukketPeriode?.fom, "4.4.1.1.1.1 trygdetid.frivilligPerioder.1.periode.lukketPeriode.fom")
        // 4.4.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidFrivilligPerioderFirstPeriode?.lukketPeriode?.tom, "4.4.1.1.1.2 trygdetid.frivilligPerioder.0.periode.lukketPeriode.tom")
        // 4.4.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidFrivilligPerioderSecondPeriode?.lukketPeriode?.tom, "4.4.1.1.1.2 trygdetid.frivilligPerioder.1.periode.lukketPeriode.tom")

        // 4.4.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidFrivilligPerioderFirstPeriode?.openPeriode?.type, "4.4.1.1.2.1 trygdetid.frivilligPerioder.0.periode.openPeriode.type")
        // 4.4.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidFrivilligPerioderSecondPeriode?.openPeriode?.type, "4.4.1.1.2.1 trygdetid.frivilligPerioder.1.periode.openPeriode.type")
        // 4.4.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidFrivilligPerioderFirstPeriode?.openPeriode?.fom, "4.4.1.1.2.2 trygdetid.frivilligPerioder.0.periode.openPeriode.fom")
        // 4.4.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidFrivilligPerioderSecondPeriode?.openPeriode?.fom, "4.4.1.1.2.2 trygdetid.frivilligPerioder.1.periode.openPeriode.fom")
        // 4.4.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidFrivilligPerioderFirst?.usikkerDatoIndikator, "4.4.1.2 trygdetid.frivilligPerioder.0.usikkerDatoIndikator")
        // 4.4.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidFrivilligPerioderSecond?.usikkerDatoIndikator, "4.4.1.2 trygdetid.frivilligPerioder.1.usikkerDatoIndikator")
        // 4.4.1.3 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].country.value[x]
        assertEquals("NO", trygdetidFrivilligPerioderFirst?.land, "4.4.1.3 trygdetid.frivilligPerioder.0.land")
        // 4.4.1.3 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].country.value[x]
        assertEquals("SE", trygdetidFrivilligPerioderSecond?.land, "4.4.1.3 trygdetid.frivilligPerioder.1.land")
        // 4.4.1.4 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].additionalInformationConcerningPeriod
        assertEquals("4.4.1.4 Test additional Information Concerning Period 1", trygdetidFrivilligPerioderFirst?.annenInformasjon, "4.4.1.4 trygdetid.frivilligPerioder.0.annenInformasjon")
        // 4.4.1.4 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.frivilligPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.VoluntaryInsurancePeriods.VoluntaryInsurance[x].additionalInformationConcerningPeriod
        assertEquals("4.4.1.4 Test additional Information Concerning Period 2", trygdetidFrivilligPerioderSecond?.annenInformasjon, "4.4.1.4 trygdetid.frivilligPerioder.1.annenInformasjon")
        // 4.5.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidForsvartjenestePerioderFirstPeriode?.lukketPeriode?.fom, "4.5.1.1.1.1 trygdetid.forsvartjenestePerioder.0.periode.lukketPeriode.fom")
        // 4.5.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidForsvartjenestePerioderSecondPeriode?.lukketPeriode?.fom, "4.5.1.1.1.1 trygdetid.forsvartjenestePerioder.1.periode.lukketPeriode.fom")
        // 4.5.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidForsvartjenestePerioderFirstPeriode?.lukketPeriode?.tom, "4.5.1.1.1.2 trygdetid.forsvartjenestePerioder.0.periode.lukketPeriode.tom")
        // 4.5.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidForsvartjenestePerioderSecondPeriode?.lukketPeriode?.tom, "4.5.1.1.1.2 trygdetid.forsvartjenestePerioder.1.periode.lukketPeriode.tom")

        // 4.5.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidForsvartjenestePerioderFirstPeriode?.openPeriode?.type, "4.5.1.1.2.1 trygdetid.forsvartjenestePerioder.0.periode.openPeriode.type")
        // 4.5.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidForsvartjenestePerioderSecondPeriode?.openPeriode?.type, "4.5.1.1.2.1 trygdetid.forsvartjenestePerioder.1.periode.openPeriode.type")
        // 4.5.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidForsvartjenestePerioderFirstPeriode?.openPeriode?.fom, "4.5.1.1.2.2 trygdetid.forsvartjenestePerioder.0.periode.openPeriode.fom")
        // 4.5.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidForsvartjenestePerioderSecondPeriode?.openPeriode?.fom, "4.5.1.1.2.2 trygdetid.forsvartjenestePerioder.1.periode.openPeriode.fom")
        // 4.5.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidForsvartjenestePerioderFirst?.usikkerDatoIndikator, "4.5.1.2 trygdetid.forsvartjenestePerioder.0.usikkerDatoIndikator")
        // 4.5.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidForsvartjenestePerioderSecond?.usikkerDatoIndikator, "4.5.1.2 trygdetid.forsvartjenestePerioder.1.usikkerDatoIndikator")
        // 4.5.1.3 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].country.value[x]
        assertEquals("NO", trygdetidForsvartjenestePerioderFirst?.land, "4.5.1.3 trygdetid.forsvartjenestePerioder.0.land")
        // 4.5.1.3 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].country.value[x]
        assertEquals("SE", trygdetidForsvartjenestePerioderSecond?.land, "4.5.1.3 trygdetid.forsvartjenestePerioder.1.land")
        // 4.5.1.4 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].additionalInformationConcerningPeriod
        assertEquals("4.5.1.4 Test additional Information Concerning Period 1", trygdetidForsvartjenestePerioderFirst?.annenInformasjon, "4.5.1.4 trygdetid.forsvartjenestePerioder.0.annenInformasjon")
        // 4.5.1.4 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.forsvartjenestePerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.MilitaryServicePeriods.MilitaryService[x].additionalInformationConcerningPeriod
        assertEquals("4.5.1.4 Test additional Information Concerning Period 2", trygdetidForsvartjenestePerioderSecond?.annenInformasjon, "4.5.1.4 trygdetid.forsvartjenestePerioder.1.annenInformasjon")
        // 4.6.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidFoedselspermisjonPerioderFirstPeriode?.lukketPeriode?.fom, "4.6.1.1.1.1 trygdetid.foedselspermisjonPerioder.0.periode.lukketPeriode.fom")
        // 4.6.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidFoedselspermisjonPerioderSecondPeriode?.lukketPeriode?.fom, "4.6.1.1.1.1 trygdetid.foedselspermisjonPerioder.1.periode.lukketPeriode.fom")
        // 4.6.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidFoedselspermisjonPerioderFirstPeriode?.lukketPeriode?.tom, "4.6.1.1.1.2 trygdetid.foedselspermisjonPerioder.0.periode.lukketPeriode.tom")
        // 4.6.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidFoedselspermisjonPerioderSecondPeriode?.lukketPeriode?.tom, "4.6.1.1.1.2 trygdetid.foedselspermisjonPerioder.1.periode.lukketPeriode.tom")

        // 4.6.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidFoedselspermisjonPerioderFirstPeriode?.openPeriode?.type, "4.6.1.1.2.1 trygdetid.foedselspermisjonPerioder.0.periode.openPeriode.type")
        // 4.6.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidFoedselspermisjonPerioderSecondPeriode?.openPeriode?.type, "4.6.1.1.2.1 trygdetid.foedselspermisjonPerioder.1.periode.openPeriode.type")
        // 4.6.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidFoedselspermisjonPerioderFirstPeriode?.openPeriode?.fom, "4.6.1.1.2.2 trygdetid.foedselspermisjonPerioder.0.periode.openPeriode.fom")
        // 4.6.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidFoedselspermisjonPerioderSecondPeriode?.openPeriode?.fom, "4.6.1.1.2.2 trygdetid.foedselspermisjonPerioder.1.periode.openPeriode.fom")
        // 4.6.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidFoedselspermisjonPerioderFirst?.usikkerDatoIndikator, "4.6.1.2 trygdetid.foedselspermisjonPerioder.0.usikkerDatoIndikator")
        // 4.6.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidFoedselspermisjonPerioderSecond?.usikkerDatoIndikator, "4.6.1.2 trygdetid.foedselspermisjonPerioder.1.usikkerDatoIndikator")
        // 4.6.1.3 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].country.value[x]
        assertEquals("NO", trygdetidFoedselspermisjonPerioderFirst?.land, "4.6.1.3 trygdetid.foedselspermisjonPerioder.0.land")
        // 4.6.1.3 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].country.value[x]
        assertEquals("SE", trygdetidFoedselspermisjonPerioderSecond?.land, "4.6.1.3 trygdetid.foedselspermisjonPerioder.1.land")
        // 4.6.1.4 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].additionalInformationConcerningPeriod
        assertEquals("4.6.1.4 Test additional Information Concerning Period 1", trygdetidFoedselspermisjonPerioderFirst?.annenInformasjon, "4.6.1.4 trygdetid.foedselspermisjonPerioder.0.annenInformasjon")
        // 4.6.1.4 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.foedselspermisjonPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.MaternityPeriods.Maternity[x].additionalInformationConcerningPeriod
        assertEquals("4.6.1.4 Test additional Information Concerning Period 2", trygdetidFoedselspermisjonPerioderSecond?.annenInformasjon, "4.6.1.4 trygdetid.foedselspermisjonPerioder.1.annenInformasjon")
        // 4.7.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidOpplaeringPerioderFirstPeriode?.lukketPeriode?.fom, "4.7.1.1.1.1 trygdetid.opplaeringPerioder.0.periode.lukketPeriode.fom")
        // 4.7.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidOpplaeringPerioderSecondPeriode?.lukketPeriode?.fom, "4.7.1.1.1.1 trygdetid.opplaeringPerioder.1.periode.lukketPeriode.fom")
        // 4.7.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidOpplaeringPerioderFirstPeriode?.lukketPeriode?.tom, "4.7.1.1.1.2 trygdetid.opplaeringPerioder.0.periode.lukketPeriode.tom")
        // 4.7.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidOpplaeringPerioderSecondPeriode?.lukketPeriode?.tom, "4.7.1.1.1.2 trygdetid.opplaeringPerioder.1.periode.lukketPeriode.tom")

        // 4.7.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidOpplaeringPerioderFirstPeriode?.openPeriode?.type, "4.7.1.1.2.1 trygdetid.opplaeringPerioder.0.periode.openPeriode.type")
        // 4.7.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidOpplaeringPerioderSecondPeriode?.openPeriode?.type, "4.7.1.1.2.1 trygdetid.opplaeringPerioder.1.periode.openPeriode.type")
        // 4.7.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidOpplaeringPerioderFirstPeriode?.openPeriode?.fom, "4.7.1.1.2.2 trygdetid.opplaeringPerioder.0.periode.openPeriode.fom")
        // 4.7.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidOpplaeringPerioderSecondPeriode?.openPeriode?.fom, "4.7.1.1.2.2 trygdetid.opplaeringPerioder.1.periode.openPeriode.fom")
        // 4.7.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidOpplaeringPerioderFirst?.usikkerDatoIndikator, "4.7.1.2 trygdetid.opplaeringPerioder.0.usikkerDatoIndikator")
        // 4.7.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidOpplaeringPerioderSecond?.usikkerDatoIndikator, "4.7.1.2 trygdetid.opplaeringPerioder.1.usikkerDatoIndikator")
        // 4.7.1.3 name Training Institution (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].navnPaaInstitusjon -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].nameTrainingInstitution
        assertEquals("4.7.1.3 Test name Training Institution 1", trygdetidOpplaeringPerioderFirst?.navnPaaInstitusjon, "4.7.1.3 trygdetid.opplaeringPerioder.0.navnPaaInstitusjon")
        // 4.7.1.3 name Training Institution (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].navnPaaInstitusjon -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].nameTrainingInstitution
        assertEquals("4.7.1.3 Test name Training Institution 2", trygdetidOpplaeringPerioderSecond?.navnPaaInstitusjon, "4.7.1.3 trygdetid.opplaeringPerioder.1.navnPaaInstitusjon")
        // 4.7.1.4 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].country.value[x]
        assertEquals("NO", trygdetidOpplaeringPerioderFirst?.land, "4.7.1.4 trygdetid.opplaeringPerioder.0.land")
        // 4.7.1.4 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].country.value[x]
        assertEquals("SE", trygdetidOpplaeringPerioderSecond?.land, "4.7.1.4 trygdetid.opplaeringPerioder.1.land")
        // 4.7.1.5 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].additionalInformationConcerningPeriod
        assertEquals("4.7.1.5 Test additional Information Concerning Period 1", trygdetidOpplaeringPerioderFirst?.annenInformasjon, "4.7.1.5 trygdetid.opplaeringPerioder.0.annenInformasjon")
        // 4.7.1.5 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.opplaeringPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.TrainingPeriods.Training[x].additionalInformationConcerningPeriod
        assertEquals("4.7.1.5 Test additional Information Concerning Period 2", trygdetidOpplaeringPerioderSecond?.annenInformasjon, "4.7.1.5 trygdetid.opplaeringPerioder.1.annenInformasjon")
        // 4.8.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidArbeidsledigPerioderFirstPeriode?.lukketPeriode?.fom, "4.8.1.1.1.1 trygdetid.arbeidsledigPerioder.0.periode.lukketPeriode.fom")
        // 4.8.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidArbeidsledigPerioderSecondPeriode?.lukketPeriode?.fom, "4.8.1.1.1.1 trygdetid.arbeidsledigPerioder.1.periode.lukketPeriode.fom")
        // 4.8.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidArbeidsledigPerioderFirstPeriode?.lukketPeriode?.tom, "4.8.1.1.1.2 trygdetid.arbeidsledigPerioder.0.periode.lukketPeriode.tom")
        // 4.8.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidArbeidsledigPerioderSecondPeriode?.lukketPeriode?.tom, "4.8.1.1.1.2 trygdetid.arbeidsledigPerioder.1.periode.lukketPeriode.tom")
        // 4.8.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.periodebeskrivelse.typeDato -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidArbeidsledigPerioderFirstPeriode?.periodebeskrivelse?.typeDato, "4.8.1.1.2.1 trygdetid.arbeidsledigPerioder.0.periode.periodebeskrivelse.typeDato")
        // 4.8.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.periodebeskrivelse.typeDato -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidArbeidsledigPerioderSecondPeriode?.periodebeskrivelse?.typeDato, "4.8.1.1.2.1 trygdetid.arbeidsledigPerioder.1.periode.periodebeskrivelse.typeDato")
        // 4.8.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidArbeidsledigPerioderFirstPeriode?.openPeriode?.fom, "4.8.1.1.2.2 trygdetid.arbeidsledigPerioder.0.periode.openPeriode.fom")
        // 4.8.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidArbeidsledigPerioderSecondPeriode?.openPeriode?.fom, "4.8.1.1.2.2 trygdetid.arbeidsledigPerioder.1.periode.openPeriode.fom")
        // 4.8.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidArbeidsledigPerioderFirst?.usikkerDatoIndikator, "4.8.1.2 trygdetid.arbeidsledigPerioder.0.usikkerDatoIndikator")
        // 4.8.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidArbeidsledigPerioderSecond?.usikkerDatoIndikator, "4.8.1.2 trygdetid.arbeidsledigPerioder.1.usikkerDatoIndikator")
        // 4.8.1.3 name Paying Institution (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].navnPaaInstitusjon -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].namePayingInstitution
        assertEquals("4.8.1.3 Test name Paying Institution 1", trygdetidArbeidsledigPerioderFirst?.navnPaaInstitusjon, "4.8.1.3 trygdetid.arbeidsledigPerioder.0.navnPaaInstitusjon")
        // 4.8.1.3 name Paying Institution (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].navnPaaInstitusjon -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].namePayingInstitution
        assertEquals("4.8.1.3 Test name Paying Institution 2", trygdetidArbeidsledigPerioderSecond?.navnPaaInstitusjon, "4.8.1.3 trygdetid.arbeidsledigPerioder.1.navnPaaInstitusjon")
        // 4.8.1.4 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].country.value[x]
        assertEquals("NO", trygdetidArbeidsledigPerioderFirst?.land, "4.8.1.4 trygdetid.arbeidsledigPerioder.0.land")
        // 4.8.1.4 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].country.value[x]
        assertEquals("SE", trygdetidArbeidsledigPerioderSecond?.land, "4.8.1.4 trygdetid.arbeidsledigPerioder.1.land")
        // 4.8.1.5 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].additionalInformationConcerningPeriod
        assertEquals("4.8.1.5 Test additional Information Concerning Period 1", trygdetidArbeidsledigPerioderFirst?.annenInformasjon, "4.8.1.5 trygdetid.arbeidsledigPerioder.0.annenInformasjon")
        // 4.8.1.5 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.arbeidsledigPerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.UnemploymentBenefitsPeriods.UnemploymentBenefits[x].additionalInformationConcerningPeriod
        assertEquals("4.8.1.5 Test additional Information Concerning Period 2", trygdetidArbeidsledigPerioderSecond?.annenInformasjon, "4.8.1.5 trygdetid.arbeidsledigPerioder.1.annenInformasjon")
        // 4.9.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidSykePerioderFirstPeriode?.lukketPeriode?.fom, "4.9.1.1.1.1 trygdetid.sykePerioder.0.periode.lukketPeriode.fom")
        // 4.9.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidSykePerioderSecondPeriode?.lukketPeriode?.fom, "4.9.1.1.1.1 trygdetid.sykePerioder.1.periode.lukketPeriode.fom")
        // 4.9.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidSykePerioderFirstPeriode?.lukketPeriode?.tom, "4.9.1.1.1.2 trygdetid.sykePerioder.0.periode.lukketPeriode.tom")
        // 4.9.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidSykePerioderSecondPeriode?.lukketPeriode?.tom, "4.9.1.1.1.2 trygdetid.sykePerioder.1.periode.lukketPeriode.tom")

        // 4.9.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidSykePerioderFirstPeriode?.openPeriode?.type, "4.9.1.1.2.1 trygdetid.sykePerioder.0.periode.openPeriode.type")
        // 4.9.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidSykePerioderSecondPeriode?.openPeriode?.type, "4.9.1.1.2.1 trygdetid.sykePerioder.1.periode.openPeriode.type")
        // 4.9.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidSykePerioderFirstPeriode?.openPeriode?.fom, "4.9.1.1.2.2 trygdetid.sykePerioder.0.periode.openPeriode.fom")
        // 4.9.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidSykePerioderSecondPeriode?.openPeriode?.fom, "4.9.1.1.2.2 trygdetid.sykePerioder.1.periode.openPeriode.fom")
        // 4.9.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidSykePerioderFirst?.usikkerDatoIndikator, "4.9.1.2 trygdetid.sykePerioder.0.usikkerDatoIndikator")
        // 4.9.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidSykePerioderSecond?.usikkerDatoIndikator, "4.9.1.2 trygdetid.sykePerioder.1.usikkerDatoIndikator")
        // 4.9.1.3 name Paying Institution (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].navnPaaInstitusjon -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].namePayingInstitution
        assertEquals("4.9.1.3 Test name Paying Institution 1", trygdetidSykePerioderFirst?.navnPaaInstitusjon, "4.9.1.3 trygdetid.sykePerioder.0.navnPaaInstitusjon")
        // 4.9.1.3 name Paying Institution (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].navnPaaInstitusjon -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].namePayingInstitution
        assertEquals("4.9.1.3 Test name Paying Institution 2", trygdetidSykePerioderSecond?.navnPaaInstitusjon, "4.9.1.3 trygdetid.sykePerioder.1.navnPaaInstitusjon")
        // 4.9.1.4 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].country.value[x]
        assertEquals("NO", trygdetidSykePerioderFirst?.land, "4.9.1.4 trygdetid.sykePerioder.0.land")
        // 4.9.1.4 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].country.value[x]
        assertEquals("SE", trygdetidSykePerioderSecond?.land, "4.9.1.4 trygdetid.sykePerioder.1.land")
        // 4.9.1.5 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].additionalInformationConcerningPeriod
        assertEquals("4.9.1.5 Test additional Information Concerning Period 1", trygdetidSykePerioderFirst?.annenInformasjon, "4.9.1.5 trygdetid.sykePerioder.0.annenInformasjon")
        // 4.9.1.5 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.sykePerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.SicknessBenefitsPeriods.SicknessBenefit[x].additionalInformationConcerningPeriod
        assertEquals("4.9.1.5 Test additional Information Concerning Period 2", trygdetidSykePerioderSecond?.annenInformasjon, "4.9.1.5 trygdetid.sykePerioder.1.annenInformasjon")
        // 4.10.1.1.1.1 start Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.FixedPeriod.startDate
        assertEquals("2020-01-02", trygdetidAndrePerioderFirstPeriode?.lukketPeriode?.fom, "4.10.1.1.1.1 trygdetid.andrePerioder.0.periode.lukketPeriode.fom")
        // 4.10.1.1.1.1 start Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.lukketPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.FixedPeriod.startDate
        assertEquals("2021-01-02", trygdetidAndrePerioderSecondPeriode?.lukketPeriode?.fom, "4.10.1.1.1.1 trygdetid.andrePerioder.1.periode.lukketPeriode.fom")
        // 4.10.1.1.1.2 end Date (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.FixedPeriod.endDate
        assertEquals("2020-03-02", trygdetidAndrePerioderFirstPeriode?.lukketPeriode?.tom, "4.10.1.1.1.2 trygdetid.andrePerioder.0.periode.lukketPeriode.tom")
        // 4.10.1.1.1.2 end Date (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.lukketPeriode.tom -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.FixedPeriod.endDate
        assertEquals("2021-03-02", trygdetidAndrePerioderSecondPeriode?.lukketPeriode?.tom, "4.10.1.1.1.2 trygdetid.andrePerioder.1.periode.lukketPeriode.tom")

        // 4.10.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("98", trygdetidAndrePerioderFirstPeriode?.openPeriode?.type, "4.10.1.1.2.1 trygdetid.andrePerioder.0.periode.openPeriode.type")
        // 4.10.1.1.2.1 type Open Period (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.openPeriode.type -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.OpenPeriod.typeOpenPeriod.value[x]
        assertEquals("01", trygdetidAndrePerioderSecondPeriode?.openPeriode?.type, "4.10.1.1.2.1 trygdetid.andrePerioder.1.periode.openPeriode.type")
        // 4.10.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.OpenPeriod.startDate
        assertEquals("2020-02-02", trygdetidAndrePerioderFirstPeriode?.openPeriode?.fom, "4.10.1.1.2.2 trygdetid.andrePerioder.0.periode.openPeriode.fom")
        // 4.10.1.1.2.2 start Date (number from ACL field metadata; not visible in supplied PDF) (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].periode.openPeriode.fom -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].Period.OpenPeriod.startDate
        assertEquals("2021-02-02", trygdetidAndrePerioderSecondPeriode?.openPeriode?.fom, "4.10.1.1.2.2 trygdetid.andrePerioder.1.periode.openPeriode.fom")
        // 4.10.1.2 uncertain Dates Indicator (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].uncertainDatesIndicator.value[x]
        assertEquals("0", trygdetidAndrePerioderFirst?.usikkerDatoIndikator, "4.10.1.2 trygdetid.andrePerioder.0.usikkerDatoIndikator")
        // 4.10.1.2 uncertain Dates Indicator (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].usikkerDatoIndikator -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].uncertainDatesIndicator.value[x]
        assertEquals("1", trygdetidAndrePerioderSecond?.usikkerDatoIndikator, "4.10.1.2 trygdetid.andrePerioder.1.usikkerDatoIndikator")
        // 4.10.1.3 type Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].typePeriode -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].typePeriod
        assertEquals("4.10.1.3 Test type Period 1", trygdetidAndrePerioderFirst?.typePeriode, "4.10.1.3 trygdetid.andrePerioder.0.typePeriode")
        // 4.10.1.3 type Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].typePeriode -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].typePeriod
        assertEquals("4.10.1.3 Test type Period 2", trygdetidAndrePerioderSecond?.typePeriode, "4.10.1.3 trygdetid.andrePerioder.1.typePeriode")
        // 4.10.1.4 country (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].country.value[x]
        assertEquals("NO", trygdetidAndrePerioderFirst?.land, "4.10.1.4 trygdetid.andrePerioder.0.land")
        // 4.10.1.4 country (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].land -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].country.value[x]
        assertEquals("SE", trygdetidAndrePerioderSecond?.land, "4.10.1.4 trygdetid.andrePerioder.1.land")
        // 4.10.1.5 additional Information Concerning Period (entry 1)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].additionalInformationConcerningPeriod
        assertEquals("4.10.1.5 Test additional Information Concerning Period 1", trygdetidAndrePerioderFirst?.annenInformasjon, "4.10.1.5 trygdetid.andrePerioder.0.annenInformasjon")
        // 4.10.1.5 additional Information Concerning Period (entry 2)
        // Mapping (ACL v4.5): $trygdetid.andrePerioder[x].annenInformasjon -> P4000.InformationInsuredPersonInsuranceHistory.OtherTypesInsurancePeriods.OtherTypeInsurancePeriods[x].additionalInformationConcerningPeriod
        assertEquals("4.10.1.5 Test additional Information Concerning Period 2", trygdetidAndrePerioderSecond?.annenInformasjon, "4.10.1.5 trygdetid.andrePerioder.1.annenInformasjon")
    }

    @Test
    fun supportedFieldsSurviveJsonRoundTrip() {
        val expected = p4000Json()
        val p4000 = mapJsonToAny<P4000>(expected)

        // 1-4 All populated supported fields, including collection sizes and order.
        JSONAssert.assertEquals(expected, p4000.toJsonSkipEmpty(), true)
    }
}
