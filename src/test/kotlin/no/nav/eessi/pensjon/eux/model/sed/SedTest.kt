package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SedTest {

    @Test
    fun `Feil under json-mapping skal kaste en exception`() {
        val json = javaClass.getResource("/sed/P2000-NAV-INVALID.json").readText()
        assertThrows<Exception> {
            SED.fromJsonToConcrete(json)
        }
    }
}