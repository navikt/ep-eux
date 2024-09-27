package no.nav.eessi.pensjon.eux.klient

import com.tngtech.archunit.thirdparty.com.google.common.base.CharMatcher.any
import com.tngtech.archunit.thirdparty.com.google.common.base.Verify.verify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.model.SedType
import no.nav.eessi.pensjon.eux.model.sed.P2000
import no.nav.eessi.pensjon.eux.model.sed.SED
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
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
        val path = "/buc/{$euxCaseId}/sed/{$dokumentId}/send?ventePaAksjon=false"

        val mockResponse = ResponseEntity<String>("", HttpStatus.OK)
        every {
            mockTemplate.exchange(
                path,
                HttpMethod.POST,
                any<HttpEntity<String>>(),
                String::class.java
            )
        } returns mockResponse

        val result = euxKlientLib.sendSed(euxCaseId, dokumentId)

        assertTrue(result)
        verify { mockTemplate.exchange(path, HttpMethod.POST, any<HttpEntity<String>>(), String::class.java) }
    }
}