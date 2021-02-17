package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import no.nav.eessi.pensjon.eux.model.sed.SedType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BucTest {

    @Test
    fun deserialize() {
        val json = javaClass.getResource("/buc/exampleBuc.json").readText()

        val buc = jacksonObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readValue(json, object : TypeReference<Buc>() {})

        // Buc
        assertNotNull(buc)
        assertEquals("1198662", buc.id)
        assertNotNull(buc.startDate)
        assertNotNull(buc.lastUpdate)
        assertEquals("P_BUC_05", buc.processDefinitionName)
        assertEquals("PO", buc.applicationRoleId)
        assertEquals("1198662", buc.businessId)
        assertEquals("1afa566e735246c39e83508b7e241eec", buc.internationalId)

        // Document
        val document = buc.documents!!.first()
        assertEquals(SedType.P8000, document.type)
        assertEquals("f12d7c39f14745ed8fb033b353525092", document.id)
        assertEquals("json", document.mimeType)
        assertEquals("OUT", document.direction)
        assertEquals("Request for additional information", document.displayName)
        assertNotNull(document.lastUpdate)
        assertNotNull(document.creationDate)

        // Document Creator
        assertEquals("SRVEESSIPENSJON", document.creator!!.name)
        assertEquals("User", document.creator!!.type)

        // Participants
        val participant = buc.participants!!.first()
        assertEquals("CaseOwner", participant.role)

        // Participant Organisation
        val organisation = participant.organisation!!
        assertEquals("NO:NAVAT07", organisation.id)
        assertEquals("NAV ACCEPTANCE TEST 07", organisation.name)
        assertEquals("NAV ACCT 07", organisation.acronym)
        assertEquals("NO", organisation.address!!.country)
        assertEquals("NO", organisation.countryCode)
    }
}
