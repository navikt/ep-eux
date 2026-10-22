package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

/**
 * Field numbers come from P2100.pdf (14 pages, printed 27 May 2019).
 * All fixture values are synthetic. This checks JSON <-> P2100, not the runtime ACL.
 * The v4.4 and v4.5 ACL templates were compared and are identical.
 *
 * Not supported by the current P2100 model: 7.2 (entitlement to sickness benefits).
 *
 * Known template/model differences:
 * - 7.1.8: templates read sluttdatoUtbetaling, but the model's entitlement end date
 *   is sluttdatorettilutbetaling. Distinct dates below prevent conflating it with 7.1.6.
 * - 4.1/4.2: templates use dodsDetalj; the model uses dodsdetalj.
 * Non-SEPA fields in the templates have no visible numbers in this PDF and are not covered.
 */
class P2100HelMappetTest {
    private fun p2100Json(): String =
        requireNotNull(javaClass.getResource("/sed/P2100-HelMappet-NAV.json")).readText()

    @Test
    fun localCaseNumbers() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val firstCase = p2100.nav?.eessisak?.getOrNull(0)
        val secondCase = p2100.nav?.eessisak?.getOrNull(1)

        // 1.1.1 Country (case 1)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].land -> P2100.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("NO", firstCase?.land, "1.1.1 nav.eessisak.0.land")
        // 1.1.2 Case number (case 1)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].saksnummer -> P2100.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("TEST-CASE-1", firstCase?.saksnummer, "1.1.2 nav.eessisak.0.saksnummer")
        // 1.1.3.1 Institution ID (case 1)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].institusjonsid -> P2100.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("NO:TEST-CASE-1", firstCase?.institusjonsid, "1.1.3.1 nav.eessisak.0.institusjonsid")
        // 1.1.3.2 Institution name (case 1)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].institusjonsnavn -> P2100.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("Test case institution 1", firstCase?.institusjonsnavn, "1.1.3.2 nav.eessisak.0.institusjonsnavn")
        // 1.1.1 Country (case 2)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].land -> P2100.LocalCaseNumbers.LocalCaseNumber[x].country.value[x]
        assertEquals("SE", secondCase?.land, "1.1.1 nav.eessisak.1.land")
        // 1.1.2 Case number (case 2)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].saksnummer -> P2100.LocalCaseNumbers.LocalCaseNumber[x].caseNumber
        assertEquals("TEST-CASE-2", secondCase?.saksnummer, "1.1.2 nav.eessisak.1.saksnummer")
        // 1.1.3.1 Institution ID (case 2)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].institusjonsid -> P2100.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionID
        assertEquals("NO:TEST-CASE-2", secondCase?.institusjonsid, "1.1.3.1 nav.eessisak.1.institusjonsid")
        // 1.1.3.2 Institution name (case 2)
        // Mapping (ACL v4.4/v4.5): $nav.eessisak[x].institusjonsnavn -> P2100.LocalCaseNumbers.LocalCaseNumber[x].Institution.institutionName
        assertEquals("Test case institution 2", secondCase?.institusjonsnavn, "1.1.3.2 nav.eessisak.1.institusjonsnavn")
    }

    @Test
    fun insuredPerson() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val deathDetails = p2100.nav?.bruker?.person?.dodsdetalj
        val bruker = p2100.nav?.bruker
        val person = bruker?.person
        val firstPin = person?.pin?.getOrNull(0)
        val secondPin = person?.pin?.getOrNull(1)
        val birthplace = person?.foedested
        val address = bruker?.adresse

        // 2.1.1 Family name
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.etternavn -> P2100.InsuredPerson.PersonIdentification.familyName
        assertEquals("2.1 Test family name", person?.etternavn, "2.1.1 nav.bruker.person.etternavn")
        // 2.1.2 Forename
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.fornavn -> P2100.InsuredPerson.PersonIdentification.forename
        assertEquals("2.1 Test forename", person?.fornavn, "2.1.2 nav.bruker.person.fornavn")
        // 2.1.3 Date of birth
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.foedselsdato -> P2100.InsuredPerson.PersonIdentification.dateBirth
        assertEquals("1970-02-03", person?.foedselsdato, "2.1.3 nav.bruker.person.foedselsdato")
        // 2.1.4 Sex
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.kjoenn -> P2100.InsuredPerson.PersonIdentification.sex.value[x]
        assertEquals("K", person?.kjoenn, "2.1.4 nav.bruker.person.kjoenn")
        // 2.1.5 Family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.etternavnvedfoedsel -> P2100.InsuredPerson.PersonIdentification.familyNameAtBirth
        assertEquals("2.1 Test birth family name", person?.etternavnvedfoedsel, "2.1.5 nav.bruker.person.etternavnvedfoedsel")
        // 2.1.6 Forename at birth
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.fornavnvedfoedsel -> P2100.InsuredPerson.PersonIdentification.forenameAtBirth
        assertEquals("2.1 Test birth forename", person?.fornavnvedfoedsel, "2.1.6 nav.bruker.person.fornavnvedfoedsel")
        // 2.1.7.1.1 PIN country (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].land -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", firstPin?.land, "2.1.7.1.1 nav.bruker.person.pin.0.land")
        // 2.1.7.1.2 PIN (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].identifikator -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-2.1-1", firstPin?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.0.identifikator")
        // 2.1.7.1.3 Sector (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].sektor -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", firstPin?.sektor, "2.1.7.1.3 nav.bruker.person.pin.0.sektor")
        // 2.1.7.1.4.1 Institution ID (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].institusjon.institusjonsid -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-2.1-1", firstPin?.institusjon?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.0.institusjon.institusjonsid")
        // 2.1.7.1.4.2 Institution name (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].institusjon.institusjonsnavn -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 2.1-1", firstPin?.institusjon?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.0.institusjon.institusjonsnavn")
        // 2.1.7.1.1 PIN country (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].land -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", secondPin?.land, "2.1.7.1.1 nav.bruker.person.pin.1.land")
        // 2.1.7.1.2 PIN (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].identifikator -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-2.1-2", secondPin?.identifikator, "2.1.7.1.2 nav.bruker.person.pin.1.identifikator")
        // 2.1.7.1.3 Sector (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].sektor -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", secondPin?.sektor, "2.1.7.1.3 nav.bruker.person.pin.1.sektor")
        // 2.1.7.1.4.1 Institution ID (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].institusjon.institusjonsid -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-2.1-2", secondPin?.institusjon?.institusjonsid, "2.1.7.1.4.1 nav.bruker.person.pin.1.institusjon.institusjonsid")
        // 2.1.7.1.4.2 Institution name (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.pin[x].institusjon.institusjonsnavn -> P2100.InsuredPerson.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 2.1-2", secondPin?.institusjon?.institusjonsnavn, "2.1.7.1.4.2 nav.bruker.person.pin.1.institusjon.institusjonsnavn")
        // 2.1.8.1.1 Place of birth: town
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.foedested.by -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("2.1 Birth town", birthplace?.by, "2.1.8.1.1 nav.bruker.person.foedested.by")
        // 2.1.8.1.2 Place of birth: region
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.foedested.region -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("2.1 Birth region", birthplace?.region, "2.1.8.1.2 nav.bruker.person.foedested.region")
        // 2.1.8.1.3 Place of birth: country
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.foedested.land -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("DK", birthplace?.land, "2.1.8.1.3 nav.bruker.person.foedested.land")
        // 2.1.8.2 Father's family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.bruker.far.person.etternavnvedfoedsel -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("2.1 Father birth name", bruker?.far?.person?.etternavnvedfoedsel, "2.1.8.2 nav.bruker.far.person.etternavnvedfoedsel")
        // 2.1.8.3 Father's forename
        // Mapping (ACL v4.4/v4.5): $nav.bruker.far.person.fornavn -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("2.1 Father forename", bruker?.far?.person?.fornavn, "2.1.8.3 nav.bruker.far.person.fornavn")
        // 2.1.8.4 Mother's family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.bruker.mor.person.etternavnvedfoedsel -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("2.1 Mother birth name", bruker?.mor?.person?.etternavnvedfoedsel, "2.1.8.4 nav.bruker.mor.person.etternavnvedfoedsel")
        // 2.1.8.5 Mother's forename
        // Mapping (ACL v4.4/v4.5): $nav.bruker.mor.person.fornavn -> P2100.InsuredPerson.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("2.1 Mother forename", bruker?.mor?.person?.fornavn, "2.1.8.5 nav.bruker.mor.person.fornavn")
        // 2.2.1.1 Nationality
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.statsborgerskap[x].land -> P2100.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("FI", person?.statsborgerskap?.getOrNull(0)?.land, "2.2.1.1 nav.bruker.person.statsborgerskap.0.land")
        // 2.2.1.2 Previous family name
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.tidligereetternavn -> P2100.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("2.2.1 Previous family name", person?.tidligereetternavn, "2.2.1.2 nav.bruker.person.tidligereetternavn")
        // 2.2.1.3 Previous forename
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.tidligerefornavn -> P2100.InsuredPerson.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("2.2.1 Previous forename", person?.tidligerefornavn, "2.2.1.3 nav.bruker.person.tidligerefornavn")
        // 2.2.2.1 Street
        // Mapping (ACL v4.4/v4.5): $nav.bruker.adresse.gate -> P2100.InsuredPerson.AdditionalInformationPerson.Address.street
        assertEquals("2.2.2 Test street", address?.gate, "2.2.2.1 nav.bruker.adresse.gate")
        // 2.2.2.2 Building name
        // Mapping (ACL v4.4/v4.5): $nav.bruker.adresse.bygning -> P2100.InsuredPerson.AdditionalInformationPerson.Address.buildingName
        assertEquals("2.2.2 Test building", address?.bygning, "2.2.2.2 nav.bruker.adresse.bygning")
        // 2.2.2.3 Town
        // Mapping (ACL v4.4/v4.5): $nav.bruker.adresse.by -> P2100.InsuredPerson.AdditionalInformationPerson.Address.town
        assertEquals("2.2.2 Test town", address?.by, "2.2.2.3 nav.bruker.adresse.by")
        // 2.2.2.4 Postal code
        // Mapping (ACL v4.4/v4.5): $nav.bruker.adresse.postnummer -> P2100.InsuredPerson.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", address?.postnummer, "2.2.2.4 nav.bruker.adresse.postnummer")
        // 2.2.2.5 Region
        // Mapping (ACL v4.4/v4.5): $nav.bruker.adresse.region -> P2100.InsuredPerson.AdditionalInformationPerson.Address.region
        assertEquals("2.2.2 Test region", address?.region, "2.2.2.5 nav.bruker.adresse.region")
        // 2.2.2.6 Country
        // Mapping (ACL v4.4/v4.5): $nav.bruker.adresse.land -> P2100.InsuredPerson.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", address?.land, "2.2.2.6 nav.bruker.adresse.land")

    }

    @Test
    fun insuredPersonPensionDetails() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val pension = p2100.pensjon?.gjenlevende?.person?.relasjontilavdod?.pensjondetalj?.firstOrNull()
        val institution = pension?.institusjon

        // 3.1 Pension recipient at date of death
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.pensjondetalj[x].mottattPensjonvedDod -> P2100.InsuredPersonPensionDetails.pensionRecipientAtDateDeathIndicator.value[x]
        assertEquals("1", pension?.mottattPensjonvedDod, "3.1 Pension recipient at date of death")
        // 3.2.1 Pension type
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.pensjondetalj[x].mottattPensjonType -> P2100.InsuredPersonPensionDetails.PensionReceived[x].pension.value[x]
        assertEquals("01", pension?.mottattPensjonType, "3.2.1 Pension type")
        // 3.2.2.1.1 Institution ID
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.pensjondetalj[x].institusjon.institusjonsid -> P2100.InsuredPersonPensionDetails.PensionReceived[x].Institution.Institution.institutionID
        assertEquals("NO:TEST-PENSION", institution?.institusjonsid, "3.2.2.1.1 Institution ID")
        // 3.2.2.1.2 Institution name
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.pensjondetalj[x].institusjon.institusjonsnavn -> P2100.InsuredPersonPensionDetails.PensionReceived[x].Institution.Institution.institutionName
        assertEquals("Test pension institution", institution?.institusjonsnavn, "3.2.2.1.2 Institution name")
        // 3.2.2.2 Case number
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.pensjondetalj[x].institusjon.saksnummer -> P2100.InsuredPersonPensionDetails.PensionReceived[x].Institution.caseNumber
        assertEquals("TEST-PENSION-CASE", institution?.saksnummer, "3.2.2.2 Case number")
        // 3.2.3 Start date of pension right
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.pensjondetalj[x].startDatoPensjonrettighet -> P2100.InsuredPersonPensionDetails.PensionReceived[x].startDatePensionRight
        assertEquals("2019-04-01", pension?.startDatoPensjonrettighet, "3.2.3 Start date of pension right")
    }

    @Disabled
    @Test
    fun kap4() {

        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val dodsdetalj = p2100.pensjon?.gjenlevende?.person?.dodsdetalj
        println("Rezz: ${p2100.pensjon?.gjenlevende?.person?.toJson()}")

        // 4.1 sted
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.dodsDetalj.sted -> P2100.InsuredPersonDeathDetails.placeDeath
        assertEquals("4.1 Test place of death", dodsdetalj?.sted, "4.1 nav.bruker.person.dodsdetalj.sted")
        // 4.2 dato
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.dodsDetalj.dato -> P2100.InsuredPersonDeathDetails.dateDeath
        assertEquals("2024-01-01", dodsdetalj?.dato, "4.2 nav.bruker.person.dodsdetalj.dato")
        // 4.3.1 arsak
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.dodsdetalj.arsaker[x].arsak -> P2100.InsuredPersonDeathDetails.CauseDeath[x].causeDeath.value[x]
        assertEquals("99", dodsdetalj?.arsaker?.getOrNull(0)?.arsak, "4.3.1 nav.bruker.person.dodsdetalj.arsaker.0.arsak")
        // 4.3.2.1 annenArsak
        // Mapping (ACL v4.4/v4.5): $nav.bruker.person.dodsdetalj.arsaker[x].annenArsak -> P2100.InsuredPersonDeathDetails.CauseDeath[x].PleaseFillInFollowingIfCauseDeathOther.describeOtherCauseDeath
        assertEquals("4.3.2.1 Test other cause", dodsdetalj?.arsaker?.getOrNull(0)?.annenArsak, "4.3.2.1 nav.bruker.person.dodsdetalj.arsaker.0.annenArsak")
    }


    @Test
    fun claimant() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val relationship = p2100.pensjon?.gjenlevende?.person?.relasjontilavdod
        val newPartner = p2100.pensjon?.gjenlevende?.person?.nyttEkteskapPartnerskapEtterForsikredeDod
        val employment = p2100.pensjon?.bruker?.arbeidsforhold?.getOrNull(0)
        val income = employment?.inntekt?.getOrNull(0)
        val claimant = p2100.pensjon?.gjenlevende
        val person = claimant?.person
        val firstPin = person?.pin?.getOrNull(0)
        val secondPin = person?.pin?.getOrNull(1)
        val birthplace = person?.foedested
        val address = claimant?.adresse
        val contact = person?.kontakt

        // 5.1.1 Family name
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.etternavn -> P2100.Claimant.PersonIdentification.familyName
        assertEquals("5.1 Test family name", person?.etternavn, "5.1.1 pensjon.gjenlevende.person.etternavn")
        // 5.1.2 Forename
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.fornavn -> P2100.Claimant.PersonIdentification.forename
        assertEquals("5.1 Test forename", person?.fornavn, "5.1.2 pensjon.gjenlevende.person.fornavn")
        // 5.1.3 Date of birth
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.foedselsdato -> P2100.Claimant.PersonIdentification.dateBirth
        assertEquals("1970-02-03", person?.foedselsdato, "5.1.3 pensjon.gjenlevende.person.foedselsdato")
        // 5.1.4 Sex
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.kjoenn -> P2100.Claimant.PersonIdentification.sex.value[x]
        assertEquals("K", person?.kjoenn, "5.1.4 pensjon.gjenlevende.person.kjoenn")
        // 5.1.5 Family name at birth
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.etternavnvedfoedsel -> P2100.Claimant.PersonIdentification.familyNameAtBirth
        assertEquals("5.1 Test birth family name", person?.etternavnvedfoedsel, "5.1.5 pensjon.gjenlevende.person.etternavnvedfoedsel")
        // 5.1.6 Forename at birth
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.fornavnvedfoedsel -> P2100.Claimant.PersonIdentification.forenameAtBirth
        assertEquals("5.1 Test birth forename", person?.fornavnvedfoedsel, "5.1.6 pensjon.gjenlevende.person.fornavnvedfoedsel")
        // 5.1.7.1.1 PIN country (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].land -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", firstPin?.land, "5.1.7.1.1 pensjon.gjenlevende.person.pin.0.land")
        // 5.1.7.1.2 PIN (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].identifikator -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-5.1-1", firstPin?.identifikator, "5.1.7.1.2 pensjon.gjenlevende.person.pin.0.identifikator")
        // 5.1.7.1.3 Sector (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].sektor -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", firstPin?.sektor, "5.1.7.1.3 pensjon.gjenlevende.person.pin.0.sektor")
        // 5.1.7.1.4.1 Institution ID (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsid -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-5.1-1", firstPin?.institusjonsid, "5.1.7.1.4.1 pensjon.gjenlevende.person.pin.0.institusjonsid")
        // 5.1.7.1.4.2 Institution name (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 5.1-1", firstPin?.institusjonsnavn, "5.1.7.1.4.2 pensjon.gjenlevende.person.pin.0.institusjonsnavn")
        // 5.1.7.1.1 PIN country (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].land -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", secondPin?.land, "5.1.7.1.1 pensjon.gjenlevende.person.pin.1.land")
        // 5.1.7.1.2 PIN (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].identifikator -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-5.1-2", secondPin?.identifikator, "5.1.7.1.2 pensjon.gjenlevende.person.pin.1.identifikator")
        // 5.1.7.1.3 Sector (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].sektor -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", secondPin?.sektor, "5.1.7.1.3 pensjon.gjenlevende.person.pin.1.sektor")
        // 5.1.7.1.4.1 Institution ID (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsid -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-5.1-2", secondPin?.institusjonsid, "5.1.7.1.4.1 pensjon.gjenlevende.person.pin.1.institusjonsid")
        // 5.1.7.1.4.2 Institution name (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.pin[x].institusjonsnavn -> P2100.Claimant.PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 5.1-2", secondPin?.institusjonsnavn, "5.1.7.1.4.2 pensjon.gjenlevende.person.pin.1.institusjonsnavn")
        // 5.1.8.1.1 Place of birth: town
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.foedested.by -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("5.1 Birth town", birthplace?.by, "5.1.8.1.1 pensjon.gjenlevende.person.foedested.by")
        // 5.1.8.1.2 Place of birth: region
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.foedested.region -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("5.1 Birth region", birthplace?.region, "5.1.8.1.2 pensjon.gjenlevende.person.foedested.region")
        // 5.1.8.1.3 Place of birth: country
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.foedested.land -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("DK", birthplace?.land, "5.1.8.1.3 pensjon.gjenlevende.person.foedested.land")
        // 5.1.8.2 Father's family name at birth
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.far.person.etternavnvedfoedsel -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("5.1 Father birth name", claimant?.far?.person?.etternavnvedfoedsel, "5.1.8.2 pensjon.gjenlevende.far.person.etternavnvedfoedsel")
        // 5.1.8.3 Father's forename
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.far.person.fornavn -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("5.1 Father forename", claimant?.far?.person?.fornavn, "5.1.8.3 pensjon.gjenlevende.far.person.fornavn")
        // 5.1.8.4 Mother's family name at birth
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.mor.person.etternavnvedfoedsel -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("5.1 Mother birth name", claimant?.mor?.person?.etternavnvedfoedsel, "5.1.8.4 pensjon.gjenlevende.mor.person.etternavnvedfoedsel")
        // 5.1.8.5 Mother's forename
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.mor.person.fornavn -> P2100.Claimant.PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("5.1 Mother forename", claimant?.mor?.person?.fornavn, "5.1.8.5 pensjon.gjenlevende.mor.person.fornavn")
        // 5.2.1.1 Nationality
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.statsborgerskap[x].land -> P2100.Claimant.AdditionalInformationPerson.AdditionalInformationPerson.nationality.value[x]
        assertEquals("FI", person?.statsborgerskap?.getOrNull(0)?.land, "5.2.1.1 pensjon.gjenlevende.person.statsborgerskap.0.land")
        // 5.2.1.2 Previous family name
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.tidligereetternavn -> P2100.Claimant.AdditionalInformationPerson.AdditionalInformationPerson.previousFamilyName
        assertEquals("5.2.1 Previous family name", person?.tidligereetternavn, "5.2.1.2 pensjon.gjenlevende.person.tidligereetternavn")
        // 5.2.1.3 Previous forename
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.tidligerefornavn -> P2100.Claimant.AdditionalInformationPerson.AdditionalInformationPerson.previousForename
        assertEquals("5.2.1 Previous forename", person?.tidligerefornavn, "5.2.1.3 pensjon.gjenlevende.person.tidligerefornavn")
        // 5.2.2.1 Family status (sivilstand Rina enum)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.sivilstand[x].status -> P2100.Claimant.AdditionalInformationPerson.FamilyStatus[x].familyStatus.value[x]
        assertEquals(SivilstandRina.gift, person?.sivilstand?.getOrNull(0)?.status, "5.2.2.1 pensjon.gjenlevende.person.sivilstand.0.status")
        // 5.2.2.2 Family status date
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.sivilstand[x].fradato -> P2100.Claimant.AdditionalInformationPerson.FamilyStatus[x].familyStatusDate
        assertEquals("2000-01-02", person?.sivilstand?.getOrNull(0)?.fradato, "5.2.2.2 pensjon.gjenlevende.person.sivilstand.0.fradato")
        // 5.2.3.1 Street
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.adresse.gate -> P2100.Claimant.AdditionalInformationPerson.Address.street
        assertEquals("5.2.3 Test street", address?.gate, "5.2.3.1 pensjon.gjenlevende.adresse.gate")
        // 5.2.3.2 Building name
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.adresse.bygning -> P2100.Claimant.AdditionalInformationPerson.Address.buildingName
        assertEquals("5.2.3 Test building", address?.bygning, "5.2.3.2 pensjon.gjenlevende.adresse.bygning")
        // 5.2.3.3 Town
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.adresse.by -> P2100.Claimant.AdditionalInformationPerson.Address.town
        assertEquals("5.2.3 Test town", address?.by, "5.2.3.3 pensjon.gjenlevende.adresse.by")
        // 5.2.3.4 Postal code
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.adresse.postnummer -> P2100.Claimant.AdditionalInformationPerson.Address.postalCode
        assertEquals("0123", address?.postnummer, "5.2.3.4 pensjon.gjenlevende.adresse.postnummer")
        // 5.2.3.5 Region
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.adresse.region -> P2100.Claimant.AdditionalInformationPerson.Address.region
        assertEquals("5.2.3 Test region", address?.region, "5.2.3.5 pensjon.gjenlevende.adresse.region")
        // 5.2.3.6 Country
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.adresse.land -> P2100.Claimant.AdditionalInformationPerson.Address.country.value[x]
        assertEquals("NO", address?.land, "5.2.3.6 pensjon.gjenlevende.adresse.land")
        // 5.2.4.1.1.1 Telephone type (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.kontakt.telefon[x].type -> P2100.Claimant.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", contact?.telefon?.getOrNull(0)?.type, "5.2.4.1.1.1 pensjon.gjenlevende.person.kontakt.telefon.0.type")
        // 5.2.4.1.1.2 Telephone number (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.kontakt.telefon[x].nummer -> P2100.Claimant.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("00000001", contact?.telefon?.getOrNull(0)?.nummer, "5.2.4.1.1.2 pensjon.gjenlevende.person.kontakt.telefon.0.nummer")
        // 5.2.4.1.1.1 Telephone type (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.kontakt.telefon[x].type -> P2100.Claimant.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", contact?.telefon?.getOrNull(1)?.type, "5.2.4.1.1.1 pensjon.gjenlevende.person.kontakt.telefon.1.type")
        // 5.2.4.1.1.2 Telephone number (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.kontakt.telefon[x].nummer -> P2100.Claimant.AdditionalInformationPerson.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("00000002", contact?.telefon?.getOrNull(1)?.nummer, "5.2.4.1.1.2 pensjon.gjenlevende.person.kontakt.telefon.1.nummer")
        // 5.2.4.2.1.1 Email address
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.kontakt.email[x].adresse -> P2100.Claimant.AdditionalInformationPerson.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-5.2.4@example.invalid", contact?.email?.getOrNull(0)?.adresse, "5.2.4.2.1.1 pensjon.gjenlevende.person.kontakt.email.0.adresse")
        // 5.2.5 Relationship to the insured person
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.relasjon -> P2100.Claimant.AdditionalInformationPerson.relationshipInsuredPerson.value[x]
        assertEquals("01", person?.relasjontilavdod?.relasjon, "5.2.5 pensjon.gjenlevende.person.relasjontilavdod.relasjon")

        // 5.2.6 sammehusholdning
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.sammehusholdning -> P2100.Claimant.AdditionalInformationPerson.sameHouseholdWithInsuredPersonIndicator.value[x]
        assertEquals("1", relationship?.sammehusholdning, "5.2.6 pensjon.gjenlevende.person.relasjontilavdod.sammehusholdning")
        // 5.2.7.1 sammehusholdningfradato
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.sammehusholdningfradato -> P2100.Claimant.AdditionalInformationPerson.FillInIfSameHouseholdWithInsured.startDateLivingTogether
        assertEquals("2000-01-01", relationship?.sammehusholdningfradato, "5.2.7.1 pensjon.gjenlevende.person.relasjontilavdod.sammehusholdningfradato")
        // 5.3.1 giftParnerDato
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.giftParnerDato -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.dateMarriageOrRegisteredPartnershipWithInsuredPerson
        assertEquals("2001-02-03", relationship?.giftParnerDato, "5.3.1 pensjon.gjenlevende.person.relasjontilavdod.giftParnerDato")
        // 5.3.2.1 harfellesbarn
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.harfellesbarn -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.CommonChildrenClaimantInsuredPerson.didClaimantInsuredPersonHaveHadAChildInCommonIndicator.value[x]
        assertEquals("1", relationship?.harfellesbarn, "5.3.2.1 pensjon.gjenlevende.person.relasjontilavdod.harfellesbarn")
        // 5.3.2.2 forventetTerim
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.forventetTerim -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.CommonChildrenClaimantInsuredPerson.ifClaimantExpectingAChildStateExpectedDateBirthForChild
        assertEquals("2024-08-01", relationship?.forventetTerim, "5.3.2.2 pensjon.gjenlevende.person.relasjontilavdod.forventetTerim")
        // 5.3.3 separasjonType
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.relasjontilavdod.separasjonType -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.separationType.value[x]
        assertEquals("01", relationship?.separasjonType, "5.3.3 pensjon.gjenlevende.person.relasjontilavdod.separasjonType")
        // 5.3.4.1 fraDato
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.nyttEkteskapPartnerskapEtterForsikredeDod.fraDato -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.MarriageOrRegisteredPartnershipAfterDeathInsured.dateMarriageOrRegisteredPartnershipAfterDeathInsured
        assertEquals("2025-01-01", newPartner?.fraDato, "5.3.4.1 pensjon.gjenlevende.person.nyttEkteskapPartnerskapEtterForsikredeDod.fraDato")
        // 5.3.4.2.1 etternavn
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.nyttEkteskapPartnerskapEtterForsikredeDod.etternavn -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.MarriageOrRegisteredPartnershipAfterDeathInsured.NewSpousePartnerClaimant.familyName
        assertEquals("5.3.4.2.1 Test partner family name", newPartner?.etternavn, "5.3.4.2.1 pensjon.gjenlevende.person.nyttEkteskapPartnerskapEtterForsikredeDod.etternavn")
        // 5.3.4.2.2 fornavn
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.nyttEkteskapPartnerskapEtterForsikredeDod.fornavn -> P2100.Claimant.AdditionalInformationClaimantWhenClaimant.MarriageOrRegisteredPartnershipAfterDeathInsured.NewSpousePartnerClaimant.forename
        assertEquals("5.3.4.2.2 Test partner forename", newPartner?.fornavn, "5.3.4.2.2 pensjon.gjenlevende.person.nyttEkteskapPartnerskapEtterForsikredeDod.fornavn")
        // 5.4 bormednypartner
        // Mapping (ACL v4.4/v4.5): $pensjon.gjenlevende.person.bormednypartner -> P2100.Claimant.livingTogetherWithNewSpousePartnerIndicator.value[x]
        assertEquals("1", p2100.pensjon?.gjenlevende?.person?.bormednypartner, "5.4 pensjon.gjenlevende.person.bormednypartner")
        // 6.1.1 yrke
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].yrke -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].occupation
        assertEquals("6.1.1 Test occupation", employment?.yrke, "6.1.1 pensjon.bruker.arbeidsforhold.0.yrke")
        // 6.1.2 type
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].type -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].employmentSelfEmployment.value[x]
        assertEquals("01", employment?.type, "6.1.2 pensjon.bruker.arbeidsforhold.0.type")
        // 6.1.3 planlagtstartdato
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].planlagtstartdato -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].startDateIntendedEmploymentOrSelfEmployment
        assertEquals("2025-02-01", employment?.planlagtstartdato, "6.1.3 pensjon.bruker.arbeidsforhold.0.planlagtstartdato")
        // 6.1.4 sluttdato
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].sluttdato -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].endDateEmploymentOrSelfEmployment
        assertEquals("2026-03-01", employment?.sluttdato, "6.1.4 pensjon.bruker.arbeidsforhold.0.sluttdato")
        // 6.1.5 planlagtpensjoneringsdato
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].planlagtpensjoneringsdato -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].intendedRetirementDateFromEmploymentOrSelfEmployment
        assertEquals("2027-04-01", employment?.planlagtpensjoneringsdato, "6.1.5 pensjon.bruker.arbeidsforhold.0.planlagtpensjoneringsdato")
        // 6.1.6 arbeidstimerperuke
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].arbeidstimerperuke -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].hoursPerWeek
        assertEquals("37.5", employment?.arbeidstimerperuke, "6.1.6 pensjon.bruker.arbeidsforhold.0.arbeidstimerperuke")
        // 6.1.7.1 beloep
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloep -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amount
        assertEquals("12345.67", income?.beloep, "6.1.7.1 pensjon.bruker.arbeidsforhold.0.inntekt.0.beloep")
        // 6.1.7.2 valuta
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].inntekt[x].valuta -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].currency.value[x]
        assertEquals("NOK", income?.valuta, "6.1.7.2 pensjon.bruker.arbeidsforhold.0.inntekt.0.valuta")
        // 6.1.7.3 beloeputbetaltsiden
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].inntekt[x].beloeputbetaltsiden -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].amountEffectiveSince
        assertEquals("2025-02-01", income?.beloeputbetaltsiden, "6.1.7.3 pensjon.bruker.arbeidsforhold.0.inntekt.0.beloeputbetaltsiden")
        // 6.1.7.4 betalingshyppighetinntekt
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].inntekt[x].betalingshyppighetinntekt -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].paymentFrequency.value[x]
        assertEquals("99", income?.betalingshyppighetinntekt, "6.1.7.4 pensjon.bruker.arbeidsforhold.0.inntekt.0.betalingshyppighetinntekt")
        // 6.1.7.5.1 annenbetalingshyppighetinntekt
        // Mapping (ACL v4.4/v4.5): $pensjon.bruker.arbeidsforhold[x].inntekt[x].annenbetalingshyppighetinntekt -> P2100.ClaimantEmploymentSelfEmploymentDetails.EmploymentSelfEmploymentDetails[x].IncomeAmount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("6.1.7.5.1 Test frequency", income?.annenbetalingshyppighetinntekt, "6.1.7.5.1 pensjon.bruker.arbeidsforhold.0.inntekt.0.annenbetalingshyppighetinntekt")
    }

    @Test
    fun claimantBenefits() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val firstBenefit = p2100.pensjon?.ytelser?.getOrNull(0)
        val secondBenefit = p2100.pensjon?.ytelser?.getOrNull(1)
        val firstInstitution = firstBenefit?.institusjon
        val secondInstitution = secondBenefit?.institusjon
        val firstBenefitFirstAmount = firstBenefit?.beloep?.getOrNull(0)
        val firstBenefitSecondAmount = firstBenefit?.beloep?.getOrNull(1)
        val secondBenefitFirstAmount = secondBenefit?.beloep?.getOrNull(0)
        val secondBenefitSecondAmount = secondBenefit?.beloep?.getOrNull(1)

        // 7.1.1 Benefit (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].ytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].benefits.value[x]
        assertEquals("99", firstBenefit?.ytelse, "7.1.1 pensjon.ytelser.0.ytelse")
        // 7.1.2.1 Other benefit (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].annenytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].PleaseFillInFollowingIfBenefitsOtherBenefits.otherBenefits
        assertEquals("7.1.2.1 Test benefit 1", firstBenefit?.annenytelse, "7.1.2.1 pensjon.ytelser.0.annenytelse")
        // 7.1.3 Status (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].status -> P2100.ClaimantBenefitDetails.BenefitDetails[x].status.value[x]
        assertEquals("02", firstBenefit?.status, "7.1.3 pensjon.ytelser.0.status")
        // 7.1.4.1.1 Institution ID (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].institusjon.institusjonsid -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Institution.Institution.institutionID
        assertEquals("NO:TEST-BENEFIT-1", firstInstitution?.institusjonsid, "7.1.4.1.1 pensjon.ytelser.0.institusjon.institusjonsid")
        // 7.1.4.1.2 Institution name (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].institusjon.institusjonsnavn -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Institution.Institution.institutionName
        assertEquals("Test benefit institution 1", firstInstitution?.institusjonsnavn, "7.1.4.1.2 pensjon.ytelser.0.institusjon.institusjonsnavn")
        // 7.1.4.2 Case number (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].institusjon.saksnummer -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Institution.caseNumber
        assertEquals("TEST-BENEFIT-CASE-1", firstInstitution?.saksnummer, "7.1.4.2 pensjon.ytelser.0.institusjon.saksnummer")
        // 7.1.5 Start date of benefit payment (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].startdatoutbetaling -> P2100.ClaimantBenefitDetails.BenefitDetails[x].startDateBenefitsPayment
        assertEquals("2020-02-01", firstBenefit?.startdatoutbetaling, "7.1.5 pensjon.ytelser.0.startdatoutbetaling")
        // 7.1.6 End date of benefit payment (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].sluttdatoutbetaling -> P2100.ClaimantBenefitDetails.BenefitDetails[x].endDateBenefitsPayment
        assertEquals("2024-03-01", firstBenefit?.sluttdatoutbetaling, "7.1.6 pensjon.ytelser.0.sluttdatoutbetaling")
        // 7.1.7 Start date of entitlement (entry 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].startdatoretttilytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].startDateEntitlementBenefits
        assertEquals("2020-01-01", firstBenefit?.startdatoretttilytelse, "7.1.7 pensjon.ytelser.0.startdatoretttilytelse")
        // 7.1.8 End date of entitlement (model only; entry 1)
        //TODO 7.1.8 Fix the ACL path: sluttdatoUtbetaling is missing; the model uses sluttdatorettilutbetaling.
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].sluttdatoUtbetaling -> P2100.ClaimantBenefitDetails.BenefitDetails[x].endDateEntitlementBenefits
        // Model assertion: $pensjon.ytelser[x].sluttdatorettilutbetaling (not currently mapped by the ACL).
        assertEquals("2024-02-01", firstBenefit?.sluttdatorettilutbetaling, "7.1.8 pensjon.ytelser.0.sluttdatorettilutbetaling")
        // 7.1.9.1 Amount (benefit 1, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].beloep -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("1100.50", firstBenefitFirstAmount?.beloep, "7.1.9.1 pensjon.ytelser.0.beloep.0.beloep")
        // 7.1.9.2 Currency (benefit 1, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].valuta -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", firstBenefitFirstAmount?.valuta, "7.1.9.2 pensjon.ytelser.0.beloep.0.valuta")
        // 7.1.9.3 Amount effective since (benefit 1, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2020-02-01", firstBenefitFirstAmount?.gjeldendesiden, "7.1.9.3 pensjon.ytelser.0.beloep.0.gjeldendesiden")
        // 7.1.9.4 Payment frequency (benefit 1, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.maaned_12_per_aar, firstBenefitFirstAmount?.betalingshyppighetytelse, "7.1.9.4 pensjon.ytelser.0.beloep.0.betalingshyppighetytelse")
        // 7.1.9.1 Amount (benefit 1, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].beloep -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("1200.50", firstBenefitSecondAmount?.beloep, "7.1.9.1 pensjon.ytelser.0.beloep.1.beloep")
        // 7.1.9.2 Currency (benefit 1, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].valuta -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", firstBenefitSecondAmount?.valuta, "7.1.9.2 pensjon.ytelser.0.beloep.1.valuta")
        // 7.1.9.3 Amount effective since (benefit 1, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2021-02-01", firstBenefitSecondAmount?.gjeldendesiden, "7.1.9.3 pensjon.ytelser.0.beloep.1.gjeldendesiden")
        // 7.1.9.4 Payment frequency (benefit 1, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet, firstBenefitSecondAmount?.betalingshyppighetytelse, "7.1.9.4 pensjon.ytelser.0.beloep.1.betalingshyppighetytelse")
        // 7.1.9.5.1 Other payment frequency (benefit 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("7.1.9.5.1 Test frequency 1", firstBenefitSecondAmount?.annenbetalingshyppighetytelse, "7.1.9.5.1 pensjon.ytelser.0.beloep.1.annenbetalingshyppighetytelse")
        // 7.1.1 Benefit (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].ytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].benefits.value[x]
        assertEquals("01", secondBenefit?.ytelse, "7.1.1 pensjon.ytelser.1.ytelse")
        // 7.1.2.1 Other benefit (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].annenytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].PleaseFillInFollowingIfBenefitsOtherBenefits.otherBenefits
        assertEquals("7.1.2.1 Test benefit 2", secondBenefit?.annenytelse, "7.1.2.1 pensjon.ytelser.1.annenytelse")
        // 7.1.3 Status (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].status -> P2100.ClaimantBenefitDetails.BenefitDetails[x].status.value[x]
        assertEquals("02", secondBenefit?.status, "7.1.3 pensjon.ytelser.1.status")
        // 7.1.4.1.1 Institution ID (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].institusjon.institusjonsid -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Institution.Institution.institutionID
        assertEquals("NO:TEST-BENEFIT-2", secondInstitution?.institusjonsid, "7.1.4.1.1 pensjon.ytelser.1.institusjon.institusjonsid")
        // 7.1.4.1.2 Institution name (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].institusjon.institusjonsnavn -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Institution.Institution.institutionName
        assertEquals("Test benefit institution 2", secondInstitution?.institusjonsnavn, "7.1.4.1.2 pensjon.ytelser.1.institusjon.institusjonsnavn")
        // 7.1.4.2 Case number (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].institusjon.saksnummer -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Institution.caseNumber
        assertEquals("TEST-BENEFIT-CASE-2", secondInstitution?.saksnummer, "7.1.4.2 pensjon.ytelser.1.institusjon.saksnummer")
        // 7.1.5 Start date of benefit payment (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].startdatoutbetaling -> P2100.ClaimantBenefitDetails.BenefitDetails[x].startDateBenefitsPayment
        assertEquals("2020-02-01", secondBenefit?.startdatoutbetaling, "7.1.5 pensjon.ytelser.1.startdatoutbetaling")
        // 7.1.6 End date of benefit payment (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].sluttdatoutbetaling -> P2100.ClaimantBenefitDetails.BenefitDetails[x].endDateBenefitsPayment
        assertEquals("2024-03-01", secondBenefit?.sluttdatoutbetaling, "7.1.6 pensjon.ytelser.1.sluttdatoutbetaling")
        // 7.1.7 Start date of entitlement (entry 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].startdatoretttilytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].startDateEntitlementBenefits
        assertEquals("2020-01-01", secondBenefit?.startdatoretttilytelse, "7.1.7 pensjon.ytelser.1.startdatoretttilytelse")
        // 7.1.8 End date of entitlement (model only; entry 2)
        //TODO 7.1.8 Fix the ACL path: sluttdatoUtbetaling is missing; the model uses sluttdatorettilutbetaling.
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].sluttdatoUtbetaling -> P2100.ClaimantBenefitDetails.BenefitDetails[x].endDateEntitlementBenefits
        // Model assertion: $pensjon.ytelser[x].sluttdatorettilutbetaling (not currently mapped by the ACL).
        assertEquals("2024-02-01", secondBenefit?.sluttdatorettilutbetaling, "7.1.8 pensjon.ytelser.1.sluttdatorettilutbetaling")
        // 7.1.9.1 Amount (benefit 2, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].beloep -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("2100.50", secondBenefitFirstAmount?.beloep, "7.1.9.1 pensjon.ytelser.1.beloep.0.beloep")
        // 7.1.9.2 Currency (benefit 2, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].valuta -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", secondBenefitFirstAmount?.valuta, "7.1.9.2 pensjon.ytelser.1.beloep.0.valuta")
        // 7.1.9.3 Amount effective since (benefit 2, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2020-02-01", secondBenefitFirstAmount?.gjeldendesiden, "7.1.9.3 pensjon.ytelser.1.beloep.0.gjeldendesiden")
        // 7.1.9.4 Payment frequency (benefit 2, amount 1)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.maaned_12_per_aar, secondBenefitFirstAmount?.betalingshyppighetytelse, "7.1.9.4 pensjon.ytelser.1.beloep.0.betalingshyppighetytelse")
        // 7.1.9.1 Amount (benefit 2, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].beloep -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amount
        assertEquals("2200.50", secondBenefitSecondAmount?.beloep, "7.1.9.1 pensjon.ytelser.1.beloep.1.beloep")
        // 7.1.9.2 Currency (benefit 2, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].valuta -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].currency.value[x]
        assertEquals("NOK", secondBenefitSecondAmount?.valuta, "7.1.9.2 pensjon.ytelser.1.beloep.1.valuta")
        // 7.1.9.3 Amount effective since (benefit 2, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].gjeldendesiden -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].amountEffectiveSince
        assertEquals("2021-02-01", secondBenefitSecondAmount?.gjeldendesiden, "7.1.9.3 pensjon.ytelser.1.beloep.1.gjeldendesiden")
        // 7.1.9.4 Payment frequency (benefit 2, amount 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].betalingshyppighetytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].paymentFrequency.value[x]
        assertEquals(Betalingshyppighet.annet, secondBenefitSecondAmount?.betalingshyppighetytelse, "7.1.9.4 pensjon.ytelser.1.beloep.1.betalingshyppighetytelse")
        // 7.1.9.5.1 Other payment frequency (benefit 2)
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].beloep[x].annenbetalingshyppighetytelse -> P2100.ClaimantBenefitDetails.BenefitDetails[x].Amount[x].PleaseFillInFollowingIfPaymentFrequencyOther.otherPaymentFrequency
        assertEquals("7.1.9.5.1 Test frequency 2", secondBenefitSecondAmount?.annenbetalingshyppighetytelse, "7.1.9.5.1 pensjon.ytelser.1.beloep.1.annenbetalingshyppighetytelse")

        //TODO 7.2 Add model support and a field assertion; the current model cannot resolve $pensjon.ytelser[x].retttilytelsevedsykdom.
        // Mapping (ACL v4.4/v4.5): $pensjon.ytelser[x].retttilytelsevedsykdom -> P2100.ClaimantBenefitDetails.entitlementSicknessBenefitsInKind.value[x]


    }

    @Test
    fun claimantChildren() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val firstChild = p2100.nav?.barn?.getOrNull(0)
        val secondChild = p2100.nav?.barn?.getOrNull(1)
        val firstPerson = firstChild?.person
        val secondPerson = secondChild?.person
        val firstChildFirstPin = firstPerson?.pin?.getOrNull(0)
        val firstChildSecondPin = firstPerson?.pin?.getOrNull(1)
        val secondChildFirstPin = secondPerson?.pin?.getOrNull(0)
        val secondChildSecondPin = secondPerson?.pin?.getOrNull(1)
        val firstBirthplace = firstPerson?.foedested
        val secondBirthplace = secondPerson?.foedested

        // 8.1.1.1 Family name
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.etternavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.familyName
        assertEquals("8.1.1 Test family name", firstPerson?.etternavn, "8.1.1.1 nav.barn.0.person.etternavn")
        // 8.1.1.2 Forename
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.fornavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.forename
        assertEquals("8.1.1 Test forename", firstPerson?.fornavn, "8.1.1.2 nav.barn.0.person.fornavn")
        // 8.1.1.3 Date of birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedselsdato -> P2100.ClaimantChildren.Child[x].PersonIdentification.dateBirth
        assertEquals("1970-02-03", firstPerson?.foedselsdato, "8.1.1.3 nav.barn.0.person.foedselsdato")
        // 8.1.1.4 Sex
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.kjoenn -> P2100.ClaimantChildren.Child[x].PersonIdentification.sex.value[x]
        assertEquals("K", firstPerson?.kjoenn, "8.1.1.4 nav.barn.0.person.kjoenn")
        // 8.1.1.5 Family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.etternavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.familyNameAtBirth
        assertEquals("8.1.1 Test birth family name", firstPerson?.etternavnvedfoedsel, "8.1.1.5 nav.barn.0.person.etternavnvedfoedsel")
        // 8.1.1.6 Forename at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.fornavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.forenameAtBirth
        assertEquals("8.1.1 Test birth forename", firstPerson?.fornavnvedfoedsel, "8.1.1.6 nav.barn.0.person.fornavnvedfoedsel")
        // 8.1.1.7.1.1 PIN country (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].land -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", firstChildFirstPin?.land, "8.1.1.7.1.1 nav.barn.0.person.pin.0.land")
        // 8.1.1.7.1.2 PIN (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].identifikator -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-8.1.1-1", firstChildFirstPin?.identifikator, "8.1.1.7.1.2 nav.barn.0.person.pin.0.identifikator")
        // 8.1.1.7.1.3 Sector (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].sektor -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", firstChildFirstPin?.sektor, "8.1.1.7.1.3 nav.barn.0.person.pin.0.sektor")
        // 8.1.1.7.1.4.1 Institution ID (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-8.1.1-1", firstChildFirstPin?.institusjon?.institusjonsid, "8.1.1.7.1.4.1 nav.barn.0.person.pin.0.institusjon.institusjonsid")
        // 8.1.1.7.1.4.2 Institution name (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 8.1.1-1", firstChildFirstPin?.institusjon?.institusjonsnavn, "8.1.1.7.1.4.2 nav.barn.0.person.pin.0.institusjon.institusjonsnavn")
        // 8.1.1.7.1.1 PIN country (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].land -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", firstChildSecondPin?.land, "8.1.1.7.1.1 nav.barn.0.person.pin.1.land")
        // 8.1.1.7.1.2 PIN (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].identifikator -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-8.1.1-2", firstChildSecondPin?.identifikator, "8.1.1.7.1.2 nav.barn.0.person.pin.1.identifikator")
        // 8.1.1.7.1.3 Sector (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].sektor -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", firstChildSecondPin?.sektor, "8.1.1.7.1.3 nav.barn.0.person.pin.1.sektor")
        // 8.1.1.7.1.4.1 Institution ID (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-8.1.1-2", firstChildSecondPin?.institusjon?.institusjonsid, "8.1.1.7.1.4.1 nav.barn.0.person.pin.1.institusjon.institusjonsid")
        // 8.1.1.7.1.4.2 Institution name (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 8.1.1-2", firstChildSecondPin?.institusjon?.institusjonsnavn, "8.1.1.7.1.4.2 nav.barn.0.person.pin.1.institusjon.institusjonsnavn")
        // 8.1.1.8.1.1 Place of birth: town
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedested.by -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("8.1.1 Birth town", firstBirthplace?.by, "8.1.1.8.1.1 nav.barn.0.person.foedested.by")
        // 8.1.1.8.1.2 Place of birth: region
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedested.region -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("8.1.1 Birth region", firstBirthplace?.region, "8.1.1.8.1.2 nav.barn.0.person.foedested.region")
        // 8.1.1.8.1.3 Place of birth: country
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedested.land -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("DK", firstBirthplace?.land, "8.1.1.8.1.3 nav.barn.0.person.foedested.land")
        // 8.1.1.8.2 Father's family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].far.person.etternavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("8.1.1 Father birth name", firstChild?.far?.person?.etternavnvedfoedsel, "8.1.1.8.2 nav.barn.0.far.person.etternavnvedfoedsel")
        // 8.1.1.8.3 Father's forename
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].far.person.fornavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("8.1.1 Father forename", firstChild?.far?.person?.fornavn, "8.1.1.8.3 nav.barn.0.far.person.fornavn")
        // 8.1.1.8.4 Mother's family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].mor.person.etternavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("8.1.1 Mother birth name", firstChild?.mor?.person?.etternavnvedfoedsel, "8.1.1.8.4 nav.barn.0.mor.person.etternavnvedfoedsel")
        // 8.1.1.8.5 Mother's forename
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].mor.person.fornavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("8.1.1 Mother forename", firstChild?.mor?.person?.fornavn, "8.1.1.8.5 nav.barn.0.mor.person.fornavn")
        // 8.1.2.1 Nationality (child 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.statsborgerskap[x].land -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", firstPerson?.statsborgerskap?.getOrNull(0)?.land, "8.1.2.1 nav.barn.0.person.statsborgerskap.0.land")
        // 8.1.2.2 Relationship to claimant (child 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].relasjontilbruker -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.relationshipClaimant.value[x]
        assertEquals("99", firstChild?.relasjontilbruker, "8.1.2.2 nav.barn.0.relasjontilbruker")
        // 8.1.2.3.1 Other child details (child 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].opplysningeromannetbarn -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.FillInIfRelationshipClaimantOther.specificsOtherChild
        assertEquals("8.1.2.3.1 Test child 1", firstChild?.opplysningeromannetbarn, "8.1.2.3.1 nav.barn.0.opplysningeromannetbarn")
        // 8.1.2.4 Date of death (child 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.doedsdato -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.dateDeath
        assertEquals("2023-01-02", firstPerson?.doedsdato, "8.1.2.4 nav.barn.0.person.doedsdato")
        // 8.1.1.1 Family name
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.etternavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.familyName
        assertEquals("8.1.1 Test family name", secondPerson?.etternavn, "8.1.1.1 nav.barn.1.person.etternavn")
        // 8.1.1.2 Forename
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.fornavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.forename
        assertEquals("8.1.1 Test forename", secondPerson?.fornavn, "8.1.1.2 nav.barn.1.person.fornavn")
        // 8.1.1.3 Date of birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedselsdato -> P2100.ClaimantChildren.Child[x].PersonIdentification.dateBirth
        assertEquals("1970-02-03", secondPerson?.foedselsdato, "8.1.1.3 nav.barn.1.person.foedselsdato")
        // 8.1.1.4 Sex
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.kjoenn -> P2100.ClaimantChildren.Child[x].PersonIdentification.sex.value[x]
        assertEquals("K", secondPerson?.kjoenn, "8.1.1.4 nav.barn.1.person.kjoenn")
        // 8.1.1.5 Family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.etternavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.familyNameAtBirth
        assertEquals("8.1.1 Test birth family name", secondPerson?.etternavnvedfoedsel, "8.1.1.5 nav.barn.1.person.etternavnvedfoedsel")
        // 8.1.1.6 Forename at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.fornavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.forenameAtBirth
        assertEquals("8.1.1 Test birth forename", secondPerson?.fornavnvedfoedsel, "8.1.1.6 nav.barn.1.person.fornavnvedfoedsel")
        // 8.1.1.7.1.1 PIN country (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].land -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("NO", secondChildFirstPin?.land, "8.1.1.7.1.1 nav.barn.1.person.pin.0.land")
        // 8.1.1.7.1.2 PIN (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].identifikator -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-8.1.1-1", secondChildFirstPin?.identifikator, "8.1.1.7.1.2 nav.barn.1.person.pin.0.identifikator")
        // 8.1.1.7.1.3 Sector (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].sektor -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", secondChildFirstPin?.sektor, "8.1.1.7.1.3 nav.barn.1.person.pin.0.sektor")
        // 8.1.1.7.1.4.1 Institution ID (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-8.1.1-1", secondChildFirstPin?.institusjon?.institusjonsid, "8.1.1.7.1.4.1 nav.barn.1.person.pin.0.institusjon.institusjonsid")
        // 8.1.1.7.1.4.2 Institution name (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 8.1.1-1", secondChildFirstPin?.institusjon?.institusjonsnavn, "8.1.1.7.1.4.2 nav.barn.1.person.pin.0.institusjon.institusjonsnavn")
        // 8.1.1.7.1.1 PIN country (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].land -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].country.value[x]
        assertEquals("SE", secondChildSecondPin?.land, "8.1.1.7.1.1 nav.barn.1.person.pin.1.land")
        // 8.1.1.7.1.2 PIN (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].identifikator -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].personalIdentificationNumber
        assertEquals("TEST-8.1.1-2", secondChildSecondPin?.identifikator, "8.1.1.7.1.2 nav.barn.1.person.pin.1.identifikator")
        // 8.1.1.7.1.3 Sector (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].sektor -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].sector.value[x]
        assertEquals("alle", secondChildSecondPin?.sektor, "8.1.1.7.1.3 nav.barn.1.person.pin.1.sektor")
        // 8.1.1.7.1.4.1 Institution ID (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsid -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionID
        assertEquals("NO:TEST-8.1.1-2", secondChildSecondPin?.institusjon?.institusjonsid, "8.1.1.7.1.4.1 nav.barn.1.person.pin.1.institusjon.institusjonsid")
        // 8.1.1.7.1.4.2 Institution name (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.pin[x].institusjon.institusjonsnavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.PINPersonInEachInstitution.PersonalIdentificationNumber[x].Institution.institutionName
        assertEquals("Test institution 8.1.1-2", secondChildSecondPin?.institusjon?.institusjonsnavn, "8.1.1.7.1.4.2 nav.barn.1.person.pin.1.institusjon.institusjonsnavn")
        // 8.1.1.8.1.1 Place of birth: town
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedested.by -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.town
        assertEquals("8.1.1 Birth town", secondBirthplace?.by, "8.1.1.8.1.1 nav.barn.1.person.foedested.by")
        // 8.1.1.8.1.2 Place of birth: region
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedested.region -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.region
        assertEquals("8.1.1 Birth region", secondBirthplace?.region, "8.1.1.8.1.2 nav.barn.1.person.foedested.region")
        // 8.1.1.8.1.3 Place of birth: country
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.foedested.land -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.PlaceBirth.country.value[x]
        assertEquals("DK", secondBirthplace?.land, "8.1.1.8.1.3 nav.barn.1.person.foedested.land")
        // 8.1.1.8.2 Father's family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].far.person.etternavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.fatherFamilyNameAtBirth
        assertEquals("8.1.1 Father birth name", secondChild?.far?.person?.etternavnvedfoedsel, "8.1.1.8.2 nav.barn.1.far.person.etternavnvedfoedsel")
        // 8.1.1.8.3 Father's forename
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].far.person.fornavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameFather
        assertEquals("8.1.1 Father forename", secondChild?.far?.person?.fornavn, "8.1.1.8.3 nav.barn.1.far.person.fornavn")
        // 8.1.1.8.4 Mother's family name at birth
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].mor.person.etternavnvedfoedsel -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.motherFamilyNameAtBirth
        assertEquals("8.1.1 Mother birth name", secondChild?.mor?.person?.etternavnvedfoedsel, "8.1.1.8.4 nav.barn.1.mor.person.etternavnvedfoedsel")
        // 8.1.1.8.5 Mother's forename
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].mor.person.fornavn -> P2100.ClaimantChildren.Child[x].PersonIdentification.IfPINNotProvidedForEveryInstitutionPleaseProvide.forenameMother
        assertEquals("8.1.1 Mother forename", secondChild?.mor?.person?.fornavn, "8.1.1.8.5 nav.barn.1.mor.person.fornavn")
        // 8.1.2.1 Nationality (child 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.statsborgerskap[x].land -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.nationality.value[x]
        assertEquals("SE", secondPerson?.statsborgerskap?.getOrNull(0)?.land, "8.1.2.1 nav.barn.1.person.statsborgerskap.0.land")
        // 8.1.2.2 Relationship to claimant (child 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].relasjontilbruker -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.relationshipClaimant.value[x]
        assertEquals("99", secondChild?.relasjontilbruker, "8.1.2.2 nav.barn.1.relasjontilbruker")
        // 8.1.2.3.1 Other child details (child 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].opplysningeromannetbarn -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.FillInIfRelationshipClaimantOther.specificsOtherChild
        assertEquals("8.1.2.3.1 Test child 2", secondChild?.opplysningeromannetbarn, "8.1.2.3.1 nav.barn.1.opplysningeromannetbarn")
        // 8.1.2.4 Date of death (child 2)
        // Mapping (ACL v4.4/v4.5): $nav.barn[x].person.doedsdato -> P2100.ClaimantChildren.Child[x].AdditionalInformationPerson.dateDeath
        assertEquals("2023-01-02", secondPerson?.doedsdato, "8.1.2.4 nav.barn.1.person.doedsdato")
    }

    @Test
    fun representative() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val representative = p2100.nav?.verge
        val person = representative?.person
        val address = representative?.adresse
        val contact = person?.kontakt

        // 9.1 Representative family name
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.etternavn -> P2100.InformationRepresentativeLegalGuardian.familyName
        assertEquals("9.1 Test guardian family name", person?.etternavn, "9.1 nav.verge.person.etternavn")
        // 9.2 Representative forename
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.fornavn -> P2100.InformationRepresentativeLegalGuardian.forename
        assertEquals("9.2 Test guardian forename", person?.fornavn, "9.2 nav.verge.person.fornavn")
        // 9.3 Grounds for representation
        // Mapping (ACL v4.4/v4.5): $nav.verge.vergemaal.mandat -> P2100.InformationRepresentativeLegalGuardian.grounds
        assertEquals("9.3 Test mandate", representative?.vergemaal?.mandat, "9.3 nav.verge.vergemaal.mandat")
        // 9.4.1 Street
        // Mapping (ACL v4.4/v4.5): $nav.verge.adresse.gate -> P2100.InformationRepresentativeLegalGuardian.Address.street
        assertEquals("9.4 Test street", address?.gate, "9.4.1 nav.verge.adresse.gate")
        // 9.4.2 Building name
        // Mapping (ACL v4.4/v4.5): $nav.verge.adresse.bygning -> P2100.InformationRepresentativeLegalGuardian.Address.buildingName
        assertEquals("9.4 Test building", address?.bygning, "9.4.2 nav.verge.adresse.bygning")
        // 9.4.3 Town
        // Mapping (ACL v4.4/v4.5): $nav.verge.adresse.by -> P2100.InformationRepresentativeLegalGuardian.Address.town
        assertEquals("9.4 Test town", address?.by, "9.4.3 nav.verge.adresse.by")
        // 9.4.4 Postal code
        // Mapping (ACL v4.4/v4.5): $nav.verge.adresse.postnummer -> P2100.InformationRepresentativeLegalGuardian.Address.postalCode
        assertEquals("0123", address?.postnummer, "9.4.4 nav.verge.adresse.postnummer")
        // 9.4.5 Region
        // Mapping (ACL v4.4/v4.5): $nav.verge.adresse.region -> P2100.InformationRepresentativeLegalGuardian.Address.region
        assertEquals("9.4 Test region", address?.region, "9.4.5 nav.verge.adresse.region")
        // 9.4.6 Country
        // Mapping (ACL v4.4/v4.5): $nav.verge.adresse.land -> P2100.InformationRepresentativeLegalGuardian.Address.country.value[x]
        assertEquals("NO", address?.land, "9.4.6 nav.verge.adresse.land")
        // 9.5.1.1.1 Telephone type (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.kontakt.telefon[x].type -> P2100.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("hjem", contact?.telefon?.getOrNull(0)?.type, "9.5.1.1.1 nav.verge.person.kontakt.telefon.0.type")
        // 9.5.1.1.2 Telephone number (entry 1)
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.kontakt.telefon[x].nummer -> P2100.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("00000001", contact?.telefon?.getOrNull(0)?.nummer, "9.5.1.1.2 nav.verge.person.kontakt.telefon.0.nummer")
        // 9.5.1.1.1 Telephone type (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.kontakt.telefon[x].type -> P2100.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].type.value[x]
        assertEquals("mobil", contact?.telefon?.getOrNull(1)?.type, "9.5.1.1.1 nav.verge.person.kontakt.telefon.1.type")
        // 9.5.1.1.2 Telephone number (entry 2)
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.kontakt.telefon[x].nummer -> P2100.InformationRepresentativeLegalGuardian.ContactInformationPerson.TelephoneNumbers.TelephoneNumber[x].number
        assertEquals("00000002", contact?.telefon?.getOrNull(1)?.nummer, "9.5.1.1.2 nav.verge.person.kontakt.telefon.1.nummer")
        // 9.5.2.1.1 Email address
        // Mapping (ACL v4.4/v4.5): $nav.verge.person.kontakt.email[x].adresse -> P2100.InformationRepresentativeLegalGuardian.ContactInformationPerson.EmailAddresses.EmailAddress[x].email
        assertEquals("test-9.5@example.invalid", contact?.email?.getOrNull(0)?.adresse, "9.5.2.1.1 nav.verge.person.kontakt.email.0.adresse")
    }

    @Test
    fun paymentInformation() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val account = p2100.nav?.bruker?.bank?.konto

        // 10.1 Payment to claimant
        // Mapping (ACL v4.4/v4.5): $nav.bruker.bank.konto.innehaver.rolle -> P2100.InformationPayment.payment.value[x]
        assertEquals("01", account?.innehaver?.rolle, "10.1 nav.bruker.bank.konto.innehaver.rolle")
        // 10.2.1 Account holder name
        // Mapping (ACL v4.4/v4.5): $nav.bruker.bank.konto.innehaver.navn -> P2100.InformationPayment.BankInformation.accountHolderName
        assertEquals("10.2.1 Test account holder", account?.innehaver?.navn, "10.2.1 nav.bruker.bank.konto.innehaver.navn")
        // 10.2.2.1 IBAN
        // Mapping (ACL v4.4/v4.5): $nav.bruker.bank.konto.sepa.iban -> P2100.InformationPayment.BankInformation.SEPAAccount.IBAN
        assertEquals("TEST-IBAN-NOT-A-REAL-ACCOUNT", account?.sepa?.iban, "10.2.2.1 nav.bruker.bank.konto.sepa.iban")
        // 10.2.2.2 BIC-SWIFT
        // Mapping (ACL v4.4/v4.5): $nav.bruker.bank.konto.sepa.swift -> P2100.InformationPayment.BankInformation.SEPAAccount.BICSWIFT
        assertEquals("TEST-NOT-A-REAL-BIC", account?.sepa?.swift, "10.2.2.2 nav.bruker.bank.konto.sepa.swift")
    }

    @Test
    fun dateOfClaim() {
        val p2100 = mapJsonToAny<P2100>(p2100Json())
        val pension = p2100.pensjon

        // 11.1 Date of claim
        // Mapping (ACL v4.4/v4.5): $nav.krav.dato -> P2100.Miscellaneous.dateClaim
        assertEquals("2024-01-02", p2100.nav?.krav?.dato, "11.1 nav.krav.dato")

        // 11.2.1 mottaker
        // Mapping (ACL v4.4/v4.5): $pensjon.vedtak[x].mottaker[x] -> P2100.Miscellaneous.RecipientDecision[x].recipientDecision.value[x]
        assertEquals("01", pension?.vedtak?.getOrNull(0)?.mottaker?.getOrNull(0), "11.2.1 pensjon.vedtak.0.mottaker.0")
        // 11.3.1 trekkgrunnlag
        // Mapping (ACL v4.4/v4.5): $pensjon.vedtak[x].trekkgrunnlag[x] -> P2100.Miscellaneous.DeductionsGrounds[x].deductionsGrounds.value[x]
        assertEquals("02", pension?.vedtak?.getOrNull(0)?.trekkgrunnlag?.getOrNull(0), "11.3.1 pensjon.vedtak.0.trekkgrunnlag.0")
        // 11.4.1 vedlegg
        // Mapping (ACL v4.4/v4.5): $pensjon.vedlegg[x] -> P2100.Miscellaneous.Attachments.attachments.value[x]
        assertEquals("99", pension?.vedlegg?.getOrNull(0), "11.4.1 pensjon.vedlegg.0")
        // 11.4.2.1 vedleggandre
        // Mapping (ACL v4.4/v4.5): $pensjon.vedleggandre -> P2100.Miscellaneous.Attachments.PleaseFillInFollowingIfAttachmentsOther.otherAttachment
        assertEquals("11.4.2.1 Test attachment", pension?.vedleggandre, "11.4.2.1 pensjon.vedleggandre")
        // 11.5 etterspurtedokumenter
        // Mapping (ACL v4.4/v4.5): $pensjon.etterspurtedokumenter -> P2100.Miscellaneous.requestedDocuments
        assertEquals("11.5 Test requested documents", pension?.etterspurtedokumenter, "11.5 pensjon.etterspurtedokumenter")
        // 11.6 ytterligeinformasjon
        // Mapping (ACL v4.4/v4.5): $pensjon.ytterligeinformasjon -> P2100.Miscellaneous.additionalInformation
        assertEquals("11.6 Test additional information", pension?.ytterligeinformasjon, "11.6 pensjon.ytterligeinformasjon")
        // 11.7 antallSokereKjent
        // Mapping (ACL v4.4/v4.5): $pensjon.antallSokereKjent -> P2100.Miscellaneous.numberKnownClaimantsRelatedInsuredPerson
        assertEquals("2", pension?.antallSokereKjent, "11.7 pensjon.antallSokereKjent")
    }

    @Test
    fun supportedFieldsSurviveJsonRoundTrip() {
        val expected = p2100Json()
        val p2100 = mapJsonToAny<P2100>(expected)

        // 1-11 All supported numbered fields; strict comparison also checks collection sizes and order.
        JSONAssert.assertEquals(expected, p2100.toJsonSkipEmpty(), true)
    }
}
