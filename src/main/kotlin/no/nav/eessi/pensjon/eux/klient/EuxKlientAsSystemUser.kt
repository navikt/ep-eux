package no.nav.eessi.pensjon.eux.klient

import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponents

/**
 * Gir tilgang til eux tjenester med både innlogget navIdent (euxRestTemplate) og app-til-app auth (euxSystemRestTemplate)
 */
class EuxKlientAsSystemUser( val euxRestTemplate: RestTemplate,private val euxSystemRestTemplate: RestTemplate) : EuxKlientLib(euxRestTemplate){

    fun getBucJsonAsSystemuser(euxCaseId: String): String = getBucJson(euxCaseId, euxSystemRestTemplate)
    fun getSedOnBucByDocumentIdAsSystemuser(euxCaseId: String, documentId: String): String =
        getSedOnBucByDocumentId(euxCaseId, documentId, euxSystemRestTemplate)

    companion object{
        fun getRinasakerUri(fnr: String?, euxCaseId: String?): UriComponents =  EuxKlientLib.getRinasakerUri(fnr, euxCaseId)
    }
}