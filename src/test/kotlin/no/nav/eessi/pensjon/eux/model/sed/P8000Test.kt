package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class P8000Test {

    @Test
    fun deserialiseringsTest() {
        val p8000sed = mapJsonToAny<P8000Frontend>(javaClass.getResource("/sed/P8000-NAV.json")!!.readText())

        assertEquals("Options(type=Type(bosettingsstatus=UTL, spraak=nb, ytelse=UT), ofteEtterspurtInformasjon=OfteEtterspurtInformasjon(tiltak=null, inntektFoerUfoerhetIUtlandet=InntektFoerUfoerhetIUtlandet(value=true, landkode=null, periodeFra=null, periodeTil=null), brukersAdresse=null, medisinskInformasjon=null, naavaerendeArbeid=null, dokumentasjonPaaArbeidINorge=BooleanValue(value=true), ytelseshistorikk=BooleanValue(value=true), ibanSwift=BooleanValue(value=true), folkbokfoering=null, brukersSivilstand=null, opplysningerOmEPS=null, personUtenPNRDNR=null), informasjonSomKanLeggesInn=null)".trimIndent(),
            p8000sed.options.toString()
        )
        assertEquals("02", p8000sed.p8000Pensjon?.anmodning?.referanseTilPerson)
    }
}
