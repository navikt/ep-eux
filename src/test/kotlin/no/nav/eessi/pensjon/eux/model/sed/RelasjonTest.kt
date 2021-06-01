package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RelasjonTest {

    @Test
    fun testRelasjon() {

        val reslasjon = RelasjonTilAvdod.EKTEFELLE
        assertEquals("01", reslasjon.kode)

        val relasjonavdod = RelasjonTilAvdod.values().firstOrNull { it.kode == "06" }
        assertEquals(RelasjonTilAvdod.EGET_BARN, relasjonavdod)
        assertEquals(true, relasjonavdod?.erGjenlevendeBarn())

    }


}