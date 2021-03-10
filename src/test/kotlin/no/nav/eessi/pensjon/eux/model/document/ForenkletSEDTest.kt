package no.nav.eessi.pensjon.eux.model.document

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class ForenkletSEDTest {

    @Test
    fun `Deserialisering av dokumenter`() {
        val json = javaClass.getResource("/buc/alldocumentsids.json").readText()
        val documents = jacksonObjectMapper().readValue(json, object : TypeReference<List<ForenkletSED>>() {})

        assertEquals(5, documents.size)

        documents.forEach {
            assertNotNull(it.id)
            assertNotNull(it.status)
            assertNotNull(it.type)
        }
    }
}