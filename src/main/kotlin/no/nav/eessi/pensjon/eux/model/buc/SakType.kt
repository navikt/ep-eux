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

enum class SakStatus {
    AVSLUTTET,
    LOPENDE,
    OPPRETTET,
    TIL_BEHANDLING
}