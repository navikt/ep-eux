package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Field numbers checked against P9000.pdf (15 pages, printed 27 May 2019).
 * Non-SEPA fields and v4.5 negative-reply additions use versioned ACL metadata where absent in the PDF.
 * Synthetic fixture only. Tests JSON <-> P9000, not the cross-repository runtime ACL.
 * Each version's source path is documented separately; v4.4 and v4.5 are not identical.
 * Section 20.1.2 changes from followingSED in v4.4 to a FollowingSED list in v4.5.
 * TODOs retain unresolved source paths; missing fields are not asserted as null.
 * Pension fixture data follows the current model, including merinformasjon as a single object.
 * Model-only assertions retain TODOs for ACL cardinality mismatches and missing properties.
 * Includes education, requested documents, benefits, activity details and information confirmations.
 */
class P9000HelMappetTest {
    private fun p9000Json(): String =
        requireNotNull(javaClass.getResource("/sed/P9000-HelMappet-NAV.json")).readText()

    @Test
    fun localCaseNumbers() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navEessisak = nav?.eessisak
        val navEessisakNr1 = navEessisak?.getOrNull(0)
        val navEessisakNr2 = navEessisak?.getOrNull(1)

        // 1.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].land -> P9000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("NO", navEessisakNr1?.land, "1.1.1 nav.eessisak.0.land")
        // 1.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].land -> P9000.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("SE", navEessisakNr2?.land, "1.1.1 nav.eessisak.1.land")
        // 1.1.2 case Number (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].saksnummer -> P9000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 1", navEessisakNr1?.saksnummer, "1.1.2 nav.eessisak.0.saksnummer")
        // 1.1.2 case Number (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].saksnummer -> P9000.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("1.1.2 Test case Number 2", navEessisakNr2?.saksnummer, "1.1.2 nav.eessisak.1.saksnummer")
        // 1.1.3.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsid -> P9000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 1", navEessisakNr1?.institusjonsid, "1.1.3.1 nav.eessisak.0.institusjonsid")
        // 1.1.3.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsid -> P9000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("1.1.3.1 Test institution ID 2", navEessisakNr2?.institusjonsid, "1.1.3.1 nav.eessisak.1.institusjonsid")
        // 1.1.3.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsnavn -> P9000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 1", navEessisakNr1?.institusjonsnavn, "1.1.3.2 nav.eessisak.0.institusjonsnavn")
        // 1.1.3.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.eessisak[x].institusjonsnavn -> P9000.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("1.1.3.2 Test institution Name 2", navEessisakNr2?.institusjonsnavn, "1.1.3.2 nav.eessisak.1.institusjonsnavn")
    }

    @Test
    fun insuredPerson() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navBruker = nav?.bruker
        val navBrukerPerson = navBruker?.person
        val navBrukerAdresse = navBruker?.adresse
        val navBrukerPersonPin = navBrukerPerson?.pin
        val navBrukerPersonKontakt = navBrukerPerson?.kontakt
        val navBrukerPersonFoedested = navBrukerPerson?.foedested
        val navBrukerPersonKontaktTelefon = navBrukerPersonKontakt?.telefon
        val navBrukerPersonPin1 = navBrukerPersonPin?.getOrNull(0)
        val navBrukerPersonPin2 = navBrukerPersonPin?.getOrNull(1)

        // 2.1.1 family Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.etternavn -> P9000.InsuredPerson.PersonIdentification.familyName
        assertEquals("2.1.1 Test family Name 1", navBrukerPerson?.etternavn, "2.1.1 nav.bruker.person.etternavn")
        // 2.1.2 forename (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.fornavn -> P9000.InsuredPerson.PersonIdentification.forename
        assertEquals("2.1.2 Test forename 1", navBrukerPerson?.fornavn, "2.1.2 nav.bruker.person.fornavn")
        // 2.1.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedselsdato -> P9000.InsuredPerson.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navBrukerPerson?.foedselsdato, "2.1.3 nav.bruker.person.foedselsdato")
        // 2.1.4 sex (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.kjoenn -> P9000.InsuredPerson.PersonIdentification.sex.value[x]
        assertEquals("K", navBrukerPerson?.kjoenn, "2.1.4 nav.bruker.person.kjoenn")
        // 2.1.5 family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.etternavnvedfoedsel -> P9000.InsuredPerson.PersonIdentification.familyNameAtBirth
        assertEquals("2.1.5 Test family Name At Birth 1", navBrukerPerson?.etternavnvedfoedsel, "2.1.5 nav.bruker.person.etternavnvedfoedsel")
        // 2.1.6 forename At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.fornavnvedfoedsel -> P9000.InsuredPerson.PersonIdentification.forenameAtBirth
        assertEquals("2.1.6 Test forename At Birth 1", navBrukerPerson?.fornavnvedfoedsel, "2.1.6 nav.bruker.person.fornavnvedfoedsel")
        // 2.1.7.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].land -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navBrukerPersonPin1?.land, "2.1.7.1.1 nav.bruker.person.pin.0.land")
        // 2.1.7.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].land -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navBrukerPersonPin2?.land, "2.1.7.1.1 nav.bruker.person.pin.1.land")
        // 2.1.7.1.2 personal Identification Number (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].identifikator -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 1", navBrukerPersonPin1?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.0.identifikator")
        // 2.1.7.1.2 personal Identification Number (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].identifikator -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("2.1.7.1.2 Test personal Identification Number 2", navBrukerPersonPin2?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.1.identifikator")
        // 2.1.7.1.3 sector (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].sektor -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPin1?.sektor, "2.1.7.1.3 nav.bruker.person.pin.0.sektor")
        // 2.1.7.1.3 sector (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].sektor -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navBrukerPersonPin2?.sektor, "2.1.7.1.3 nav.bruker.person.pin.1.sektor")
        // 2.1.7.1.4.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsid -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 1", navBrukerPersonPin1?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.0.institusjonsid")
        // 2.1.7.1.4.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsid -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("2.1.7.1.4.1 Test institution ID 2", navBrukerPersonPin2?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.1.institusjonsid")
        // 2.1.7.1.4.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsnavn -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 1", navBrukerPersonPin1?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.0.institusjonsnavn")
        // 2.1.7.1.4.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.pin[x].institusjonsnavn -> P9000.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("2.1.7.1.4.2 Test institution Name 2", navBrukerPersonPin2?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.1.institusjonsnavn")
        // 2.1.8.1.1 town (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.by -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("2.1.8.1.1 Test town 1", navBrukerPersonFoedested?.by, "2.1.8.1.1 nav.bruker.person.foedested.by")
        // 2.1.8.1.2 region (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.region -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("2.1.8.1.2 Test region 1", navBrukerPersonFoedested?.region, "2.1.8.1.2 nav.bruker.person.foedested.region")
        // 2.1.8.1.3 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.foedested.land -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navBrukerPersonFoedested?.land, "2.1.8.1.3 nav.bruker.person.foedested.land")
        // 2.1.8.2 father Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.far.person.etternavnvedfoedsel -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("2.1.8.2 Test father Family Name At Birth 1", navBruker?.far?.person?.etternavnvedfoedsel, "2.1.8.2 nav.bruker.far.person.etternavnvedfoedsel")
        // 2.1.8.3 forename Father (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.far.person.fornavn -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("2.1.8.3 Test forename Father 1", navBruker?.far?.person?.fornavn, "2.1.8.3 nav.bruker.far.person.fornavn")
        // 2.1.8.4 mother Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.mor.person.etternavnvedfoedsel -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("2.1.8.4 Test mother Family Name At Birth 1", navBruker?.mor?.person?.etternavnvedfoedsel, "2.1.8.4 nav.bruker.mor.person.etternavnvedfoedsel")
        // 2.1.8.5 forename Mother (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.mor.person.fornavn -> P9000.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("2.1.8.5 Test forename Mother 1", navBruker?.mor?.person?.fornavn, "2.1.8.5 nav.bruker.mor.person.fornavn")
        // 2.2.1.1 nationality (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.statsborgerskap[x].land -> P9000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("NO", navBrukerPerson?.statsborgerskap?.getOrNull(0)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.0.land")
        // 2.2.1.1 nationality (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.statsborgerskap[x].land -> P9000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", navBrukerPerson?.statsborgerskap?.getOrNull(1)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.1.land")
        // 2.2.1.2 previous Family Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.tidligereetternavn -> P9000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("2.2.1.2 Test previous Family Name 1", navBrukerPerson?.tidligereetternavn, "2.2.1.2 nav.bruker.person.tidligereetternavn")
        // 2.2.1.3 previous Forename (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.tidligerefornavn -> P9000.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("2.2.1.3 Test previous Forename 1", navBrukerPerson?.tidligerefornavn, "2.2.1.3 nav.bruker.person.tidligerefornavn")
        // 2.2.2.1 street (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.gate -> P9000.InsuredPerson.AdditionalInformationPerson.Address.street
        assertEquals("2.2.2.1 Test street 1", navBrukerAdresse?.gate, "2.2.2.1 nav.bruker.adresse.gate")
        // 2.2.2.2 building Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.bygning -> P9000.InsuredPerson.AdditionalInformationPerson.Address.buildingName
        assertEquals("2.2.2.2 Test building Name 1", navBrukerAdresse?.bygning, "2.2.2.2 nav.bruker.adresse.bygning")
        // 2.2.2.3 town (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.by -> P9000.InsuredPerson.AdditionalInformationPerson.Address.town
        assertEquals("2.2.2.3 Test town 1", navBrukerAdresse?.by, "2.2.2.3 nav.bruker.adresse.by")
        // 2.2.2.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.postnummer -> P9000.InsuredPerson.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", navBrukerAdresse?.postnummer, "2.2.2.4 nav.bruker.adresse.postnummer")
        // 2.2.2.5 region (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.region -> P9000.InsuredPerson.AdditionalInformationPerson.Address.region
        assertEquals("2.2.2.5 Test region 1", navBrukerAdresse?.region, "2.2.2.5 nav.bruker.adresse.region")
        // 2.2.2.6 country (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.adresse.land -> P9000.InsuredPerson.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", navBrukerAdresse?.land, "2.2.2.6 nav.bruker.adresse.land")
        // 2.2.3.1.1.1 type (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.kontakt.telefon[x].type -> P9000.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", navBrukerPersonKontaktTelefon?.getOrNull(0)?.type, "2.2.3.1.1.1 nav.bruker.person.kontakt.telefon.0.type")
        // 2.2.3.1.1.1 type (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.kontakt.telefon[x].type -> P9000.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", navBrukerPersonKontaktTelefon?.getOrNull(1)?.type, "2.2.3.1.1.1 nav.bruker.person.kontakt.telefon.1.type")
        // 2.2.3.1.1.2 number (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.kontakt.telefon[x].nummer -> P9000.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("2.2.3.1.1.2 Test number 1", navBrukerPersonKontaktTelefon?.getOrNull(0)?.nummer, "2.2.3.1.1.2 nav.bruker.person.kontakt.telefon.0.nummer")
        // 2.2.3.1.1.2 number (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.kontakt.telefon[x].nummer -> P9000.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("2.2.3.1.1.2 Test number 2", navBrukerPersonKontaktTelefon?.getOrNull(1)?.nummer, "2.2.3.1.1.2 nav.bruker.person.kontakt.telefon.1.nummer")
        // 2.2.3.2.1.1 email (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.kontakt.email[x].adresse -> P9000.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-2.2.3.2.1.1-1@example.invalid", navBrukerPersonKontakt?.email?.getOrNull(0)?.adresse, "2.2.3.2.1.1 nav.bruker.person.kontakt.email.0.adresse")
        // 2.2.3.2.1.1 email (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.kontakt.email[x].adresse -> P9000.InsuredPerson.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-2.2.3.2.1.1-2@example.invalid", navBrukerPersonKontakt?.email?.getOrNull(1)?.adresse, "2.2.3.2.1.1 nav.bruker.person.kontakt.email.1.adresse")
    }

    @Test
    fun otherPerson() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navAnnenperson = nav?.annenperson
        val navAnnenpersonPerson = navAnnenperson?.person
        val navAnnenpersonPersonPin = navAnnenpersonPerson?.pin
        val navAnnenpersonPersonKontakt = navAnnenpersonPerson?.kontakt
        val navAnnenpersonPersonFoedested = navAnnenpersonPerson?.foedested
        val navAnnenpersonPersonKontaktTelefon = navAnnenpersonPersonKontakt?.telefon
        val navAnnenpersonPersonPin1 = navAnnenpersonPersonPin?.getOrNull(0)
        val navAnnenpersonPersonPin2 = navAnnenpersonPersonPin?.getOrNull(1)

        // 3.1 role Person Concerned (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.rolle -> P9000.OtherPerson.rolePersonConcerned.value[x]
        assertEquals("01", navAnnenpersonPerson?.rolle, "3.1 nav.annenperson.person.rolle")
        // 3.2.1 family Name (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.etternavn -> P9000.OtherPerson.PersonIdentification.familyName
        assertEquals("3.2.1 Test family Name 1", navAnnenpersonPerson?.etternavn, "3.2.1 nav.annenperson.person.etternavn")
        // 3.2.2 forename (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.fornavn -> P9000.OtherPerson.PersonIdentification.forename
        assertEquals("3.2.2 Test forename 1", navAnnenpersonPerson?.fornavn, "3.2.2 nav.annenperson.person.fornavn")
        // 3.2.3 date Birth (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.foedselsdato -> P9000.OtherPerson.PersonIdentification.dateBirth
        assertEquals("2020-01-02", navAnnenpersonPerson?.foedselsdato, "3.2.3 nav.annenperson.person.foedselsdato")
        // 3.2.4 sex (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.kjoenn -> P9000.OtherPerson.PersonIdentification.sex.value[x]
        assertEquals("K", navAnnenpersonPerson?.kjoenn, "3.2.4 nav.annenperson.person.kjoenn")
        // 3.2.5 family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.etternavnvedfoedsel -> P9000.OtherPerson.PersonIdentification.familyNameAtBirth
        assertEquals("3.2.5 Test family Name At Birth 1", navAnnenpersonPerson?.etternavnvedfoedsel, "3.2.5 nav.annenperson.person.etternavnvedfoedsel")
        // 3.2.6 forename At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.fornavnvedfoedsel -> P9000.OtherPerson.PersonIdentification.forenameAtBirth
        assertEquals("3.2.6 Test forename At Birth 1", navAnnenpersonPerson?.fornavnvedfoedsel, "3.2.6 nav.annenperson.person.fornavnvedfoedsel")
        // 3.2.7.1.1 country (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].land -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", navAnnenpersonPersonPin1?.land, "3.2.7.1.1 nav.annenperson.person.pin.0.land")
        // 3.2.7.1.1 country (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].land -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", navAnnenpersonPersonPin2?.land, "3.2.7.1.1 nav.annenperson.person.pin.1.land")
        // 3.2.7.1.2 personal Identification Number (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].identifikator -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.2.7.1.2 Test personal Identification Number 1", navAnnenpersonPersonPin1?.identifikator, "3.2.7.1.2 nav.annenperson.person.pin.0.identifikator")
        // 3.2.7.1.2 personal Identification Number (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].identifikator -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("3.2.7.1.2 Test personal Identification Number 2", navAnnenpersonPersonPin2?.identifikator, "3.2.7.1.2 nav.annenperson.person.pin.1.identifikator")
        // 3.2.7.1.3 sector (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].sektor -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navAnnenpersonPersonPin1?.sektor, "3.2.7.1.3 nav.annenperson.person.pin.0.sektor")
        // 3.2.7.1.3 sector (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].sektor -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("pensjoner", navAnnenpersonPersonPin2?.sektor, "3.2.7.1.3 nav.annenperson.person.pin.1.sektor")
        // 3.2.7.1.4.1 institution ID (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].institusjonsid -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.2.7.1.4.1 Test institution ID 1", navAnnenpersonPersonPin1?.institusjonsid, "3.2.7.1.4.1 nav.annenperson.person.pin.0.institusjonsid")
        // 3.2.7.1.4.1 institution ID (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].institusjonsid -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("3.2.7.1.4.1 Test institution ID 2", navAnnenpersonPersonPin2?.institusjonsid, "3.2.7.1.4.1 nav.annenperson.person.pin.1.institusjonsid")
        // 3.2.7.1.4.2 institution Name (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].institusjonsnavn -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.2.7.1.4.2 Test institution Name 1", navAnnenpersonPersonPin1?.institusjonsnavn, "3.2.7.1.4.2 nav.annenperson.person.pin.0.institusjonsnavn")
        // 3.2.7.1.4.2 institution Name (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.pin[x].institusjonsnavn -> P9000.OtherPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("3.2.7.1.4.2 Test institution Name 2", navAnnenpersonPersonPin2?.institusjonsnavn, "3.2.7.1.4.2 nav.annenperson.person.pin.1.institusjonsnavn")
        // 3.2.8.1.1 town (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.foedested.by -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("3.2.8.1.1 Test town 1", navAnnenpersonPersonFoedested?.by, "3.2.8.1.1 nav.annenperson.person.foedested.by")
        // 3.2.8.1.2 region (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.foedested.region -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("3.2.8.1.2 Test region 1", navAnnenpersonPersonFoedested?.region, "3.2.8.1.2 nav.annenperson.person.foedested.region")
        // 3.2.8.1.3 country (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.foedested.land -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("NO", navAnnenpersonPersonFoedested?.land, "3.2.8.1.3 nav.annenperson.person.foedested.land")
        val father = navAnnenperson?.far?.person
        val mother = navAnnenperson?.mor?.person

        // 3.2.8.2 father Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.far.person.etternavnvedfoedsel -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("3.2.8.2 Test father Family Name At Birth 1", father?.etternavnvedfoedsel, "3.2.8.2 nav.annenperson.far.person.etternavnvedfoedsel")
        // 3.2.8.3 forename Father (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.far.person.fornavnvedfoedsel -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("3.2.8.3 Test forename Father 1", father?.fornavnvedfoedsel, "3.2.8.3 nav.annenperson.far.person.fornavnvedfoedsel")
        // 3.2.8.3 Father's forename, model-only comparison with the source above.
        assertEquals("3.2.8.3 Test current forename", father?.fornavn, "3.2.8.3 nav.annenperson.far.person.fornavn")
        // 3.2.8.4 mother Family Name At Birth (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.mor.person.etternavnvedfoedsel -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("3.2.8.4 Test mother Family Name At Birth 1", mother?.etternavnvedfoedsel, "3.2.8.4 nav.annenperson.mor.person.etternavnvedfoedsel")
        // 3.2.8.5 forename Mother (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.mor.person.fornavnvedfoedsel -> P9000.OtherPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("3.2.8.5 Test forename Mother 1", mother?.fornavnvedfoedsel, "3.2.8.5 nav.annenperson.mor.person.fornavnvedfoedsel")
        // 3.2.8.5 Mother's forename, model-only comparison with the source above.
        assertEquals("3.2.8.5 Test current forename", mother?.fornavn, "3.2.8.5 nav.annenperson.mor.person.fornavn")
        // 3.3.1.1 nationality (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.statsborgerskap[x].land -> P9000.OtherPerson.AdditionalInformationPerson.AdditionalInformationAboutAPerson.nationality.value[x]
        assertEquals("NO", navAnnenpersonPerson?.statsborgerskap?.getOrNull(0)?.land, "3.3.1.1 nav.annenperson.person.statsborgerskap.0.land")
        // 3.3.1.1 nationality (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.statsborgerskap[x].land -> P9000.OtherPerson.AdditionalInformationPerson.AdditionalInformationAboutAPerson.nationality.value[x]
        assertEquals("SE", navAnnenpersonPerson?.statsborgerskap?.getOrNull(1)?.land, "3.3.1.1 nav.annenperson.person.statsborgerskap.1.land")
        // 3.3.1.2 previous Family Name (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.tidligereetternavn -> P9000.OtherPerson.AdditionalInformationPerson.AdditionalInformationAboutAPerson.previousFamilyName
        assertEquals("3.3.1.2 Test previous Family Name 1", navAnnenpersonPerson?.tidligereetternavn, "3.3.1.2 nav.annenperson.person.tidligereetternavn")
        // 3.3.1.3 previous Forename (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.tidligerefornavn -> P9000.OtherPerson.AdditionalInformationPerson.AdditionalInformationAboutAPerson.previousForename
        assertEquals("3.3.1.3 Test previous Forename 1", navAnnenpersonPerson?.tidligerefornavn, "3.3.1.3 nav.annenperson.person.tidligerefornavn")
        // 3.3.2.1.1.1 type (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.kontakt.telefon[x].type -> P9000.OtherPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", navAnnenpersonPersonKontaktTelefon?.getOrNull(0)?.type, "3.3.2.1.1.1 nav.annenperson.person.kontakt.telefon.0.type")
        // 3.3.2.1.1.1 type (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.kontakt.telefon[x].type -> P9000.OtherPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", navAnnenpersonPersonKontaktTelefon?.getOrNull(1)?.type, "3.3.2.1.1.1 nav.annenperson.person.kontakt.telefon.1.type")
        // 3.3.2.1.1.2 number (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.kontakt.telefon[x].nummer -> P9000.OtherPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("3.3.2.1.1.2 Test number 1", navAnnenpersonPersonKontaktTelefon?.getOrNull(0)?.nummer, "3.3.2.1.1.2 nav.annenperson.person.kontakt.telefon.0.nummer")
        // 3.3.2.1.1.2 number (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.kontakt.telefon[x].nummer -> P9000.OtherPerson.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("3.3.2.1.1.2 Test number 2", navAnnenpersonPersonKontaktTelefon?.getOrNull(1)?.nummer, "3.3.2.1.1.2 nav.annenperson.person.kontakt.telefon.1.nummer")
        // 3.3.2.2.1.1 email (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.person.kontakt.email[x].adresse -> P9000.OtherPerson.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-3.3.2.2.1.1-1@example.invalid", navAnnenpersonPersonKontakt?.email?.getOrNull(0)?.adresse, "3.3.2.2.1.1 nav.annenperson.person.kontakt.email.0.adresse")
        // 3.3.2.2.1.1 email (entry 2)
        // Mapping (ACL v4.5): $nav.annenperson.person.kontakt.email[x].adresse -> P9000.OtherPerson.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-3.3.2.2.1.1-2@example.invalid", navAnnenpersonPersonKontakt?.email?.getOrNull(1)?.adresse, "3.3.2.2.1.1 nav.annenperson.person.kontakt.email.1.adresse")
    }

    @Test
    fun kapittel4() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        // 4.1 Date of the P8000 request
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.p8000.dato -> P9000.ReferenceRequestP8000.thisReplyMadeInResponseRequestP8000Dated
        assertEquals("2024-01-15", p9000.p9000Pensjon?.tilleggsinformasjon?.p8000?.dato, "4.1 pensjon.tilleggsinformasjon.p8000.dato")
        // 4.2 Reference person
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.p8000.henvisningperson -> P9000.ReferenceRequestP8000.thisReplyMadeWithReferencePersonNamedIn.value[x]
        assertEquals("01", p9000.p9000Pensjon?.tilleggsinformasjon?.p8000?.henvisningperson, "4.2 pensjon.tilleggsinformasjon.p8000.henvisningperson")
    }

    @Test
    fun requestedDocuments() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val additional = p9000.p9000Pensjon?.tilleggsinformasjon
        // 5.1 Attached document (entry 1)
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.vedlegg[x].dokument -> P9000.ReplyRequestForDocument.pleaseFindAttachedFollowingDocument.value[x]
        assertEquals("01", additional?.vedlegg?.getOrNull(0)?.dokument, "5.1 pensjon.tilleggsinformasjon.vedlegg.0.dokument")
        // 5.1 Attached document (entry 2)
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.vedlegg[x].dokument -> P9000.ReplyRequestForDocument.pleaseFindAttachedFollowingDocument.value[x]
        assertEquals("02", additional?.vedlegg?.getOrNull(1)?.dokument, "5.1 pensjon.tilleggsinformasjon.vedlegg.1.dokument")
        // 5.2 Additional document information
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.vedlegginfo -> P9000.ReplyRequestForDocument.additionalInformationDocument
        assertEquals("Test attached document details", additional?.vedlegginfo, "5.2 pensjon.tilleggsinformasjon.vedlegginfo")
    }

    @Test
    fun lifeAndDeath() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val merinformasjon = p9000.p9000Pensjon?.merinformasjon
        val informationLivdoedinfo = merinformasjon?.livdoedinfo
        // 6.1 Life/death status
        // Mapping (ACL v4.5): $pensjon.merinformasjon.livdoedinfo.lever -> P9000.LifeDeathInformation.accordingOurOfficialSources.value[x]
        assertEquals("01", informationLivdoedinfo?.lever, "6.1 pensjon.merinformasjon.livdoedinfo.lever")
        // 6.2 Date of death
        // Mapping (ACL v4.5): $pensjon.merinformasjon.livdoedinfo.dodsdato -> P9000.LifeDeathInformation.dateDeathPersonIs
        assertEquals("2020-06-15", informationLivdoedinfo?.dodsdato, "6.2 pensjon.merinformasjon.livdoedinfo.dodsdato")
        // 6.3.1 Birth town
        // Mapping (ACL v4.5): $pensjon.merinformasjon.livdoedinfo.by -> P9000.LifeDeathInformation.PlaceBirth.townBirth
        assertEquals("Test birth town 1", informationLivdoedinfo?.by, "6.3.1 pensjon.merinformasjon.livdoedinfo.by")
        // 6.3.2 Birth region
        // Mapping (ACL v4.5): $pensjon.merinformasjon.livdoedinfo.region -> P9000.LifeDeathInformation.PlaceBirth.regionBirth
        assertEquals("Test birth region 1", informationLivdoedinfo?.region, "6.3.2 pensjon.merinformasjon.livdoedinfo.region")
        // 6.3.3 Birth country
        // Mapping (ACL v4.5): $pensjon.merinformasjon.livdoedinfo.land -> P9000.LifeDeathInformation.PlaceBirth.countryBirth.value[x]
        assertEquals("NO", informationLivdoedinfo?.land, "6.3.3 pensjon.merinformasjon.livdoedinfo.land")
    }

    @Test
    fun education() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val information1 = p9000.p9000Pensjon?.merinformasjon
        val information1School1 = information1?.infoskolegang?.getOrNull(0)
        // 7.1.1 Education start date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].startdatoskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].startDateAttendanceSchoolTrainingHigherStudies
        assertEquals("2020-01-01", information1School1?.startdatoskolegang, "7.1.1 pensjon.merinformasjon.infoskolegang.0.startdatoskolegang")
        // 7.1.2 Education end date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].sluttdatoskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].endDateAttendanceSchoolTrainingHigherStudies
        assertEquals("2020-06-01", information1School1?.sluttdatoskolegang, "7.1.2 pensjon.merinformasjon.infoskolegang.0.sluttdatoskolegang")
        // 7.1.3 Education type
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].typeskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].typeSchoolTrainingHigherStudies
        assertEquals("Test education type 1-1", information1School1?.typeskolegang, "7.1.3 pensjon.merinformasjon.infoskolegang.0.typeskolegang")
        // 7.1.4 Educational institution name
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].skolenavn -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].nameEducationalInstitutionSchoolUniversity
        assertEquals("Test institution 1-1", information1School1?.skolenavn, "7.1.4 pensjon.merinformasjon.infoskolegang.0.skolenavn")
        // 7.1.5 Additional education information
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].merinfoomskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].detailedInformationAttendanceSchoolOrTraining
        assertEquals("Test education details 1-1", information1School1?.merinfoomskolegang, "7.1.5 pensjon.merinformasjon.infoskolegang.0.merinfoomskolegang")
        val information1School2 = information1?.infoskolegang?.getOrNull(1)
        // 7.1.1 Education start date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].startdatoskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].startDateAttendanceSchoolTrainingHigherStudies
        assertEquals("2020-01-02", information1School2?.startdatoskolegang, "7.1.1 pensjon.merinformasjon.infoskolegang.1.startdatoskolegang")
        // 7.1.2 Education end date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].sluttdatoskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].endDateAttendanceSchoolTrainingHigherStudies
        assertEquals("2020-06-02", information1School2?.sluttdatoskolegang, "7.1.2 pensjon.merinformasjon.infoskolegang.1.sluttdatoskolegang")
        // 7.1.3 Education type
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].typeskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].typeSchoolTrainingHigherStudies
        assertEquals("Test education type 1-2", information1School2?.typeskolegang, "7.1.3 pensjon.merinformasjon.infoskolegang.1.typeskolegang")
        // 7.1.4 Educational institution name
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].skolenavn -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].nameEducationalInstitutionSchoolUniversity
        assertEquals("Test institution 1-2", information1School2?.skolenavn, "7.1.4 pensjon.merinformasjon.infoskolegang.1.skolenavn")
        // 7.1.5 Additional education information
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoskolegang[x].merinfoomskolegang -> P9000.InformationAttendanceSchoolTrainingHigherStudies.AttendanceSchoolTrainingHigherStudies[x].detailedInformationAttendanceSchoolOrTraining
        assertEquals("Test education details 1-2", information1School2?.merinfoomskolegang, "7.1.5 pensjon.merinformasjon.infoskolegang.1.merinfoomskolegang")
    }

    @Test
    fun currentAddress() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navAnnenperson = nav?.annenperson
        val navAnnenpersonAdresse = navAnnenperson?.adresse

        // 8.1.1 street (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.gate -> P9000.CurrentAddressPerson.Address.street
        assertEquals("8.1.1 Test street 1", navAnnenpersonAdresse?.gate, "8.1.1 nav.annenperson.adresse.gate")
        // 8.1.2 building Name (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.bygning -> P9000.CurrentAddressPerson.Address.buildingName
        assertEquals("8.1.2 Test building Name 1", navAnnenpersonAdresse?.bygning, "8.1.2 nav.annenperson.adresse.bygning")
        // 8.1.3 town (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.by -> P9000.CurrentAddressPerson.Address.town
        assertEquals("8.1.3 Test town 1", navAnnenpersonAdresse?.by, "8.1.3 nav.annenperson.adresse.by")
        // 8.1.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.postnummer -> P9000.CurrentAddressPerson.Address.postalCode
        assertEquals("0123", navAnnenpersonAdresse?.postnummer, "8.1.4 nav.annenperson.adresse.postnummer")
        // 8.1.5 region (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.region -> P9000.CurrentAddressPerson.Address.region
        assertEquals("8.1.5 Test region 1", navAnnenpersonAdresse?.region, "8.1.5 nav.annenperson.adresse.region")
        // 8.1.6 country (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.land -> P9000.CurrentAddressPerson.Address.country.value[x]
        assertEquals("NO", navAnnenpersonAdresse?.land, "8.1.6 nav.annenperson.adresse.land")
        // 8.2 date Change Address (entry 1)
        // Mapping (ACL v4.5): $nav.annenperson.adresse.datoforadresseendring -> P9000.CurrentAddressPerson.dateChangeAddress
        assertEquals("2020-01-02", navAnnenpersonAdresse?.datoforadresseendring, "8.2 nav.annenperson.adresse.datoforadresseendring")


        // 9. personalIdentificationNumber
        // 9.1 personal Identification Number For Person Concerned Is
        // Mapping (ACL v4.5): $nav.annenperson.person.pinannen.identifikator -> P9000.PersonalIdentificationNumber.personalIdentificationNumberForPersonConcernedIs
        assertEquals("3.2.7.2 Test personal Identification Number For Person Concerned", navAnnenperson?.person?.pinannen?.identifikator, "9.1 nav.annenperson.person.pinannen.identifikator")
    }


    @Test
    fun familyStatus() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navBruker = nav?.bruker
        val navBrukerPerson = navBruker?.person
        val navBrukerPersonSivilstand = navBrukerPerson?.sivilstand

        // 10.1.1 family Status (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.sivilstand[x].status -> P9000.FamilyStatus.FamilyStatus[x].familyStatus.value[x]
        assertEquals(SivilstandRina.gift, navBrukerPersonSivilstand?.getOrNull(0)?.status, "10.1.1 nav.bruker.person.sivilstand.0.status")
        // 10.1.1 family Status (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.sivilstand[x].status -> P9000.FamilyStatus.FamilyStatus[x].familyStatus.value[x]
        assertEquals(SivilstandRina.gift, navBrukerPersonSivilstand?.getOrNull(1)?.status, "10.1.1 nav.bruker.person.sivilstand.1.status")
        // 10.1.2 family Status Date (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.person.sivilstand[x].fradato -> P9000.FamilyStatus.FamilyStatus[x].familyStatusDate
        assertEquals("2020-01-02", navBrukerPersonSivilstand?.getOrNull(0)?.fradato, "10.1.2 nav.bruker.person.sivilstand.0.fradato")
        // 10.1.2 family Status Date (entry 2)
        // Mapping (ACL v4.5): $nav.bruker.person.sivilstand[x].fradato -> P9000.FamilyStatus.FamilyStatus[x].familyStatusDate
        assertEquals("2021-01-02", navBrukerPersonSivilstand?.getOrNull(1)?.fradato, "10.1.2 nav.bruker.person.sivilstand.1.fradato")
    }

    @Test
    fun representative() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navVerge = nav?.verge
        val navVergeAdresse = navVerge?.adresse

        // 11.1 representative name
        // Mapping (ACL v4.5): $nav.verge.vergenavn -> P9000.InformationRepresentativeLegalGuardian.name
        assertEquals("11.1 Test representative name", navVerge?.vergenavn, "11.1 nav.verge.vergenavn")
        // 11.2 grounds (entry 1)
        // Mapping (ACL v4.5): $nav.verge.vergemaal.mandat -> P9000.InformationRepresentativeLegalGuardian.grounds
        assertEquals("11.2 Test grounds 1", navVerge?.vergemaal?.mandat, "11.2 nav.verge.vergemaal.mandat")
        // 11.3.1 street (entry 1)
        // Mapping (ACL v4.5): $nav.verge.adresse.gate -> P9000.InformationRepresentativeLegalGuardian.Address.street
        assertEquals("11.3.1 Test street 1", navVergeAdresse?.gate, "11.3.1 nav.verge.adresse.gate")
        // 11.3.2 building Name (entry 1)
        // Mapping (ACL v4.5): $nav.verge.adresse.bygning -> P9000.InformationRepresentativeLegalGuardian.Address.buildingName
        assertEquals("11.3.2 Test building Name 1", navVergeAdresse?.bygning, "11.3.2 nav.verge.adresse.bygning")
        // 11.3.3 town (entry 1)
        // Mapping (ACL v4.5): $nav.verge.adresse.by -> P9000.InformationRepresentativeLegalGuardian.Address.town
        assertEquals("11.3.3 Test town 1", navVergeAdresse?.by, "11.3.3 nav.verge.adresse.by")
        // 11.3.4 postal Code (entry 1)
        // Mapping (ACL v4.5): $nav.verge.adresse.postnummer -> P9000.InformationRepresentativeLegalGuardian.Address.postalCode
        assertEquals("0123", navVergeAdresse?.postnummer, "11.3.4 nav.verge.adresse.postnummer")
        // 11.3.5 region (entry 1)
        // Mapping (ACL v4.5): $nav.verge.adresse.region -> P9000.InformationRepresentativeLegalGuardian.Address.region
        assertEquals("11.3.5 Test region 1", navVergeAdresse?.region, "11.3.5 nav.verge.adresse.region")
        // 11.3.6 country (entry 1)
        // Mapping (ACL v4.5): $nav.verge.adresse.land -> P9000.InformationRepresentativeLegalGuardian.Address.country.value[x]
        assertEquals("NO", navVergeAdresse?.land, "11.3.6 nav.verge.adresse.land")
    }

    @Test
    fun paymentInformation() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val nav = p9000.nav
        val navBruker = nav?.bruker
        val navBrukerBank = navBruker?.bank
        val navBrukerBankKonto = navBrukerBank?.konto
        val navBrukerBankAdresse = navBrukerBank?.adresse

        // 12.1 payment To (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.konto.innehaver.rolle -> P9000.InformationPayment.paymentTo.value[x]
        assertEquals("01", navBrukerBankKonto?.innehaver?.rolle, "12.1 nav.bruker.bank.konto.innehaver.rolle")
        // 12.2.1 account Holder Name (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.konto.innehaver.navn -> P9000.InformationPayment.BankInformation.accountHolderName
        assertEquals("12.2.1 Test account Holder Name 1", navBrukerBankKonto?.innehaver?.navn, "12.2.1 nav.bruker.bank.konto.innehaver.navn")
        // 12.2.2.1 IBAN (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.konto.sepa.iban -> P9000.InformationPayment.BankInformation.SEPAAccount.IBAN
        assertEquals("TEST-IBAN-NOT-A-REAL-ACCOUNT", navBrukerBankKonto?.sepa?.iban, "12.2.2.1 nav.bruker.bank.konto.sepa.iban")
        // 12.2.2.2 BICSWIFT (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.konto.sepa.swift -> P9000.InformationPayment.BankInformation.SEPAAccount.BICSWIFT
        assertEquals("TEST-NOT-A-REAL-BIC", navBrukerBankKonto?.sepa?.swift, "12.2.2.2 nav.bruker.bank.konto.sepa.swift")
        // 12.2.3.1 account Number (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.konto.kontonr -> P9000.InformationPayment.BankInformation.NonSEPAAccount.accountNumber
        assertEquals("12.2.3.1 Test account Number 1", navBrukerBankKonto?.kontonr, "12.2.3.1 nav.bruker.bank.konto.kontonr")
        // 12.2.3.2 BICSWIFT (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.konto.ikkesepa.swift -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BICSWIFT
        assertEquals("TEST-NOT-A-REAL-BIC", navBrukerBankKonto?.ikkesepa?.swift, "12.2.3.2 nav.bruker.bank.konto.ikkesepa.swift")
        // 12.2.3.3 bank Name (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.navn -> P9000.InformationPayment.BankInformation.NonSEPAAccount.bankName
        assertEquals("12.2.3.3 Test bank Name 1", navBrukerBank?.navn, "12.2.3.3 nav.bruker.bank.navn")
        // 12.2.3.4.1 street (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.adresse.gate -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.street
        assertEquals("12.2.3.4.1 Test street 1", navBrukerBankAdresse?.gate, "12.2.3.4.1 nav.bruker.bank.adresse.gate")
        // 12.2.3.4.2 building Name (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.adresse.bygning -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.buildingName
        assertEquals("12.2.3.4.2 Test building Name 1", navBrukerBankAdresse?.bygning, "12.2.3.4.2 nav.bruker.bank.adresse.bygning")
        // 12.2.3.4.3 town (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.adresse.by -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.town
        assertEquals("12.2.3.4.3 Test town 1", navBrukerBankAdresse?.by, "12.2.3.4.3 nav.bruker.bank.adresse.by")
        // 12.2.3.4.4 postal Code (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.adresse.postkode -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.postalCode
        assertEquals("0123", navBrukerBankAdresse?.postkode, "12.2.3.4.4 nav.bruker.bank.adresse.postkode")
        // 12.2.3.4.5 region (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.adresse.region -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.region
        assertEquals("12.2.3.4.5 Test region 1", navBrukerBankAdresse?.region, "12.2.3.4.5 nav.bruker.bank.adresse.region")
        // 12.2.3.4.6 country (number from versioned ACL metadata; not visible in supplied PDF) (entry 1)
        // Mapping (ACL v4.5): $nav.bruker.bank.adresse.land -> P9000.InformationPayment.BankInformation.NonSEPAAccount.BankAddress.country.value[x]
        assertEquals("NO", navBrukerBankAdresse?.land, "12.2.3.4.6 nav.bruker.bank.adresse.land")
    }

    @Test
    fun contactInformation() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val information1 = p9000.p9000Pensjon?.merinformasjon
        val information1Contact = information1?.bruker?.person?.kontakt
        val information1ContactPhone1 = information1Contact?.telefon?.getOrNull(0)
        // 13.1.1.1 Phone type
        // Mapping (ACL v4.5): $pensjon.merinformasjon.bruker.person.kontakt.telefon[x].type -> P9000.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", information1ContactPhone1?.type, "13.1.1.1 pensjon.merinformasjon.bruker.person.kontakt.telefon.0.type")
        // 13.1.1.2 Phone number
        // Mapping (ACL v4.5): $pensjon.merinformasjon.bruker.person.kontakt.telefon[x].nummer -> P9000.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("TEST-PHONE-1-1", information1ContactPhone1?.nummer, "13.1.1.2 pensjon.merinformasjon.bruker.person.kontakt.telefon.0.nummer")
        val information1ContactPhone2 = information1Contact?.telefon?.getOrNull(1)
        // 13.1.1.1 Phone type
        // Mapping (ACL v4.5): $pensjon.merinformasjon.bruker.person.kontakt.telefon[x].type -> P9000.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", information1ContactPhone2?.type, "13.1.1.1 pensjon.merinformasjon.bruker.person.kontakt.telefon.1.type")
        // 13.1.1.2 Phone number
        // Mapping (ACL v4.5): $pensjon.merinformasjon.bruker.person.kontakt.telefon[x].nummer -> P9000.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("TEST-PHONE-1-2", information1ContactPhone2?.nummer, "13.1.1.2 pensjon.merinformasjon.bruker.person.kontakt.telefon.1.nummer")
        // 13.2.1.1 Email address
        // Mapping (ACL v4.5): $pensjon.merinformasjon.bruker.person.kontakt.email[x].adresse -> P9000.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-1-1@example.invalid", information1Contact?.email?.getOrNull(0)?.adresse, "13.2.1.1 pensjon.merinformasjon.bruker.person.kontakt.email.0.adresse")
        // 13.2.1.1 Email address
        // Mapping (ACL v4.5): $pensjon.merinformasjon.bruker.person.kontakt.email[x].adresse -> P9000.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-1-2@example.invalid", information1Contact?.email?.getOrNull(1)?.adresse, "13.2.1.1 pensjon.merinformasjon.bruker.person.kontakt.email.1.adresse")
    }

    @Test
    fun benefits() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val ytelser = p9000.p9000Pensjon?.merinformasjon?.ytelser
        val ytelseNr1 = ytelser?.getOrNull(0)
        // 14.1.1 Benefit status
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].status -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].thePerson.value[x]
        assertEquals("01", ytelseNr1?.status, "14.1.1 pensjon.merinformasjon.ytelser.0.status")
        // 14.1.2 Benefit type (entry 1)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsestype -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].theFollowingBenefit.value[x]
        assertEquals("10", ytelseNr1?.ytelsestype, "14.1.2 pensjon.merinformasjon.ytelser.0.ytelsestype")
        // 14.1.3.1 Other benefit
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.annenytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].FillInIfTheFollowingBenefitOther.otherBenefit
        assertEquals("Test other benefit 1", ytelseNr1?.ytelsesdatoer?.annenytelse, "14.1.3.1 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.annenytelse")
        // 14.1.15 Benefit rejection date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].datotyper.datoavslagpaaytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].dateRejectionBenefit
        assertEquals("2022-09-15", ytelseNr1?.datotyper?.datoavslagpaaytelse, "14.1.15 pensjon.merinformasjon.ytelser.0.datotyper.datoavslagpaaytelse")
        // 14.1.6 Entitlement end date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].datotyper.sluttdatorettytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDateEntitlement
        assertEquals("2022-10-15", ytelseNr1?.datotyper?.sluttdatorettytelse, "14.1.6 pensjon.merinformasjon.ytelser.0.datotyper.sluttdatorettytelse")
        // 14.1.12 Benefit reduction end date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].datotyper.datotyper.sluttdatoredusertytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDateReductionBenefit
        assertEquals("2022-11-15", ytelseNr1?.datotyper?.sluttdatoredusertytelse, "14.1.12 pensjon.merinformasjon.ytelser.0.datotyper.datotyper.sluttdatoredusertytelse")
        // 14.1.14 Benefit supplements
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].tilleggsytelserutbetalingitilleggtilpensjon -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].supplementsAdditionalFrequentPaymentsPension
        assertEquals("Test benefit supplements 1", ytelseNr1?.tilleggsytelserutbetalingitilleggtilpensjon, "14.1.14 pensjon.merinformasjon.ytelser.0.tilleggsytelserutbetalingitilleggtilpensjon")
        // 14.1.16 Additional benefit information
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].anneninfoomytelsertekst -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].otherInformationBenefit
        assertEquals("Test benefit details 1", ytelseNr1?.anneninfoomytelsertekst, "14.1.16 pensjon.merinformasjon.ytelser.0.anneninfoomytelsertekst")
        val ytelsesdatoer = ytelseNr1?.ytelsesdatoer?.datotyper
        // 14.1.4 Claim date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.datokravytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].dateClaimForBenefit
        assertEquals("2022-01-15", ytelsesdatoer?.datokravytelse, "14.1.4 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.datokravytelse")
        // 14.1.5 Entitlement start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoretttilytelser -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDateEntitlement
        assertEquals("2022-02-15", ytelsesdatoer?.startdatoretttilytelser, "14.1.5 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.startdatoretttilytelser")
        // 14.1.7 Payment start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoutbetaling -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDatePayment
        assertEquals("2022-03-15", ytelsesdatoer?.startdatoutbetaling, "14.1.7 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.startdatoutbetaling")
        // 14.1.8 Payment end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.sluttdatoutbetaling -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDatePayment
        assertEquals("2022-04-15", ytelsesdatoer?.sluttdatoutbetaling, "14.1.8 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.sluttdatoutbetaling")
        // 14.1.9 Suspension start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoforstansytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDateSuspensionBenefit
        assertEquals("2022-05-15", ytelsesdatoer?.startdatoforstansytelse, "14.1.9 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.startdatoforstansytelse")
        // 14.1.10 Suspension end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.sluttdatoforstansiytelser -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDateSuspensionBenefit
        assertEquals("2022-06-15", ytelsesdatoer?.sluttdatoforstansiytelser, "14.1.10 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.sluttdatoforstansiytelser")
        // 14.1.11 Reduction start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoredusertytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDateReductionBenefit
        assertEquals("2022-07-15", ytelsesdatoer?.startdatoredusertytelse, "14.1.11 pensjon.merinformasjon.ytelser.0.ytelsesdatoer.datotyper.startdatoredusertytelse")
        val belopItem1 = ytelseNr1?.beloep?.getOrNull(0)
        // 14.1.13.1 Benefit amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].beloep -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amount
        assertEquals("3000.50", belopItem1?.beloep, "14.1.13.1 pensjon.merinformasjon.ytelser.0.beloep.0.beloep")
        // 14.1.13.2 Benefit currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].valuta -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].currency.value[x]
        assertEquals("NOK", belopItem1?.valuta, "14.1.13.2 pensjon.merinformasjon.ytelser.0.beloep.0.valuta")
        // 14.1.13.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].gjeldendesiden -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amountEffectiveSince
        assertEquals("2022-08-01", belopItem1?.gjeldendesiden, "14.1.13.3 pensjon.merinformasjon.ytelser.0.beloep.0.gjeldendesiden")
        // 14.1.13.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet.kode, belopItem1?.betalingshyppighetytelse, "14.1.13.4 pensjon.merinformasjon.ytelser.0.beloep.0.betalingshyppighetytelse")
        // 14.1.13.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test benefit frequency 1-1", belopItem1?.annenbetalingshyppighetytelse, "14.1.13.5.1 pensjon.merinformasjon.ytelser.0.beloep.0.annenbetalingshyppighetytelse")
        val belopItem2 = ytelseNr1?.beloep?.getOrNull(1)
        // 14.1.13.1 Benefit amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].beloep -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amount
        assertEquals("3010.50", belopItem2?.beloep, "14.1.13.1 pensjon.merinformasjon.ytelser.0.beloep.1.beloep")
        // 14.1.13.2 Benefit currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].valuta -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].currency.value[x]
        assertEquals("SEK", belopItem2?.valuta, "14.1.13.2 pensjon.merinformasjon.ytelser.0.beloep.1.valuta")
        // 14.1.13.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].gjeldendesiden -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amountEffectiveSince
        assertEquals("2022-08-02", belopItem2?.gjeldendesiden, "14.1.13.3 pensjon.merinformasjon.ytelser.0.beloep.1.gjeldendesiden")
        // 14.1.13.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet.kode, belopItem2?.betalingshyppighetytelse, "14.1.13.4 pensjon.merinformasjon.ytelser.0.beloep.1.betalingshyppighetytelse")
        // 14.1.13.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test benefit frequency 1-2", belopItem2?.annenbetalingshyppighetytelse, "14.1.13.5.1 pensjon.merinformasjon.ytelser.0.beloep.1.annenbetalingshyppighetytelse")
        val ytelseNr2 = ytelser?.getOrNull(1)
        // 14.1.1 Benefit status
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].status -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].thePerson.value[x]
        assertEquals("02", ytelseNr2?.status, "14.1.1 pensjon.merinformasjon.ytelser.1.status")
        // 14.1.2 Benefit type (entry 2)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsestype -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].theFollowingBenefit.value[x]
        assertEquals("11", ytelseNr2?.ytelsestype, "14.1.2 pensjon.merinformasjon.ytelser.1.ytelsestype")
        // 14.1.3.1 Other benefit
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.annenytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].FillInIfTheFollowingBenefitOther.otherBenefit
        assertEquals("Test other benefit 2", ytelseNr2?.ytelsesdatoer?.annenytelse, "14.1.3.1 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.annenytelse")
        // 14.1.15 Benefit rejection date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].datotyper.datoavslagpaaytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].dateRejectionBenefit
        assertEquals("2023-09-15", ytelseNr2?.datotyper?.datoavslagpaaytelse, "14.1.15 pensjon.merinformasjon.ytelser.1.datotyper.datoavslagpaaytelse")
        // 14.1.6 Entitlement end date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].datotyper.sluttdatorettytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDateEntitlement
        assertEquals("2023-10-15", ytelseNr2?.datotyper?.sluttdatorettytelse, "14.1.6 pensjon.merinformasjon.ytelser.1.datotyper.sluttdatorettytelse")
        // 14.1.12 Benefit reduction end date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].datotyper.sluttdatoredusertytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDateReductionBenefit
        assertEquals("2023-11-15", ytelseNr2?.datotyper?.sluttdatoredusertytelse, "14.1.12 pensjon.merinformasjon.ytelser.1.datotyper.datotyper.sluttdatoredusertytelse")
        // 14.1.14 Benefit supplements
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].tilleggsytelserutbetalingitilleggtilpensjon -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].supplementsAdditionalFrequentPaymentsPension
        assertEquals("Test benefit supplements 2", ytelseNr2?.tilleggsytelserutbetalingitilleggtilpensjon, "14.1.14 pensjon.merinformasjon.ytelser.1.tilleggsytelserutbetalingitilleggtilpensjon")
        // 14.1.16 Additional benefit information
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].anneninfoomytelsertekst -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].otherInformationBenefit
        assertEquals("Test benefit details 2", ytelseNr2?.anneninfoomytelsertekst, "14.1.16 pensjon.merinformasjon.ytelser.1.anneninfoomytelsertekst")
        val benefit2Dates = ytelseNr2?.ytelsesdatoer?.datotyper
        // 14.1.4 Claim date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.datokravytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].dateClaimForBenefit
        assertEquals("2023-01-15", benefit2Dates?.datokravytelse, "14.1.4 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.datokravytelse")
        // 14.1.5 Entitlement start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoretttilytelser -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDateEntitlement
        assertEquals("2023-02-15", benefit2Dates?.startdatoretttilytelser, "14.1.5 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.startdatoretttilytelser")
        // 14.1.7 Payment start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoutbetaling -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDatePayment
        assertEquals("2023-03-15", benefit2Dates?.startdatoutbetaling, "14.1.7 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.startdatoutbetaling")
        // 14.1.8 Payment end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.sluttdatoutbetaling -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDatePayment
        assertEquals("2023-04-15", benefit2Dates?.sluttdatoutbetaling, "14.1.8 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.sluttdatoutbetaling")
        // 14.1.9 Suspension start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoforstansytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDateSuspensionBenefit
        assertEquals("2023-05-15", benefit2Dates?.startdatoforstansytelse, "14.1.9 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.startdatoforstansytelse")
        // 14.1.10 Suspension end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.sluttdatoforstansiytelser -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].endDateSuspensionBenefit
        assertEquals("2023-06-15", benefit2Dates?.sluttdatoforstansiytelser, "14.1.10 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.sluttdatoforstansiytelser")
        // 14.1.11 Reduction start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].ytelsesdatoer.datotyper.startdatoredusertytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].startDateReductionBenefit
        assertEquals("2023-07-15", benefit2Dates?.startdatoredusertytelse, "14.1.11 pensjon.merinformasjon.ytelser.1.ytelsesdatoer.datotyper.startdatoredusertytelse")
        val ytelseNr2Belop1 = ytelseNr2?.beloep?.getOrNull(0)
        // 14.1.13.1 Benefit amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].beloep -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amount
        assertEquals("3100.50", ytelseNr2Belop1?.beloep, "14.1.13.1 pensjon.merinformasjon.ytelser.1.beloep.0.beloep")
        // 14.1.13.2 Benefit currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].valuta -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].currency.value[x]
        assertEquals("NOK", ytelseNr2Belop1?.valuta, "14.1.13.2 pensjon.merinformasjon.ytelser.1.beloep.0.valuta")
        // 14.1.13.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].gjeldendesiden -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amountEffectiveSince
        assertEquals("2023-08-01", ytelseNr2Belop1?.gjeldendesiden, "14.1.13.3 pensjon.merinformasjon.ytelser.1.beloep.0.gjeldendesiden")
        // 14.1.13.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet.kode, ytelseNr2Belop1?.betalingshyppighetytelse, "14.1.13.4 pensjon.merinformasjon.ytelser.1.beloep.0.betalingshyppighetytelse")
        // 14.1.13.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test benefit frequency 2-1", ytelseNr2Belop1?.annenbetalingshyppighetytelse, "14.1.13.5.1 pensjon.merinformasjon.ytelser.1.beloep.0.annenbetalingshyppighetytelse")
        val ytelseNr2Belop2 = ytelseNr2?.beloep?.getOrNull(1)
        // 14.1.13.1 Benefit amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].beloep -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amount
        assertEquals("3110.50", ytelseNr2Belop2?.beloep, "14.1.13.1 pensjon.merinformasjon.ytelser.1.beloep.1.beloep")
        // 14.1.13.2 Benefit currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].valuta -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].currency.value[x]
        assertEquals("SEK", ytelseNr2Belop2?.valuta, "14.1.13.2 pensjon.merinformasjon.ytelser.1.beloep.1.valuta")
        // 14.1.13.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].gjeldendesiden -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].amountEffectiveSince
        assertEquals("2023-08-02", ytelseNr2Belop2?.gjeldendesiden, "14.1.13.3 pensjon.merinformasjon.ytelser.1.beloep.1.gjeldendesiden")
        // 14.1.13.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet.kode, ytelseNr2Belop2?.betalingshyppighetytelse, "14.1.13.4 pensjon.merinformasjon.ytelser.1.beloep.1.betalingshyppighetytelse")
        // 14.1.13.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P9000.InformationPersonBenefit.PersonBenefitInformation[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test benefit frequency 2-2", ytelseNr2Belop2?.annenbetalingshyppighetytelse, "14.1.13.5.1 pensjon.merinformasjon.ytelser.1.beloep.1.annenbetalingshyppighetytelse")
    }

    @Test
    fun occupationalActivity() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val merinformation = p9000.p9000Pensjon?.merinformasjon
        val aktivitetsinformasjon = merinformation?.aktivitetsinfo?.getOrNull(0)
        // 15.1.1 Activity type
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].ansattype -> P9000.InformationPersonActivity.DetailedInformationActivity[x].thePersonIsWasRunningActivityAs.value[x]
        assertEquals("01", aktivitetsinformasjon?.ansattype, "15.1.1 pensjon.merinformasjon.aktivitetsinfo.0.ansattype")
        // 15.1.2 Activity start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].startdato -> P9000.InformationPersonActivity.DetailedInformationActivity[x].startDateActivity
        assertEquals("2020-01-01", aktivitetsinformasjon?.startdato, "15.1.2 pensjon.merinformasjon.aktivitetsinfo.0.startdato")
        // 15.1.3 Activity end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].sluttdato -> P9000.InformationPersonActivity.DetailedInformationActivity[x].endDateActivity
        assertEquals("2020-02-01", aktivitetsinformasjon?.sluttdato, "15.1.3 pensjon.merinformasjon.aktivitetsinfo.0.sluttdato")
        // 15.1.4 Hours per week
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].antalltimer -> P9000.InformationPersonActivity.DetailedInformationActivity[x].workingHoursPerWeek
        assertEquals("30", aktivitetsinformasjon?.antalltimer, "15.1.4 pensjon.merinformasjon.aktivitetsinfo.0.antalltimer")
        // 15.1.5 Occupation
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].sted -> P9000.InformationPersonActivity.DetailedInformationActivity[x].occupation
        assertEquals("Test occupation 1-1", aktivitetsinformasjon?.sted, "15.1.5 pensjon.merinformasjon.aktivitetsinfo.0.sted")
        val aktivitetsinformasjon2 = merinformation?.aktivitetsinfo?.getOrNull(1)
        // 15.1.1 Activity type
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].ansattype -> P9000.InformationPersonActivity.DetailedInformationActivity[x].thePersonIsWasRunningActivityAs.value[x]
        assertEquals("02", aktivitetsinformasjon2?.ansattype, "15.1.1 pensjon.merinformasjon.aktivitetsinfo.1.ansattype")
        // 15.1.2 Activity start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].startdato -> P9000.InformationPersonActivity.DetailedInformationActivity[x].startDateActivity
        assertEquals("2020-01-02", aktivitetsinformasjon2?.startdato, "15.1.2 pensjon.merinformasjon.aktivitetsinfo.1.startdato")
        // 15.1.3 Activity end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].sluttdato -> P9000.InformationPersonActivity.DetailedInformationActivity[x].endDateActivity
        assertEquals("2020-02-02", aktivitetsinformasjon2?.sluttdato, "15.1.3 pensjon.merinformasjon.aktivitetsinfo.1.sluttdato")
        // 15.1.4 Hours per week
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].antalltimer -> P9000.InformationPersonActivity.DetailedInformationActivity[x].workingHoursPerWeek
        assertEquals("31", aktivitetsinformasjon2?.antalltimer, "15.1.4 pensjon.merinformasjon.aktivitetsinfo.1.antalltimer")
        // 15.1.5 Occupation
        // Mapping (ACL v4.5): $pensjon.merinformasjon.aktivitetsinfo[x].sted -> P9000.InformationPersonActivity.DetailedInformationActivity[x].occupation
        assertEquals("Test occupation 1-2", aktivitetsinformasjon2?.sted, "15.1.5 pensjon.merinformasjon.aktivitetsinfo.1.sted")
        val yrkesaktiv = merinformation?.yrkesaktiv?.getOrNull(0)
        // 15.2.1 Non-activity indicator
        // Mapping (ACL v4.5): $pensjon.merinformasjon.yrkesaktiv[x].yrkesstatus -> P9000.InformationPersonActivity.InformationNonActivity[x].thePersonIsWasNonActiveIndicator.value[x]
        assertEquals("0", yrkesaktiv?.yrkesstatus, "15.2.1 pensjon.merinformasjon.yrkesaktiv.0.yrkesstatus")
        // 15.2.2 Non-activity start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.yrkesaktiv[x].startdato -> P9000.InformationPersonActivity.InformationNonActivity[x].startDate
        assertEquals("2020-07-01", yrkesaktiv?.startdato, "15.2.2 pensjon.merinformasjon.yrkesaktiv.0.startdato")
        // 15.2.3 Non-activity end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.yrkesaktiv[x].sluttdato -> P9000.InformationPersonActivity.InformationNonActivity[x].endDate
        assertEquals("2020-08-01", yrkesaktiv?.sluttdato, "15.2.3 pensjon.merinformasjon.yrkesaktiv.0.sluttdato")
        val information1NonActivity2 = merinformation?.yrkesaktiv?.getOrNull(1)
        // 15.2.1 Non-activity indicator (entry 2)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.yrkesaktiv[x].yrkesstatus -> P9000.InformationPersonActivity.InformationNonActivity[x].thePersonIsWasNonActiveIndicator.value[x]
        assertEquals("1", information1NonActivity2?.yrkesstatus, "15.2.1 pensjon.merinformasjon.yrkesaktiv.1.yrkesstatus")
        // 15.2.2 Non-activity start (entry 2)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.yrkesaktiv[x].startdato -> P9000.InformationPersonActivity.InformationNonActivity[x].startDate
        assertEquals("2020-08-02", information1NonActivity2?.startdato, "15.2.2 pensjon.merinformasjon.yrkesaktiv.1.startdato")
        // 15.2.3 Non-activity end (entry 2)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.yrkesaktiv[x].sluttdato -> P9000.InformationPersonActivity.InformationNonActivity[x].endDate
        assertEquals("2020-09-02", information1NonActivity2?.sluttdato, "15.2.3 pensjon.merinformasjon.yrkesaktiv.1.sluttdato")
        val information1Unemployment1 = merinformation?.infoarbeidsledig?.getOrNull(0)
        // 15.3.2 Unemployment start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoarbeidsledig[x].startdato -> P9000.InformationPersonActivity.InformationUnemployment[x].startDate
        assertEquals("2020-03-01", information1Unemployment1?.startdato, "15.3.2 pensjon.merinformasjon.infoarbeidsledig.0.startdato")
        // 15.3.3 Unemployment end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoarbeidsledig[x].sluttdato -> P9000.InformationPersonActivity.InformationUnemployment[x].endDate
        assertEquals("2020-04-01", information1Unemployment1?.sluttdato, "15.3.3 pensjon.merinformasjon.infoarbeidsledig.0.sluttdato")
        val infoArbeidsledig2 = merinformation?.infoarbeidsledig?.getOrNull(1)
        // 15.3.2 Unemployment start (entry 2)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoarbeidsledig[x].startdato -> P9000.InformationPersonActivity.InformationUnemployment[x].startDate
        assertEquals("2020-04-02", infoArbeidsledig2?.startdato, "15.3.2 pensjon.merinformasjon.infoarbeidsledig.1.startdato")
        // 15.3.3 Unemployment end (entry 2)
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoarbeidsledig[x].sluttdato -> P9000.InformationPersonActivity.InformationUnemployment[x].endDate
        assertEquals("2020-05-02", infoArbeidsledig2?.sluttdato, "15.3.3 pensjon.merinformasjon.infoarbeidsledig.1.sluttdato")
        // 15.3.1 Unemployment indicator (entry 1)
        // Mapping (ACL v4.5): $pensjon.infoarbeidsledig[x].arbeidsledig -> P9000.InformationPersonActivity.InformationUnemployment[x].thePersonIsWasUnemployedIndicator.value[x]
        assertEquals("01", information1Unemployment1?.arbeidsledig, "15.3.1 pensjon.merinformasjon.infoarbeidsledig.0.arbeidsledig")
        // 15.3.1 Unemployment indicator (entry 2)
        // Mapping (ACL v4.5): $pensjon.infoarbeidsledig[x].arbeidsledig -> P9000.InformationPersonActivity.InformationUnemployment[x].thePersonIsWasUnemployedIndicator.value[x]
        assertEquals("02", infoArbeidsledig2?.arbeidsledig, "15.3.1 pensjon.merinformasjon.infoarbeidsledig.1.arbeidsledig")
    }

    @Test
    fun additionalActivityInformation() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        // 15.4 Additional activity and non-activity information
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.yrkesaktivitet.ingenaktivtetinformasjon -> P9000.InformationPersonActivity.additionalInformationActivityNonActivity
        assertEquals("Test activity and non-activity details", p9000.p9000Pensjon?.tilleggsinformasjon?.yrkesaktivitet?.ingenaktivtetinformasjon, "15.4 pensjon.tilleggsinformasjon.yrkesaktivitet.ingenaktivtetinformasjon")
    }

    @Test
    fun income() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val merinformasjon = p9000.p9000Pensjon?.merinformasjon
        val merinformasjon1InfoInntekt1 = merinformasjon?.infoinntekt?.getOrNull(0)
        // 16.1.1 Income status
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektellerikke -> P9000.InformationPersonIncome.PersonIncome[x].thePerson.value[x]
        assertEquals("01", merinformasjon1InfoInntekt1?.inntektellerikke, "16.1.1 pensjon.merinformasjon.infoinntekt.0.inntektellerikke")
        // 16.1.2 Income source
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektskilde -> P9000.InformationPersonIncome.PersonIncome[x].sourceIncome
        assertEquals("Test income source 1-1", merinformasjon1InfoInntekt1?.inntektskilde, "16.1.2 pensjon.merinformasjon.infoinntekt.0.inntektskilde")
        // 16.1.3 Income start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].startdatomottakinntekt -> P9000.InformationPersonIncome.PersonIncome[x].startDateReceivingIncome
        assertEquals("2020-09-01", merinformasjon1InfoInntekt1?.startdatomottakinntekt, "16.1.3 pensjon.merinformasjon.infoinntekt.0.startdatomottakinntekt")
        // 16.1.4 Income end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].sluttdatomottakinntekt -> P9000.InformationPersonIncome.PersonIncome[x].endDateReceivingIncome
        assertEquals("2020-12-01", merinformasjon1InfoInntekt1?.sluttdatomottakinntekt, "16.1.4 pensjon.merinformasjon.infoinntekt.0.sluttdatomottakinntekt")
        // 16.1.6 Income supplements
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].tilleggsytelserutbetalingeritilleggtilinntekt -> P9000.InformationPersonIncome.PersonIncome[x].supplementsAdditionalFrequentPaymentsIncome
        assertEquals("Test income supplement 1-1", merinformasjon1InfoInntekt1?.tilleggsytelserutbetalingeritilleggtilinntekt, "16.1.6 pensjon.merinformasjon.infoinntekt.0.tilleggsytelserutbetalingeritilleggtilinntekt")
        // 16.1.7 Income details
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].ytterligereinfoinntekt -> P9000.InformationPersonIncome.PersonIncome[x].additionalInformationIncome
        assertEquals("Test income details 1-1", merinformasjon1InfoInntekt1?.ytterligereinfoinntekt, "16.1.7 pensjon.merinformasjon.infoinntekt.0.ytterligereinfoinntekt")
        val merinformasjon1InfoInntektsBelop1 = merinformasjon1InfoInntekt1?.inntektbelop?.getOrNull(0)
        // 16.1.5.1 Income amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloep -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amount
        assertEquals("1000.50", merinformasjon1InfoInntektsBelop1?.beloep, "16.1.5.1 pensjon.merinformasjon.infoinntekt.0.inntektbelop.0.beloep")
        // 16.1.5.2 Currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].valuta -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].currency.value[x]
        assertEquals("NOK", merinformasjon1InfoInntektsBelop1?.valuta, "16.1.5.2 pensjon.merinformasjon.infoinntekt.0.inntektbelop.0.valuta")
        // 16.1.5.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloepgjeldendesiden -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amountEffectiveSince
        assertEquals("2020-10-01", merinformasjon1InfoInntektsBelop1?.beloepgjeldendesiden, "16.1.5.3 pensjon.merinformasjon.infoinntekt.0.inntektbelop.0.beloepgjeldendesiden")
        // 16.1.5.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].betalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].paymentFrequency.value[x]
        assertEquals("99", merinformasjon1InfoInntektsBelop1?.betalingshyppighet, "16.1.5.4 pensjon.merinformasjon.infoinntekt.0.inntektbelop.0.betalingshyppighet")
        // 16.1.5.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].annenbetalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test other frequency 1-1-1", merinformasjon1InfoInntektsBelop1?.annenbetalingshyppighet, "16.1.5.5.1 pensjon.merinformasjon.infoinntekt.0.inntektbelop.0.annenbetalingshyppighet")
        val information1Income1Amount2 = merinformasjon1InfoInntekt1?.inntektbelop?.getOrNull(1)
        // 16.1.5.1 Income amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloep -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amount
        assertEquals("1001.50", information1Income1Amount2?.beloep, "16.1.5.1 pensjon.merinformasjon.infoinntekt.0.inntektbelop.1.beloep")
        // 16.1.5.2 Currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].valuta -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].currency.value[x]
        assertEquals("SEK", information1Income1Amount2?.valuta, "16.1.5.2 pensjon.merinformasjon.infoinntekt.0.inntektbelop.1.valuta")
        // 16.1.5.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloepgjeldendesiden -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amountEffectiveSince
        assertEquals("2020-10-02", information1Income1Amount2?.beloepgjeldendesiden, "16.1.5.3 pensjon.merinformasjon.infoinntekt.0.inntektbelop.1.beloepgjeldendesiden")
        // 16.1.5.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].betalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].paymentFrequency.value[x]
        assertEquals("99", information1Income1Amount2?.betalingshyppighet, "16.1.5.4 pensjon.merinformasjon.infoinntekt.0.inntektbelop.1.betalingshyppighet")
        // 16.1.5.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].annenbetalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test other frequency 1-1-2", information1Income1Amount2?.annenbetalingshyppighet, "16.1.5.5.1 pensjon.merinformasjon.infoinntekt.0.inntektbelop.1.annenbetalingshyppighet")
        val information1Income2 = merinformasjon?.infoinntekt?.getOrNull(1)
        // 16.1.1 Income status
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektellerikke -> P9000.InformationPersonIncome.PersonIncome[x].thePerson.value[x]
        assertEquals("03", information1Income2?.inntektellerikke, "16.1.1 pensjon.merinformasjon.infoinntekt.1.inntektellerikke")
        // 16.1.2 Income source
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektskilde -> P9000.InformationPersonIncome.PersonIncome[x].sourceIncome
        assertEquals("Test income source 1-2", information1Income2?.inntektskilde, "16.1.2 pensjon.merinformasjon.infoinntekt.1.inntektskilde")
        // 16.1.3 Income start
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].startdatomottakinntekt -> P9000.InformationPersonIncome.PersonIncome[x].startDateReceivingIncome
        assertEquals("2020-09-02", information1Income2?.startdatomottakinntekt, "16.1.3 pensjon.merinformasjon.infoinntekt.1.startdatomottakinntekt")
        // 16.1.4 Income end
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].sluttdatomottakinntekt -> P9000.InformationPersonIncome.PersonIncome[x].endDateReceivingIncome
        assertEquals("2020-12-02", information1Income2?.sluttdatomottakinntekt, "16.1.4 pensjon.merinformasjon.infoinntekt.1.sluttdatomottakinntekt")
        // 16.1.6 Income supplements
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].tilleggsytelserutbetalingeritilleggtilinntekt -> P9000.InformationPersonIncome.PersonIncome[x].supplementsAdditionalFrequentPaymentsIncome
        assertEquals("Test income supplement 1-2", information1Income2?.tilleggsytelserutbetalingeritilleggtilinntekt, "16.1.6 pensjon.merinformasjon.infoinntekt.1.tilleggsytelserutbetalingeritilleggtilinntekt")
        // 16.1.7 Income details
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].ytterligereinfoinntekt -> P9000.InformationPersonIncome.PersonIncome[x].additionalInformationIncome
        assertEquals("Test income details 1-2", information1Income2?.ytterligereinfoinntekt, "16.1.7 pensjon.merinformasjon.infoinntekt.1.ytterligereinfoinntekt")
        val information1Income2Amount1 = information1Income2?.inntektbelop?.getOrNull(0)
        // 16.1.5.1 Income amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloep -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amount
        assertEquals("1010.50", information1Income2Amount1?.beloep, "16.1.5.1 pensjon.merinformasjon.infoinntekt.1.inntektbelop.0.beloep")
        // 16.1.5.2 Currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].valuta -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].currency.value[x]
        assertEquals("NOK", information1Income2Amount1?.valuta, "16.1.5.2 pensjon.merinformasjon.infoinntekt.1.inntektbelop.0.valuta")
        // 16.1.5.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloepgjeldendesiden -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amountEffectiveSince
        assertEquals("2020-10-03", information1Income2Amount1?.beloepgjeldendesiden, "16.1.5.3 pensjon.merinformasjon.infoinntekt.1.inntektbelop.0.beloepgjeldendesiden")
        // 16.1.5.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].betalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].paymentFrequency.value[x]
        assertEquals("99", information1Income2Amount1?.betalingshyppighet, "16.1.5.4 pensjon.merinformasjon.infoinntekt.1.inntektbelop.0.betalingshyppighet")
        // 16.1.5.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].annenbetalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test other frequency 1-2-1", information1Income2Amount1?.annenbetalingshyppighet, "16.1.5.5.1 pensjon.merinformasjon.infoinntekt.1.inntektbelop.0.annenbetalingshyppighet")
        val information1Income2Amount2 = information1Income2?.inntektbelop?.getOrNull(1)
        // 16.1.5.1 Income amount
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloep -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amount
        assertEquals("1011.50", information1Income2Amount2?.beloep, "16.1.5.1 pensjon.merinformasjon.infoinntekt.1.inntektbelop.1.beloep")
        // 16.1.5.2 Currency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].valuta -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].currency.value[x]
        assertEquals("SEK", information1Income2Amount2?.valuta, "16.1.5.2 pensjon.merinformasjon.infoinntekt.1.inntektbelop.1.valuta")
        // 16.1.5.3 Amount effective since
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].beloepgjeldendesiden -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].amountEffectiveSince
        assertEquals("2020-10-04", information1Income2Amount2?.beloepgjeldendesiden, "16.1.5.3 pensjon.merinformasjon.infoinntekt.1.inntektbelop.1.beloepgjeldendesiden")
        // 16.1.5.4 Payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].betalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].paymentFrequency.value[x]
        assertEquals("99", information1Income2Amount2?.betalingshyppighet, "16.1.5.4 pensjon.merinformasjon.infoinntekt.1.inntektbelop.1.betalingshyppighet")
        // 16.1.5.5.1 Other payment frequency
        // Mapping (ACL v4.5): $pensjon.merinformasjon.infoinntekt[x].inntektbelop[x].annenbetalingshyppighet -> P9000.InformationPersonIncome.PersonIncome[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("Test other frequency 1-2-2", information1Income2Amount2?.annenbetalingshyppighet, "16.1.5.5.1 pensjon.merinformasjon.infoinntekt.1.inntektbelop.1.annenbetalingshyppighet")
    }

    @Test
    fun otherInformation() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        // 17.1 Other requested information
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.anneninformation -> P9000.ReplyRequestForOtherInformation.otherInformation
        assertEquals("Test other requested information", p9000.p9000Pensjon?.tilleggsinformasjon?.anneninformation, "17.1 pensjon.tilleggsinformasjon.anneninformation")
    }

    @Test
    fun informationConfirmation() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val confirmations = p9000.p9000Pensjon?.tilleggsinformasjon?.bekreftelseSed
        val confirmation1 = confirmations?.getOrNull(0)
        // 18.1.1 P8000 reference point
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].p8000ref -> P9000.ConfirmationInformation.ConfirmationInformation[x].theInformationInP8000StatedInPoint
        assertEquals("6.1", confirmation1?.p8000ref, "18.1.1 pensjon.tilleggsinformasjon.bekreftelseSed.0.p8000ref")
        // 18.1.2 Confirmation indicator
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].info -> P9000.ConfirmationInformation.ConfirmationInformation[x].theInformation.value[x]
        assertEquals("01", confirmation1?.info, "18.1.2 pensjon.tilleggsinformasjon.bekreftelseSed.0.info")
        // 18.1.3 Reason for not confirming
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].aarsak -> P9000.ConfirmationInformation.ConfirmationInformation[x].reasonWhyDoNotConfirmInformationDifferences
        assertEquals("Test confirmation reason 1", confirmation1?.aarsak, "18.1.3 pensjon.tilleggsinformasjon.bekreftelseSed.0.aarsak")
        // 18.1.4 Different information in our possession
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].grunn -> P9000.ConfirmationInformation.ConfirmationInformation[x].differentInformationInOurPossessionIs
        assertEquals("Test different information 1", confirmation1?.grunn, "18.1.4 pensjon.tilleggsinformasjon.bekreftelseSed.0.grunn")
        val confirmation2 = confirmations?.getOrNull(1)
        // 18.1.1 P8000 reference point
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].p8000ref -> P9000.ConfirmationInformation.ConfirmationInformation[x].theInformationInP8000StatedInPoint
        assertEquals("7.1", confirmation2?.p8000ref, "18.1.1 pensjon.tilleggsinformasjon.bekreftelseSed.1.p8000ref")
        // 18.1.2 Confirmation indicator
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].info -> P9000.ConfirmationInformation.ConfirmationInformation[x].theInformation.value[x]
        assertEquals("02", confirmation2?.info, "18.1.2 pensjon.tilleggsinformasjon.bekreftelseSed.1.info")
        // 18.1.3 Reason for not confirming
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].aarsak -> P9000.ConfirmationInformation.ConfirmationInformation[x].reasonWhyDoNotConfirmInformationDifferences
        assertEquals("Test confirmation reason 2", confirmation2?.aarsak, "18.1.3 pensjon.tilleggsinformasjon.bekreftelseSed.1.aarsak")
        // 18.1.4 Different information in our possession
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.bekreftelseSed[x].grunn -> P9000.ConfirmationInformation.ConfirmationInformation[x].differentInformationInOurPossessionIs
        assertEquals("Test different information 2", confirmation2?.grunn, "18.1.4 pensjon.tilleggsinformasjon.bekreftelseSed.1.grunn")
    }

    @Test
    fun declaration() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val information1 = p9000.p9000Pensjon?.merinformasjon
        val information1Declaration = information1?.egenerklaering
        // 19.1 Signed declaration
        // Mapping (ACL v4.5): $pensjon.merinformasjon.egenerklaering.egenerklaering -> P9000.StatementPerson.thePersonMadeSignedAtOurInstitutionFollowingStatement
        assertEquals("Test signed declaration 1", information1Declaration?.egenerklaering, "19.1 pensjon.merinformasjon.egenerklaering.egenerklaering")
        // 19.2 Declaration date
        // Mapping (ACL v4.5): $pensjon.merinformasjon.egenerklaering.dato -> P9000.StatementPerson.dateStatement
        assertEquals("2020-05-15", information1Declaration?.dato, "19.2 pensjon.merinformasjon.egenerklaering.dato")
    }

    @Test
    fun negativeReply() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        val negativtSvar = p9000.p9000Pensjon?.tilleggsinformasjon?.negativtsvar
        // 20.1.1 Information that cannot be sent
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.informasjon -> P9000.NegativeReply.WeRegretButAreUnableSendYou.followingInformation
        assertEquals("Test unavailable information", negativtSvar?.informasjon, "20.1.1 pensjon.tilleggsinformasjon.negativtsvar.informasjon")
        // 20.1.2 SED that cannot be sent (v4.4 field from the PDF)
        // ACL v4.4 only: $pensjon.tilleggsinformasjon.negativtsvar.sed -> P9000.NegativeReply.WeRegretButAreUnableSendYou.followingSED
        assertEquals("P4000", negativtSvar?.sed, "20.1.2 pensjon.tilleggsinformasjon.negativtsvar.sed")
        // 20.1.3 Document that cannot be sent
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.dokument -> P9000.NegativeReply.WeRegretButAreUnableSendYou.followingDocument
        assertEquals("Test unavailable document", negativtSvar?.dokument, "20.1.3 pensjon.tilleggsinformasjon.negativtsvar.dokument")
        // 20.1.4 Confirmation that cannot be sent
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.bekreftelseinformasjon -> P9000.NegativeReply.WeRegretButAreUnableSendYou.followingConfirmationInformation
        assertEquals("Test unavailable confirmation", negativtSvar?.bekreftelseinformasjon, "20.1.4 pensjon.tilleggsinformasjon.negativtsvar.bekreftelseinformasjon")
        // 20.2 Reason
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.aarsakgrunn -> P9000.NegativeReply.reasons.value[x]
        assertEquals("99", negativtSvar?.aarsakgrunn, "20.2 pensjon.tilleggsinformasjon.negativtsvar.aarsakgrunn")
        // 20.3.1 Other reason
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.aarsakannen -> P9000.NegativeReply.PleaseFillInFollowingIfReasonsOtherReasons.otherReasons
        assertEquals("Test other reason", negativtSvar?.aarsakannen, "20.3.1 pensjon.tilleggsinformasjon.negativtsvar.aarsakannen")
        // 20.4 Reason for not sending a SED
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.aarsakikkesendsed -> P9000.NegativeReply.reasonsForNotSendingSED
        assertEquals("Test reason for unavailable SED", negativtSvar?.aarsakikkesendsed, "20.4 pensjon.tilleggsinformasjon.negativtsvar.aarsakikkesendsed")
        // 20.1.2.1 SED unable to be sent in v4.5
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.SedsProvidedInMultipleType -> P9000.NegativeReply.WeRegretButAreUnableSendYou.FollowingSED[x].sEDUnableToBeSent.value[x]
        assertEquals("06", negativtSvar?.sedsProvidedInMultipleType, "20.1.2.1 pensjon.tilleggsinformasjon.negativtsvar.SedsProvidedInMultipleType")
        // 20.1.2.2.1 Other SEDs unable to be sent in v4.5
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.negativtsvar.annensed -> P9000.NegativeReply.WeRegretButAreUnableSendYou.FollowingSED[x].FillInIfSEDsUnableToBeSentOther.otherSEDsUnableToBeSent
        assertEquals("Test additional unavailable SED", negativtSvar?.annensed, "20.1.2.2.1 pensjon.tilleggsinformasjon.negativtsvar.annensed")
    }

    // v4.5 negative-reply additions use versioned ACL metadata, not the supplied PDF.

    @Test
    fun additionalInformation() {
        val p9000 = mapJsonToAny<P9000>(p9000Json())
        // 21.1 Additional information
        // Mapping (ACL v4.5): $pensjon.tilleggsinformasjon.yrkesaktivitet.tilleggsopplysning -> P9000.AdditionalInformation.additionalInformation
        assertEquals("Test additional reply details", p9000.p9000Pensjon?.tilleggsinformasjon?.yrkesaktivitet?.tilleggsopplysning, "21.1 pensjon.tilleggsinformasjon.yrkesaktivitet.tilleggsopplysning")
    }

    @Test
    fun supportedFieldsSurviveJsonRoundTrip() {
        val expected = p9000Json()
        val p9000 = mapJsonToAny<P9000>(expected)

        // 1-21 All populated supported fields, including collection sizes and order.
        JSONAssert.assertEquals(expected, p9000.toJsonSkipEmpty(), true)
    }
}
