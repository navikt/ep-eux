package no.nav.eessi.pensjon.eux.klient

import no.nav.eessi.pensjon.eux.model.buc.Buc
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import no.nav.eessi.pensjon.eux.model.sed.SED
import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.client.RestTemplate

class EuxKlientLib(private val euxRestTemplate: RestTemplate) {

    private val logger: Logger by lazy { LoggerFactory.getLogger(EuxKlientLib::class.java) }

    fun hentSedJson(rinaSakId: String, dokumentId: String): String? {
        logger.info("Henter SED for rinaSakId: $rinaSakId , dokumentId: $dokumentId")

        return euxRestTemplate.getForObject(
            "/buc/$rinaSakId/sed/$dokumentId", String::class.java
        )
    }

    inline fun <reified T : SED> hentSed (rinaSakId: String, dokumentId: String): T? {
        return hentSedJson(rinaSakId, dokumentId)?.let { mapJsonToAny(it) }
    }

    fun hentBucJson(rinaSakId: String): String?{
        logger.info("Henter BUC (RinaSakId: $rinaSakId)")

        return euxRestTemplate.getForObject(
            "/buc/$rinaSakId", String::class.java)

    }
    fun hentBuc(rinaSakId: String): Buc? {
        return hentBucJson(rinaSakId)?.let { mapJsonToAny(it) }
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

    fun hentAlleDokumentfiler(rinaSakId: String, dokumentId: String): SedDokumentfiler? {
        logger.info("Henter PDF for SED og tilhørende vedlegg for rinaSakId: $rinaSakId , dokumentId: $dokumentId")
        return execute {
            euxRestTemplate.getForObject(
                "/buc/$rinaSakId/sed/$dokumentId/filer",
                SedDokumentfiler::class.java
            )
        }
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
