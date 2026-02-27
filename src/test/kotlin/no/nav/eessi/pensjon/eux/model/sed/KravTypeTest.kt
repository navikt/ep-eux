package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class KravTypeTest {

    @Test
    fun `fraVerdi skal returnere riktig KravType`() {
        assertEquals(KravType.ALDER, KravType.fromValue("01"))
        assertEquals(KravType.ALDER, KravType.fromValue("ALDER"))
        assertEquals(KravType.GJENLEV, KravType.fromValue("02"))
        assertEquals(KravType.UFOREP, KravType.fromValue("03"))
    }

    @Test
    fun `fraVerdi skal returnere null for ugyldig verdi`() {
        assertNull(KravType.fromValue("04"))
        assertNull(KravType.fromValue(null))
    }
}