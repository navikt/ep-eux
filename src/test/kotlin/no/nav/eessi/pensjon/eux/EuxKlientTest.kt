package no.nav.eessi.pensjon.eux

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import no.nav.eessi.pensjon.eux.model.buc.BucType
import no.nav.eessi.pensjon.eux.model.buc.Institusjon
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import no.nav.eessi.pensjon.security.sts.typeRef
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
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

    @AfterEach
    fun afterEach() {
        clearMocks(mockRestTemplate)
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

            assertNotNull(sedDokument)
            assertNull(sedDokument.vedlegg)

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
            assertEquals("ManglendeMimeType.png", vedlegg.filnavn)
            assertNull(vedlegg.mimeType)
            assertNotNull(vedlegg.innhold)

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

            assertNull(klient.hentAlleDokumentfiler(RINA_ID, DOK_ID))

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

    @Nested
    inner class SendDokument {
        @Test
        fun `Send SED med 200 OK`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            } returns ResponseEntity.ok().build()

            assertTrue(klient.sendDokument(RINA_ID, DOK_ID))

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            }
        }

        @Test
        fun `Send SED gir feil status`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            } returns ResponseEntity.badRequest().build()

            assertFalse(klient.sendDokument(RINA_ID, DOK_ID))

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            }
        }

        @Test
        fun `Send SED kaster 404 feil`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            } throws HttpClientErrorException(HttpStatus.NOT_FOUND)

            assertFalse(klient.sendDokument(RINA_ID, DOK_ID))

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            }
        }

        @Test
        fun `Send SED kaster feil`() {
            every {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            } throws HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)

            assertThrows<HttpClientErrorException> {
                klient.sendDokument(RINA_ID, DOK_ID)
            }

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/buc/$RINA_ID/sed/$DOK_ID/send",
                    HttpMethod.POST,
                    null,
                    String::class.java
                )
            }
        }
    }

    @Nested
    inner class HentInstitusjoner {
        @Test
        fun `Sjekk at henting av institusjoner fungerer som forventet`() {
            val json = javaClass.getResource("/buc/institusjoner.json").readText()
            val institusjoner = mapJsonToAny(json, typeRefs<List<Institusjon>>())

            every {
                mockRestTemplate.exchange(
                    any<String>(),
                    HttpMethod.GET,
                    null,
                    typeRef<List<Institusjon>>()
                )
            } returns ResponseEntity.ok(institusjoner)

            val resultat = klient.hentInstitusjoner(BucType.P_BUC_02, "NO")

            assertEquals(6, resultat.size)
            assertEquals(institusjoner, resultat)

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/institusjoner?BuCType=P_BUC_02&LandKode=NO",
                    HttpMethod.GET,
                    null,
                    typeRef<List<Institusjon>>()
                )
            }
        }

        @Test
        fun `Sjekk at henting av institusjoner med tom landkode fungerer`() {
            val json = javaClass.getResource("/buc/institusjoner.json").readText()
            val institusjoner = mapJsonToAny(json, typeRefs<List<Institusjon>>())

            every {
                mockRestTemplate.exchange(
                    any<String>(),
                    HttpMethod.GET,
                    null,
                    typeRef<List<Institusjon>>()
                )
            } returns ResponseEntity.ok(institusjoner)

            val resultat = klient.hentInstitusjoner(BucType.P_BUC_02)

            assertEquals(6, resultat.size)
            assertEquals(institusjoner, resultat)

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/institusjoner?BuCType=P_BUC_02&LandKode=",
                    HttpMethod.GET,
                    null,
                    typeRef<List<Institusjon>>()
                )
            }
        }

        @Test
        fun `Feil ved henting av institusjoner gir tom liste`() {
            every {
                mockRestTemplate.exchange(
                    any<String>(),
                    HttpMethod.GET,
                    null,
                    typeRef<List<Institusjon>>()
                )
            } throws HttpClientErrorException(HttpStatus.NOT_FOUND)

            val resultat = klient.hentInstitusjoner(BucType.P_BUC_02, "NO")

            assertTrue(resultat.isEmpty())

            verify(exactly = 1) {
                mockRestTemplate.exchange(
                    "/institusjoner?BuCType=P_BUC_02&LandKode=NO",
                    HttpMethod.GET,
                    null,
                    typeRef<List<Institusjon>>()
                )
            }
        }
    }

    private fun <T : Any> mapJsonToAny(json: String, type: KClass<T>): T =
        jacksonObjectMapper().readValue(json, type.java)

    private fun <T : Any> mapJsonToAny(json: String, type: TypeReference<T>): T =
        jacksonObjectMapper().readValue(json, type)

    private inline fun <reified T : Any> typeRefs(): TypeReference<T> = object : TypeReference<T>() {}
}
