package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class P7000Test {

    @Test
    fun mapJsonToP7000() {
        val p7000 = mapJsonToAny<P7000>(javaClass.getResource("/sed/P7000-NAV.json")!!.readText())

        Assertions.assertEquals(2, p7000.pensjon?.samletVedtak?.tildeltepensjoner?.size)
        Assertions.assertEquals(1, p7000.pensjon?.samletVedtak?.avslag?.size)

        val noTildelt = p7000.pensjon?.samletVedtak?.tildeltepensjoner?.firstOrNull { it.institusjon?.land == "NO" }
        val seTildelt = p7000.pensjon?.samletVedtak?.tildeltepensjoner?.firstOrNull { it.institusjon?.land == "SE" }
        val dkAvslag = p7000.pensjon?.samletVedtak?.avslag?.firstOrNull { it.pin?.land == "DK" }

        Assertions.assertEquals("2021-07-20", noTildelt?.revurderingtidsfrist)
        Assertions.assertEquals("123123123123", noTildelt?.ytelser?.first()?.beloep?.first()?.beloepBrutto)
        Assertions.assertEquals(
            "4.1.8.2 revjrflkasdfjasdf asdfasd fadfg",
            noTildelt?.adressatForRevurdering?.first()?.adressatforrevurdering
        )
        Assertions.assertEquals("01", noTildelt?.pensjonType)

        Assertions.assertEquals("2021-07-21", seTildelt?.revurderingtidsfrist)
        Assertions.assertEquals("41", seTildelt?.ytelser?.first()?.beloep?.first()?.beloepBrutto)
        Assertions.assertEquals("4.1.8.2.1", seTildelt?.adressatForRevurdering?.first()?.adressatforrevurdering)
        Assertions.assertEquals("01", seTildelt?.pensjonType)

        Assertions.assertEquals("2021-07-20", dkAvslag?.dato)
        Assertions.assertEquals("5.1.5.1 tid", dkAvslag?.tidsfristForRevurdering)
        Assertions.assertEquals("06", dkAvslag?.begrunnelse)
        Assertions.assertEquals("01", dkAvslag?.pensjonType)

    }
}