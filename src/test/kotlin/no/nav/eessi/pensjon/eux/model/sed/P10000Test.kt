package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class P10000Test {

    @Test
    fun mapJsonToP10000() {

        val p10000Json = javaClass.getResource("/sed/P10000-NAV.json")!!.readText()
        val p10000 = mapJsonToAny<P10000>(p10000Json)

        Assertions.assertEquals(1, p10000.pensjon?.merinformasjon?.ytelser?.size)


        Assertions.assertEquals("02", p10000.pensjon?.merinformasjon?.ytelser?.firstOrNull()?.ytelsestype)
        Assertions.assertEquals("SOLEIDE 2225 street", p10000.nav?.bruker?.adresse?.gate)
        Assertions.assertEquals("1870", p10000.nav?.bruker?.adresse?.postnummer)
    }

}

