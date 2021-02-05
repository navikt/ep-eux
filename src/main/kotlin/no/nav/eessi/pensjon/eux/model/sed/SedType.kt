package no.nav.eessi.pensjon.eux.model.sed

/**
 * SED-typer som brukes av EP (EESSI Pensjon).
 * Typen forteller hvilken type en SED er.
 *
 * SED = "Structured Electronic Document"
 *
 * @param kanInneholdeIdent: Forteller om SED av valgt type kan inneholde en person-ident (fnr, dnr, osv.)
 * @param beskrivelse: Beskrivelse av SedTypen. Kan eksempelvis brukes ved logging, e.l.
 */
enum class SedType(
    val kanInneholdeIdent: Boolean,
    val beskrivelse: String
) {
    P1000(false, "P1000 - Anmodning om perioder med omsorg for barn"),
    P1100(false, "P1100 - Svar på anmodning om perioder med omsorg for barn"),
    P2000(true, "P2000 - Krav om alderspensjon"),
    P2100(true, "P2100 - Krav om gjenlevendepensjon"),
    P2200(true, "P2200 - Krav om uførepensjon"),
    P3000_AT(true, "P3000_AT - Landsspesifikk informasjon - Østerrike"),
    P3000_BE(true, "P3000_BE - Landsspesifikk informasjon - Belgia"),
    P3000_BG(true, "P3000_BG - Landsspesifikk informasjon - Bulgaria"),
    P3000_CH(true, "P3000_CH - Landsspesifikk informasjon - Sveits"),
    P3000_CY(true, "P3000_CY - Landsspesifikk informasjon - Kypros"),
    P3000_CZ(true, "P3000_CZ - Landsspesifikk informasjon - Republikken Tsjekkia"),
    P3000_DE(true, "P3000_DE - Landsspesifikk informasjon - Tyskland"),
    P3000_DK(true, "P3000_DK - Landsspesifikk informasjon - Danmark"),
    P3000_EE(true, "P3000_EE - Landsspesifikk informasjon - Estland"),
    P3000_EL(true, "P3000_EL - Landsspesifikk informasjon - Hellas"),
    P3000_ES(true, "P3000_ES - Landsspesifikk informasjon - Spania"),
    P3000_FI(true, "P3000_FI - Landsspesifikk informasjon - Finland"),
    P3000_FR(true, "P3000_FR - Landsspesifikk informasjon - Frankrike"),
    P3000_HR(true, "P3000_HR - Landsspesifikk informasjon - Kroatia"),
    P3000_HU(true, "P3000_HU - Landsspesifikk informasjon - Ungarn"),
    P3000_IE(true, "P3000_IE - Landsspesifikk informasjon - Irland"),
    P3000_IS(true, "P3000_IS - Landsspesifikk informasjon - Island "),
    P3000_IT(true, "P3000_IT - Landsspesifikk informasjon - Italia"),
    P3000_LI(true, "P3000_LI - Landsspesifikk informasjon - Liechtenstein"),
    P3000_LT(true, "P3000_LT - Landsspesifikk informasjon - Litauen"),
    P3000_LU(true, "P3000_LU - Landsspesifikk informasjon - Luxembourg"),
    P3000_LV(true, "P3000_LV - Landsspesifikk informasjon - Latvia"),
    P3000_MT(true, "P3000_MT - Landsspesifikk informasjon - Malta"),
    P3000_NL(true, "P3000_NL - Landsspesifikk informasjon - Nederland"),
    P3000_NO(true, "P3000_NO - Landsspesifikk informasjon - Norge"),
    P3000_PL(true, "P3000_PL - Landsspesifikk informasjon - Polen"),
    P3000_PT(true, "P3000_PT - Landsspesifikk informasjon - Portugal"),
    P3000_RO(true, "P3000_RO - Landsspesifikk informasjon - Romania"),
    P3000_SE(true, "P3000_SE - Landsspesifikk informasjon - Sverige"),
    P3000_SI(true, "P3000_SI - Landsspesifikk informasjon - Slovenia"),
    P3000_SK(true, "P3000_SK - Landsspesifikk informasjon - Slovakia"),
    P3000_UK(true, "P3000_UK - Landsspesifikk informasjon - Storbritannia"),
    P4000(true, "P4000 - Brukers oversikt botid og arbeid"),
    P5000(true, "P5000 - Oversikt TT"),
    P6000(true, "P6000 - Melding om vedtak"),
    P7000(true, "P7000 - Samlet melding om vedtak"),
    P8000(true, "P8000 - Forespørsel om informasjon"),
    P9000(true, "P9000 - Svar på forespørsel om informasjon"),
    P10000(true, "P10000 - Oversendelse av informasjon"),
    P11000(true, "P11000 - Anmodning om pensjonsbeløp"),
    P12000(true, "P12000 - Informasjon om pensjonsbeløp"),
    P13000(false, "P13000 - Informasjon om pensjonstillegg"),
    P14000(true, "P14000 - Endring i personlige forhold"),
    P15000(true, "P15000 - Overføring av pensjonssaker til EESSI"),

    // Administrative
    X001(false, "X001 - Anmodning om avslutning"),
    X002(false, "X002 - Anmodning om gjenåpning av avsluttet sak"),
    X003(false, "X003 - Svar på anmodning om gjenåpning av avsluttet sak"),
    X004(false, "X004 - Gjenåpne saken"),
    X005(false, "X005 - Legg til ny institusjon"),
    X006(false, "X006 - Fjern institusjon"),
    X007(false, "X007 - Videresend sak"),
    X008(false, "X008 - Ugyldiggjøre SED"),
    X009(false, "X009 - Påminnelse"),
    X010(false, "X010 - Svar på påminnelse"),
    X011(false, "X011 - Avvis SED"),
    X012(false, "X012 - Klargjør innhold"),
    X013(false, "X013 - Svar på anmodning om klargjøring"),
    X050(false, "X050 - Unntaksfeil"),
    X100(false, "X100 - Endre deltaker"),

    // Horisontal
    H001(false, "H001 - Melding/anmodning om informasjon"),
    H002(false, "H002 - Svar på anmodning om informasjon"),
    H020(true, "H020 - Krav om - refusjon - administrativ kontroll / medisinsk informasjon"),
    H021(true, "H021 - Svar på krav om refusjon - administrativ kontroll / legeundersøkelse / medisinsk informasjon"),
    H070(true, "H070 - Melding om dødsfall"),
    H120(true, "H120 - Anmodning om medisinsk informasjon"),
    H121(true, "H121 - Melding om medisinsk informasjon / Svar på forespørsel om medisinsk informasjon"),

    // Seder i R_BUC_02 Motregning av overskytende utbetaling i etterbetalinger er
    R004(true, "R004 - Melding om utbetaling"),
    R005(true, "R005 - Anmodning om motregning i etterbetalinger (foreløpig eller endelig)"),
    R006(true, "R006 - Svar på anmodning om informasjon");
}
