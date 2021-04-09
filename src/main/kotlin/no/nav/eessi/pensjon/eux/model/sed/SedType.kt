package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonCreator

/**
 * SED-typer som brukes av EP (EESSI Pensjon).
 * Typen forteller hvilken type en SED er.
 *
 * SED = "Structured Electronic Document"
 *
 * @param beskrivelse: Beskrivelse av SedTypen. Kan eksempelvis brukes ved logging, e.l.
 */
enum class SedType(val beskrivelse: String) {
    DummyChooseParts(""), // I særtilfeller hvor SedType ikke er valgt (P_BUC_06)
    P1000("Anmodning om perioder med omsorg for barn"),
    P1100("Svar på anmodning om perioder med omsorg for barn"),
    P2000("Krav om alderspensjon"),
    P2100("Krav om gjenlevendepensjon"),
    P2200("Krav om uførepensjon"),
    P3000_AT("Landsspesifikk informasjon - Østerrike"),
    P3000_BE("Landsspesifikk informasjon - Belgia"),
    P3000_BG("Landsspesifikk informasjon - Bulgaria"),
    P3000_CH("Landsspesifikk informasjon - Sveits"),
    P3000_CY("Landsspesifikk informasjon - Kypros"),
    P3000_CZ("Landsspesifikk informasjon - Republikken Tsjekkia"),
    P3000_DE("Landsspesifikk informasjon - Tyskland"),
    P3000_DK("Landsspesifikk informasjon - Danmark"),
    P3000_EE("Landsspesifikk informasjon - Estland"),
    P3000_EL("Landsspesifikk informasjon - Hellas"),
    P3000_ES("Landsspesifikk informasjon - Spania"),
    P3000_FI("Landsspesifikk informasjon - Finland"),
    P3000_FR("Landsspesifikk informasjon - Frankrike"),
    P3000_GR("Landsspesifikk informasjon - Hellas"),
    P3000_HR("Landsspesifikk informasjon - Kroatia"),
    P3000_HU("Landsspesifikk informasjon - Ungarn"),
    P3000_IE("Landsspesifikk informasjon - Irland"),
    P3000_IS("Landsspesifikk informasjon - Island "),
    P3000_IT("Landsspesifikk informasjon - Italia"),
    P3000_LI("Landsspesifikk informasjon - Liechtenstein"),
    P3000_LT("Landsspesifikk informasjon - Litauen"),
    P3000_LU("Landsspesifikk informasjon - Luxembourg"),
    P3000_LV("Landsspesifikk informasjon - Latvia"),
    P3000_MT("Landsspesifikk informasjon - Malta"),
    P3000_NL("Landsspesifikk informasjon - Nederland"),
    P3000_NO("Landsspesifikk informasjon - Norge"),
    P3000_PL("Landsspesifikk informasjon - Polen"),
    P3000_PT("Landsspesifikk informasjon - Portugal"),
    P3000_RO("Landsspesifikk informasjon - Romania"),
    P3000_SE("Landsspesifikk informasjon - Sverige"),
    P3000_SI("Landsspesifikk informasjon - Slovenia"),
    P3000_SK("Landsspesifikk informasjon - Slovakia"),
    P3000_UK("Landsspesifikk informasjon - Storbritannia"),
    P4000("Brukers oversikt botid og arbeid"),
    P5000("Oversikt TT"),
    P6000("Melding om vedtak"),
    P7000("Samlet melding om vedtak"),
    P8000("Forespørsel om informasjon"),
    P9000("Svar på forespørsel om informasjon"),
    P10000("Oversendelse av informasjon"),
    P11000("Anmodning om pensjonsbeløp"),
    P12000("Informasjon om pensjonsbeløp"),
    P13000("Informasjon om pensjonstillegg"),
    P14000("Endring i personlige forhold"),
    P15000("Overføring av pensjonssaker til EESSI"),

    // Administrative
    X001("Anmodning om avslutning"),
    X002("Anmodning om gjenåpning av avsluttet sak"),
    X003("Svar på anmodning om gjenåpning av avsluttet sak"),
    X004("Gjenåpne saken"),
    X005("Legg til ny institusjon"),
    X006("Fjern institusjon"),
    X007("Videresend sak"),
    X008("Ugyldiggjøre SED"),
    X009("Påminnelse"),
    X010("Svar på påminnelse"),
    X011("Avvis SED"),
    X012("Klargjør innhold"),
    X013("Svar på anmodning om klargjøring"),
    X050("Unntaksfeil"),
    X100("Endre deltaker"),

    // Horisontal
    H001("Melding/anmodning om informasjon"),
    H002("Svar på anmodning om informasjon"),
    H020("Krav om - refusjon - administrativ kontroll / medisinsk informasjon"),
    H021("Svar på krav om refusjon - administrativ kontroll / legeundersøkelse / medisinsk informasjon"),
    H070("Melding om dødsfall"),
    H120("Anmodning om medisinsk informasjon"),
    H121("Melding om medisinsk informasjon / Svar på forespørsel om medisinsk informasjon"),

    // Seder i R_BUC_02 Motregning av overskytende utbetaling i etterbetalinger er
    R004("Melding om utbetaling"),
    R005("Anmodning om motregning i etterbetalinger (foreløpig eller endelig)"),
    R006("Svar på anmodning om informasjon");

    /**
     * Lager beskrivelse med SedType som prefiks.
     * Eks: "P8000 - Tekst som beskriver typen"
     *
     * @return [String] beskrivelse med sedtype.
     */
    fun typeMedBeskrivelse(): String = "${this.name} - $beskrivelse"

    companion object {
        @JvmStatic
        @JsonCreator
        fun from(s: String): SedType? {
            return try {
                valueOf(s)
            } catch (e: Exception) {
                null
            }
        }

        @JvmStatic
        fun isValidSEDType(input: String): Boolean {
            return try {
                valueOf(input)
                true
            } catch (ia: IllegalArgumentException) {
                false
            }
        }
    }

    /**
     * Sjekker om SedTypen er gyldig for prefill.
     * Basert på listen over SedTyper som var tilgjengelig i appen fra commit 6fc9b6c7298262ff9b4a53f70549215a700bc40a
     */
    fun kanPrefilles(): Boolean {
        return this in listOf(
            SedType.P2000, P2100, P2200, P4000, P6000, P5000, P7000, P8000, P9000, P10000, P14000, P15000, X005,
            H020, H021, H070, H120, H121, P12000, P13000, P1000, P1100, P11000, P3000_FR, P3000_RO, P3000_IE,
            P3000_HU, P3000_LT, P3000_IS, P3000_UK, P3000_NO, P3000_IT, P3000_SI, P3000_MT, P3000_BE, P3000_EE,
            P3000_AT, P3000_BG, P3000_LI, P3000_DK, P3000_SE, P3000_FI, P3000_PL, P3000_DE, P3000_ES, P3000_PT,
            P3000_LV, P3000_SK, P3000_NL, P3000_GR, P3000_HR, P3000_CY, P3000_LU, P3000_CH, P3000_CZ
        )
    }
}
