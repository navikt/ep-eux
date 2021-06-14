package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty

// SED class main request class to basis
// Strukturerte Elektroniske Dokumenter
open class GenericSED(
    @JsonProperty("sed")
    override val type: SedType,
    override val sedGVer: String,
    override val sedVer: String,
    override val nav: Nav? = null,
    override val pensjon: Pensjon? = null
    ): SED