package no.nav.eessi.pensjon.eux.klient

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.eux.model.sed.P2000
import no.nav.eessi.pensjon.eux.model.sed.SED
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class EuxKlientLibTest {

    var mockTemplate: RestTemplate = mockk()
    var euxKlientLib: EuxKlientLib = EuxKlientLib(mockTemplate)

   @Test
    fun `hentBuc skal returnere et Buc dokument`() {
        val json = javaClass.getResource("/buc/exampleBuc.json")!!.readText()
        every { mockTemplate.getForObject(eq("/buc/111"), eq(String::class.java)) } returns json

        val resultat = euxKlientLib.hentBuc("111")!!
        assertEquals("1198662", resultat.id)
    }

    @Test
    fun `hentSed skal returnere et SED document som er en SED eller et subset av SED`() {
        val json = javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
        every { mockTemplate.getForObject(eq("/buc/111/sed/222"), eq(String::class.java)) } returns json

        val resultAsSED = euxKlientLib.hentSed<SED>("111", "222")!!
        assertEquals("28064843062", resultAsSED.nav?.bruker?.person?.pin?.firstOrNull()?.identifikator)

        val resultAsP2000 = euxKlientLib.hentSed<P2000>("111", "222")!!
        assertEquals(SedType.P2000, resultAsP2000.type)
    }

    @Test
    fun `sendSed skal sende korrekt id for buc og sed`() {
        val euxCaseId = "1111"
        val dokumentId = "2222"
        val path = "/buc/$euxCaseId/sed/$dokumentId/send?ventePaAksjon=false"

        val mockResponse = ResponseEntity<String>("", HttpStatus.OK)
        every {
            mockTemplate.postForEntity(
                path,
                any<HttpEntity<String>>(),
                String::class.java
            )
        } returns mockResponse

        val result = euxKlientLib.sendSed(euxCaseId, dokumentId)

        assertTrue(result)
        verify { mockTemplate.postForEntity(path, any<HttpEntity<String>>(), String::class.java) }
    }
    @Test
    fun `sendTo skal sende sed mottakere`() {
        val euxCaseId = "1234"
        val dokumentId = "5678"
        val mottakere = listOf("Mottaker1", "Mottaker2")

        every {
            mockTemplate.postForEntity(
                match<String> { path -> path.contains("/buc/$euxCaseId/sed/$dokumentId/sendTo") },
                any<HttpEntity<String>>(),
                String::class.java
            )
        } returns ResponseEntity("", HttpStatus.OK)

        assertTrue(euxKlientLib.sendTo(euxCaseId, dokumentId, mottakere))
        verify {
            mockTemplate.postForEntity(
                match<String> { path ->
                    path.contains("Mottaker1 Mottaker2") && path.contains("/buc/1234/sed/5678/sendTo?KorrelasjonsId=")},
                any<HttpEntity<String>>(),
            String::class.java) }
    }
}