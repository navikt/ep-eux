package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.utils.toJsonSkipEmpty
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.skyscreamer.jsonassert.JSONAssert

internal class JsonMappingToSed {

    @ParameterizedTest
    @MethodSource("listSedType")
    fun `Beskrivelse inneholder SedType`(pair: Pair<SedType, String>) {
        val type = pair.first
        val filename = pair.second

        val json = try {
            readFile(filename)
        } catch (ex: Exception) {
            println("Feil readFile :${ex.message}")
            """ {
              "sed": "$type",
              "sedGVer": "4",
              "sedVer": "2",
              "nav": null 
              }
            """.trimIndent()
        }
        val sed = SED.fromJsonToConcrete(json)
        println("*** ${sed.type} ***")

        assertEquals(type, sed.type)
        assertEquals(type.name, sed.javaClass.simpleName)
        assertPensjon(sed)

        JSONAssert.assertEquals( sed.toJsonSkipEmpty(), json, false)

    }

    fun assertPensjon(sed: SED) {
        println("*** detaljert assert på nav og pensjon type: ${sed.type} ***")
        when(sed) {
            is P2000 -> {
                assertEquals(P2000::class.java.name, sed.javaClass.name)
                assertEquals("4.2", sedVersion(sed))
                sed.sedVer = "5"
                assertEquals("4.5", sedVersion(sed))
                sed.sedVer = "2" //sett tilbake

            }
            is P2100 -> {
                assertEquals(P2100::class.java.name, sed.javaClass.name)
                assertEquals("4.0", sedVersion(sed))
                sed.sedVer = "5"
                assertEquals("4.5", sedVersion(sed))
                sed.sedVer = "0" // sett tilbake
                assertEquals("adgadfgadfgadfgadfgadfgadfgadfgadfgadfgadfgdafgdaf", sed.pensjon?.gjenlevende?.person?.etternavn)
            }
            is P5000 -> {
                val p5000: P5000 = sed
                assertEquals(P5000::class.java.name, p5000.javaClass.name)
                assertNotNull(p5000.p5000Pensjon)
                assertEquals("231", p5000.p5000Pensjon?.medlemskapboarbeid?.gyldigperiode)
            }
            is R005 -> {
                val r005: R005 = sed
                assertEquals(R005::class.java.name, r005.javaClass.name)
                assertNotNull(r005.recoveryNav?.brukere)
                assertEquals("TRANE", r005.recoveryNav?.brukere?.first()?.person?.etternavn)
            }
            is X005 -> {
                val x005: X005 = sed
                assertEquals(X005::class.java.name, x005.javaClass.name)
                assertNotNull(x005.xnav?.sak)
                assertEquals("Duck", x005.xnav?.sak?.kontekst?.bruker?.person?.etternavn)
                assertEquals("Dummy", x005.xnav?.sak?.kontekst?.bruker?.person?.fornavn)
            }
            is X010 -> {
                assertEquals(X010::class.java.name, sed.javaClass.name)
                assertNotNull(sed.xnav?.sak)
                assertEquals("BJELLEKLANGEN", sed.xnav?.sak?.kontekst?.bruker?.person?.etternavn)
                assertEquals("Opplysningenenenenen", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.kommersenere?.first()?.opplysninger)
            }
            is P6000 -> {
                val p6000: P6000 = sed
                assertEquals(P6000::class.java.name, p6000.javaClass.name)
                assertNotNull(p6000.p6000Pensjon)
                assertEquals("asdffsdaf", p6000.p6000Pensjon?.gjenlevende?.person?.etternavn)
                assertEquals("sdafsdf", p6000.p6000Pensjon?.gjenlevende?.person?.fornavn)
            }
            is P7000 -> {
                val p7000 = sed
                assertEquals(2, p7000.p7000Pensjon?.samletVedtak?.tildeltepensjoner?.size)
                assertEquals(1, p7000.p7000Pensjon?.samletVedtak?.avslag?.size)
            }
            is P8000 -> {
                assertEquals(P8000::class.java.name, sed.javaClass.name)
                assertNotNull(sed.p8000Pensjon)
                assertNotNull(sed.nav?.annenperson)
                assertEquals("02", sed.p8000Pensjon?.anmodning?.referanseTilPerson)
                assertEquals("Gjenlev", sed.nav?.annenperson?.person?.etternavn)
            }
            is P15000 -> {
                assertEquals(P15000::class.java.name, sed.javaClass.name)
                assertNotNull(sed.p15000Pensjon)
                assertEquals("322", sed.p15000Pensjon?.gjenlevende?.person?.fornavn)
                assertEquals("321", sed.p15000Pensjon?.gjenlevende?.person?.etternavn)
            }
            else -> println("Ikke noe detaljert assert på ${sed.type}") //
        }
    }

    private fun sedVersion(sed: SED) = "${sed.sedGVer}.${sed.sedVer}"

    private fun readFile(file: String): String = javaClass.getResource(file).readText()

    companion object {
        @JvmStatic
        fun listSedType(): List<Pair<SedType,String>> {
            return SED.listSupportetConcreteClass()
                .filterNot { it == SedType.P4000 }
                .map { Pair(it, "/sed/$it-NAV.json") }
        }
    }

}