package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import no.nav.eessi.pensjon.utils.mapJsonToAny

@JsonIgnoreProperties(ignoreUnknown = true)
class Buc(
    val id: String? = null,
    val startDate: String? = null,
    val lastUpdate: String? = null,
    val status: String? = null,
    var subject: Subject? = null,
    var creator: Creator? = null,
    var documents: List<Document>? = null,
    var participants: List<Participant>? = null,
    val processDefinitionName: String? = null, // TODO: String -> BucType
    val applicationRoleId: String? = null,
    val businessId: String? = null,
    val internationalId: String? = null,
    val processDefinitionVersion: String? = null,
    val comments: List<Any>? = null,
    var actions: List<ActionsItem>? = null,
    val attachments: List<Attachment>? = null
) {

    companion object {
        fun from(json: String): Buc = mapJsonToAny(json)
    }

}
