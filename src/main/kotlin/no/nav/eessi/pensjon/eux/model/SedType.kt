package no.nav.eessi.pensjon.eux.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 * SED-typer som brukes av EP (EESSI Pensjon).
 * Typen forteller hvilken type en SED er.
 *
 * SED = "Structured Electronic Document"
 *
 * @param beskrivelse: Beskrivelse av SedTypen. Kan eksempelvis brukes ved logging, e.l.
 */
enum class SedType(val jsonValue: String, val beskrivelse: String) {
    SEDTYPE_DummyChooseParts("DummyChooseParts", ""), // I særtilfeller hvor SedType ikke er valgt (P_BUC_06)
    SEDTYPE_P1000("P1000","Anmodning om perioder med omsorg for barn"),
    SEDTYPE_P1100("P1100","Svar på anmodning om perioder med omsorg for barn"),
    SEDTYPE_P2000("P2000","Krav om alderspensjon"),
    SEDTYPE_P2100("P2100","Krav om gjenlevendepensjon"),
    SEDTYPE_P2200("P2200","Krav om uførepensjon"),
    SEDTYPE_P3000_AT("P3000_AT","Landsspesifikk informasjon - Østerrike"),
    SEDTYPE_P3000_BE("P3000_BE","Landsspesifikk informasjon - Belgia"),
    SEDTYPE_P3000_BG("P3000_BG","Landsspesifikk informasjon - Bulgaria"),
    SEDTYPE_P3000_CH("P3000_CH","Landsspesifikk informasjon - Sveits"),
    SEDTYPE_P3000_CY("P3000_CY","Landsspesifikk informasjon - Kypros"),
    SEDTYPE_P3000_CZ("P3000_CZ","Landsspesifikk informasjon - Republikken Tsjekkia"),
    SEDTYPE_P3000_DE("P3000_DE","Landsspesifikk informasjon - Tyskland"),
    SEDTYPE_P3000_DK("P3000_DK","Landsspesifikk informasjon - Danmark"),
    SEDTYPE_P3000_EE("P3000_EE","Landsspesifikk informasjon - Estland"),
    SEDTYPE_P3000_EL("P3000_EL","Landsspesifikk informasjon - Hellas"),
    SEDTYPE_P3000_ES("P3000_ES","Landsspesifikk informasjon - Spania"),
    SEDTYPE_P3000_FI("P3000_FI","Landsspesifikk informasjon - Finland"),
    SEDTYPE_P3000_FR("P3000_FR","Landsspesifikk informasjon - Frankrike"),
    SEDTYPE_P3000_GR("P3000_GR","Landsspesifikk informasjon - Hellas"),
    SEDTYPE_P3000_HR("P3000_HR","Landsspesifikk informasjon - Kroatia"),
    SEDTYPE_P3000_HU("P3000_HU","Landsspesifikk informasjon - Ungarn"),
    SEDTYPE_P3000_IE("P3000_IE","Landsspesifikk informasjon - Irland"),
    SEDTYPE_P3000_IS("P3000_IS","Landsspesifikk informasjon - Island "),
    SEDTYPE_P3000_IT("P3000_IT","Landsspesifikk informasjon - Italia"),
    SEDTYPE_P3000_LI("P3000_LI","Landsspesifikk informasjon - Liechtenstein"),
    SEDTYPE_P3000_LT("P3000_LT","Landsspesifikk informasjon - Litauen"),
    SEDTYPE_P3000_LU("P3000_LU","Landsspesifikk informasjon - Luxembourg"),
    SEDTYPE_P3000_LV("P3000_LV","Landsspesifikk informasjon - Latvia"),
    SEDTYPE_P3000_MT("P3000_MT","Landsspesifikk informasjon - Malta"),
    SEDTYPE_P3000_NL("P3000_NL","Landsspesifikk informasjon - Nederland"),
    SEDTYPE_P3000_NO("P3000_ND","Landsspesifikk informasjon - Norge"),
    SEDTYPE_P3000_PL("P3000_PL","Landsspesifikk informasjon - Polen"),
    SEDTYPE_P3000_PT("P3000_PT","Landsspesifikk informasjon - Portugal"),
    SEDTYPE_P3000_RO("P3000_RD","Landsspesifikk informasjon - Romania"),
    SEDTYPE_P3000_SE("P3000_SE","Landsspesifikk informasjon - Sverige"),
    SEDTYPE_P3000_SI("P3000_SI","Landsspesifikk informasjon - Slovenia"),
    SEDTYPE_P3000_SK("P3000_SK","Landsspesifikk informasjon - Slovakia"),
    SEDTYPE_P3000_UK("P3000_UK","Landsspesifikk informasjon - Storbritannia"),
    SEDTYPE_P4000("P4000","Brukers oversikt botid og arbeid"),
    SEDTYPE_P5000("P5000","Oversikt TT"),
    SEDTYPE_P6000("P6000","Melding om vedtak"),
    SEDTYPE_P7000("P7000","Samlet melding om vedtak"),
    SEDTYPE_P8000("P8000","Forespørsel om informasjon"),
    SEDTYPE_P9000("P9000","Svar på forespørsel om informasjon"),
    SEDTYPE_P10000("P10000","Oversendelse av informasjon"),
    SEDTYPE_P11000("P11000","Anmodning om pensjonsbeløp"),
    SEDTYPE_P12000("P12000","Informasjon om pensjonsbeløp"),
    SEDTYPE_P13000("P13000","Informasjon om pensjonstillegg"),
    SEDTYPE_P14000("P14000","Endring i personlige forhold"),
    SEDTYPE_P15000("P15000","Overføring av pensjonssaker til EESSI"),

