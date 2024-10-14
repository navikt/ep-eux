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
    fun <T> retryHelper(func: KFunction<T>, maxAttempts: Int = 3, skipError: List<HttpStatus>? = emptyList()): T {
        var failException: Throwable? = null
        var count = 0

        while (count < maxAttempts) {
            try {
                if (count > 0) logRetry(count, func.name, null)
                return func.call()
            } catch (ex: Throwable) {
                if (isSkippableError(ex, skipError)) {
                    logSkippedError(func.name, ex)
                    throw ex
                }
                count++
                logRetry(count, func.name, ex.message)
                failException = ex
                Thread.sleep(overrideWaitTimes)
            }
        }

        logger.error("Feilet å kontakte eux melding: ${failException?.message}", failException)

        throw failException ?: IllegalStateException("Unexpected failure without exception") // Avoid null crash
    }

    private fun isSkippableError(ex: Throwable, skipError: List<HttpStatus>?): Boolean {
        return ex is HttpClientErrorException && !skipError.isNullOrEmpty() && skipError.contains(ex.statusCode)
    }

    private fun logRetry(count: Int, functionName: String, errorMessage: String?) {
        if (errorMessage == null) {
            logger.info("Prøver for $count gang for metode: $functionName")
        } else {
            logger.warn("$functionName feilet å kontakte eux prøver på nytt. nr.: $count, feilmelding: $errorMessage")
        }
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