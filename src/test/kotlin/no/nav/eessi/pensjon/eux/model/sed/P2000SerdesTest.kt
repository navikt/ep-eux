package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class P2000SerdesTest {

    @Test
    fun `validerer at P2000 har korrekt format`() {
        val file = javaClass.getResource("/sed/P2000-NAV.json")!!.readText()
        val p2000 = mapJsonToAny<P2000>(file, true)
        val pensjon = requireNotNull(p2000.p2000pensjon) { "p2000Pensjon skal ikke være null" }

        assertThat(pensjon.angitidligstdato).isEqualTo("nei")
        assertThat(pensjon.mottaker).containsExactly("forsikret_person")
        assertThat(pensjon.forespurtstartdato).isEqualTo("2020-01-01")
        assertThat(pensjon.etterspurtedokumenter).isEqualTo("P5000 and P6000")
        assertThat(pensjon.ytterligeinformasjon).isEqualTo("INFO")

        // mor
        with(requireNotNull(p2000.nav?.bruker?.mor?.person)) {
            assertThat(fornavn).isEqualTo("HANNAMARI-MASK")
            assertThat(pin).first().extracting { it.identifikator }.isEqualTo("20080745894")
            assertThat(doedsdato).isEqualTo("1989-01-28")
        }

        // far
        with(requireNotNull(p2000.nav.bruker.far?.person)) {
            assertThat(fornavn).isEqualTo("CHRISSI-MASK")
            assertThat(pin).first().extracting { it.identifikator }.isEqualTo("14030746512")
            assertThat(etternavnvedfoedsel).isEqualTo("GRANGUIST")
        }

        // utsettelse
        assertThat(pensjon.utsettelse).hasSize(1)
            .first().extracting { it.tildato }.isEqualTo("2023-01-01")

        // ytelser
        assertThat(pensjon.ytelser).hasSize(1)
        with(requireNotNull(pensjon.ytelser!!.first())) {
            assertThat(totalbruttobeloeparbeidsbasert).isEqualTo("200")
            assertThat(startdatoutbetaling).isEqualTo("2020-01-01")
            assertThat(mottasbasertpaa).isEqualTo("botid")
            assertThat(ytelse).isEqualTo("10")
            assertThat(startdatoretttilytelse).isEqualTo("2023-01-01")
            with(requireNotNull(beloep).first()) {
                assertThat(betalingshyppighetytelse).isEqualTo(Betalingshyppighet.maaned_12_per_aar)
                assertThat(valuta).isEqualTo("EUR")
                assertThat(beloep).isEqualTo("1000")
                assertThat(gjeldendesiden).isEqualTo("2020-01-01")
            }
        }
        // arbeidsforhold
        with(requireNotNull(pensjon.bruker!!.arbeidsforhold!!.first().inntekt!!.first())) {
            assertThat(betalingshyppighetinntekt).isEqualTo("03")
            assertThat(beloeputbetaltsiden).isEqualTo("2002-02-02")
            assertThat(valuta).isEqualTo("NOK")
            assertThat(beloep).isEqualTo("999")
        }
    }
}