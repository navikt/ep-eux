package no.nav.eessi.pensjon.eux.klient

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.config.EuxCacheConfig
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.web.client.RestTemplate


@SpringJUnitConfig(classes = [EuxCacheConfig::class, EuxKlientCacheTest.Config::class])
class EuxKlientCacheTest {
    private val RINASAK_ID = 123

    @Autowired
    lateinit var euxKlientLib: EuxKlientLib

    @Autowired
    lateinit var euxRestTemplate: RestTemplate

    @BeforeEach
    fun setup() {
        every {
            euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/11111", String::class.java )
        } returns javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
        every {
            euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/22222", String::class.java )
        } returns javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
        every {
            euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/33333", String::class.java )
        } returns javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
    }

    @Test
    fun `euxKlient skal cache henting av SED og kun kalle ekstern API én gang`() {
        euxKlientLib.hentSedJson("${RINASAK_ID}", "11111")
        euxKlientLib.hentSedJson("${RINASAK_ID}", "11111")
        euxKlientLib.hentSedJson("${RINASAK_ID}", "22222")
        euxKlientLib.hentSedJson("${RINASAK_ID}", "33333")

        verify(exactly = 1) {
            euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/11111", String::class.java )
        }
        verify(exactly = 1) {
            euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/22222", String::class.java)
        }
        verify(exactly = 1) {
            euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/33333", String::class.java)
        }
    }

    @TestConfiguration
    class Config {
        @Bean
        fun euxRestTemplate(): RestTemplate = mockk(relaxed = true)
        @Bean
        fun EuxKlientLib(): EuxKlientLib = EuxKlientLib(euxRestTemplate())
    }
}