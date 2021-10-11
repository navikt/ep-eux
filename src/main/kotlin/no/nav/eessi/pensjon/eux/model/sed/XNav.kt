package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class XNav(
    //X005 - X010
    val sak: Navsak? = null,
)

//X005, X010
@JsonIgnoreProperties(ignoreUnknown = true)
data class Navsak (
    val kontekst: Kontekst? = null,
    val leggtilinstitusjon: Leggtilinstitusjon? = null,
    val paaminnelse: Paaminnelse? = null
)

//X010
data class Paaminnelse(
    val svar: Svar? = null,
    val sende: List<SendeItem?>? = null
)

//X009
data class SendeItem(
    val type: String? = null,
    val detaljer: String? = null
)

data class Svar(
    val informasjon: Informasjon? = null
)

//X010
data class Informasjon(
    val ikketilgjengelig: List<IkkeTilgjengelig>? = null,
    val kommersenere: List<KommersenereItem>? = null
)

//X010
data class KommersenereItem(
    val type: String? = null,
    val opplysninger: String? = null,
    val forventetdato: String? = null
)

//X005, X009, X010
@JsonIgnoreProperties(ignoreUnknown = true)
data class Kontekst(
    val bruker: Bruker? = null,
    val refusjonskrav: Refusjonskrav? = null,
    val arbeidsgiver: XArbeidsgiver? = null,
)

//X009
data class XArbeidsgiver(
    val identifikator: List<IdentifikatorItem?>? = null,
    val adresse: Adresse? = null,
    val navn: String? = null
)

//X009
data class IdentifikatorItem(
    val id: String? = null,
    val type: String? = null
)

data class Refusjonskrav(
   val antallkrav: String? = null,
   val id: String? = null
)


//X005
@JsonIgnoreProperties(ignoreUnknown = true)
data class Leggtilinstitusjon(
    val institusjon: InstitusjonX005? = null
)

//X005
data class InstitusjonX005(
    val id: String? = null,
    val navn: String? = null
)

data class IkkeTilgjengelig(
    val type: String? = null,
    val opplysninger: String? = null,
    val grunn: Grunn? = null
)

data class Grunn (
    val type: String? = null,
    val annet: String? = null
)