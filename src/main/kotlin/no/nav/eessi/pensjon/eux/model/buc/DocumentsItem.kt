package no.nav.eessi.pensjon.eux.model.buc

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.sed.SedType

@JsonIgnoreProperties(ignoreUnknown = true)
class DocumentsItem(
    @JsonProperty("DMProcessId")
    val dmProcessId: String? = null,

    @JsonProperty("ParentType")
    val parentType: String? = null,

    val parendDocumentId: Any? = null,
    val attachments: List<Attachment>? = null,
    val sendExecuted: Boolean? = null,
    val displayName: String? = null,
    val admin: Boolean? = null,
    val isDummyDocument: Boolean? = null,
    val mimeType: String? = null,
    val firstDocument: Boolean? = null,
    val type: SedType? = null,
    val conversations: List<ConversationsItem>? = null,
    val isSendExecuted: Boolean? = null,
    val hasMultipleVersions: Boolean? = null,
    val hasBusinessValidation: Boolean? = null,
    val id: String? = null,
    val hasCancel: Boolean? = null,
    val validation: Validation? = null,
    val direction: String? = null,
    val order: Int? = null,
    val hasLetter: Boolean? = null,
    val creator: Creator? = null,
    val hasReplyClarify: Boolean? = null,
    val hasReject: Boolean? = null,
    val comments: List<Any?>? = null,
    val starter: Boolean? = null,
    val mlc: Boolean? = null,
    val isMLC: Boolean? = null,
    val selectParticipants: Boolean? = null,
    val isAdmin: Boolean? = null,
    val subProcessId: Int? = null,
    val creationDate: Any? = null,
    val hasClarify: Boolean? = null,
    val createTemplate: Any? = null,
    val tags: Any? = null,
    val toSenderOnly: Boolean? = null,
    val typeVersion: String? = null,
    val allowsAttachments: Boolean? = null,
    val versions: List<VersionsItem>? = null,
    val lastUpdate: Any? = null,
    val dummyDocument: Boolean? = null,
    val name: Any? = null,
    val canBeSentWithoutChild: Boolean? = null,
    val bulk: Boolean? = null,
    val parentDocumentId: String? = null,
    val isFirstDocument: Boolean? = null,
    val status: String? = null
)
