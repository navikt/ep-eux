package no.nav.eessi.pensjon.eux

import com.fasterxml.jackson.core.type.TypeReference
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.model.buc.Organisation
import no.nav.eessi.pensjon.eux.model.buc.ParticipantsItem
import no.nav.eessi.pensjon.eux.model.document.EuxDokument
import no.nav.eessi.pensjon.eux.model.document.MimeType
import no.nav.eessi.pensjon.eux.model.document.SedDokument
import no.nav.eessi.pensjon.eux.model.sed.SED
import no.nav.eessi.pensjon.eux.model.sed.SedType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle

@TestInstance(Lifecycle.PER_CLASS)
internal class EuxServiceTest {

    private val rinaSakId = "123"
    private val dokumentId = "456"

    private val mockKlient = mockk<EuxKlient>()

    private val euxService = EuxService(mockKlient)

    @BeforeAll
    fun beforeAll() {
        euxService.initMetrics()
    }

    @Test
    fun hentSed() {
        val expected = SED(SedType.P2000)

        every { mockKlient.hentSedJson(any(), any()) } returns expected.toJson()

        val result = euxService.hentSed(rinaSakId, dokumentId, object : TypeReference<SED>() {})

        assertEquals(expected, result)

        verify(exactly = 1) { mockKlient.hentSedJson(rinaSakId, dokumentId) }
    }

    @Test
    fun hentAlleDokumentfiler() {
        val expected = SedDokument(EuxDokument("filnavn", MimeType.PDF, "innhold"), emptyList())

        every { mockKlient.hentAlleDokumentfiler(any(), any()) } returns expected

        val result = euxService.hentAlleDokumentfiler(rinaSakId, dokumentId)

        assertEquals(expected, result)

        verify(exactly = 1) { mockKlient.hentAlleDokumentfiler(rinaSakId, dokumentId) }
    }

    @Test
    fun settSensitivSak() {
        every { mockKlient.settSensitivSak(any()) } returns true

        val result = euxService.settSensitivSak(rinaSakId)

        assertTrue(result)

        verify(exactly = 1) { mockKlient.settSensitivSak(rinaSakId) }
    }


    @Test
    fun hentBucDeltakere() {
        val participants = listOf(ParticipantsItem("role", Organisation(), true))

        every { mockKlient.hentBucDeltakere(any()) } returns participants

        val result = euxService.hentBucDeltakere(rinaSakId)

        assertEquals(participants, result)

        verify(exactly = 1) { mockKlient.hentBucDeltakere(rinaSakId) }
    }

}
