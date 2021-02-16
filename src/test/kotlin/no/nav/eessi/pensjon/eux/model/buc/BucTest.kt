package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class BucTest {

    @Test
    fun deserialize() {
        val json = javaClass.getResource("/buc/exampleBuc.json").readText()

        val buc = jacksonObjectMapper().readValue(json, object : TypeReference<Buc>() {})

        assertNotNull(buc)
    }
}
