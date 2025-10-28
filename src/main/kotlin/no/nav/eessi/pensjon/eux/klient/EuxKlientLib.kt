package no.nav.eessi.pensjon.eux.klient

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.eessi.pensjon.eux.model.InstitusjonDetalj
import no.nav.eessi.pensjon.eux.model.buc.Buc
import no.nav.eessi.pensjon.eux.model.buc.Participant
import no.nav.eessi.pensjon.eux.model.buc.PreviewPdf
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import no.nav.eessi.pensjon.eux.model.sed.SED
import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder
import java.util.*


open class EuxKlientLib(private val euxRestTemplate: RestTemplate, override var overrideWaitTimes: Long = 5000L) : EuxExceptionHandler(overrideWaitTimes) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(EuxKlientLib::class.java) }

    fun hentSedJson(rinaSakId: String, dokumentId: String, skipError: List<HttpStatus>? = emptyList()): String? {
        logger.info("Henter SED for rinaSakId: $rinaSakId , dokumentId: $dokumentId")
        return retryHelper(
            func = { euxRestTemplate.getForObject("/buc/$rinaSakId/sed/$dokumentId", String::class.java) },
            maxAttempts = 3,
            skipError = skipError
        )
    }

    inline fun <reified T : SED> hentSed (rinaSakId: String, dokumentId: String): T? {
        return hentSedJson(rinaSakId, dokumentId)?.let { mapJsonToAny(it) }
    }

    fun hentBucJson(rinaSakId: String, skipError: List<HttpStatus>? = emptyList()): String?{
        logger.info("Henter BUC (RinaSakId: $rinaSakId)")

        return retryHelper(
            func = { euxRestTemplate.getForObject("/buc/$rinaSakId", String::class.java)},
            maxAttempts = 3,
            skipError = skipError
        )
    }
    fun hentBuc(rinaSakId: String): Buc? {
        return hentBucJson(rinaSakId)?.let { mapJsonToAny(it) }
    }

    fun hentSedMetadata(rinasakId: String, dokumentId: String): SedMetadata? {
        logger.info("Henter SED metadata for rinaSakId: $rinasakId , dokumentId: $dokumentId")

        val response = euxRestTemplate.getForObject("/buc/$rinasakId/sed/$dokumentId/oversikt", String::class.java)
        return response?.let { mapJsonToAny<SedMetadata>(it) }

    }

    fun settSensitivSak(rinaSakId: String): Boolean {
        logger.info("Setter BUC (RinaSakId: $rinaSakId) som sensitiv.")

        val response = euxRestTemplate.exchange(
            "/buc/$rinaSakId/sensitivsak",
            HttpMethod.PUT,
            null,
            String::class.java
        )

        return response.statusCode == HttpStatus.OK || response.statusCode == HttpStatus.NO_CONTENT
    }

    fun hentAlleDokumentfiler(rinaSakId: String, dokumentId: String, skipError: List<HttpStatus>? = emptyList()): SedDokumentfiler? {
        logger.info("Henter PDF for SED og tilhørende vedlegg for rinaSakId: $rinaSakId , dokumentId: $dokumentId")
        return retryHelper(
            func = { euxRestTemplate.getForObject("/buc/$rinaSakId/sed/$dokumentId/filer", SedDokumentfiler::class.java) },
            maxAttempts = 3,
            skipError = skipError
        )
    }

    private fun <T> execute(block: () -> T): T? {
        try {
            return block.invoke()
        } catch (ex: Exception) {
            logger.error("Ukjent feil oppsto: ", ex)
            throw ex
        }
    }
    @Throws(EuxGenericServerException::class, SedDokumentIkkeOpprettetException::class)
    fun opprettSvarSed(navSEDjson: String,
                       euxCaseId: String,
                       parentDocumentId: String): BucSedResponse {
        val response = euxRestTemplate.postForEntity(
            "/buc/$euxCaseId/sed/$parentDocumentId/svar",
            HttpEntity(navSEDjson, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }),
            String::class.java
        )
        return BucSedResponse(euxCaseId, response.body!!)
    }


    //ny SED på ekisterende type eller ny svar SED på ekisternede rina
    @Throws(EuxGenericServerException::class, SedDokumentIkkeOpprettetException::class)
    fun opprettSed(navSEDjson: String,
                   euxCaseId: String): BucSedResponse {

        val response = try {
            euxRestTemplate.postForEntity(
                "/buc/$euxCaseId/sed?ventePaAksjon=false",
                HttpEntity(navSEDjson, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }),
                String::class.java
            )
        } catch (ex: Exception) {
            logger.error("En feil oppstod ${ex.message}", ex)
            throw ex
        }
        return BucSedResponse(euxCaseId, response.body!!)

    }

    fun getRinaUrl(): String {
        val rinaCallid = "-1-11-111"
        val path = "/url/buc/$rinaCallid"
        val response = euxRestTemplate.exchange(path, HttpMethod.GET, null, String::class.java)

        val url =  response.body ?: run {
            logger.error("Feiler ved lasting av navSed: $path")
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Feiler ved response av RinaURL")
        }
        return url.replace(rinaCallid, "").also { logger.info("Url til Rina: $it") }
    }

    fun getSedOnBucByDocumentIdNotAsSystemUser(euxCaseId: String, documentId: String, skipError: List<HttpStatus>? = emptyList()): String =
        getSedOnBucByDocumentId(euxCaseId, documentId, euxRestTemplate, skipError)

    protected fun getSedOnBucByDocumentId(euxCaseId: String, documentId: String, restTemplate: RestTemplate, skipError: List<HttpStatus>? = emptyList()): String {
        val path = "/buc/$euxCaseId/sed/$documentId"

        val response = retryHelper(
            func = { restTemplate.exchange(path,HttpMethod.GET,null, String::class.java) },
            maxAttempts = 3,
            skipError = skipError)

        return response.body ?: run {
            logger.error("Feiler ved lasting av navSed: $path")
            throw SedDokumentIkkeLestException("Feiler ved lesing av navSED, feiler ved uthenting av SED")
        }
    }

    fun getBucJsonAsNavIdent(euxCaseId: String): String?  = getBucJson(euxCaseId, euxRestTemplate).also { logBucJsonResult(euxCaseId, it, "getBucJsonAsNavIdent") }
    fun getBucJsonAsSystemuser(euxCaseId: String, restTemplate: RestTemplate): String? = getBucJson(euxCaseId, restTemplate).also { logBucJsonResult(euxCaseId, it, "getBucJsonAsSystemuser")}

    fun logBucJsonResult(euxCaseId: String, bucJson: String?, metode: String) {
        if (bucJson == null) {
            logger.warn("Ingen Buc verdi for euxCaseId: $euxCaseId, metode: $metode. Buc er null")
        } else {
            logger.info("getBucJson fra $metode for euxCaseId: $euxCaseId")
        }
    }

    private fun getBucJson(euxCaseId: String, restTemplate: RestTemplate): String? {
        val path = "/buc/$euxCaseId"
        logger.info("getBucJsonWithRest prøver å kontakte EUX $path")

        val response =  restTemplate.exchange(path, HttpMethod.GET, null, String::class.java)
        return response.body ?: throw GenericUnprocessableEntity("Feil ved henting av BUCdata ingen data, euxCaseId $euxCaseId")
    }

    fun getPdfJson(euxCaseId: String, documentId: String): PreviewPdf {

        val path = "/buc/${euxCaseId}/sed/${documentId}/pdf"
        logger.info("getPdfJsonWithRest prøver å kontakte EUX path")

        val response = euxRestTemplate.exchange(
            path,
            HttpMethod.GET,
            HttpEntity("/", HttpHeaders().apply { contentType = MediaType.APPLICATION_PDF }),
            Resource::class.java)
        val filnavn = response.headers.contentDisposition.filename
        val contentType = response.headers.contentType!!.toString()

        val dokumentInnholdBase64 = String(Base64.getEncoder().encode(response.body!!.inputStream.readBytes()))
        return PreviewPdf(dokumentInnholdBase64, filnavn!!, contentType)

    }

    fun getBucDeltakere(euxCaseId: String): List<Participant> {
        logger.info("euxCaseId: $euxCaseId")

        val path = "/buc/$euxCaseId/bucdeltakere"
        val response = euxRestTemplate.exchange(path, HttpMethod.GET,null,String::class.java)

        return mapJsonToAny(response.body!!)
    }

    /**
     * List all institutions connected to RINA.
     */
    fun getInstitutions(bucType: String, landkode: String? = ""): List<InstitusjonDetalj> {
        val url = "/institusjoner?BuCType=$bucType&LandKode=${landkode ?: ""}"

        val responseInstitution = euxRestTemplate.exchange(url, HttpMethod.GET, null, String::class.java)

        return  mapJsonToAny(responseInstitution.body!!)
    }
