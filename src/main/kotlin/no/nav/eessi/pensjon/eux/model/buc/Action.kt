package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.sed.SedType

class ActionsItem(
    val template: String? = null,
    val documentType: SedType? = null,
    val isDocumentRelated: Boolean? = null,
    val displayName: String? = null,
    val caseRelated: Boolean? = null,
    val type: String? = null,
    val subdocumentId: Any? = null,
    val poolGroup: PoolGroup? = null,
    val isCaseRelated: Boolean? = null,
    val hasBusinessValidation: Boolean? = null,
    val caseId: String? = null,
    val id: String? = null,
    val canClose: Boolean? = null,
    val requiresValidDocument: Boolean? = null,
    val tempDocumentId: String? = null,
    val hasSendValidationOnBulk: Boolean? = null,
    val documentRelated: Boolean? = null,
    val tags: Tags? = null,
    val actor: String? = null,
    val typeVersion: String? = null,
    val name: String? = null,
    val documentId: String? = null,
    val actionGroup: ActionGroup? = null,
    val bulk: Boolean? = null,
    val isBulk: Boolean? = null,
    val operation: String? = null,
    val parentDocumentId: Any? = null,
    val status: Any? = null
)

class ActionGroup(
    @JsonProperty("DMProcessId") val dMProcessId: String? = null,
    @JsonProperty("DocumentId") val documentId: String? = null,
    @JsonProperty("Operation") val operation: String? = null,
    @JsonProperty("ParentDocId") val parentDocId: Any? = null,
    @JsonProperty("ParentType") val parentType: String? = null,
    @JsonProperty("Type") val type: String? = null,

    val activityInstanceId: String? = null,
    val hasLocalClose: Boolean? = null
)
