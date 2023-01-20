package no.nav.eessi.pensjon.eux.model.buc

enum class SakType {
    ALDER,
    UFOREP,
    GJENLEV,
    BARNEP,
    OMSORG,
    GENRL,
    KRIGSP,
    GAM_YRK,
    AFP_PRIVAT,
    AFP
}

enum class SakStatus(val status: String) {
    TIL_BEHANDLING("TIL_BEHANDLING"),
    AVSLUTTET("AVSL"),
    LOPENDE("INNV"),
    OPPHOR("OPPHOR"),
    OPPRETTET(""),
    UKJENT("");

    companion object {
        @JvmStatic
        fun from(s: String): SakStatus {
            return values().firstOrNull { it.status == s } ?: UKJENT
        }
    }

}