//    FIXME: Feil i dokumentasjonen
    /**
     * Lister alle rinasaker på valgt fnr eller euxcaseid, eller bucType...
     * fnr er påkrved resten er fritt
     * @param fnr String, fødselsnummer
     * @param euxCaseId String, euxCaseid sak ID
     * @param bucType String, type buc
     * @param status String, status
     * @return List<Rinasak>
     */
    fun getRinasaker(fnr: String? = null, euxCaseId: String? = null): List<Rinasak> {

        val uriComponent = getRinasakerUri("/rinasaker", fnr, euxCaseId)
        logger.debug("** fnr: $fnr, eux: $euxCaseId, buc: NULL, status: OPEN **, Url: ${uriComponent.toUriString()}")
        val response =  euxRestTemplate.exchange(uriComponent.toUriString(), HttpMethod.GET, null, String::class.java)
        return mapJsonToAny(response.body!!)
    }

    fun createBuc(bucType: String): String {
        val correlationId = correlationId()
        val builder = UriComponentsBuilder.fromPath("/buc")
            .queryParam("BuCType", bucType)
            .queryParam("KorrelasjonsId", correlationId)
            .build()

        logger.info("Kontakter EUX for å prøve på opprette ny BUC med korrelasjonId: $correlationId ${builder.toUriString()}")
        val response = euxRestTemplate.exchange(builder.toUriString(), HttpMethod.POST, null, String::class.java)

        response.body?.let { return it } ?: run {
            logger.error("Får ikke opprettet BUC på bucType: $bucType")
            throw IkkeFunnetException("Fant ikke noen euxCaseId på bucType: $bucType")
        }
    }

    private fun correlationId() = MDC.get("x_request_id") ?: UUID.randomUUID().toString()

    fun convertListInstitusjonItemToString(deltakere: List<String>): String {
        return deltakere.joinToString(separator = "") { deltaker ->
            require(deltaker.contains(":")) { "Ikke korrekt format på mottaker/institusjon... "}
            "&mottakere=${deltaker}"
        }
    }

    fun putBucMottakere(euxCaseId: String, institusjoner: List<String>): Boolean {
        val correlationId = correlationId()
        val builder = UriComponentsBuilder.fromPath("/buc/$euxCaseId/mottakere")
            .queryParam("KorrelasjonsId", correlationId)
            .build()
        val url = builder.toUriString() + convertListInstitusjonItemToString(institusjoner)

        logger.debug("Kontakter EUX for å legge til deltager: $institusjoner med korrelasjonId: $correlationId på type: $euxCaseId")

        val result = euxRestTemplate.exchange(url, HttpMethod.PUT, null, String::class.java)

        return result.statusCode == HttpStatus.OK
    }

    fun sendSed(euxCaseId: String, dokumentId : String):  Boolean {
        val url = "/buc/$euxCaseId/sed/$dokumentId/send?ventePaAksjon=false"
        logger.info("Kaller sendSed for buc: $euxCaseId, sed: $dokumentId, path: $url")

        val result: ResponseEntity<String> = euxRestTemplate.postForEntity(
            url,
            HttpEntity<String>(HttpHeaders().apply {
                set("accept", "*/*")
            }), String::class.java
        )

        logger.info("""
            | Response Code: ${result.statusCode}
            | Response Body: ${result.body}""".trimMargin())
        return result.statusCode == HttpStatus.OK
    }

    fun sendTo(euxCaseId: String, dokumentId : String, mottakere: List<String>):  Boolean {
        val correlationId = correlationId()
        val url = UriComponentsBuilder.fromPath("/buc/$euxCaseId/sed/$dokumentId/sendTo")
            .queryParam("KorrelasjonsId", correlationId)
            .queryParam("MottakereId", mottakere.joinToString(separator = " "))
            .build().toUriString()

        val result: ResponseEntity<String> = euxRestTemplate.postForEntity(
            url,
            HttpEntity<String>(HttpHeaders().apply {
                set("accept", "*/*")
            }), String::class.java
        )

        logger.info("""
            | Response Code: ${result.statusCode}
            | Response Body: ${result.body}""".trimMargin())
        return result.statusCode == HttpStatus.OK
    }

    //Brukes av saksbehandlere til å resende dokumenter
    fun resend(dokumentListe: String): HentResponseBody {
        val correlationId = correlationId()
        val url = UriComponentsBuilder.fromPath("/resend/liste")
            .queryParam("KorrelasjonsId", correlationId)
            .queryParam("intervall", 1)
            .build().toUriString()


        val response: ResponseEntity<String> = euxRestTemplate.postForEntity(
            url,
            HttpEntity<String>(
                dokumentListe,
                HttpHeaders().apply {
                set("accept", "*/*")
            }), String::class.java
        )

        logger.info("""
            | Response Code: ${response.statusCode}
            | Response Body: ${response.body}""".trimMargin())
        if(response.statusCode == HttpStatus.OK) {
            return HentResponseBody(HttpStatus.OK, response.body ?: "", Date().toString())
        }
        throw SedDokumentIkkeLestException("Feil ved oversending av dokumenter: ${response.statusCode}")
    }

    //Brukes av saksbehandlere til å resende dokumenter
    fun resendeDokMedrinaId(rinasakId: String, dokumentId: String): HentResponseBody {
        val correlationId = correlationId()
        val url = UriComponentsBuilder.fromPath("/resend/buc/$rinasakId/sed/$dokumentId")
            .queryParam("KorrelasjonsId", correlationId)
            .queryParam("intervall", 1)
            .build().toUriString()


        val response: ResponseEntity<String> = euxRestTemplate.postForEntity(
            url,
            HttpEntity<String>(
                HttpHeaders().apply {
                    set("accept", "*/*")
                }), String::class.java
        )
        logger.info("""
            | Response Code: ${response.statusCode}
            | Response Body: ${response.body}""".trimMargin())
        if(response.statusCode == HttpStatus.OK) {
            return HentResponseBody(HttpStatus.OK, response.body ?: "", Date().toString())
        }
        throw SedDokumentIkkeLestException("Feil ved oversending av dokumenter: ${response.statusCode}")
    }

    fun updateSedOnBuc(euxCaseId: String, dokumentId: String, sedPayload: String): Boolean {
        val path = "/buc/$euxCaseId/sed/$dokumentId?ventePaAksjon=false"

        val result =
            euxRestTemplate.exchange(
                path,
                HttpMethod.PUT,
                HttpEntity(sedPayload, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }),
                String::class.java
            )

        return result.statusCode == HttpStatus.OK
    }

    fun lagPdf(jsonPdf: String) : PreviewPdf? {
        val path = "/sed/pdf"

        return try {
            val response = euxRestTemplate.exchange(
                path,
                HttpMethod.POST,
                HttpEntity(jsonPdf, HttpHeaders().apply {
                    contentType = MediaType.APPLICATION_JSON
                    accept = listOf(MediaType.APPLICATION_PDF, MediaType.APPLICATION_JSON)
                }),
                ByteArray::class.java
            )
            logger.debug("pdf response body: {}", response.body)

            if (response.statusCode.is2xxSuccessful && response.body != null) {
                val filnavn = response.headers.contentDisposition.filename ?: "unknown.pdf"
                val pdfContent = Base64.getEncoder().encodeToString(response.body)

                logger.debug("PDF ble laget: filename=$filnavn, contentType=application/json;charset=utf-8")
                PreviewPdf(pdfContent, filnavn, "application/json;charset=utf-8")
            } else {
                logger.warn("En feil oppstod under generering av pdf: Status code: ${response.statusCode}")
                null
            }
        } catch (e: Exception) {
            logger.error("En feil oppstod under generering av pdf: ${e.message}", e)
            null
        }
    }

    companion object {

        fun getRinasakerUri(url: String, fnr: String? = null, euxCaseId: String? = null): UriComponents {
            require(!(fnr == null && euxCaseId == null)) {
                "Minst et søkekriterie må fylles ut for å få et resultat fra Rinasaker"
            }

            return UriComponentsBuilder.fromPath(url).apply {
                when {
                    euxCaseId != null && fnr == null -> queryParam("rinasaksnummer", euxCaseId)
                    euxCaseId == null -> queryParam("fødselsnummer", fnr)
                    else -> {
                        queryParam("fødselsnummer", fnr ?: "")
                        queryParam("rinasaksnummer", euxCaseId ?: "")
                    }
                }
                queryParam("status", "\"open\"")
            }.build()
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class HentResponseBody(
        val status: HttpStatus? = null,
        val messages: String? = null,
        val timestamp: String? = null
    )
}
