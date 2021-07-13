package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class XNav(
    //X005 - X010
    val sak: Navsak? = null,
)

//X005, X010
data class Navsak (
    val kontekst: Kontekst? = null,
    val leggtilinstitusjon: Leggtilinstitusjon? = null,
    val paaminnelse: Paaminnelse? = null
)

//X010
data class Paaminnelse(
    val svar: Svar? = null
)

data class Svar(
    val informasjon: Informasjon? = null
)

//X010
data class Informasjon(
    val kommersenere: List<KommersenereItem>? = null
)

//X010
data class KommersenereItem(
    val type: String? = null,
    val opplysninger: String? = null,
    val forventetdato: String? = null
)

//X005
data class Kontekst(
    val bruker: Bruker? = null
)

//X005
@JsonIgnoreProperties(ignoreUnknown = true)
data class Leggtilinstitusjon(
    val institusjon: InstitusjonX005? = null,
)

//X005
data class InstitusjonX005(
    val id: String,
    val navn: String
)