package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class R005SerdesTest {

    @Test
    fun `validerer at R005 har korrekt format`() {
        val file = javaClass.getResource("/sed/R005-CDM44-NAV.json")!!.readText()
        val r005 = mapJsonToAny<R005>(file, true)
        val recoveryNav = requireNotNull(r005.recoveryNav) { "recoveryNav skal ikke være null" }
        val tilbakekreving = requireNotNull(r005.tilbakekreving) { "tilbakekreving skal ikke være null" }

        assertThat(r005.type.name).isEqualTo("R005")

        assertThat(recoveryNav.eessisak).hasSize(1)
        with(requireNotNull(recoveryNav.eessisak!!.first())) {
            assertThat(institusjonsnavn).isEqualTo("Danish Pension Authority")
            assertThat(saksnummer).isEqualTo("2024-08-385291")
            assertThat(land).isEqualTo("DK")
            assertThat(institusjonsid).isEqualTo("DK:4201")
        }

        val begunstigetBruker = requireNotNull(recoveryNav.begunstiget?.bruker) { "begunstiget.bruker skal ikke være null" }

        // begunstiget person
        with(requireNotNull(begunstigetBruker.person)) {
            assertThat(kjoenn).isEqualTo("K")
            assertThat(fornavn).isEqualTo("Ingrid Marie")
            assertThat(etternavn).isEqualTo("Hansen")
            assertThat(foedselsdato).isEqualTo("1987-05-23")
            assertThat(foedested?.by).isEqualTo("AARHUS")
            assertThat(foedested?.land).isEqualTo("DK")

            assertThat(pin).hasSize(1)
            assertThat(pin!!.first().identifikator).isEqualTo("19870523-7842")
            assertThat(pin.first().institusjonsid).isEqualTo("DK:4201")
            assertThat(pin.first().sektor).isEqualTo("alle")
            assertThat(pin.first().land).isEqualTo("DK")
            assertThat(pin.first().institusjonsnavn).isEqualTo("Danish Pension Authority")
        }

        val forsikretBruker = requireNotNull(recoveryNav.forsikret?.bruker) { "forsikret.bruker skal ikke være null" }

        // forsikret person
        with(requireNotNull(forsikretBruker.person)) {
            assertThat(kjoenn).isEqualTo("M")
            assertThat(fornavn).isEqualTo("Lars")
            assertThat(etternavn).isEqualTo("Kristensen")
            assertThat(foedselsdato).isEqualTo("1982-03-14")
            assertThat(foedested?.by).isEqualTo("Bergen")
            assertThat(foedested?.land).isEqualTo("NO")

            assertThat(pin).hasSize(2)
            assertThat(pin!!.first().identifikator).isEqualTo("19820314-6293")
            assertThat(pin.first().institusjonsid).isEqualTo("DK:4201")
            assertThat(pin.first().land).isEqualTo("DK")
            assertThat(pin.first().sektor).isEqualTo("alle")
            assertThat(pin.first().institusjonsnavn).isEqualTo("Danish Pension Authority")

            assertThat(pin[1].identifikator).isEqualTo("140382 42156")
            assertThat(pin[1].institusjonsid).isEqualTo("NO:974760673")
            assertThat(pin[1].land).isEqualTo("NO")
            assertThat(pin[1].sektor).isEqualTo("alle")
            assertThat(pin[1].institusjonsnavn).isEqualTo("The Norwegian Labour and Welfare Administration")
        }

        assertThat(tilbakekreving.anmodning?.type).isEqualTo("endelig")
        assertThat(tilbakekreving.feilutbetaling?.ytelse?.type).isEqualTo("alderspensjon")
    }
}

