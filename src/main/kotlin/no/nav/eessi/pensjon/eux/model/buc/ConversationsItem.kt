package no.nav.eessi.pensjon.eux.model.buc

typealias Receiver = Organisation
class ConversationsItem(
    val userMessages: List<UserMessagesItem>? = null,
    val id: String? = null,
    val participants: List<ParticipantsItem?>? = null
)

class UserMessagesItem(
    val receiver: Receiver? = null,
    val sender: Sender? = null,
    val error: Any? = null,
)

class Sender (
    acronym: String? = null,
    countryCode: String? = null,
    name: String? = null,
    id: String? = null,
    val identifier: String? = null,
    val contactTypeIdentifier: String? = null,
    val authority: String? = null
): Organisation(
    acronym = acronym,
    countryCode = countryCode,
    name = name,
    id= id
)