    // Administrative
    SEDTYPE_X001("X001" ,"Anmodning om avslutning"),
    SEDTYPE_X002("X002" ,"Anmodning om gjenåpning av avsluttet sak"),
    SEDTYPE_X003("X003" ,"Svar på anmodning om gjenåpning av avsluttet sak"),
    SEDTYPE_X004("X004" ,"Gjenåpne saken"),
    SEDTYPE_X005("X005" ,"Legg til ny institusjon"),
    SEDTYPE_X006("X006" ,"Fjern institusjon"),
    SEDTYPE_X007("X007" ,"Videresend sak"),
    SEDTYPE_X008("X008" ,"Ugyldiggjøre SED"),
    SEDTYPE_X009("X009" ,"Påminnelse"),
    SEDTYPE_X010("X010" ,"Svar på påminnelse"),
    SEDTYPE_X011("X011" ,"Avvis SED"),
    SEDTYPE_X012("X012" ,"Klargjør innhold"),
    SEDTYPE_X013("X013" ,"Svar på anmodning om klargjøring"),
    SEDTYPE_X050("X050" ,"Unntaksfeil"),
    SEDTYPE_X100("X100" ,"Endre deltaker"),

    // Horisontal
    SEDTYPE_H001("H001","Melding/anmodning om informasjon"),
    SEDTYPE_H002("H002","Svar på anmodning om informasjon"),
    SEDTYPE_H020("H020","Krav om - refusjon - administrativ kontroll / medisinsk informasjon"),
    SEDTYPE_H021("H021","Svar på krav om refusjon - administrativ kontroll / legeundersøkelse / medisinsk informasjon"),
    SEDTYPE_H070("H070","Melding om dødsfall"),
    SEDTYPE_H120("H120","Anmodning om medisinsk informasjon"),
    SEDTYPE_H121("H121","Melding om medisinsk informasjon / Svar på forespørsel om medisinsk informasjon"),

    // Seder i R_BUC_02 Motregning av overskytende utbetaling i etterbetalinger er
    SEDTYPE_R004("R004", "Melding om utbetaling"),
    SEDTYPE_R005("R005", "Anmodning om motregning i etterbetalinger (foreløpig eller endelig)"),
    SEDTYPE_R006("R006", "Svar på anmodning om informasjon"),

