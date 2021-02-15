package no.nav.eessi.pensjon.eux

import com.fasterxml.jackson.core.type.TypeReference
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.model.buc.Buc
import no.nav.eessi.pensjon.eux.model.buc.DocumentsItem
import no.nav.eessi.pensjon.eux.model.buc.Organisation
import no.nav.eessi.pensjon.eux.model.buc.ParticipantsItem
import no.nav.eessi.pensjon.eux.model.document.MimeType
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import no.nav.eessi.pensjon.eux.model.document.SedVedlegg
import no.nav.eessi.pensjon.eux.model.sed.SED
import no.nav.eessi.pensjon.eux.model.sed.SedType
import org.junit.jupiter.api.AfterEach
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

    @AfterEach
    fun afterEach() {
        confirmVerified(mockKlient)
        clearAllMocks()
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
        val expected = SedDokumentfiler(SedVedlegg("filnavn", MimeType.PDF, "innhold"), emptyList())

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

    @Test
    fun hentBucDokumenter() {
        val documents = listOf(
            DocumentsItem(id = "1", type = SedType.P1000, status = "empty"),
            DocumentsItem(id = "2", type = SedType.X001, status = "empty"),
            DocumentsItem(id = "3", type = SedType.R004, status = "sent"),
            DocumentsItem(id = "4", type = SedType.P8000, status = "empty"),
            DocumentsItem(id = "5", type = SedType.P3000_BG, status = "received"),
            DocumentsItem(id = null, type = SedType.X005, status = "received") // skal ignoreres pga id=null
        )

        every { mockKlient.hentBuc(any()) } returns Buc(documents = documents)

        val result = euxService.hentBucDokumenter(rinaSakId)

        assertEquals(5, result.size)

        verify(exactly = 1) { mockKlient.hentBuc(rinaSakId) }
    }

    @Test
    fun hentBucDokumenter_manglerInnhold() {
        every { mockKlient.hentBuc(any()) } returns null
        assertTrue(euxService.hentBucDokumenter(rinaSakId).isEmpty())

        every { mockKlient.hentBuc(any()) } returns Buc(documents = null)
        assertTrue(euxService.hentBucDokumenter(rinaSakId).isEmpty())

        verify(exactly = 2) { mockKlient.hentBuc(rinaSakId) }
    }

}
