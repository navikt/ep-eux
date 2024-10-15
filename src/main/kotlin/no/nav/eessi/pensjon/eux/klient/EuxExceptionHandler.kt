package no.nav.eessi.pensjon.eux.klient

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import kotlin.reflect.KFunction

open class EuxExceptionHandler(open var overrideWaitTimes: Long = 1000L) {
    private val logger: Logger by lazy { LoggerFactory.getLogger(EuxExceptionHandler::class.java) }

    @Throws(Throwable::class)
    fun <T> retryHelper(func: () -> T, maxAttempts: Int = 3, skipError: List<HttpStatus>? = emptyList()): T {
        val orgMetode = Thread.currentThread().stackTrace[2].methodName
        var exception: Throwable? = null
        var count = 0

        while (count < maxAttempts) {
            try {
                if (count > 0) logRetry(count, orgMetode, null)
                return func.invoke()
            } catch (ex: Throwable) {
                if (isSkippableError(ex, skipError)) {
                    logSkippedError(orgMetode, ex)
                    throw ex
                }
                count++
                logRetry(count, orgMetode, ex.message)

                exception = ex
                Thread.sleep(overrideWaitTimes)
            }
        }

        logger.error("Feilet å kontakte eux melding: ${exception?.message}", exception)

        throw exception ?: IllegalStateException("Unexpected failure without exception")
    }

    private fun isSkippableError(ex: Throwable, skipError: List<HttpStatus>?): Boolean {
        return ex is HttpClientErrorException && !skipError.isNullOrEmpty() && skipError.contains(ex.statusCode)
    }

    private fun logRetry(count: Int, functionName: String, errorMessage: String?) {
        logger.warn("""
            Funksjon: $functionName, feilet å kontakte eux prøver på nytt. 
            Forsøk: $count
            ${errorMessage?.let { "Feilmelding: \"$it\"" } ?: ""}""".trimIndent()
        )
    }

    private fun logSkippedError(functionName: String, ex: Throwable) {
        logger.warn("$functionName feilet å kontakte eux, feilmelding: ${ex.message}, ${if (ex is HttpClientErrorException) ex.statusCode else "N/A"} ligger i listen over ignorerte exceptions for retry")
    }
}
//--- Disse er benyttet av restTemplateErrorhandler  -- start
class IkkeFunnetException(message: String) : ResponseStatusException(HttpStatus.NOT_FOUND, message)

class RinaIkkeAutorisertBrukerException(message: String?) : ResponseStatusException(HttpStatus.UNAUTHORIZED, message)

class ForbiddenException(message: String?) : ResponseStatusException(HttpStatus.FORBIDDEN, message)

class EuxRinaServerException(message: String?) : ResponseStatusException(HttpStatus.NOT_FOUND, message)

class GenericUnprocessableEntity(message: String) : ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, message)

class GatewayTimeoutException(message: String?) : ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, message)

class ServerException(message: String?) : ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message)

class EuxConflictException(message: String?) : ResponseStatusException(HttpStatus.CONFLICT, message)

//--- Disse er benyttet av restTemplateErrorhandler  -- slutt

class SedDokumentIkkeOpprettetException(message: String) : ResponseStatusException(HttpStatus.NOT_FOUND, message)

class SedDokumentIkkeLestException(message: String?) : ResponseStatusException(HttpStatus.NOT_FOUND, message)

class EuxGenericServerException(message: String?) : ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message)