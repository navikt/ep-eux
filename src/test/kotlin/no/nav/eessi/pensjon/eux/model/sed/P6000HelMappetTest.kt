package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Field numbers checked against P6000.pdf (10 pages, printed 27 May 2019).
 * Synthetic fixture only. Tests JSON <-> P6000, not the cross-repository runtime ACL.
 * Mapping comments identify each ACL version independently.
 * Both ACL templates are identical and all source paths resolve to model properties.
 * TODOs identify structural mapping reviews, not confirmed runtime defects.
 */
class P6000HelMappetTest {
    private fun p6000Json(): String =
        requireNotNull(javaClass.getResource("/sed/P6000-HelMappet-NAV.json")).readText()

    @Test
    fun localCaseNumbers() {
        val p6000 = mapJsonToAny<P6000>(p6000Json())
        val nav = p6000.nav
        val navEessisak = nav?.eessisak
        val navEessisakFirst = navEessisak?.getOrNull(0)
        val navEessisakSecond = navEessisak?.getOrNull(1)

        // 1.1.1 country (entry 1)
        // Mapping: $nav.eessisak[x].land -> P6000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("NO", navEessisakFirst?.land, "1.1.1 nav.eessisak.0.land")
        // 1.1.1 country (entry 2)
        // Mapping: $nav.eessisak[x].land -> P6000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("SE", navEessisakSecond?.land, "1.1.1 nav.eessisak.1.land")
        // 1.1.2 case Number (entry 1)
        // Mapping: $nav.eessisak[x].saksnummer -> P6000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 1", navEessisakFirst?.saksnummer, "1.1.2 nav.eessisak.0.saksnummer")
        // 1.1.2 case Number (entry 2)
        // Mapping: $nav.eessisak[x].saksnummer -> P6000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 2", navEessisakSecond?.saksnummer, "1.1.2 nav.eessisak.1.saksnummer")
        // 1.1.3.1 institution ID (entry 1)
        // Mapping: $nav.eessisak[x].institusjonsid -> P6000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 1", navEessisakFirst?.institusjonsid, "1.1.3.1 nav.eessisak.0.institusjonsid")
        // 1.1.3.1 institution ID (entry 2)
        // Mapping: $nav.eessisak[x].institusjonsid -> P6000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 2", navEessisakSecond?.institusjonsid, "1.1.3.1 nav.eessisak.1.institusjonsid")
        // 1.1.3.2 institution Name (entry 1)
        // Mapping: $nav.eessisak[x].institusjonsnavn -> P6000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 1", navEessisakFirst?.institusjonsnavn, "1.1.3.2 nav.eessisak.0.institusjonsnavn")
        // 1.1.3.2 institution Name (entry 2)
        // Mapping: $nav.eessisak[x].institusjonsnavn -> P6000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 2", navEessisakSecond?.institusjonsnavn, "1.1.3.2 nav.eessisak.1.institusjonsnavn")
    }

    @Test
    fun insuredPerson() {
        val p6000 = mapJsonToAny<P6000>(p6000Json())
        val nav = p6000.nav
        val navBruker = nav?.bruker
        val navBrukerPerson = navBruker?.person
        val navBrukerAdresse = navBruker?.adresse
        val navBrukerPersonPin = navBrukerPerson?.pin
        val navBrukerPersonFoedested = navBrukerPerson?.foedested
        val navBrukerPersonPinFirst = navBrukerPersonPin?.getOrNull(0)
        val navBrukerPersonPinSecond = navBrukerPersonPin?.getOrNull(1)

        // 2.1.1 family Name (entry 1)
        // Mapping: $nav.bruker.person.etternavn -> P6000.InsuredPerson.PersonIdentification.familyName
        assertEquals("2.1.1 Test family Name 1", navBrukerPerson?.etternavn, "2.1.1 nav.bruker.person.etternavn")
        // 2.1.2 forename (entry 1)
        // Mapping: $nav.bruker.person.fornavn -> ´P6000.InsuredPerson.PersonIdentification.forename
        assertEquals("2.1.2 Test forename 1", navBrukerPerson?.fornavn, "2.1.2 nav.bruker.person.fornavn")
        // 2.1.3 date Birth (entry 1)
        // Mapping: $nav.bruker.person.foedselsdato -> P6000.InsuredPerson.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navBrukerPerson?.foedselsdato, "2.1.3 nav.bruker.person.foedselsdato")
        // 2.1.4 sex (entry 1)
        // Mapping: $nav.bruker.person.kjoenn -> P6000.InsuredPerson.PersonIdentification.sex.value[x]
        assertEquals("K", navBrukerPerson?.kjoenn, "2.1.4 nav.bruker.person.kjoenn")
        // 2.1.5 family Name At Birth (entry 1)
        // Mapping: $nav.bruker.person.etternavnvedfoedsel -> P6000.InsuredPerson.PersonIdentification.familyNameAtBirth
        assertEquals("2.1.5 Test family Name At Birth 1", navBrukerPerson?.etternavnvedfoedsel, "2.1.5 nav.bruker.person.etternavnvedfoedsel")
        // 2.1.6 forename At Birth (entry 1)
        // Mapping: $nav.bruker.person.fornavnvedfoedsel -> P6000.InsuredPerson.PersonIdentification.forenameAtBirth
        assertEquals("2.1.6 Test forename At Birth 1", navBrukerPerson?.fornavnvedfoedsel, "2.1.6 nav.bruker.person.fornavnvedfoedsel")
        // 2.1.7.1.1 country (entry 1)
        // Mapping: $nav.bruker.person.pin[x].land -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navBrukerPersonPinFirst?.land, "2.1.7.1.1 nav.bruker.person.pin.0.land")
        // 2.1.7.1.1 country (entry 2)
        // Mapping: $nav.bruker.person.pin[x].land -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navBrukerPersonPinSecond?.land, "2.1.7.1.1 nav.bruker.person.pin.1.land")
        // 2.1.7.1.2 personal Identification Number (entry 1)
        // Mapping: $nav.bruker.person.pin[x].identifikator -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 1", navBrukerPersonPinFirst?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.0.identifikator")
        // 2.1.7.1.2 personal Identification Number (entry 2)
        // Mapping: $nav.bruker.person.pin[x].identifikator -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 2", navBrukerPersonPinSecond?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.1.identifikator")
        // 2.1.7.1.3 sector (entry 1)
        // Mapping: $nav.bruker.person.pin[x].sektor -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPinFirst?.sektor, "2.1.7.1.3 nav.bruker.person.pin.0.sektor")
        // 2.1.7.1.3 sector (entry 2)
        // Mapping: $nav.bruker.person.pin[x].sektor -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPinSecond?.sektor, "2.1.7.1.3 nav.bruker.person.pin.1.sektor")
        // 2.1.7.1.4.1 institution ID (entry 1)
        // Mapping: $nav.bruker.person.pin[x].institusjonsid -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 1", navBrukerPersonPinFirst?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.0.institusjonsid")
        // 2.1.7.1.4.1 institution ID (entry 2)
        // Mapping: $nav.bruker.person.pin[x].institusjonsid -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 2", navBrukerPersonPinSecond?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.1.institusjonsid")
        // 2.1.7.1.4.2 institution Name (entry 1)
        // Mapping: $nav.bruker.person.pin[x].institusjonsnavn -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 1", navBrukerPersonPinFirst?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.0.institusjonsnavn")
        // 2.1.7.1.4.2 institution Name (entry 2)
        // Mapping: $nav.bruker.person.pin[x].institusjonsnavn -> P6000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 2", navBrukerPersonPinSecond?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.1.institusjonsnavn")
        // 2.1.8.1.1 town (entry 1)
        // Mapping: $nav.bruker.person.foedested.by -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("2.1.8.1.1 Test town 1", navBrukerPersonFoedested?.by, "2.1.8.1.1 nav.bruker.person.foedested.by")
        // 2.1.8.1.2 region (entry 1)
        // Mapping: $nav.bruker.person.foedested.region -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("2.1.8.1.2 Test region 1", navBrukerPersonFoedested?.region, "2.1.8.1.2 nav.bruker.person.foedested.region")
        // 2.1.8.1.3 country (entry 1)
        // Mapping: $nav.bruker.person.foedested.land -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navBrukerPersonFoedested?.land, "2.1.8.1.3 nav.bruker.person.foedested.land")
        // 2.1.8.2 father Family Name At Birth (entry 1)
        // Mapping: $nav.bruker.far.person.etternavnvedfoedsel -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("2.1.8.2 Test father Family Name At Birth 1", navBruker?.far?.person?.etternavnvedfoedsel, "2.1.8.2 nav.bruker.far.person.etternavnvedfoedsel")
        // 2.1.8.3 forename Father (entry 1)
        // Mapping: $nav.bruker.far.person.fornavn -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("2.1.8.3 Test forename Father 1", navBruker?.far?.person?.fornavn, "2.1.8.3 nav.bruker.far.person.fornavn")
        // 2.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping: $nav.bruker.mor.person.etternavnvedfoedsel -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("2.1.8.4 Test mother Family Name At Birth 1", navBruker?.mor?.person?.etternavnvedfoedsel, "2.1.8.4 nav.bruker.mor.person.etternavnvedfoedsel")
        // 2.1.8.5 forename Mother (entry 1)
        // Mapping: $nav.bruker.mor.person.fornavn -> P6000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("2.1.8.5 Test forename Mother 1", navBruker?.mor?.person?.fornavn, "2.1.8.5 nav.bruker.mor.person.fornavn")
        // 2.2.1.1 nationality (entry 1)
        // Mapping: $nav.bruker.person.statsborgerskap[x].land -> P6000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navBrukerPerson?.statsborgerskap?.getOrNull(0)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.0.land")
        // 2.2.1.1 nationality (entry 2)
        // Mapping: $nav.bruker.person.statsborgerskap[x].land -> P6000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navBrukerPerson?.statsborgerskap?.getOrNull(1)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.1.land")
        // 2.2.1.2 previous Family Name (entry 1)
        // Mapping: $nav.bruker.person.tidligereetternavn -> P6000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("2.2.1.2 Test previous Family Name 1", navBrukerPerson?.tidligereetternavn, "2.2.1.2 nav.bruker.person.tidligereetternavn")
        // 2.2.1.3 previous Forename (entry 1)
        // Mapping: $nav.bruker.person.tidligerefornavn -> P6000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("2.2.1.3 Test previous Forename 1", navBrukerPerson?.tidligerefornavn, "2.2.1.3 nav.bruker.person.tidligerefornavn")
        // 2.2.2.1 street (entry 1)
        // Mapping: $nav.bruker.adresse.gate -> P6000.InsuredPerson.AdditionalInformationPerson.Address.street
        assertEquals("2.2.2.1 Test street 1", navBrukerAdresse?.gate, "2.2.2.1 nav.bruker.adresse.gate")
        // 2.2.2.2 building Name (entry 1)
        // Mapping: $nav.bruker.adresse.bygning -> P6000.InsuredPerson.AdditionalInformationPerson.Address.buildingName
        assertEquals("2.2.2.2 Test building Name 1", navBrukerAdresse?.bygning, "2.2.2.2 nav.bruker.adresse.bygning")
        // 2.2.2.3 town (entry 1)
        // Mapping: $nav.bruker.adresse.by -> P6000.InsuredPerson.AdditionalInformationPerson.Address.town
        assertEquals("2.2.2.3 Test town 1", navBrukerAdresse?.by, "2.2.2.3 nav.bruker.adresse.by")
        // 2.2.2.4 postal Code (entry 1)
        // Mapping: $nav.bruker.adresse.postnummer -> P6000.InsuredPerson.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", navBrukerAdresse?.postnummer, "2.2.2.4 nav.bruker.adresse.postnummer")
        // 2.2.2.5 region (entry 1)
        // Mapping: $nav.bruker.adresse.region -> P6000.InsuredPerson.AdditionalInformationPerson.Address.region
        assertEquals("2.2.2.5 Test region 1", navBrukerAdresse?.region, "2.2.2.5 nav.bruker.adresse.region")
        // 2.2.2.6 country (entry 1)
        // Mapping: $nav.bruker.adresse.land -> P6000.InsuredPerson.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", navBrukerAdresse?.land, "2.2.2.6 nav.bruker.adresse.land")
    }