    SEDTYPE_M040("M040", "Krav om førtidspensjon"),
    SEDTYPE_M050("M050", "Anmodning om informasjon om inntekt - særskilte innskuddsfrie kontantytelser"),
    SEDTYPE_M051("M051", "Svar på anmodning om informasjon om inntekt - særskilte innskuddsfrie kontantytelser"),
    SEDTYPE_M052("M052", "Anmodning om informasjon om ansettelsesforhold/selvstendig næringsvirksomhet/bosetningsperioder - særskilte innskuddsfrie kontantytelser"),
    SEDTYPE_M053("M053", "Svar på anmodning om informasjon om ansettelsesforhold/selvstendig næringsvirksomhet/bosetningsperioder - særskilte innskuddsfrie kontantytelser")
    ;

    /**
     * Lager beskrivelse med SedType som prefiks.
     * Eks: "P8000 - Tekst som beskriver typen"
     *
     * @return [String] beskrivelse med sedtype.
     */
    fun typeMedBeskrivelse(): String = "${this.name} - $beskrivelse"

    @JsonValue
    fun toJson(): String = jsonValue

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromJson(value: String): SedType {
            return entries.find { it.jsonValue == value }
                ?: throw IllegalArgumentException("Unknown value: $value")
        }

//        @JvmStatic
//        @JsonCreator
//        fun from(s: String): SedType? {
//            return try {
//                valueOf(s)
//            } catch (e: Exception) {
//                null
//            }
//        }

        @JvmStatic
        fun isValidSEDType(input: String): Boolean = try {
            valueOf(input)
            true
        } catch (ia: IllegalArgumentException) {
            false
        }
    }

    /**
     * Sjekker om SedTypen er gyldig for prefill.
     * Basert på listen over SedTyper som var tilgjengelig i appen fra commit 6fc9b6c7298262ff9b4a53f70549215a700bc40a
     */
    fun kanPrefilles(): Boolean {
        return this in listOf(
            SEDTYPE_P2000,
            SEDTYPE_P2100,
            SEDTYPE_P2200,
            SEDTYPE_P4000,
            SEDTYPE_P6000,
            SEDTYPE_P5000,
            SEDTYPE_P7000,
            SEDTYPE_P8000,
            SEDTYPE_P9000,
            SEDTYPE_P10000,
            SEDTYPE_P14000,
            SEDTYPE_P15000,
            SEDTYPE_X005,
            SEDTYPE_X010,
            SEDTYPE_X011,
            SEDTYPE_H020,
            SEDTYPE_H021,
            SEDTYPE_H070,
            SEDTYPE_H120,
            SEDTYPE_H121,
            SEDTYPE_P12000,
            SEDTYPE_P13000,
            SEDTYPE_P1000,
            SEDTYPE_P1100,
            SEDTYPE_P11000,
            SEDTYPE_P3000_FR,
            SEDTYPE_P3000_RO,
            SEDTYPE_P3000_IE,
            SEDTYPE_P3000_HU,
            SEDTYPE_P3000_LT,
            SEDTYPE_P3000_IS,
            SEDTYPE_P3000_UK,
            SEDTYPE_P3000_NO,
            SEDTYPE_P3000_IT,
            SEDTYPE_P3000_SI,
            SEDTYPE_P3000_MT,
            SEDTYPE_P3000_BE,
            SEDTYPE_P3000_EE,
            SEDTYPE_P3000_AT,
            SEDTYPE_P3000_BG,
            SEDTYPE_P3000_LI,
            SEDTYPE_P3000_DK,
            SEDTYPE_P3000_SE,
            SEDTYPE_P3000_FI,
            SEDTYPE_P3000_PL,
            SEDTYPE_P3000_DE,
            SEDTYPE_P3000_ES,
            SEDTYPE_P3000_PT,
            SEDTYPE_P3000_LV,
            SEDTYPE_P3000_SK,
            SEDTYPE_P3000_NL,
            SEDTYPE_P3000_GR,
            SEDTYPE_P3000_HR,
            SEDTYPE_P3000_CY,
            SEDTYPE_P3000_LU,
            SEDTYPE_P3000_CH,
            SEDTYPE_P3000_CZ,
            SEDTYPE_P3000_EL,
            SEDTYPE_M050,
            SEDTYPE_M051,
            SEDTYPE_M052,
            SEDTYPE_M053,
            SEDTYPE_R004,
            SEDTYPE_R005
        )
    }
}
