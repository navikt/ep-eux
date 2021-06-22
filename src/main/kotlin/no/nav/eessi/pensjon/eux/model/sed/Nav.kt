package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class Nav(
        val eessisak: List<EessisakItem>? = null,
        val bruker: Bruker? = null,
        val brukere: List<Brukere>? = null, //brukere benyttes kun av Rseder, se R005
        val ektefelle: Ektefelle? = null,
        val barn: List<BarnItem>? = null, //pkt 6 og 8
        val verge: Verge? = null,
        val krav: Krav? = null,

        //X005 - X010
        val sak: Navsak? = null,
        //P10000 hvordan få denne til å bli val?
        var annenperson: Bruker? = null,
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

data class Krav(
        var dato: String? = null,
        //P15000
        val type: String? = null
)

@Suppress("unused") // val kode (jsonvalue) brukes av jackson
enum class KravType(@JsonValue val kode: String?) {
        ALDER("01"),
        ETTERLATTE("02"),
        UFORE("03")
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Bruker(
        val mor: Foreldre? = null,
        val far: Foreldre? = null,
        val person: Person? = null,
        val adresse: Adresse? = null,
        val arbeidsforhold: List<ArbeidsforholdItem>? = null,
        val bank: Bank? = null,
)

//kun for R005
@JsonIgnoreProperties(ignoreUnknown = true)
data class Brukere(
        val mor: Foreldre? = null,
        val far: Foreldre? = null,
        val person: Person? = null,
        @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY]) // i R005 kan det være flere adresser pr person
        val adresse: List<Adresse>? = null,
        val arbeidsforhold: List<ArbeidsforholdItem>? = null,
        val bank: Bank? = null,
        val tilbakekreving: Tilbakekreving? = null // Kun brukt av R005
)

data class Tilbakekreving(
        val feilutbetaling: Feilutbetaling? = null,
        val status: Status? = null
)

data class Feilutbetaling(val ytelse: Ytelse?)

data class Ytelse(val type: String?)
data class Status(val type: String?)

data class Bank(
        val navn: String? = null,
        val konto: Konto? = null,
        val adresse: Adresse? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Konto(
        val sepa: Sepa? = null,
        val innehaver: Innehaver? = null,
)

data class Sepa(
        val iban: String? = null,
        val swift: String? = null
)

data class Innehaver(
        val rolle: String? = null,
        val navn: String? = null
)

data class Foreldre(
        val person: Person
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BarnItem(
        val mor: Foreldre? = null,
        val person: Person? = null,
        val far: Foreldre? = null,
        val relasjontilbruker: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ektefelle(
        val person: Person? = null,
        val type: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Verge(
        val person: Person? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Person(
        val pin: List<PinItem>? = null,
        var pinland: PinLandItem? = null, //for H020 og H021
        val statsborgerskap: List<StatsborgerskapItem>? = null, //nasjonalitet
        val etternavn: String? = null,
        val fornavn: String? = null,
        val kjoenn: String? = null,
        val foedested: Foedested? = null,
        val foedselsdato: String? = null,
        @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
        val sivilstand: List<SivilstandItem>? = null,   //familiestatus
        val relasjontilavdod: RelasjonAvdodItem? = null, //5.2.5 P2100
        //noe enkel måte å få denne til å forbli val?
        var rolle: String? = null  //3.1 i P10000
)

data class PinLandItem(
        val oppholdsland: String? = null,
        val kompetenteuland: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RelasjonAvdodItem(
        val relasjon: String? = null,  //5.2.5  P2100
)

// TODO bytt denne med RelasjonAvdodItem
@Suppress("unused")
enum class RelasjonTilAvdod(@JsonValue val kode: String?) {
        EKTEFELLE("01"),
        PART_I_ET_REGISTRERT_PARTNERSKAP("02"),
        SAMBOER("03"),
        TIDLIGERE_EKTEFELLE("04"),
        TIDLIGERE_PARTNER_I_ET_REGISTRERT_PARTNERSKAP("05"),
        EGET_BARN("06"),
        ADOPTIVBARN("07"),
        FOSTERBARN("08"),
        STEBARN("09"),
        BARNEBARN("10"),
        SØSKEN("11"),
        ANNEN_SLEKTNING("12");

        fun erGjenlevendeBarn(): Boolean = this in listOf(EGET_BARN, ADOPTIVBARN, FOSTERBARN, STEBARN)
}

data class SivilstandItem(
        val fradato: String? = null,
        val status: String? = null
)

data class StatsborgerskapItem(
        val land: String? = null
)

data class ArbeidsforholdItem(
        val inntekt: List<InntektItem?>? = null,
        val planlagtstartdato: String? = null,
        val arbeidstimerperuke: String? = null,
        val planlagtpensjoneringsdato: String? = null,
        val yrke: String? = null,
        val type: String? = null,
        val sluttdato: String? = null
)

data class InntektItem(
        val betalingshyppighetinntekt: String? = null,
        val beloeputbetaltsiden: String? = null,
        val valuta: String? = null,
        val annenbetalingshyppighetinntekt: String? = null,
        val beloep: String? = null
)

data class PinItem(
        val institusjonsnavn: String? = null,
        val institusjonsid: String? = null,
        val sektor: String? = null,
        val identifikator: String? = null,  //rename? f.eks personnummer
        val land: String? = null,
        //P2000, P2100, P2200
        val institusjon: Institusjon? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Adresse(
        val gate: String? = null,
        val bygning: String? = null,
        val by: String? = null,
        val postnummer: String? = null,
        val region: String? = null,
        val land: String? = null,
        val kontaktpersonadresse: String? = null,
        val datoforadresseendring: String? = null,
        val postadresse: String? = null,
        val startdato: String? = null
)

data class Foedested(
        val by: String? = null,
        val land: String? = null,
        val region: String? = null
)

data class EessisakItem(
        val institusjonsid: String? = null,
        val institusjonsnavn: String? = null,
        val saksnummer: String? = null,
        val land: String? = null
)