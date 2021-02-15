package no.nav.eessi.pensjon.eux

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate
import kotlin.reflect.KClass

internal class EuxKlientTest {

    private val mockRestTemplate = mockk<RestTemplate>()

    private val klient = EuxKlient(mockRestTemplate)

    companion object {
        private const val RINA_ID = "1"
        private const val DOK_ID = "1"
    }

    @Nested
    inner class HentAlleDokumentfiler {
        @Test
        fun `Dokument uten vedlegg`() {
            val fileContent = javaClass.getResource("/pdf/pdfResponseUtenVedlegg.json").readText()

            every {
                mockRestTemplate.getForObject("/buc/$RINA_ID/sed/$DOK_ID/filer", SedDokumentfiler::class.java)
            } returns mapJsonToAny(fileContent, SedDokumentfiler::class)

            val sedDokument = klient.hentAlleDokumentfiler(RINA_ID, DOK_ID)!!

            Assertions.assertNotNull(sedDokument)
            Assertions.assertNull(sedDokument.vedlegg)

            verify(exactly = 1) {
                mockRestTemplate.getForObject(
                    "/buc/$RINA_ID/sed/$DOK_ID/filer",
                    SedDokumentfiler::class.java
                )
            }
        }

        @Test
        fun `Dokument uten MimeType paa vedlegg`() {
            val fileContent = javaClass.getResource("/pdf/pdfResponseMedManglendeMimeType.json").readText()
            every {
                mockRestTemplate.getForObject("/buc/$RINA_ID/sed/$DOK_ID/filer", SedDokumentfiler::class.java)
            } returns mapJsonToAny(fileContent, SedDokumentfiler::class)

            val sedDokument = klient.hentAlleDokumentfiler(RINA_ID, DOK_ID)!!

            val vedlegg = sedDokument.vedlegg!!.single()
            Assertions.assertEquals("ManglendeMimeType.png", vedlegg.filnavn)
            Assertions.assertNull(vedlegg.mimeType)
            Assertions.assertNotNull(vedlegg.innhold)

            verify(exactly = 1) {
                mockRestTemplate.getForObject(
                    "/buc/$RINA_ID/sed/$DOK_ID/filer",
                    SedDokumentfiler::class.java
                )
            }
        }

        @Test
        fun `Dokument ikke funnet kaster 404 NOT FOUND og returnerer NULL`() {
            every {
                mockRestTemplate.getForObject("/buc/$RINA_ID/sed/$DOK_ID/filer", SedDokumentfiler::class.java)
            } throws HttpClientErrorException(HttpStatus.NOT_FOUND)

            Assertions.assertNull(klient.hentAlleDokumentfiler(RINA_ID, DOK_ID))

            verify(exactly = 1) {
                mockRestTemplate.getForObject(
                    "/buc/$RINA_ID/sed/$DOK_ID/filer",
                    SedDokumentfiler::class.java
                )
            }
        }

        @Test
        fun `Ukjent HttpClientErrorException kastes videre`() {
            every {
                mockRestTemplate.getForObject("/buc/$RINA_ID/sed/$DOK_ID/filer", SedDokumentfiler::class.java)
            } throws HttpClientErrorException(HttpStatus.UNAUTHORIZED)

            assertThrows<HttpClientErrorException> {
                klient.hentAlleDokumentfiler(RINA_ID, DOK_ID)
            }

            verify(exactly = 1) {
                mockRestTemplate.getForObject(
                    "/buc/$RINA_ID/sed/$DOK_ID/filer",
                    SedDokumentfiler::class.java
                )
            }
        }

        @Test
        fun `Ukjent exception kastes videre`() {
            every {
                mockRestTemplate.getForObject("/buc/$RINA_ID/sed/$DOK_ID/filer", SedDokumentfiler::class.java)
            } throws Exception("Noe gikk galt")

            assertThrows<Exception> {
                klient.hentAlleDokumentfiler(RINA_ID, DOK_ID)
            }

            verify(exactly = 1) {
                mockRestTemplate.getForObject(
                    "/buc/$RINA_ID/sed/$DOK_ID/filer",
                    SedDokumentfiler::class.java
                )
            }
        }
    }

    @Nested
    inner class HentSedJson {
        @Test
        fun `SED Json skal ikke endres av klienten`() {
            val json = """{"key":"value"}"""

            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            } returns ResponseEntity.ok(json)

            val result = klient.hentSedJson(RINA_ID, DOK_ID)

            assertEquals(json, result)

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            }
        }

        @Test
        fun `Henting av SED gir 404, skal returnere null`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            } throws HttpClientErrorException(HttpStatus.NOT_FOUND)

            val result = klient.hentSedJson(RINA_ID, DOK_ID)

            assertNull(result)

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            }
        }

        @Test
        fun `Henting av SED gir ukjent HttpStatusCodeException, skal kaste feil`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            } throws HttpClientErrorException(HttpStatus.UNAUTHORIZED)

            assertThrows<HttpStatusCodeException> {
                klient.hentSedJson(RINA_ID, DOK_ID)
            }

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            }
        }

        @Test
        fun `Henting av SED gir ukjent generisk Exception, skal kaste feil`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            } throws RuntimeException("Tullete svar fra EUX")

            assertThrows<Exception> {
                klient.hentSedJson(RINA_ID, DOK_ID)
            }

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID",
                    HttpMethod.GET,
                    null,
                    String::class.java
                )
            }
        }
    }

    private fun <T : Any> mapJsonToAny(json: String, type: KClass<T>): T =
        jacksonObjectMapper().readValue(json, type.java)
}
