package no.nav.eessi.pensjon.eux.model

import com.fasterxml.jackson.annotation.JsonCreator

enum class BucType {
    AD_BUC_01, //
    AD_BUC_02, //
    AD_BUC_03, // Add participant
    AD_BUC_04, // Remove participant
    AD_BUC_05, // Forward case
    AD_BUC_06, // Invalidate SED
    AD_BUC_07, // Reminder
    AD_BUC_08, //
    AD_BUC_09, //
    AD_BUC_10, // Update SED
    AD_BUC_11, // Business Exception «receipt of an erroneous message»
    AD_BUC_12, // Change Participant «Intelligent Routing Application»
    AD_BUC_13, //

    P_BUC_01,  // Krav om alder
    P_BUC_02,  // Krav om gjenlevende
    P_BUC_03,  // Krav om uføretrygd
    P_BUC_04,  // Anmodning om perioder med omsorg for barn
    P_BUC_05,  // Ad hoc forespørsel om informasjon (f.eks trygdetid)
    P_BUC_06,  // Oversendelse av pensjonsinformasjon
    P_BUC_07,  // Anmodning om pensjonsbeløp til fastsettelse av tilleggsytelser
    P_BUC_08,  // Informasjon om pensjonsbeløp til innvilgelse av et pensjonstillegg
    P_BUC_09,  // Endring i personlig forhold (PDF)
    P_BUC_10,  // Overganssaker

    H_BUC_01,  // Ad-hoc Exchange of Information
    H_BUC_04,  // Reimbursement of Administrative check or Medical Information
    H_BUC_07,  // Notification of Death
    H_BUC_08,  // Medical information
    H_BUC_09,  // Notification of Medical Information

    R_BUC_02,  // Motregning i etterbetaling av pensjon (PDF)
    M_BUC_02,  // Krav om førtidspensjon (PDF)
    M_BUC_03a, // Utveksling av informasjon om inntektsbeløp i forbindelse  med særlige, ikke-avgiftsbaserte kontaktytelser
    M_BUC_03b; // Utveksling av informasjon om inntektsbeløp i forbindelse  med særlige, ikke-avgiftsbaserte kontaktytelser,
               // informasjon om tid med lønnet arbeid, tid med selvstendig virksomhet eller botid tilbakebelagt i en eller
               // flere andre medlemsstater

    companion object {
        @JvmStatic
        @JsonCreator
        fun from(value: String?): BucType? {
            if (value == null) return null

            return entries.firstOrNull { it.name == value }
        }
    }
}
