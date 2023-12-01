package no.nav.eessi.pensjon.eux.klient

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.config.EuxCacheConfig
import no.nav.eessi.pensjon.eux.config.SED_CACHE
import no.nav.eessi.pensjon.utils.toJson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.web.client.RestTemplate

@SpringJUnitConfig(classes = [EuxCacheConfig::class, EuxKlientCacheTest.Config::class])
class EuxKlientCacheTest {

    @Autowired
    lateinit var euxKlient: EuxKlient

    @Autowired
    lateinit var euxRestTemplate: RestTemplate

    @Autowired
    lateinit var cacheManager: CacheManager

    private val RINASAK_ID = "123"
    private val sedIds = listOf("11111", "11111", "11111", "22222")

    @BeforeEach
    fun setup() {
        sedIds.forEach {
            every {
                euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/$it", String::class.java)
            } returns javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
        }
    }

    @Test
    fun `euxKlient skal cache henting av SED og kun kalle ekstern API én gang`() {

        sedIds.forEach {
            euxKlient.hentSedJson(RINASAK_ID, it)
        }
        sedIds.distinct().forEach {
            verify(exactly = 1) {
                euxRestTemplate.getForObject("/buc/${RINASAK_ID}/sed/$it", String::class.java)
            }
        }
        println(cacheManager.getCache(SED_CACHE)?.nativeCache?.toJson())
    }

    @TestConfiguration
    class Config {
        @Bean
        fun euxRestTemplate(): RestTemplate = mockk(relaxed = true)
        @Bean
        fun EuxKlientLib(): EuxKlient = EuxKlient(euxRestTemplate())
    }
}