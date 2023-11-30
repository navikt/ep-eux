package no.nav.eessi.pensjon.eux.klient

import io.mockk.every
import io.mockk.mockk
import no.nav.eessi.pensjon.eux.model.SedType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.web.client.RestTemplate

class EuxKlientTest {

    var mockTemplate: RestTemplate = mockk()
    var euxKlient: EuxKlient = EuxKlient(mockTemplate)

   @Test
    fun `hentBuc skal returnere et Buc dokument`() {
        val json = javaClass.getResource("/buc/exampleBuc.json")!!.readText()
        every { mockTemplate.getForObject(eq("/buc/111"), eq(String::class.java)) } returns json

        val resultat = euxKlient.hentBuc("111")!!
        assertEquals("1198662", resultat.id)
    }

    @Test
    fun `hentSed skal returnere et SED document som er en SED eller et subset av SED`() {
        val json = javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
        every { mockTemplate.getForObject(eq("/buc/111/sed/222"), eq(String::class.java)) } returns json

        val resultAsSED = euxKlient.hentSed("111", "222")!!
        assertEquals("28064843062", resultAsSED.nav?.bruker?.person?.pin?.firstOrNull()?.identifikator)

        val resultAsP2000 = euxKlient.hentSed("111", "222")!!
        assertEquals(SedType.P2000, resultAsP2000.type)
    }
}