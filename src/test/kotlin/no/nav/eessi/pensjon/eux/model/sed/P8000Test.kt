package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import no.nav.eessi.pensjon.utils.toJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class P8000Test {

    @Test
    fun deserialiseringsTest() {
        val p8000sed = mapJsonToAny<P8000>(javaClass.getResource("/sed/P8000-NAV.json")!!.readText())

        assertEquals(
            "{type={spraak=nb, bosettingsstatus=UTL, ytelse=UT}, ofteEtterspurtInformasjon={dokumentasjonPaaArbeidINorge={value=true}, ytelseshistorikk={value=true}, inntektFoerUfoerhetIUtlandet={value=true}, ibanSwift={value=true}}}",
            p8000sed._options.toString()
        )
        assertEquals("02", p8000sed.p8000Pensjon?.anmodning?.referanseTilPerson)
    }

    @Test
    fun serialiseringsTest() {
        val fraJson = mapJsonToAny<P8000>(javaClass.getResource("/sed/P8000-NAV.json")!!.readText())

        val serialisertJson = fraJson.toJson()
        val fraJsonUtenOptions = mapJsonToAny<P8000>(serialisertJson)
        assertNull(fraJsonUtenOptions._options)
    }

}
