package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Buc(
    val creator: Creator? = null,
    val attachments: List<Attachment>? = null,
    val comments: List<Any>? = null,
    val subject: Subject? = null,
    val businessId: String? = null,
    val initialVariables: Any? = null,
    val processDefinitionName: String? = null,
    val sensitive: Boolean? = null,
    val sensitiveCommitted: Boolean? = null,
    val hashCode: Int? = null,
    val lastUpdate: Any? = null,
    val internationalId: String? = null,
    val id: String? = null,
    val applicationRoleId: String? = null,
    val actions: List<ActionsItem>? = null,
    val startDate: Any? = null,
    val processDefinitionVersion: String? = null,
    val properties: Properties? = null,
    val status: String? = null,
    val participants: List<ParticipantsItem>? = null,
    val documents: List<DocumentsItem>? = null
)