    @Test
    fun survivorClaimant() {
        val p6000 = mapJsonToAny<P6000>(p6000Json())
        val pensjon = p6000.pensjon
        val pensjonGjenlevende = pensjon?.gjenlevende
        val pensjonGjenlevendePerson = pensjonGjenlevende?.person
        val pensjonGjenlevendeAdresse = pensjonGjenlevende?.adresse
        val pensjonGjenlevendePersonPin = pensjonGjenlevendePerson?.pin
        val pensjonGjenlevendePersonFoedested = pensjonGjenlevendePerson?.foedested
        val pensjonGjenlevendePersonPinFirst = pensjonGjenlevendePersonPin?.getOrNull(0)
        val pensjonGjenlevendePersonPinSecond = pensjonGjenlevendePersonPin?.getOrNull(1)

        // 3.1.1 family Name (entry 1)
        // Mapping: $pensjon.gjenlevende.person.etternavn -> P6000.ClaimantSurvivorPension.PersonIdentification.familyName
        assertEquals("3.1.1 Test family Name 1", pensjonGjenlevendePerson?.etternavn, "3.1.1 pensjon.gjenlevende.person.etternavn")
        // 3.1.2 forename (entry 1)
        // Mapping: $pensjon.gjenlevende.person.fornavn -> P6000.ClaimantSurvivorPension.PersonIdentification.forename
        assertEquals("3.1.2 Test forename 1", pensjonGjenlevendePerson?.fornavn, "3.1.2 pensjon.gjenlevende.person.fornavn")
        // 3.1.3 date Birth (entry 1)
        // Mapping: $pensjon.gjenlevende.person.foedselsdato -> P6000.ClaimantSurvivorPension.PersonIdentification.dateBirth
        assertEquals("2020-01-02", pensjonGjenlevendePerson?.foedselsdato, "3.1.3 pensjon.gjenlevende.person.foedselsdato")
        // 3.1.4 sex (entry 1)
        // Mapping: $pensjon.gjenlevende.person.kjoenn -> P6000.ClaimantSurvivorPension.PersonIdentification.sex.value[x]
        assertEquals("K", pensjonGjenlevendePerson?.kjoenn, "3.1.4 pensjon.gjenlevende.person.kjoenn")
        // 3.1.5 family Name At Birth (entry 1)
        // Mapping: $pensjon.gjenlevende.person.etternavnvedfoedsel -> P6000.ClaimantSurvivorPension.PersonIdentification.familyNameAtBirth
        assertEquals("3.1.5 Test family Name At Birth 1", pensjonGjenlevendePerson?.etternavnvedfoedsel, "3.1.5 pensjon.gjenlevende.person.etternavnvedfoedsel")
        // 3.1.6 forename At Birth (entry 1)
        // Mapping: $pensjon.gjenlevende.person.fornavnvedfoedsel -> P6000.ClaimantSurvivorPension.PersonIdentification.forenameAtBirth
        assertEquals("3.1.6 Test forename At Birth 1", pensjonGjenlevendePerson?.fornavnvedfoedsel, "3.1.6 pensjon.gjenlevende.person.fornavnvedfoedsel")
        // 3.1.7.1.1 country (entry 1)
        // Mapping: $pensjon.gjenlevende.person.pin[x].land -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", pensjonGjenlevendePersonPinFirst?.land, "3.1.7.1.1 pensjon.gjenlevende.person.pin.0.land")
        // 3.1.7.1.1 country (entry 2)
        // Mapping: $pensjon.gjenlevende.person.pin[x].land -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", pensjonGjenlevendePersonPinSecond?.land, "3.1.7.1.1 pensjon.gjenlevende.person.pin.1.land")
        // 3.1.7.1.2 personal Identification Number (entry 1)
        // Mapping: $pensjon.gjenlevende.person.pin[x].identifikator -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.1.7.1.2 Test personal Identification Number 1", pensjonGjenlevendePersonPinFirst?.identifikator, "3.1.7.1.2 pensjon.gjenlevende.person.pin.0.identifikator")
        // 3.1.7.1.2 personal Identification Number (entry 2)
        // Mapping: $pensjon.gjenlevende.person.pin[x].identifikator -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.1.7.1.2 Test personal Identification Number 2", pensjonGjenlevendePersonPinSecond?.identifikator, "3.1.7.1.2 pensjon.gjenlevende.person.pin.1.identifikator")
        // 3.1.7.1.3 sector (entry 1)
        // Mapping: $pensjon.gjenlevende.person.pin[x].sektor -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", pensjonGjenlevendePersonPinFirst?.sektor, "3.1.7.1.3 pensjon.gjenlevende.person.pin.0.sektor")
        // 3.1.7.1.3 sector (entry 2)
        // Mapping: $pensjon.gjenlevende.person.pin[x].sektor -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", pensjonGjenlevendePersonPinSecond?.sektor, "3.1.7.1.3 pensjon.gjenlevende.person.pin.1.sektor")
        // 3.1.7.1.4.1 institution ID (entry 1)
        // Mapping: $pensjon.gjenlevende.person.pin[x].institusjonsid -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.1.7.1.4.1 Test institution ID 1", pensjonGjenlevendePersonPinFirst?.institusjonsid, "3.1.7.1.4.1 pensjon.gjenlevende.person.pin.0.institusjonsid")
        // 3.1.7.1.4.1 institution ID (entry 2)
        // Mapping: $pensjon.gjenlevende.person.pin[x].institusjonsid -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.1.7.1.4.1 Test institution ID 2", pensjonGjenlevendePersonPinSecond?.institusjonsid, "3.1.7.1.4.1 pensjon.gjenlevende.person.pin.1.institusjonsid")
        // 3.1.7.1.4.2 institution Name (entry 1)
        // Mapping: $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.1.7.1.4.2 Test institution Name 1", pensjonGjenlevendePersonPinFirst?.institusjonsnavn, "3.1.7.1.4.2 pensjon.gjenlevende.person.pin.0.institusjonsnavn")
        // 3.1.7.1.4.2 institution Name (entry 2)
        // Mapping: $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P6000.ClaimantSurvivorPension.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.1.7.1.4.2 Test institution Name 2", pensjonGjenlevendePersonPinSecond?.institusjonsnavn, "3.1.7.1.4.2 pensjon.gjenlevende.person.pin.1.institusjonsnavn")
        // 3.1.8.1.1 town (entry 1)
        // Mapping: $pensjon.gjenlevende.person.foedested.by -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("3.1.8.1.1 Test town 1", pensjonGjenlevendePersonFoedested?.by, "3.1.8.1.1 pensjon.gjenlevende.person.foedested.by")
        // 3.1.8.1.2 region (entry 1)
        // Mapping: $pensjon.gjenlevende.person.foedested.region -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("3.1.8.1.2 Test region 1", pensjonGjenlevendePersonFoedested?.region, "3.1.8.1.2 pensjon.gjenlevende.person.foedested.region")
        // 3.1.8.1.3 country (entry 1)
        // Mapping: $pensjon.gjenlevende.person.foedested.land -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", pensjonGjenlevendePersonFoedested?.land, "3.1.8.1.3 pensjon.gjenlevende.person.foedested.land")
        // 3.1.8.2 father Family Name At Birth (entry 1)
        // Mapping: $pensjon.gjenlevende.far.person.etternavnvedfoedsel -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("3.1.8.2 Test father Family Name At Birth 1", pensjonGjenlevende?.far?.person?.etternavnvedfoedsel, "3.1.8.2 pensjon.gjenlevende.far.person.etternavnvedfoedsel")
        // 3.1.8.3 forename Father (entry 1)
        // Mapping: $pensjon.gjenlevende.far.person.fornavn -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("3.1.8.3 Test forename Father 1", pensjonGjenlevende?.far?.person?.fornavn, "3.1.8.3 pensjon.gjenlevende.far.person.fornavn")
        // 3.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping: $pensjon.gjenlevende.mor.person.etternavnvedfoedsel -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("3.1.8.4 Test mother Family Name At Birth 1", pensjonGjenlevende?.mor?.person?.etternavnvedfoedsel, "3.1.8.4 pensjon.gjenlevende.mor.person.etternavnvedfoedsel")
        // 3.1.8.5 forename Mother (entry 1)
        // Mapping: $pensjon.gjenlevende.mor.person.fornavn -> P6000.ClaimantSurvivorPension.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("3.1.8.5 Test forename Mother 1", pensjonGjenlevende?.mor?.person?.fornavn, "3.1.8.5 pensjon.gjenlevende.mor.person.fornavn")
        // 3.2.1.1 nationality (entry 1)
        // Mapping: $pensjon.gjenlevende.person.statsborgerskap[x].land -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", pensjonGjenlevendePerson?.statsborgerskap?.getOrNull(0)?.land, "3.2.1.1 pensjon.gjenlevende.person.statsborgerskap.0.land")
        // 3.2.1.1 nationality (entry 2)
        // Mapping: $pensjon.gjenlevende.person.statsborgerskap[x].land -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", pensjonGjenlevendePerson?.statsborgerskap?.getOrNull(1)?.land, "3.2.1.1 pensjon.gjenlevende.person.statsborgerskap.1.land")
        // 3.2.1.2 previous Family Name (entry 1)
        // Mapping: $pensjon.gjenlevende.person.tidligereetternavn -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("3.2.1.2 Test previous Family Name 1", pensjonGjenlevendePerson?.tidligereetternavn, "3.2.1.2 pensjon.gjenlevende.person.tidligereetternavn")
        // 3.2.1.3 previous Forename (entry 1)
        // Mapping: $pensjon.gjenlevende.person.tidligerefornavn -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("3.2.1.3 Test previous Forename 1", pensjonGjenlevendePerson?.tidligerefornavn, "3.2.1.3 pensjon.gjenlevende.person.tidligerefornavn")
        // 3.2.2.1 street (entry 1)
        // Mapping: $pensjon.gjenlevende.adresse.gate -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.street
        assertEquals("3.2.2.1 Test street 1", pensjonGjenlevendeAdresse?.gate, "3.2.2.1 pensjon.gjenlevende.adresse.gate")
        // 3.2.2.2 building Name (entry 1)
        // Mapping: $pensjon.gjenlevende.adresse.bygning -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.buildingName
        assertEquals("3.2.2.2 Test building Name 1", pensjonGjenlevendeAdresse?.bygning, "3.2.2.2 pensjon.gjenlevende.adresse.bygning")
        // 3.2.2.3 town (entry 1)
        // Mapping: $pensjon.gjenlevende.adresse.by -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.town
        assertEquals("3.2.2.3 Test town 1", pensjonGjenlevendeAdresse?.by, "3.2.2.3 pensjon.gjenlevende.adresse.by")
        // 3.2.2.4 postal Code (entry 1)
        // Mapping: $pensjon.gjenlevende.adresse.postnummer -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", pensjonGjenlevendeAdresse?.postnummer, "3.2.2.4 pensjon.gjenlevende.adresse.postnummer")
        // 3.2.2.5 region (entry 1)
        // Mapping: $pensjon.gjenlevende.adresse.region -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.region
        assertEquals("3.2.2.5 Test region 1", pensjonGjenlevendeAdresse?.region, "3.2.2.5 pensjon.gjenlevende.adresse.region")
        // 3.2.2.6 country (entry 1)
        // Mapping: $pensjon.gjenlevende.adresse.land -> P6000.ClaimantSurvivorPension.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", pensjonGjenlevendeAdresse?.land, "3.2.2.6 pensjon.gjenlevende.adresse.land")
    }

