package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KravTypeTest {

    @Test
    fun `fraVerdi skal returnere riktig KravType`() {
        assertEquals(KravType.ALDER, KravType.fraVerdi("01"))
        assertEquals(KravType.GJENLEV, KravType.fraVerdi("02"))
        assertEquals(KravType.UFOREP, KravType.fraVerdi("03"))
    }

    @Test
    fun `fraVerdi skal returnere null for ugyldig verdi`() {
        assertNull(KravType.fraVerdi("04"))
        assertNull(KravType.fraVerdi(null))
    }
}