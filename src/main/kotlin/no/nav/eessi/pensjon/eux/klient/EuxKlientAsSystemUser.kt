package no.nav.eessi.pensjon.eux.klient

import no.nav.eessi.pensjon.eux.model.SedMetadata
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents

/**
 * Gir tilgang til eux tjenester med både innlogget navIdent (euxRestTemplate) og app-til-app auth (euxSystemRestTemplate)
 */
class EuxKlientAsSystemUser( val euxRestTemplate: RestTemplate, private val euxSystemRestTemplate: RestTemplate, overrideWaitTimes: Long = 5000L) : EuxKlientLib(euxRestTemplate, overrideWaitTimes){

    fun getBucJsonAsSystemuser(euxCaseId: String): String? = getBucJsonAsSystemuser(euxCaseId, euxSystemRestTemplate)
    fun getSedOnBucByDocumentIdAsSystemuser(euxCaseId: String, documentId: String, skipError: List<HttpStatus> = emptyList()): String =
        getSedOnBucByDocumentId(euxCaseId, documentId, euxSystemRestTemplate, skipError)

    fun getMetaDataAsSystemuser(euxCaseId: String, documentId: String, skipError: List<HttpStatus> = emptyList()): SedMetadata?  =
        hentSedMetadata(euxCaseId, documentId, euxSystemRestTemplate)

    companion object{
        fun getRinasakerUri(fnr: String?, euxCaseId: String?): UriComponents =  getRinasakerUri("/rinasaker" , fnr, euxCaseId )
    }
}