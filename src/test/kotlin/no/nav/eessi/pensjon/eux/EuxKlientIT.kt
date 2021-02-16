package no.nav.eessi.pensjon.eux

import io.mockk.every
import io.mockk.mockk
import no.nav.eessi.pensjon.eux.model.document.ForenkletSED
import no.nav.eessi.pensjon.eux.model.document.SedStatus
import no.nav.eessi.pensjon.security.sts.STSService
import org.apache.commons.codec.binary.Base64
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate

/**
 * Kun for testing lokalt.
 *
 * Krever litt forarbeid for å ta i bruk. Må ha Kube-Forwarder og Naisdevice.
 *  1. Start og koble til Naisdevice
 *  2. Velg dev-fss som context med kubectl
 *  3. Kjør Kube-Forwarder mot EUX og STS
 *      - kubectl port-forward svc/security-token-service 8088:80
 *      - kubectl -n q2 port-forward svc/eux-rina-api 8086:80
 *  4. Legg til srveessipensjon sitt passord i IntelliJ som Environment Variable. Eks: PASSWORD=passord123
 *  5. Testen burde nå kjøre uten problemer.
 *
 */
@Disabled("Kun for testing lokalt, f.eks. når ny funksjonalitet legges til.")
@TestInstance(Lifecycle.PER_CLASS)
internal class EuxKlientIT {

    private val stsBaseUrl = "http://localhost:8088" // Use your preferred localhost port

    private val mockStsService = mockk<STSService>()

    private lateinit var klient: EuxKlient

    @BeforeAll
    fun beforeAll() {
        val configuration = EuxConfiguration(mockStsService)

        ReflectionTestUtils.setField(configuration, "euxUrl", "http://localhost:8086/cpi")

        val restTemplate = configuration.euxOidcRestTemplate(RestTemplateBuilder())

        klient = EuxKlient(restTemplate)

        every { mockStsService.getSystemOidcToken() } returns createToken() // Legg inn token her for testing
    }

    @Test
    fun hentAlleDokumentfiler() {
        val dokument = klient.hentAlleDokumentfiler("1270228", "cec23ba808364e9188cf5d4bcf9350b7")

        assertNotNull(dokument)
    }

    @Test
    fun hentSed() {
        val json = klient.hentSedJson("1270228", "cec23ba808364e9188cf5d4bcf9350b7")

        assertNotNull(json)
    }

    @Test
    fun settSensitivSak() {
        val response = klient.settSensitivSak("1221544")

        assertTrue(response)
    }

    @Test
    fun hentBucDeltakere() {
        val deltakere = klient.hentBucDeltakere("1213513")

        assertFalse(deltakere.isEmpty())
    }


    @Test
    fun hentBucDokumenter() {
        val buc = klient.hentBuc("1213513")

        val result = buc?.documents
            ?.filter { it.id != null }
            ?.map { ForenkletSED(it.id!!, it.type, SedStatus.fra(it.status)) }

        assertNotNull(buc)
    }


    private fun createToken(): String {
        val rt = RestTemplate()

        val password = getPassword()

        val encodedAuth = Base64.encodeBase64("srveessipensjon:$password".toByteArray())
        val authHeader = String(encodedAuth)

        val headers = HttpHeaders()
        headers["Authorization"] = "Basic $authHeader"

        val response = rt.exchange(
            "$stsBaseUrl/rest/v1/sts/token?grant_type=client_credentials&scope=openid",
            HttpMethod.POST,
            HttpEntity(null, headers),
            Token::class.java
        )

        return response.body?.access_token ?: throw RuntimeException("No access token!")
    }

    private fun getPassword(): String {
        val pwd = System.getenv("PASSWORD")?.trim()

        return pwd.takeUnless { it.isNullOrBlank() }
            ?: throw RuntimeException("Mangler passord! Legg til som miljøvariabel (eks. PASSWORD=passord123)")
    }

    private class Token(
        val access_token: String?,
        val token_type: String?,
        val expires_in: Long
    )

}
