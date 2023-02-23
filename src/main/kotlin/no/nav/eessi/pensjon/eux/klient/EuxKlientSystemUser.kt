package no.nav.eessi.pensjon.eux.klient

import org.springframework.web.client.RestTemplate

class EuxKlientForSystemUser( val euxRestTemplate: RestTemplate,private val euxSystemRestTemplate: RestTemplate) : EuxKlientLib(euxRestTemplate){

    fun getBucJsonAsSystemuser(euxCaseId: String): String = getBucJson(euxCaseId, euxSystemRestTemplate)
    fun getSedOnBucByDocumentIdAsJsonAndAsSystemuser(euxCaseId: String, documentId: String): String =
        getSedOnBucByDocumentId(euxCaseId, documentId, euxSystemRestTemplate)
}