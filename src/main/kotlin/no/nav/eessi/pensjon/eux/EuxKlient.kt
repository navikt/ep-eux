package no.nav.eessi.pensjon.eux

import no.nav.eessi.pensjon.eux.model.buc.Buc
import no.nav.eessi.pensjon.eux.model.buc.ParticipantsItem
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import no.nav.eessi.pensjon.security.sts.typeRef
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate

@Component
class EuxKlient(private val euxOidcRestTemplate: RestTemplate) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(EuxKlient::class.java) }

    @Retryable(
        include = [HttpStatusCodeException::class],
        exclude = [HttpClientErrorException.NotFound::class],
        backoff = Backoff(delay = 30000L, maxDelay = 3600000L, multiplier = 3.0)
    )
    internal fun hentAlleDokumentfiler(rinaSakId: String, dokumentId: String): SedDokumentfiler? {
        logger.info("Henter PDF for SED og tilhørende vedlegg for rinaSakId: $rinaSakId , dokumentId: $dokumentId")

        return execute {
            euxOidcRestTemplate.getForObject(
                "/buc/$rinaSakId/sed/$dokumentId/filer",
                SedDokumentfiler::class.java
            )
        }
    }

    @Retryable(
        include = [HttpStatusCodeException::class],
        exclude = [HttpClientErrorException.NotFound::class],
        backoff = Backoff(delay = 30000L, maxDelay = 3600000L, multiplier = 3.0)
    )
    internal fun hentSedJson(rinaSakId: String, dokumentId: String): String? {
        logger.info("Henter SED for rinaSakId: $rinaSakId , dokumentId: $dokumentId")

        val response = execute {
            euxOidcRestTemplate.exchange(
                "/buc/$rinaSakId/sed/$dokumentId",
                HttpMethod.GET,
                null,
                String::class.java
            )
        }

        return response?.body
    }

    internal fun hentBuc(rinaSakId: String): Buc? {
        logger.info("Henter BUC (RinaSakId: $rinaSakId)")

        return execute {
            euxOidcRestTemplate.getForObject(
                "/buc/$rinaSakId",
                Buc::class.java
            )
        }
    }

    internal fun settSensitivSak(rinaSakId: String): Boolean {
        logger.info("Setter BUC (RinaSakId: $rinaSakId) som sensitiv.")

        val response = execute {
            euxOidcRestTemplate.exchange(
                "/buc/$rinaSakId/sensitivsak",
                HttpMethod.PUT,
                null,
                String::class.java
            )
        }

        return response?.statusCode == HttpStatus.OK || response?.statusCode == HttpStatus.NO_CONTENT
    }

    @Retryable(
        include = [HttpStatusCodeException::class],
        exclude = [HttpClientErrorException.NotFound::class],
        backoff = Backoff(delay = 30000L, maxDelay = 3600000L, multiplier = 3.0)
    )
    internal fun hentBucDeltakere(rinaSakId: String): List<ParticipantsItem> {
        logger.info("Henter liste over deltakere i Buc (RinaSakId: $rinaSakId)")

        val response = execute {
            euxOidcRestTemplate.exchange(
                "/buc/${rinaSakId}/bucdeltakere",
                HttpMethod.GET,
                null,
                typeRef<List<ParticipantsItem>>()
            )
        }

        // TODO: emptyList() hvis ingenting funnet?
        return response?.body
            ?: throw RuntimeException("Feil ved henting av BucDeltakere: Ingen data på rinaSakId $rinaSakId")
    }

    private fun <T> execute(block: () -> T): T? {
        try {
            return block.invoke()
        } catch (ex: Exception) {
            if (ex is HttpStatusCodeException && ex.statusCode == HttpStatus.NOT_FOUND)
                return null

            logger.error("Ukjent feil oppsto: ", ex)
            throw ex
        }
    }

}
