package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.JsonValue

@JsonIgnoreProperties(ignoreUnknown = true)
data class Nav(
        val eessisak: List<EessisakItem>? = null,
        val bruker: Bruker? = null,
        val ektefelle: Ektefelle? = null,
        val barn: List<BarnItem>? = null, //pkt 6 og 8
        val verge: Verge? = null,
        val krav: Krav? = null,

        //P10000 hvordan få denne til å bli val?
        val annenperson: Bruker? = null,
)

data class Krav(
        var dato: String? = null,
        //P15000
        val type: KravType? = null
)

@Suppress("unused") // val kode (jsonvalue) brukes av jackson
enum class KravType(@JsonValue val verdi: String?) {
        ALDER("01"),
        GJENLEV("02"),
        UFOREP("03");

        companion object {
                fun fraNavnEllerVerdi(input: String?): KravType? {
                        return entries.find { it.name == input || it.verdi == input }
                }
        }
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

data class Ytelse(val type: String?)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Bank(
        val navn: String? = null,
        val konto: Konto? = null,
        val adresse: Adresse? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Konto(
        val sepa: Sepa? = null,
        val ikkesepa: IkkeSepa?  = null,
        val kontonr: String? = null,
        val innehaver: Innehaver? = null,
        val betalingsreferanse: String? = null
)

data class Sepa(
        val iban: String? = null,
        val swift: String? = null
)

data class IkkeSepa(
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
        val relasjontilbruker: String? = null,
        val opplysningeromannetbarn: String? = null,

        // ikke direkte tilgjengelig da vi benytter egne metoder for set/get
        private var _relasjontilbruker43: String? = null
) {
        @JsonGetter("relasjontilbruker43")
        fun getRelasjontilbruker43(): String? {
                return _relasjontilbruker43 ?: relasjontilbruker
        }

        @JsonSetter("relasjontilbruker43")
        fun setRelasjontilbruker43(value: String?) {
                _relasjontilbruker43 = value
        }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ektefelle(
        val person: Person? = null,
        val type: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Verge(
        val person: Person? = null,
        val vergemaal: Vergemaal? = null,
        val adresse: Adresse? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Vergemaal(
        val mandat: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Kontakt(
        val telefon: List<Telefon>? = null,
        val email: List<Email>? = null // P2000 2.2.4.2.1.1
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Telefon(
        val type: String? = null, // P2000 2.2.4.1.1.1
        val nummer: String? = null // P2000 2.2.4.1.1.2
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Email(
        val adresse: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Person(
        val pin: List<PinItem>? = null,
        var pinland: PinLandItem? = null, //for H020 og H021
        val statsborgerskap: List<StatsborgerskapItem>? = null, //nasjonalitet
        val etternavn: String? = null,
        val etternavnvedfoedsel: String? = null,
        val fornavn: String? = null,
        val fornavnvedfoedsel: String? = null,
        val tidligerefornavn: String? = null,
        val tidligereetternavn: String? = null,
        val kjoenn: String? = null,
        val foedested: Foedested? = null,
        val foedselsdato: String? = null,
        val sivilstand: List<SivilstandItem>? = null,   //familiestatus
        val relasjontilavdod: RelasjonAvdodItem? = null, //5.2.5 P2100
        //noe enkel måte å få denne til å forbli val?
        var rolle: String? = null,  //3.1 i P10000
        var kontakt: Kontakt? = null, //P2000
        val doedsdato: String? = null //P2000
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
        val status: SivilstandRina? = null
)

// Hentet fra eux-rina-api: sivilstandkoder.properties
enum class SivilstandRina {
        enslig,
        gift,
        samboer,
        skilt,
        registrert_partnerskap,
        skilt_fra_registrert_partnerskap,
        separert,
        enke_enkemann;
}

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
        val postkode: String? = null, // TODO dette er ikke postnummer - men "postkode" brukt i bankadresser (som R005)
        val region: String? = null,
        val land: String? = null,
        val kontaktpersonadresse: String? = null,
        val datoforadresseendring: String? = null,
        val postadresse: String? = null,
        val startdato: String? = null,
        val type: String? = null,
        val annen: String? = null
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