package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRawValue
import no.nav.eessi.pensjon.eux.model.SedType

class P8000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P8000,
    override val nav: Nav? = null,
    @JsonProperty("pensjon")
    val p8000Pensjon: P8000Pensjon?,
    @JsonRawValue
    private var _options: String? = null
) : SED(type, nav = nav){
    @JsonRawValue
    @JsonProperty("options")
    fun setOptions(options: String?) {
        _options = options
    }

    @JsonIgnore
    fun getOptions(): String? {
        return _options
    }
}