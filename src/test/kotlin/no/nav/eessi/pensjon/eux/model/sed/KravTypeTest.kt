package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KravTypeTest {

    @Test
    fun `fraVerdi skal returnere riktig KravType`() {
        assertEquals(KravType.ALDER, KravType.fraNavnEllerVerdi("01"))
        assertEquals(KravType.ALDER, KravType.fraNavnEllerVerdi("ALDER"))
        assertEquals(KravType.GJENLEV, KravType.fraNavnEllerVerdi("02"))
        assertEquals(KravType.UFOREP, KravType.fraNavnEllerVerdi("03"))
    }

    @Test
    fun `fraVerdi skal returnere null for ugyldig verdi`() {
        assertNull(KravType.fraNavnEllerVerdi("04"))
        assertNull(KravType.fraNavnEllerVerdi(null))
    }
}