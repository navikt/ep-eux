package no.nav.eessi.pensjon.eux.config

import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.TimeUnit

internal const val SED_CACHE = "sedcache"

@Configuration
@EnableCaching
@EnableScheduling
class EuxCacheConfig {

    private val logger = LoggerFactory.getLogger(EuxCacheConfig::class.java)

    @Bean("euxCacheManager")
    fun cacheManager(): CacheManager {
        return ConcurrentMapCacheManager(SED_CACHE)
    }

    @CacheEvict(cacheNames = [SED_CACHE])
    @Scheduled(fixedDelay = 24, timeUnit = TimeUnit.HOURS)
    fun reportCacheEvict() {
        logger.info("Flushing cache: $SED_CACHE")
    }
}