    @Test
    fun decisions() {
        val p6000 = mapJsonToAny<P6000>(p6000Json())
        val pensjon = p6000.pensjon
        val pensjonVedtak = pensjon?.vedtak
        val pensjonVedtakFirst = pensjonVedtak?.getOrNull(0)
        val pensjonVedtakSecond = pensjonVedtak?.getOrNull(1)
        val pensjonVedtakFirstGrunnlag = pensjonVedtakFirst?.grunnlag
        val pensjonVedtakSecondGrunnlag = pensjonVedtakSecond?.grunnlag
        val pensjonVedtakFirstBeregning = pensjonVedtakFirst?.beregning
        val pensjonVedtakSecondBeregning = pensjonVedtakSecond?.beregning
        val pensjonVedtakFirstDelvisstans = pensjonVedtakFirst?.delvisstans
        val pensjonVedtakSecondDelvisstans = pensjonVedtakSecond?.delvisstans
        val pensjonVedtakFirstAvslagbegrunnelse = pensjonVedtakFirst?.avslagbegrunnelse
        val pensjonVedtakSecondAvslagbegrunnelse = pensjonVedtakSecond?.avslagbegrunnelse
        val pensjonVedtakFirstBeregningFirst = pensjonVedtakFirstBeregning?.getOrNull(0)
        val pensjonVedtakSecondBeregningFirst = pensjonVedtakSecondBeregning?.getOrNull(0)
        val pensjonVedtakFirstBeregningSecond = pensjonVedtakFirstBeregning?.getOrNull(1)
        val pensjonVedtakSecondBeregningSecond = pensjonVedtakSecondBeregning?.getOrNull(1)
        val pensjonVedtakFirstDelvisstansUtbetaling = pensjonVedtakFirstDelvisstans?.utbetaling
        val pensjonVedtakSecondDelvisstansUtbetaling = pensjonVedtakSecondDelvisstans?.utbetaling
        val pensjonVedtakFirstBeregningFirstBeloepBrutto = pensjonVedtakFirstBeregningFirst?.beloepBrutto
        val pensjonVedtakSecondBeregningFirstBeloepBrutto = pensjonVedtakSecondBeregningFirst?.beloepBrutto
        val pensjonVedtakFirstBeregningSecondBeloepBrutto = pensjonVedtakFirstBeregningSecond?.beloepBrutto
        val pensjonVedtakSecondBeregningSecondBeloepBrutto = pensjonVedtakSecondBeregningSecond?.beloepBrutto

        // 4.1.1 type Pension (entry 1)
        // Mapping: $pensjon.vedtak[x].type -> P6000.Decisions.Decision[x].typePension.value[x]
        assertEquals("01", pensjonVedtakFirst?.type, "4.1.1 pensjon.vedtak.0.type")
        // 4.1.1 type Pension (entry 2)
        // Mapping: $pensjon.vedtak[x].type -> P6000.Decisions.Decision[x].typePension.value[x]
        assertEquals("02", pensjonVedtakSecond?.type, "4.1.1 pensjon.vedtak.1.type")
        // 4.1.2 defined Pension (entry 1)
        // Mapping: $pensjon.vedtak[x].basertPaa -> P6000.Decisions.Decision[x].definedPension.value[x]
        assertEquals(BasertPaa.annet, pensjonVedtakFirst?.basertPaa, "4.1.2 pensjon.vedtak.0.basertPaa")
        // 4.1.2 defined Pension (entry 2)
        // Mapping: $pensjon.vedtak[x].basertPaa -> P6000.Decisions.Decision[x].definedPension.value[x]
        assertEquals(BasertPaa.annet, pensjonVedtakSecond?.basertPaa, "4.1.2 pensjon.vedtak.1.basertPaa")
        // 4.1.3.1 other Pension (entry 1)
        // Mapping: $pensjon.vedtak[x].basertPaaAnnen -> P6000.Decisions.Decision[x].PleaseFillInFollowingIfTypePensionOther.otherPension
        assertEquals("4.1.3.1 Test other Pension 1", pensjonVedtakFirst?.basertPaaAnnen, "4.1.3.1 pensjon.vedtak.0.basertPaaAnnen")
        // 4.1.3.1 other Pension (entry 2)
        // Mapping: $pensjon.vedtak[x].basertPaaAnnen -> P6000.Decisions.Decision[x].PleaseFillInFollowingIfTypePensionOther.otherPension
        assertEquals("4.1.3.1 Test other Pension 2", pensjonVedtakSecond?.basertPaaAnnen, "4.1.3.1 pensjon.vedtak.1.basertPaaAnnen")
        // 4.1.4 decision (entry 1)
        // Mapping: $pensjon.vedtak[x].resultat -> P6000.Decisions.Decision[x].decision.value[x]
        assertEquals("01", pensjonVedtakFirst?.resultat, "4.1.4 pensjon.vedtak.0.resultat")
        // 4.1.4 decision (entry 2)
        // Mapping: $pensjon.vedtak[x].resultat -> P6000.Decisions.Decision[x].decision.value[x]
        assertEquals("02", pensjonVedtakSecond?.resultat, "4.1.4 pensjon.vedtak.1.resultat")
        // 4.1.5 award Benefit Article (entry 1)
        // Mapping: $pensjon.vedtak[x].artikkel -> P6000.Decisions.Decision[x].awardBenefitArticle.value[x]
        assertEquals("01", pensjonVedtakFirst?.artikkel, "4.1.5 pensjon.vedtak.0.artikkel")
        // 4.1.5 award Benefit Article (entry 2)
        // Mapping: $pensjon.vedtak[x].artikkel -> P6000.Decisions.Decision[x].awardBenefitArticle.value[x]
        assertEquals("02", pensjonVedtakSecond?.artikkel, "4.1.5 pensjon.vedtak.1.artikkel")
        // 4.1.6 start Date Pension Right (entry 1)
        // Mapping: $pensjon.vedtak[x].virkningsdato -> P6000.Decisions.Decision[x].startDatePensionRight
        assertEquals("2020-01-02", pensjonVedtakFirst?.virkningsdato, "4.1.6 pensjon.vedtak.0.virkningsdato")
        // 4.1.6 start Date Pension Right (entry 2)
        // Mapping: $pensjon.vedtak[x].virkningsdato -> P6000.Decisions.Decision[x].startDatePensionRight
        assertEquals("2021-01-02", pensjonVedtakSecond?.virkningsdato, "4.1.6 pensjon.vedtak.1.virkningsdato")
        // 4.1.7.1 payable From (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.fom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableFrom
        assertEquals("2020-02-02", pensjonVedtakFirstBeregningFirst?.periode?.fom, "4.1.7.1 pensjon.vedtak.0.beregning.0.periode.fom")
        // 4.1.7.1 payable From (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.fom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableFrom
        assertEquals("2021-02-02", pensjonVedtakSecondBeregningFirst?.periode?.fom, "4.1.7.1 pensjon.vedtak.1.beregning.0.periode.fom")
        // 4.1.7.1 payable From (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.fom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableFrom
        assertEquals("2022-02-02", pensjonVedtakFirstBeregningSecond?.periode?.fom, "4.1.7.1 pensjon.vedtak.0.beregning.1.periode.fom")
        // 4.1.7.1 payable From (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.fom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableFrom
        assertEquals("2023-02-02", pensjonVedtakSecondBeregningSecond?.periode?.fom, "4.1.7.1 pensjon.vedtak.1.beregning.1.periode.fom")
        // 4.1.7.2 payable To (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.tom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableTo
        assertEquals("2020-04-02", pensjonVedtakFirstBeregningFirst?.periode?.tom, "4.1.7.2 pensjon.vedtak.0.beregning.0.periode.tom")
        // 4.1.7.2 payable To (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.tom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableTo
        assertEquals("2021-04-02", pensjonVedtakSecondBeregningFirst?.periode?.tom, "4.1.7.2 pensjon.vedtak.1.beregning.0.periode.tom")
        // 4.1.7.2 payable To (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.tom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableTo
        assertEquals("2022-04-02", pensjonVedtakFirstBeregningSecond?.periode?.tom, "4.1.7.2 pensjon.vedtak.0.beregning.1.periode.tom")
        // 4.1.7.2 payable To (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].periode.tom -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].payableTo
        assertEquals("2023-04-02", pensjonVedtakSecondBeregningSecond?.periode?.tom, "4.1.7.2 pensjon.vedtak.1.beregning.1.periode.tom")
        // 4.1.7.3.1 gross Amount (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmount
        assertEquals("1100.50", pensjonVedtakFirstBeregningFirstBeloepBrutto?.beloep, "4.1.7.3.1 pensjon.vedtak.0.beregning.0.beloepBrutto.beloep")
        // 4.1.7.3.1 gross Amount (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmount
        assertEquals("1101.50", pensjonVedtakSecondBeregningFirstBeloepBrutto?.beloep, "4.1.7.3.1 pensjon.vedtak.1.beregning.0.beloepBrutto.beloep")
        // 4.1.7.3.1 gross Amount (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmount
        assertEquals("1102.50", pensjonVedtakFirstBeregningSecondBeloepBrutto?.beloep, "4.1.7.3.1 pensjon.vedtak.0.beregning.1.beloepBrutto.beloep")
        // 4.1.7.3.1 gross Amount (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmount
        assertEquals("1103.50", pensjonVedtakSecondBeregningSecondBeloepBrutto?.beloep, "4.1.7.3.1 pensjon.vedtak.1.beregning.1.beloepBrutto.beloep")
        // 4.1.7.3.2 net Amount (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepNetto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.netAmount
        assertEquals("1200.50", pensjonVedtakFirstBeregningFirst?.beloepNetto?.beloep, "4.1.7.3.2 pensjon.vedtak.0.beregning.0.beloepNetto.beloep")
        // 4.1.7.3.2 net Amount (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepNetto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.netAmount
        assertEquals("1201.50", pensjonVedtakSecondBeregningFirst?.beloepNetto?.beloep, "4.1.7.3.2 pensjon.vedtak.1.beregning.0.beloepNetto.beloep")
        // 4.1.7.3.2 net Amount (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepNetto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.netAmount
        assertEquals("1202.50", pensjonVedtakFirstBeregningSecond?.beloepNetto?.beloep, "4.1.7.3.2 pensjon.vedtak.0.beregning.1.beloepNetto.beloep")
        // 4.1.7.3.2 net Amount (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepNetto.beloep -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.netAmount
        assertEquals("1203.50", pensjonVedtakSecondBeregningSecond?.beloepNetto?.beloep, "4.1.7.3.2 pensjon.vedtak.1.beregning.1.beloepNetto.beloep")
        // 4.1.7.3.3 gross Amount Basic Pension (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentGrunnpensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountBasicPension
        assertEquals("1300.50", pensjonVedtakFirstBeregningFirstBeloepBrutto?.ytelseskomponentGrunnpensjon, "4.1.7.3.3 pensjon.vedtak.0.beregning.0.beloepBrutto.ytelseskomponentGrunnpensjon")
        // 4.1.7.3.3 gross Amount Basic Pension (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentGrunnpensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountBasicPension
        assertEquals("1301.50", pensjonVedtakSecondBeregningFirstBeloepBrutto?.ytelseskomponentGrunnpensjon, "4.1.7.3.3 pensjon.vedtak.1.beregning.0.beloepBrutto.ytelseskomponentGrunnpensjon")
        // 4.1.7.3.3 gross Amount Basic Pension (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentGrunnpensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountBasicPension
        assertEquals("1302.50", pensjonVedtakFirstBeregningSecondBeloepBrutto?.ytelseskomponentGrunnpensjon, "4.1.7.3.3 pensjon.vedtak.0.beregning.1.beloepBrutto.ytelseskomponentGrunnpensjon")
        // 4.1.7.3.3 gross Amount Basic Pension (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentGrunnpensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountBasicPension
        assertEquals("1303.50", pensjonVedtakSecondBeregningSecondBeloepBrutto?.ytelseskomponentGrunnpensjon, "4.1.7.3.3 pensjon.vedtak.1.beregning.1.beloepBrutto.ytelseskomponentGrunnpensjon")
        // 4.1.7.3.4 gross Amount Supplementary Pension (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentTilleggspensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountSupplementaryPension
        assertEquals("1400.50", pensjonVedtakFirstBeregningFirstBeloepBrutto?.ytelseskomponentTilleggspensjon, "4.1.7.3.4 pensjon.vedtak.0.beregning.0.beloepBrutto.ytelseskomponentTilleggspensjon")
        // 4.1.7.3.4 gross Amount Supplementary Pension (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentTilleggspensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountSupplementaryPension
        assertEquals("1401.50", pensjonVedtakSecondBeregningFirstBeloepBrutto?.ytelseskomponentTilleggspensjon, "4.1.7.3.4 pensjon.vedtak.1.beregning.0.beloepBrutto.ytelseskomponentTilleggspensjon")
        // 4.1.7.3.4 gross Amount Supplementary Pension (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentTilleggspensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountSupplementaryPension
        assertEquals("1402.50", pensjonVedtakFirstBeregningSecondBeloepBrutto?.ytelseskomponentTilleggspensjon, "4.1.7.3.4 pensjon.vedtak.0.beregning.1.beloepBrutto.ytelseskomponentTilleggspensjon")
        // 4.1.7.3.4 gross Amount Supplementary Pension (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].beloepBrutto.ytelseskomponentTilleggspensjon -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PensionAmount.grossAmountSupplementaryPension
        assertEquals("1403.50", pensjonVedtakSecondBeregningSecondBeloepBrutto?.ytelseskomponentTilleggspensjon, "4.1.7.3.4 pensjon.vedtak.1.beregning.1.beloepBrutto.ytelseskomponentTilleggspensjon")
        // 4.1.7.4 currency (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].valuta -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].currency.value[x]
        assertEquals("NOK", pensjonVedtakFirstBeregningFirst?.valuta, "4.1.7.4 pensjon.vedtak.0.beregning.0.valuta")
        // 4.1.7.4 currency (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].valuta -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].currency.value[x]
        assertEquals("NOK", pensjonVedtakSecondBeregningFirst?.valuta, "4.1.7.4 pensjon.vedtak.1.beregning.0.valuta")
        // 4.1.7.4 currency (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].valuta -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].currency.value[x]
        assertEquals("NOK", pensjonVedtakFirstBeregningSecond?.valuta, "4.1.7.4 pensjon.vedtak.0.beregning.1.valuta")
        // 4.1.7.4 currency (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].valuta -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].currency.value[x]
        assertEquals("NOK", pensjonVedtakSecondBeregningSecond?.valuta, "4.1.7.4 pensjon.vedtak.1.beregning.1.valuta")
        // 4.1.7.5 payment Frequency (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighet -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].paymentFrequency.value[x]
        assertEquals("99", pensjonVedtakFirstBeregningFirst?.utbetalingshyppighet, "4.1.7.5 pensjon.vedtak.0.beregning.0.utbetalingshyppighet")
        // 4.1.7.5 payment Frequency (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighet -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].paymentFrequency.value[x]
        assertEquals("99", pensjonVedtakSecondBeregningFirst?.utbetalingshyppighet, "4.1.7.5 pensjon.vedtak.1.beregning.0.utbetalingshyppighet")
        // 4.1.7.5 payment Frequency (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighet -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].paymentFrequency.value[x]
        assertEquals("99", pensjonVedtakFirstBeregningSecond?.utbetalingshyppighet, "4.1.7.5 pensjon.vedtak.0.beregning.1.utbetalingshyppighet")
        // 4.1.7.5 payment Frequency (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighet -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].paymentFrequency.value[x]
        assertEquals("99", pensjonVedtakSecondBeregningSecond?.utbetalingshyppighet, "4.1.7.5 pensjon.vedtak.1.beregning.1.utbetalingshyppighet")
        // 4.1.7.6.1 other Payment Frequency (entry 1)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighetAnnen -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.7.6.1 Test other Payment Frequency 1", pensjonVedtakFirstBeregningFirst?.utbetalingshyppighetAnnen, "4.1.7.6.1 pensjon.vedtak.0.beregning.0.utbetalingshyppighetAnnen")
        // 4.1.7.6.1 other Payment Frequency (entry 2)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighetAnnen -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.7.6.1 Test other Payment Frequency 2", pensjonVedtakSecondBeregningFirst?.utbetalingshyppighetAnnen, "4.1.7.6.1 pensjon.vedtak.1.beregning.0.utbetalingshyppighetAnnen")
        // 4.1.7.6.1 other Payment Frequency (entry 3)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighetAnnen -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.7.6.1 Test other Payment Frequency 3", pensjonVedtakFirstBeregningSecond?.utbetalingshyppighetAnnen, "4.1.7.6.1 pensjon.vedtak.0.beregning.1.utbetalingshyppighetAnnen")
        // 4.1.7.6.1 other Payment Frequency (entry 4)
        // Mapping: $pensjon.vedtak[x].beregning[x].utbetalingshyppighetAnnen -> P6000.Decisions.Decision[x].DetailsPensionPaymentPeriods[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("4.1.7.6.1 Test other Payment Frequency 4", pensjonVedtakSecondBeregningSecond?.utbetalingshyppighetAnnen, "4.1.7.6.1 pensjon.vedtak.1.beregning.1.utbetalingshyppighetAnnen")
        // 4.1.8 actual Start Date Pension Payments From Institution (entry 1)
        // Mapping: $pensjon.vedtak[x].kjoeringsdato -> P6000.Decisions.Decision[x].actualStartDatePensionPaymentsFromInstitution
        assertEquals("2020-03-02", pensjonVedtakFirst?.kjoeringsdato, "4.1.8 pensjon.vedtak.0.kjoeringsdato")
        // 4.1.8 actual Start Date Pension Payments From Institution (entry 2)
        // Mapping: $pensjon.vedtak[x].kjoeringsdato -> P6000.Decisions.Decision[x].actualStartDatePensionPaymentsFromInstitution
        assertEquals("2021-03-02", pensjonVedtakSecond?.kjoeringsdato, "4.1.8 pensjon.vedtak.1.kjoeringsdato")
        // 4.1.9 supplements Additional Frequent Payments Pension (entry 1)
        // Mapping: $pensjon.vedtak[x].ukjent.beloepBrutto.ytelseskomponentAnnen -> P6000.Decisions.Decision[x].supplementsAdditionalFrequentPaymentsPension
        assertEquals("4.1.9 Test supplements Additional Frequent Payments Pension 1", pensjonVedtakFirst?.ukjent?.beloepBrutto?.ytelseskomponentAnnen, "4.1.9 pensjon.vedtak.0.ukjent.beloepBrutto.ytelseskomponentAnnen")
        // 4.1.9 supplements Additional Frequent Payments Pension (entry 2)
        // Mapping: $pensjon.vedtak[x].ukjent.beloepBrutto.ytelseskomponentAnnen -> P6000.Decisions.Decision[x].supplementsAdditionalFrequentPaymentsPension
        assertEquals("4.1.9 Test supplements Additional Frequent Payments Pension 2", pensjonVedtakSecond?.ukjent?.beloepBrutto?.ytelseskomponentAnnen, "4.1.9 pensjon.vedtak.1.ukjent.beloepBrutto.ytelseskomponentAnnen")
        // 4.1.10 based Voluntary Contributions (entry 1)
        // Mapping: $pensjon.vedtak[x].grunnlag.medlemskap -> P6000.Decisions.Decision[x].basedVoluntaryContributions.value[x]
        assertEquals("01", pensjonVedtakFirstGrunnlag?.medlemskap, "4.1.10 pensjon.vedtak.0.grunnlag.medlemskap")
        // 4.1.10 based Voluntary Contributions (entry 2)
        // Mapping: $pensjon.vedtak[x].grunnlag.medlemskap -> P6000.Decisions.Decision[x].basedVoluntaryContributions.value[x]
        assertEquals("02", pensjonVedtakSecondGrunnlag?.medlemskap, "4.1.10 pensjon.vedtak.1.grunnlag.medlemskap")
        // 4.1.11 based Other Person Insurance Periods (entry 1)
        // Mapping: $pensjon.vedtak[x].grunnlag.opptjening.forsikredeAnnen -> P6000.Decisions.Decision[x].basedOtherPersonInsurancePeriods.value[x]
        assertEquals("01", pensjonVedtakFirstGrunnlag?.opptjening?.forsikredeAnnen, "4.1.11 pensjon.vedtak.0.grunnlag.opptjening.forsikredeAnnen")
        // 4.1.11 based Other Person Insurance Periods (entry 2)
        // Mapping: $pensjon.vedtak[x].grunnlag.opptjening.forsikredeAnnen -> P6000.Decisions.Decision[x].basedOtherPersonInsurancePeriods.value[x]
        assertEquals("02", pensjonVedtakSecondGrunnlag?.opptjening?.forsikredeAnnen, "4.1.11 pensjon.vedtak.1.grunnlag.opptjening.forsikredeAnnen")
        // 4.1.12 credited Period Indicator (entry 1)
        // Mapping: $pensjon.vedtak[x].grunnlag.framtidigtrygdetid -> P6000.Decisions.Decision[x].creditedPeriodIndicator.value[x]
        assertEquals("0", pensjonVedtakFirstGrunnlag?.framtidigtrygdetid, "4.1.12 pensjon.vedtak.0.grunnlag.framtidigtrygdetid")
        // 4.1.12 credited Period Indicator (entry 2)
        // Mapping: $pensjon.vedtak[x].grunnlag.framtidigtrygdetid -> P6000.Decisions.Decision[x].creditedPeriodIndicator.value[x]
        assertEquals("1", pensjonVedtakSecondGrunnlag?.framtidigtrygdetid, "4.1.12 pensjon.vedtak.1.grunnlag.framtidigtrygdetid")
        // 4.1.13.1 rejection Reasons (entry 1)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].begrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].rejectionReasons.value[x]
        assertEquals("99", pensjonVedtakFirstAvslagbegrunnelse?.getOrNull(0)?.begrunnelse, "4.1.13.1 pensjon.vedtak.0.avslagbegrunnelse.0.begrunnelse")
        // 4.1.13.1 rejection Reasons (entry 2)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].begrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].rejectionReasons.value[x]
        assertEquals("99", pensjonVedtakSecondAvslagbegrunnelse?.getOrNull(0)?.begrunnelse, "4.1.13.1 pensjon.vedtak.1.avslagbegrunnelse.0.begrunnelse")
        // 4.1.13.1 rejection Reasons (entry 3)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].begrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].rejectionReasons.value[x]
        assertEquals("99", pensjonVedtakFirstAvslagbegrunnelse?.getOrNull(1)?.begrunnelse, "4.1.13.1 pensjon.vedtak.0.avslagbegrunnelse.1.begrunnelse")
        // 4.1.13.1 rejection Reasons (entry 4)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].begrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].rejectionReasons.value[x]
        assertEquals("99", pensjonVedtakSecondAvslagbegrunnelse?.getOrNull(1)?.begrunnelse, "4.1.13.1 pensjon.vedtak.1.avslagbegrunnelse.1.begrunnelse")
        // 4.1.13.2.1 other Reasons (entry 1)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].annenbegrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].FillInIfRejectionReasonsOther.otherReasons
        assertEquals("4.1.13.2.1 Test other Reasons 1", pensjonVedtakFirstAvslagbegrunnelse?.getOrNull(0)?.annenbegrunnelse, "4.1.13.2.1 pensjon.vedtak.0.avslagbegrunnelse.0.annenbegrunnelse")
        // 4.1.13.2.1 other Reasons (entry 2)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].annenbegrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].FillInIfRejectionReasonsOther.otherReasons
        assertEquals("4.1.13.2.1 Test other Reasons 2", pensjonVedtakSecondAvslagbegrunnelse?.getOrNull(0)?.annenbegrunnelse, "4.1.13.2.1 pensjon.vedtak.1.avslagbegrunnelse.0.annenbegrunnelse")
        // 4.1.13.2.1 other Reasons (entry 3)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].annenbegrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].FillInIfRejectionReasonsOther.otherReasons
        assertEquals("4.1.13.2.1 Test other Reasons 3", pensjonVedtakFirstAvslagbegrunnelse?.getOrNull(1)?.annenbegrunnelse, "4.1.13.2.1 pensjon.vedtak.0.avslagbegrunnelse.1.annenbegrunnelse")
        // 4.1.13.2.1 other Reasons (entry 4)
        // Mapping: $pensjon.vedtak[x].avslagbegrunnelse[x].annenbegrunnelse -> P6000.Decisions.Decision[x].RejectionReasons[x].FillInIfRejectionReasonsOther.otherReasons
        assertEquals("4.1.13.2.1 Test other Reasons 4", pensjonVedtakSecondAvslagbegrunnelse?.getOrNull(1)?.annenbegrunnelse, "4.1.13.2.1 pensjon.vedtak.1.avslagbegrunnelse.1.annenbegrunnelse")
        // 4.1.14.1 pension Partly Suspended Indicator (entry 1)
        // Mapping: $pensjon.vedtak[x].delvisstans.indikator -> P6000.Decisions.Decision[x].PartialSuspensionPension.pensionPartlySuspendedIndicator.value[x]
        assertEquals("0", pensjonVedtakFirstDelvisstans?.indikator, "4.1.14.1 pensjon.vedtak.0.delvisstans.indikator")
        // 4.1.14.1 pension Partly Suspended Indicator (entry 2)
        // Mapping: $pensjon.vedtak[x].delvisstans.indikator -> P6000.Decisions.Decision[x].PartialSuspensionPension.pensionPartlySuspendedIndicator.value[x]
        assertEquals("1", pensjonVedtakSecondDelvisstans?.indikator, "4.1.14.1 pensjon.vedtak.1.delvisstans.indikator")
        // 4.1.14.2.1 amount (entry 1)
        // Mapping: $pensjon.vedtak[x].delvisstans.utbetaling.beloepBrutto -> P6000.Decisions.Decision[x].PartialSuspensionPension.PleaseFillInFollowingIfPensionPartlySuspendedYes.amount
        assertEquals("1100.50", pensjonVedtakFirstDelvisstansUtbetaling?.beloepBrutto, "4.1.14.2.1 pensjon.vedtak.0.delvisstans.utbetaling.beloepBrutto")
        // 4.1.14.2.1 amount (entry 2)
        // Mapping: $pensjon.vedtak[x].delvisstans.utbetaling.beloepBrutto -> P6000.Decisions.Decision[x].PartialSuspensionPension.PleaseFillInFollowingIfPensionPartlySuspendedYes.amount
        assertEquals("1101.50", pensjonVedtakSecondDelvisstansUtbetaling?.beloepBrutto, "4.1.14.2.1 pensjon.vedtak.1.delvisstans.utbetaling.beloepBrutto")
        // 4.1.14.2.2 currency (entry 1)
        // Mapping: $pensjon.vedtak[x].delvisstans.utbetaling.valuta -> P6000.Decisions.Decision[x].PartialSuspensionPension.PleaseFillInFollowingIfPensionPartlySuspendedYes.currency.value[x]
        assertEquals("NOK", pensjonVedtakFirstDelvisstansUtbetaling?.valuta, "4.1.14.2.2 pensjon.vedtak.0.delvisstans.utbetaling.valuta")
        // 4.1.14.2.2 currency (entry 2)
        // Mapping: $pensjon.vedtak[x].delvisstans.utbetaling.valuta -> P6000.Decisions.Decision[x].PartialSuspensionPension.PleaseFillInFollowingIfPensionPartlySuspendedYes.currency.value[x]
        assertEquals("NOK", pensjonVedtakSecondDelvisstansUtbetaling?.valuta, "4.1.14.2.2 pensjon.vedtak.1.delvisstans.utbetaling.valuta")
        // 4.1.14.2.3 reason (entry 1)
        // Mapping: $pensjon.vedtak[x].delvisstans.utbetaling.begrunnelse -> P6000.Decisions.Decision[x].PartialSuspensionPension.PleaseFillInFollowingIfPensionPartlySuspendedYes.reason
        assertEquals("4.1.14.2.3 Test reason 1", pensjonVedtakFirstDelvisstansUtbetaling?.begrunnelse, "4.1.14.2.3 pensjon.vedtak.0.delvisstans.utbetaling.begrunnelse")
        // 4.1.14.2.3 reason (entry 2)
        // Mapping: $pensjon.vedtak[x].delvisstans.utbetaling.begrunnelse -> P6000.Decisions.Decision[x].PartialSuspensionPension.PleaseFillInFollowingIfPensionPartlySuspendedYes.reason
        assertEquals("4.1.14.2.3 Test reason 2", pensjonVedtakSecondDelvisstansUtbetaling?.begrunnelse, "4.1.14.2.3 pensjon.vedtak.1.delvisstans.utbetaling.begrunnelse")
        // 4.1.15 additional Information (entry 1)
        // Mapping: $pensjon.vedtak[x].begrunnelseAnnen -> P6000.Decisions.Decision[x].additionalInformation
        assertEquals("4.1.15 Test additional Information 1", pensjonVedtakFirst?.begrunnelseAnnen, "4.1.15 pensjon.vedtak.0.begrunnelseAnnen")
        // 4.1.15 additional Information (entry 2)
        // Mapping: $pensjon.vedtak[x].begrunnelseAnnen -> P6000.Decisions.Decision[x].additionalInformation
        assertEquals("4.1.15 Test additional Information 2", pensjonVedtakSecond?.begrunnelseAnnen, "4.1.15 pensjon.vedtak.1.begrunnelseAnnen")
    }

