package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class P2000Test {

    @Test
    fun `validate alderpensjon for kravint`() {
        val dato = LocalDate.of(2020, 10, 11)
        val p2000 = P2000(nav = Nav(bruker = Bruker(
            person = Person(
                sivilstand = listOf(SivilstandItem(
                    dato.toString(),
            "gift"
                    )),
                statsborgerskap = listOf(
                    StatsborgerskapItem("SE")
                )
            )
        )), pensjon = null)

        assertEquals(true, p2000.validerForKravinit() )
    }

    @Test
    fun `validate alderpensjon for kravint false `() {
        val dato = LocalDate.of(2020, 10, 11)
        val p2000 = P2000(nav = Nav(bruker = Bruker(
            person = Person(
                statsborgerskap = listOf(
                    StatsborgerskapItem("SE")
                )
            )
        )), pensjon = null)

        assertEquals(false, p2000.validerForKravinit() )
    }


}