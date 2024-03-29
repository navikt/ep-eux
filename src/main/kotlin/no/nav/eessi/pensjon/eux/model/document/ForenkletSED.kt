package no.nav.eessi.pensjon.eux.model.document

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue
import no.nav.eessi.pensjon.eux.model.SedType

@JsonIgnoreProperties(ignoreUnknown = true)
data class ForenkletSED(
    val id: String,
    val type: SedType?,
    val status: SedStatus?
) {
    fun harGyldigStatus(): Boolean = (status == SedStatus.SENT || status == SedStatus.RECEIVED)

    fun erKansellert(): Boolean = (status == SedStatus.CANCELLED || status == SedStatus.ACTIVE)

    override fun toString(): String = "ForenkletSED(id=$id, type=$type, status=$status)"
}

@Suppress("unused")
enum class SedStatus(@JsonValue private val value: String) {
    NEW("new"),
    SENT("sent"),
    EMPTY("empty"),
    RECEIVED("received"),
    CANCELLED("cancelled"),
    ACTIVE("active");

    companion object {
        @JvmStatic
        @JsonCreator
        fun fra(value: String?): SedStatus? {
            return if (value == null) null
            else values().firstOrNull { it.value == value }
        }
    }
}
