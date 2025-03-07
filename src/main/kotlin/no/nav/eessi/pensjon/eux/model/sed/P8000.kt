package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRawValue
import com.fasterxml.jackson.databind.ObjectMapper
import no.nav.eessi.pensjon.eux.model.SedType

class P8000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P8000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p8000Pensjon: P8000Pensjon?,
) : SED(type, nav = nav) {

    @JsonIgnore
    var _options: Map<String, Any>? = null

    @JsonProperty("options")
    @JsonRawValue
    fun setOptions(options: Map<String, Any>? ) {
        this._options = options
    }
}