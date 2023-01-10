package no.nav.eessi.pensjon.eux.model

import no.nav.eessi.pensjon.eux.model.BucType.*
import no.nav.eessi.pensjon.eux.model.SedType.*
import no.nav.eessi.pensjon.personoppslag.Fodselsnummer
import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SedHendelseTest {

    @Test
    fun `Serdes test for SedHendelse`() {
        val sedhendelseJson = """
            {
              "id" : 0,
              "sedId" : null,
              "sektorKode" : "P",
              "bucType" : "P_BUC_01",
              "rinaSakId" : "123456479867",
              "avsenderId" : null,
              "avsenderNavn" : "avsenderNavn",
              "avsenderLand" : "avsenderLand",
              "mottakerId" : null,
              "mottakerNavn" : "mottakerNavn",
              "mottakerLand" : "mottakerLand",
              "rinaDokumentId" : "SOME_DOKUMENT_ID",
              "rinaDokumentVersjon" : "SOME_RINADOKUMENT_VERSION",
              "sedType" : "P2200",
              "navBruker" : "22190656256"
            }
        """.trimIndent()

        val sedHendelse = mapJsonToAny<SedHendelse>(sedhendelseJson)

        assertEquals(sedHendelse.id, 0)
        assertEquals(sedHendelse.sedId, null)
        assertEquals(sedHendelse.sektorKode, "P")
        assertEquals(sedHendelse.bucType, P_BUC_01)
        assertEquals(sedHendelse.rinaSakId, "123456479867")
        assertEquals(sedHendelse.avsenderId, null)
        assertEquals(sedHendelse.avsenderNavn, "avsenderNavn")
        assertEquals(sedHendelse.avsenderLand, "avsenderLand")
        assertEquals(sedHendelse.mottakerId, null)
        assertEquals(sedHendelse.mottakerNavn, "mottakerNavn")
        assertEquals(sedHendelse.mottakerLand, "mottakerLand")
        assertEquals(sedHendelse.rinaDokumentId, "SOME_DOKUMENT_ID")
        assertEquals(sedHendelse.sedType, P2200)
        assertEquals(sedHendelse.navBruker, Fodselsnummer.fra("22190656256"))
    }

}