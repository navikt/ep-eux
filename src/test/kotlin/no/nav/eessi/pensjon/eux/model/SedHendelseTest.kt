package no.nav.eessi.pensjon.eux.model

import no.nav.eessi.pensjon.eux.model.BucType.P_BUC_01
import no.nav.eessi.pensjon.eux.model.SedType.SEDTYPE_P2200
import no.nav.eessi.pensjon.shared.person.Fodselsnummer
import no.nav.eessi.pensjon.utils.mapAnyToJsonWithoutSensitiveData
import no.nav.eessi.pensjon.utils.mapJsonToAny
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SedHendelseTest {


    @Test
    fun `Propertyfiler for sed skal filterer bort navbruker`(){
        val person = generertSedhendelse()
        val json = mapAnyToJsonWithoutSensitiveData(json = person, ignoredProperties = listOf("navBruker"))
        assert(json.contains("navBruker").not())
        assert(json.contains("22190656256").not())
    }

    @Test
    fun `Serdes test for SedHendelse`() {
        val sedHendelse:SedHendelse = generertSedhendelse()

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
        assertEquals(sedHendelse.sedType, SEDTYPE_P2200)
        assertEquals(sedHendelse.navBruker, Fodselsnummer.fra("22190656256"))
    }

    private fun generertSedhendelse(): SedHendelse {
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
        return mapJsonToAny(sedhendelseJson)
    }

}