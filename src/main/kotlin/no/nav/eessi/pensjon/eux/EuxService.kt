package no.nav.eessi.pensjon.eux

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import no.nav.eessi.pensjon.eux.model.buc.Buc
import no.nav.eessi.pensjon.eux.model.buc.Participant
import no.nav.eessi.pensjon.eux.model.document.ForenkletSED
import no.nav.eessi.pensjon.eux.model.document.SedDokumentfiler
import no.nav.eessi.pensjon.eux.model.document.SedStatus
import no.nav.eessi.pensjon.metrics.MetricsHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class EuxService(
    private val klient: EuxKlient,
    @Autowired(required = false) private val metricsHelper: MetricsHelper = MetricsHelper(SimpleMeterRegistry())
) {

    private lateinit var hentSed: MetricsHelper.Metric
    private lateinit var hentBuc: MetricsHelper.Metric
    private lateinit var hentPdf: MetricsHelper.Metric
    private lateinit var settSensitiv: MetricsHelper.Metric
    private lateinit var hentBucDeltakere: MetricsHelper.Metric

    @PostConstruct
    fun initMetrics() {
        hentSed = metricsHelper.init("hentSed", alert = MetricsHelper.Toggle.OFF)
        hentBuc = metricsHelper.init("hentBuc", alert = MetricsHelper.Toggle.OFF)
        hentPdf = metricsHelper.init("hentpdf", alert = MetricsHelper.Toggle.OFF)
        settSensitiv = metricsHelper.init("settSensitiv", alert = MetricsHelper.Toggle.OFF)
        hentBucDeltakere = metricsHelper.init("hentBucDeltakere", alert = MetricsHelper.Toggle.OFF)
    }

    /**
     * Henter SED fra Rina EUX API.
     *
     * @param rinaSakId: Hvilken Rina-sak SED skal hentes fra.
     * @param dokumentId: Hvilket SED-dokument som skal hentes fra spesifisert sak.
     * @param typeRef: [TypeReference] for deserialisering til ønsket objekt.
     *
     * @return Objekt av type <T : Any> som spesifisert i param typeRef.
     */
    fun <T : Any> hentSed(rinaSakId: String, dokumentId: String, typeRef: TypeReference<T>): T? {
        return hentSed.measure {
            val json = klient.hentSedJson(rinaSakId, dokumentId)

            json?.let { mapJsonToAny(it, typeRef) }
        }
    }

    /**
     * Henter SED som JSON fra Rina EUX API.
     *
     * @param rinaSakId: Hvilken Rina-sak SED skal hentes fra.
     * @param dokumentId: Hvilket SED-dokument som skal hentes fra spesifisert sak.
     *
     * @return [String] SED JSON
     */
    fun hentSedJson(rinaSakId: String, dokumentId: String): String? {
        return hentSed.measure {
            klient.hentSedJson(rinaSakId, dokumentId)
        }
    }

    /**
     * Henter alle filer/vedlegg tilknyttet en SED fra Rina EUX API.
     *
     * @param rinaSakId: Hvilken Rina-sak filene skal hentes fra.
     * @param dokumentId: SED-dokumentet man vil hente vedleggene til.
     *
     * @return [SedDokumentfiler] som inneholder hovedfil, samt vedlegg.
     */
    fun hentAlleDokumentfiler(rinaSakId: String, dokumentId: String): SedDokumentfiler? {
        return hentPdf.measure {
            klient.hentAlleDokumentfiler(rinaSakId, dokumentId)
        }
    }

    /**
     * Henter Buc fra Rina.
     *
     * @param rinaSakId: Hvilken Rina-sak (buc) som skal hentes.
     *
     * @return [Buc]
     */
    fun hentBuc(rinaSakId: String): Buc? {
        return hentBuc.measure {
            klient.hentBuc(rinaSakId)
        }
    }

    /**
     * Henter alle dokumenter (SEDer) i en Buc.
     *
     * @param rinaSakId: Hvilken Rina-sak (buc) dokumentene skal hentes fra.
     *
     * @return Liste med [ForenkletSED]
     */
    fun hentBucDokumenter(rinaSakId: String): List<ForenkletSED> {
        val documents = hentBuc(rinaSakId)?.documents ?: return emptyList()

        return documents
            .filter { it.id != null }
            .map { ForenkletSED(it.id!!, it.type, SedStatus.fra(it.status)) }
    }

    /**
     * Markerer en Rina-sak som sensitiv.
     *
     * @param rinaSakId: Hvilken Rina-sak som skal markeres som sensitiv.
     *
     * @return [Boolean] true hvis respons fra EUX API er HttpStatus.OK
     */
    fun settSensitivSak(rinaSakId: String): Boolean {
        return settSensitiv.measure {
            klient.settSensitivSak(rinaSakId)
        }
    }

    /**
     * Henter alle deltakere på BUC / Rina-sak
     *
     * @param rinaSakId: Saken man vil hente deltakere fra.
     *
     * @return Liste over alle deltakere [Participant]
     */
    fun hentBucDeltakere(rinaSakId: String): List<Participant> {
        return hentBucDeltakere.measure {
            klient.hentBucDeltakere(rinaSakId)
        }
    }

}

private fun <T : Any> mapJsonToAny(json: String, typeRef: TypeReference<T>) = jacksonObjectMapper()
    .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .readValue(json, typeRef)
