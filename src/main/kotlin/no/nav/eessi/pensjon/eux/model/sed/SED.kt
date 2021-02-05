package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * SED: Structured Electronic Document
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class SED(
    @JsonProperty("sed")
    val type: SedType,

    val sedGVer: String? = "4",
    val sedVer: String? = "1",
    val nav: Nav? = null,
    val pensjon: Pensjon? = null,
    val trygdetid: PersonArbeidogOppholdUtland? = null, //P4000
    val ignore: Ignore? = null,
    val horisontal: Horisontal? = null,                 //H120
    val tilbakekreving: Tilbakekreving? = null          //R005
) {
    fun toJson(): String = jacksonObjectMapper()
        .setSerializationInclusion(Include.NON_NULL)
        .writeValueAsString(this)
}
