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
            assertThat(institusjonsnavn).isEqualTo("Dutch Pension Authority")
            assertThat(saksnummer).isEqualTo("2026-01-998877")
            assertThat(land).isEqualTo("NL")
            assertThat(institusjonsid).isEqualTo("NL:7001")
        }

        val begunstigetBruker = requireNotNull(recoveryNav.begunstiget?.bruker) { "begunstiget.bruker skal ikke være null" }

        // begunstiget person
        with(requireNotNull(begunstigetBruker.person)) {
            assertThat(kjoenn).isEqualTo("K")
            assertThat(fornavn).isEqualTo("Emma")
            assertThat(etternavn).isEqualTo("de Vries")
            assertThat(foedselsdato).isEqualTo("1990-02-17")
            assertThat(foedested?.by).isEqualTo("Utrecht")
            assertThat(foedested?.land).isEqualTo("NL")

            assertThat(pin).hasSize(1)
            assertThat(pin!!.first().identifikator).isEqualTo("19900217-5566")
            assertThat(pin.first().institusjonsid).isEqualTo("NL:7001")
            assertThat(pin.first().sektor).isEqualTo("alle")
            assertThat(pin.first().land).isEqualTo("NL")
            assertThat(pin.first().institusjonsnavn).isEqualTo("Dutch Pension Authority")
        }

        val forsikretBruker = requireNotNull(recoveryNav.begunstiget.forsikret?.bruker) { "forsikret.bruker skal ikke være null" }

        // forsikret person
        with(requireNotNull(forsikretBruker.person)) {
            assertThat(kjoenn).isEqualTo("M")
            assertThat(fornavn).isEqualTo("Johan")
            assertThat(etternavn).isEqualTo("Visser")
            assertThat(foedselsdato).isEqualTo("1984-11-05")
            assertThat(foedested?.by).isEqualTo("Gent")
            assertThat(foedested?.land).isEqualTo("BE")

            assertThat(pin).hasSize(2)
            assertThat(pin!!.first().identifikator).isEqualTo("19841105-7788")
            assertThat(pin.first().institusjonsid).isEqualTo("NL:7001")
            assertThat(pin.first().land).isEqualTo("NL")
            assertThat(pin.first().sektor).isEqualTo("alle")
            assertThat(pin.first().institusjonsnavn).isEqualTo("Dutch Pension Authority")

            assertThat(pin[1].identifikator).isEqualTo("051184 34567")
            assertThat(pin[1].institusjonsid).isEqualTo("BE:1200")
            assertThat(pin[1].land).isEqualTo("BE")
            assertThat(pin[1].sektor).isEqualTo("alle")
            assertThat(pin[1].institusjonsnavn).isEqualTo("Belgian Pension Service")
        }

        assertThat(tilbakekreving.anmodning?.type).isEqualTo("forelopig")
        assertThat(tilbakekreving.feilutbetaling?.ytelse?.type).isEqualTo("uforepensjon")
    }
}