    @Test
    fun reductions() {
        val p6000 = mapJsonToAny<P6000>(p6000Json())
        val pensjon = p6000.pensjon
        val pensjonReduksjon = pensjon?.reduksjon
        val pensjonReduksjonFirst = pensjonReduksjon?.getOrNull(0)
        val pensjonReduksjonSecond = pensjonReduksjon?.getOrNull(1)
        val pensjonReduksjonFirstVirkningsdato = pensjonReduksjonFirst?.virkningsdato
        val pensjonReduksjonSecondVirkningsdato = pensjonReduksjonSecond?.virkningsdato

        // 5.1.1 pension Reduced Pursuant Overlapping Benefits Same Kind (entry 1)
        // Mapping: $pensjon.reduksjon[x].type -> P6000.Reductions.ReductionsPension[x].pensionReducedPursuantOverlappingBenefitsSameKind.value[x]
        assertEquals("01", pensjonReduksjonFirst?.type, "5.1.1 pensjon.reduksjon.0.type")
        // 5.1.1 pension Reduced Pursuant Overlapping Benefits Same Kind (entry 2)
        // Mapping: $pensjon.reduksjon[x].type -> P6000.Reductions.ReductionsPension[x].pensionReducedPursuantOverlappingBenefitsSameKind.value[x]
        assertEquals("02", pensjonReduksjonSecond?.type, "5.1.1 pensjon.reduksjon.1.type")
        // 5.1.2 pension Reduced Pursuant Overlapping Or Benefits Or Income (entry 1)
        // Mapping: $pensjon.reduksjon[x].aarsak.annenytelseellerinntekt -> P6000.Reductions.ReductionsPension[x].pensionReducedPursuantOverlappingOrBenefitsOrIncome.value[x]
        assertEquals("99", pensjonReduksjonFirst?.aarsak?.annenytelseellerinntekt, "5.1.2 pensjon.reduksjon.0.aarsak.annenytelseellerinntekt")
        // 5.1.2 pension Reduced Pursuant Overlapping Or Benefits Or Income (entry 2)
        // Mapping: $pensjon.reduksjon[x].aarsak.annenytelseellerinntekt -> P6000.Reductions.ReductionsPension[x].pensionReducedPursuantOverlappingOrBenefitsOrIncome.value[x]
        assertEquals("99", pensjonReduksjonSecond?.aarsak?.annenytelseellerinntekt, "5.1.2 pensjon.reduksjon.1.aarsak.annenytelseellerinntekt")
        // 5.1.3.1 other Benefits Source Income (entry 1)
        // Mapping: $pensjon.reduksjon[x].aarsak.inntektAnnen -> P6000.Reductions.ReductionsPension[x].FillInIfPensionHasBeenReduced.otherBenefitsSourceIncome
        assertEquals("5.1.3.1 Test other Benefits Source Income 1", pensjonReduksjonFirst?.aarsak?.inntektAnnen, "5.1.3.1 pensjon.reduksjon.0.aarsak.inntektAnnen")
        // 5.1.3.1 other Benefits Source Income (entry 2)
        // Mapping: $pensjon.reduksjon[x].aarsak.inntektAnnen -> P6000.Reductions.ReductionsPension[x].FillInIfPensionHasBeenReduced.otherBenefitsSourceIncome
        assertEquals("5.1.3.1 Test other Benefits Source Income 2", pensjonReduksjonSecond?.aarsak?.inntektAnnen, "5.1.3.1 pensjon.reduksjon.1.aarsak.inntektAnnen")
        // 5.1.4 article Concerning Limitation Rule Overlapping (entry 1)
        // Mapping (ACL v4.4): $pensjon.sak.reduksjon[x].artikkeltype -> P6000.Reductions.ReductionsPension[x].articleConcerningLimitationRuleOverlapping.value[x]
        // Mapping: $pensjon.sak.reduksjon[x].artikkeltype -> P6000.Reductions.ReductionsPension[x].articleConcerningLimitationRuleOverlapping.value[x]
        //TODO: 5.1.4 Verify ACL index alignment between pensjon.sak.reduksjon[x] and pensjon.reduksjon[x], which supplies the other fields of the same CDM reduction.
        assertEquals("01", pensjon?.sak?.reduksjon?.getOrNull(0)?.artikkeltype, "5.1.4 pensjon.sak.reduksjon.0.artikkeltype")
        // 5.1.4 article Concerning Limitation Rule Overlapping (entry 2)
        // Mapping (ACL v4.4): $pensjon.sak.reduksjon[x].artikkeltype -> P6000.Reductions.ReductionsPension[x].articleConcerningLimitationRuleOverlapping.value[x]
        // Mapping: $pensjon.sak.reduksjon[x].artikkeltype -> P6000.Reductions.ReductionsPension[x].articleConcerningLimitationRuleOverlapping.value[x]
        //TODO: 5.1.4 Verify ACL index alignment between pensjon.sak.reduksjon[x] and pensjon.reduksjon[x], which supplies the other fields of the same CDM reduction.
        assertEquals("02", pensjon?.sak?.reduksjon?.getOrNull(1)?.artikkeltype, "5.1.4 pensjon.sak.reduksjon.1.artikkeltype")
        // 5.1.5.1 start Date Rule Against Overlapping (entry 1)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].startdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].startDateRuleAgainstOverlapping
        assertEquals("2020-05-02", pensjonReduksjonFirstVirkningsdato?.getOrNull(0)?.startdato, "5.1.5.1 pensjon.reduksjon.0.virkningsdato.0.startdato")
        // 5.1.5.1 start Date Rule Against Overlapping (entry 2)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].startdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].startDateRuleAgainstOverlapping
        assertEquals("2021-05-02", pensjonReduksjonSecondVirkningsdato?.getOrNull(0)?.startdato, "5.1.5.1 pensjon.reduksjon.1.virkningsdato.0.startdato")
        // 5.1.5.1 start Date Rule Against Overlapping (entry 3)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].startdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].startDateRuleAgainstOverlapping
        assertEquals("2022-05-02", pensjonReduksjonFirstVirkningsdato?.getOrNull(1)?.startdato, "5.1.5.1 pensjon.reduksjon.0.virkningsdato.1.startdato")
        // 5.1.5.1 start Date Rule Against Overlapping (entry 4)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].startdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].startDateRuleAgainstOverlapping
        assertEquals("2023-05-02", pensjonReduksjonSecondVirkningsdato?.getOrNull(1)?.startdato, "5.1.5.1 pensjon.reduksjon.1.virkningsdato.1.startdato")
        // 5.1.5.2 end Date Rule Against Overlapping (entry 1)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].sluttdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].endDateRuleAgainstOverlapping
        assertEquals("2020-06-02", pensjonReduksjonFirstVirkningsdato?.getOrNull(0)?.sluttdato, "5.1.5.2 pensjon.reduksjon.0.virkningsdato.0.sluttdato")
        // 5.1.5.2 end Date Rule Against Overlapping (entry 2)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].sluttdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].endDateRuleAgainstOverlapping
        assertEquals("2021-06-02", pensjonReduksjonSecondVirkningsdato?.getOrNull(0)?.sluttdato, "5.1.5.2 pensjon.reduksjon.1.virkningsdato.0.sluttdato")
        // 5.1.5.2 end Date Rule Against Overlapping (entry 3)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].sluttdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].endDateRuleAgainstOverlapping
        assertEquals("2022-06-02", pensjonReduksjonFirstVirkningsdato?.getOrNull(1)?.sluttdato, "5.1.5.2 pensjon.reduksjon.0.virkningsdato.1.sluttdato")
        // 5.1.5.2 end Date Rule Against Overlapping (entry 4)
        // Mapping: $pensjon.reduksjon[x].virkningsdato[x].sluttdato -> P6000.Reductions.ReductionsPension[x].PeriodOverlapping[x].endDateRuleAgainstOverlapping
        assertEquals("2023-06-02", pensjonReduksjonSecondVirkningsdato?.getOrNull(1)?.sluttdato, "5.1.5.2 pensjon.reduksjon.1.virkningsdato.1.sluttdato")
    }

