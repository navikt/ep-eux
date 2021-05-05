package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.typeRefs
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.Test

internal class P5000test {

    @Test
    fun `validating that a P5000 is correct format`() {
        val file = javaClass.getResource("/sed/P5000-NAV.json").readText()
        try {
            val p5000 = mapJsonToAny(file, typeRefs<P5000>(), true)
            val pensjon = p5000.p5000Pensjon
            assertEquals(pensjon?.medlemskapboarbeid?.medlemskap?.size, 2)
        } catch (e: Exception) {
            fail("skal ikke komme hit")
        }
    }

}