package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class P8000PensjonTest {
    @Test
    fun `P8000Pensjon init`() {
        val p8000 = mapJsonToAny<P8000>(javaClass.getResource("/sed/P8000-RINA.json")!!.readText())
        val gjenlevende = p8000.p8000Pensjon?.gjenlevende
        val anmodning = p8000.p8000Pensjon?.anmodning
        val ytterligeinformasjon = p8000.p8000Pensjon?.ytterligeinformasjon
        val vedlegg = p8000.p8000Pensjon?.vedlegg

        val p8000Pensjon = P8000Pensjon(
            gjenlevende = gjenlevende,
            anmodning = anmodning,
            ytterligeinformasjon = ytterligeinformasjon,
            vedlegg = vedlegg
        )

        assertEquals(gjenlevende, p8000Pensjon.gjenlevende)
        assertEquals(anmodning, p8000Pensjon.anmodning)
        assertEquals(ytterligeinformasjon, p8000Pensjon.ytterligeinformasjon)
        assertEquals(vedlegg, p8000Pensjon.vedlegg)
    }

    @Test
    fun `Seder init`() {
        val sendFolgendeSEDer = listOf(SedNummer.p1000, SedNummer.p1100)
        val andreEtterspurteSEDer = "Other SEDs"
        val begrunnelse = "Some reason"

        val seder = Seder(
            sendFolgendeSEDer = sendFolgendeSEDer,
            andreEtterspurteSEDer = andreEtterspurteSEDer,
            begrunnelse = begrunnelse
        )

        assertEquals(sendFolgendeSEDer, seder.sendFolgendeSEDer)
        assertEquals(andreEtterspurteSEDer, seder.andreEtterspurteSEDer)
        assertEquals(begrunnelse, seder.begrunnelse)
    }

    @Test
    fun `Seder deserialization`() {
        val json = """
            {
                "sendFolgendeSEDer": ["01", "02"],
                "andreEtterspurteSEDer": "Other SEDs",
                "begrunnelse": "Some reason"
            }
        """.trimIndent()

        val seder: Seder = mapJsonToAny(json)

        assertEquals(listOf(SedNummer.p1000, SedNummer.p1100), seder.sendFolgendeSEDer)
        assertEquals("Other SEDs", seder.andreEtterspurteSEDer)
        assertEquals("Some reason", seder.begrunnelse)
    }
}