    @Test
    fun additionalDecisionInformation() {
        val p6000 = mapJsonToAny<P6000>(p6000Json())
        val pensjon = p6000.pensjon
        val pensjonSak = pensjon?.sak
        val pensjonTilleggsinformasjon = pensjon?.tilleggsinformasjon
        val pensjonTilleggsinformasjonAnnen = pensjonTilleggsinformasjon?.annen
        val pensjonTilleggsinformasjonAndreinstitusjoner = pensjonTilleggsinformasjon?.andreinstitusjoner
        val pensjonTilleggsinformasjonAndreinstitusjonerFirst = pensjonTilleggsinformasjonAndreinstitusjoner?.getOrNull(0)
        val pensjonTilleggsinformasjonAndreinstitusjonerSecond = pensjonTilleggsinformasjonAndreinstitusjoner?.getOrNull(1)

        // 6.1 benefits A Indicator (entry 1)
        // Mapping: $pensjon.sak.artikkel54 -> P6000.AdditionalInformationDecision.benefitsAIndicator.value[x]
        assertEquals("0", pensjonSak?.artikkel54, "6.1 pensjon.sak.artikkel54")
        // 6.2 suspension Pension (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.opphoer.dato -> P6000.AdditionalInformationDecision.suspensionPension
        assertEquals("2020-01-02", pensjonTilleggsinformasjon?.opphoer?.dato, "6.2 pensjon.tilleggsinformasjon.opphoer.dato")
        // 6.3 withdrawal Pension (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.opphoer.annulleringdato -> P6000.AdditionalInformationDecision.withdrawalPension
        assertEquals("2020-01-02", pensjonTilleggsinformasjon?.opphoer?.annulleringdato, "6.3 pensjon.tilleggsinformasjon.opphoer.annulleringdato")
        // 6.4 date When Decision Issued Stated Decision (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.dato -> P6000.AdditionalInformationDecision.dateWhenDecisionIssuedStatedDecision
        assertEquals("2020-01-02", pensjonTilleggsinformasjon?.dato, "6.4 pensjon.tilleggsinformasjon.dato")
        // 6.5.1 time Limits For Review (entry 1)
        // Mapping: $pensjon.sak.kravtype[x].datoFrist -> P6000.AdditionalInformationDecision.InformationRightAReview.timeLimitsForReview
        //TODO: 6.5.1 Verify ACL behavior with multiple kravtype entries: datoFrist is indexed in the model source but timeLimitsForReview is a scalar CDM field.
        assertEquals("2020-01-02", pensjonSak?.kravtype?.getOrNull(0)?.datoFrist, "6.5.1 pensjon.sak.kravtype.0.datoFrist")
        // 6.5.1 time Limits For Review (entry 2)
        // Mapping (ACL v4.4): $pensjon.sak.kravtype[x].datoFrist -> P6000.AdditionalInformationDecision.InformationRightAReview.timeLimitsForReview
        // Mapping: $pensjon.sak.kravtype[x].datoFrist -> P6000.AdditionalInformationDecision.InformationRightAReview.timeLimitsForReview
        //TODO: 6.5.1 Verify ACL behavior with multiple kravtype entries: datoFrist is indexed in the model source but timeLimitsForReview is a scalar CDM field.
        assertEquals("2021-01-02", pensjonSak?.kravtype?.getOrNull(1)?.datoFrist, "6.5.1 pensjon.sak.kravtype.1.datoFrist")
        // 6.5.2.1.1 institution ID (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].institusjonsid -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Institution.institutionID
        assertEquals("6.5.2.1.1 Test institution ID 1", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.institusjonsid, "6.5.2.1.1 pensjon.tilleggsinformasjon.andreinstitusjoner.0.institusjonsid")
        // 6.5.2.1.1 institution ID (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].institusjonsid -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Institution.institutionID
        assertEquals("6.5.2.1.1 Test institution ID 2", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.institusjonsid, "6.5.2.1.1 pensjon.tilleggsinformasjon.andreinstitusjoner.1.institusjonsid")
        // 6.5.2.1.2 institution Name (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].institusjonsnavn -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Institution.institutionName
        assertEquals("6.5.2.1.2 Test institution Name 1", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.institusjonsnavn, "6.5.2.1.2 pensjon.tilleggsinformasjon.andreinstitusjoner.0.institusjonsnavn")
        // 6.5.2.1.2 institution Name (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].institusjonsnavn -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Institution.institutionName
        assertEquals("6.5.2.1.2 Test institution Name 2", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.institusjonsnavn, "6.5.2.1.2 pensjon.tilleggsinformasjon.andreinstitusjoner.1.institusjonsnavn")
        // 6.5.2.2.1 street (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].institusjonsadresse -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.street
        assertEquals("6.5.2.2.1 Test street 1", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.institusjonsadresse, "6.5.2.2.1 pensjon.tilleggsinformasjon.andreinstitusjoner.0.institusjonsadresse")
        // 6.5.2.2.1 street (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].institusjonsadresse -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.street
        assertEquals("6.5.2.2.1 Test street 2", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.institusjonsadresse, "6.5.2.2.1 pensjon.tilleggsinformasjon.andreinstitusjoner.1.institusjonsadresse")
        // 6.5.2.2.2 building Name (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].bygningsnavn -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.buildingName
        assertEquals("6.5.2.2.2 Test building Name 1", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.bygningsnavn, "6.5.2.2.2 pensjon.tilleggsinformasjon.andreinstitusjoner.0.bygningsnavn")
        // 6.5.2.2.2 building Name (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].bygningsnavn -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.buildingName
        assertEquals("6.5.2.2.2 Test building Name 2", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.bygningsnavn, "6.5.2.2.2 pensjon.tilleggsinformasjon.andreinstitusjoner.1.bygningsnavn")
        // 6.5.2.2.3 town (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].poststed -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.town
        assertEquals("6.5.2.2.3 Test town 1", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.poststed, "6.5.2.2.3 pensjon.tilleggsinformasjon.andreinstitusjoner.0.poststed")
        // 6.5.2.2.3 town (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].poststed -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.town
        assertEquals("6.5.2.2.3 Test town 2", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.poststed, "6.5.2.2.3 pensjon.tilleggsinformasjon.andreinstitusjoner.1.poststed")
        // 6.5.2.2.4 postal Code (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].postnummer -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.postalCode
        assertEquals("0123", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.postnummer, "6.5.2.2.4 pensjon.tilleggsinformasjon.andreinstitusjoner.0.postnummer")
        // 6.5.2.2.4 postal Code (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].postnummer -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.postalCode
        assertEquals("0123", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.postnummer, "6.5.2.2.4 pensjon.tilleggsinformasjon.andreinstitusjoner.1.postnummer")
        // 6.5.2.2.5 region (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].region -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.region
        assertEquals("6.5.2.2.5 Test region 1", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.region, "6.5.2.2.5 pensjon.tilleggsinformasjon.andreinstitusjoner.0.region")
        // 6.5.2.2.5 region (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].region -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.region
        assertEquals("6.5.2.2.5 Test region 2", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.region, "6.5.2.2.5 pensjon.tilleggsinformasjon.andreinstitusjoner.1.region")
        // 6.5.2.2.6 country (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].land -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.country.value[x]
        assertEquals("NO", pensjonTilleggsinformasjonAndreinstitusjonerFirst?.land, "6.5.2.2.6 pensjon.tilleggsinformasjon.andreinstitusjoner.0.land")
        // 6.5.2.2.6 country (entry 2)
        // Mapping: $pensjon.tilleggsinformasjon.andreinstitusjoner[x].land -> P6000.AdditionalInformationDecision.InformationRightAReview.WhereAddressReview[x].Address.country.value[x]
        assertEquals("SE", pensjonTilleggsinformasjonAndreinstitusjonerSecond?.land, "6.5.2.2.6 pensjon.tilleggsinformasjon.andreinstitusjoner.1.land")
        // 6.6 decision Given A Result Review According Art4829872009Indicator (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.artikkel48 -> P6000.AdditionalInformationDecision.decisionGivenAResultReviewAccordingArt4829872009Indicator.value[x]
        assertEquals("0", pensjonTilleggsinformasjon?.artikkel48, "6.6 pensjon.tilleggsinformasjon.artikkel48")
        // 6.7.1.1 country (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.annen.institusjonsadresse.land -> P6000.AdditionalInformationDecision.InstitutionGivingDecisionIfDifferentFromSending.Institution.country.value[x]
        assertEquals("NO", pensjonTilleggsinformasjonAnnen?.institusjonsadresse?.land, "6.7.1.1 pensjon.tilleggsinformasjon.annen.institusjonsadresse.land")
        // 6.7.1.2 personal Identification Number (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.person.pinannen.identifikator -> P6000.AdditionalInformationDecision.InstitutionGivingDecisionIfDifferentFromSending.Institution.personalIdentificationNumber
        assertEquals("6.7.1.2 Test personal Identification Number 1", pensjonTilleggsinformasjon?.person?.pinannen?.identifikator, "6.7.1.2 pensjon.tilleggsinformasjon.person.pinannen.identifikator")
        // 6.7.1.3 sector (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.person.pinannen.sektor -> P6000.AdditionalInformationDecision.InstitutionGivingDecisionIfDifferentFromSending.Institution.sector.value[x]
        assertEquals("pensjoner", pensjonTilleggsinformasjon?.person?.pinannen?.sektor, "6.7.1.3 pensjon.tilleggsinformasjon.person.pinannen.sektor")
        // 6.7.1.4.1 institution ID (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.annen.institusjonsid -> P6000.AdditionalInformationDecision.InstitutionGivingDecisionIfDifferentFromSending.Institution.Institution.institutionID
        assertEquals("6.7.1.4.1 Test institution ID 1", pensjonTilleggsinformasjonAnnen?.institusjonsid, "6.7.1.4.1 pensjon.tilleggsinformasjon.annen.institusjonsid")
        // 6.7.1.4.2 institution Name (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.annen.institusjonsnavn -> P6000.AdditionalInformationDecision.InstitutionGivingDecisionIfDifferentFromSending.Institution.Institution.institutionName
        assertEquals("6.7.1.4.2 Test institution Name 1", pensjonTilleggsinformasjonAnnen?.institusjonsnavn, "6.7.1.4.2 pensjon.tilleggsinformasjon.annen.institusjonsnavn")
        // 6.7.2 case Number (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.saksnummer -> P6000.AdditionalInformationDecision.InstitutionGivingDecisionIfDifferentFromSending.caseNumber
        assertEquals("6.7.2 Test case Number 1", pensjonTilleggsinformasjon?.saksnummer, "6.7.2 pensjon.tilleggsinformasjon.saksnummer")
        // 6.8 reference Number Decision If Other Than Case Number (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.saksnummerAnnen -> P6000.AdditionalInformationDecision.referenceNumberDecisionIfOtherThanCaseNumber
        assertEquals("6.8 Test reference Number Decision If Other Than Case Number 1", pensjonTilleggsinformasjon?.saksnummerAnnen, "6.8 pensjon.tilleggsinformasjon.saksnummerAnnen")
        // 6.9 other Information (entry 1)
        // Mapping: $pensjon.tilleggsinformasjon.anneninformation -> P6000.AdditionalInformationDecision.otherInformation
        assertEquals("6.9 Test other Information 1", pensjonTilleggsinformasjon?.anneninformation, "6.9 pensjon.tilleggsinformasjon.anneninformation")
    }

    @Test
    fun supportedFieldsSurviveJsonRoundTrip() {
        val expected = p6000Json()
        val p6000 = mapJsonToAny<P6000>(expected)

        // 1-6 All populated supported fields, including collection sizes and order.
        JSONAssert.assertEquals(expected, p6000.toJsonSkipEmpty(), true)
    }
}
