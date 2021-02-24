package no.nav.eessi.pensjon.eux.model.buc

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

    P_BUC_01, // Old age pension claim
    P_BUC_02, //
    P_BUC_03, // Invalidity pension claim
    P_BUC_04, //
    P_BUC_05, // Ad-hoc Request for Pension Information
    P_BUC_06, // Notification of pension information
    P_BUC_07, //
    P_BUC_08, //
    P_BUC_09, // Change in personal circumstances
    P_BUC_10, //

    H_BUC_01, // Ad-hoc Exchange of Information
    H_BUC_04, // Reimbursement of Administrative check or Medical Information
    H_BUC_07, // Notification of Death
    H_BUC_08, // Medical information
    H_BUC_09, // Notification of Medical Information

    R_BUC_02, // Offsetting of overpayment with arrears
    ;

    companion object {
        @JvmStatic
        @JsonCreator
        fun from(value: String?): BucType? {
            if (value == null) return null

            return values().firstOrNull { it.name == value }
        }
    }
}
