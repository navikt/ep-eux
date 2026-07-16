package no.nav.eessi.pensjon.eux.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BucInfoTest {

    @Test
    fun `skal normalisere BucInfo og motparter`() {
        val original = BucInfo(
            sakTittel = "  Min sak  ",
            sakType = " H070 ",
            internasjonalSakId = " 123456 ",
            erSakseier = " true ",
            motparter = listOf(
                Motparter(
                    motpartLand = " SWEDEN ",
                    motpartLandkode = " SE",
                    motpartId = " 987654 "
                ),
                Motparter(
                    motpartLand = "   ",
                    motpartLandkode = null,
                    motpartId = " ABC "
                )
            ),
            sistEndretDato = " 2026-07-16 ",
            opprettetDato = "   "
        )

        val result = original.normalisert()

        assertEquals("Min sak", result.sakTittel)
        assertEquals("H070", result.sakType)
        assertEquals("123456", result.internasjonalSakId)
        assertEquals("true", result.erSakseier)
        assertEquals("2026-07-16", result.sistEndretDato)

        val firstMotpart = result.motparter!![0]

        assertEquals("SWEDEN", firstMotpart.motpartLand)
        assertEquals("987654", firstMotpart.motpartId)

        val secondMotpart = result.motparter[1]

        assertEquals("ABC", secondMotpart.motpartId)

        // copy() gir en uendret versjon
        assertEquals("  Min sak  ", original.sakTittel)
        assertEquals(" SWEDEN ", original.motparter!![0].motpartLand)
    }
}