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

        assertEquals(type, sed.type)
        assertEquals(type.name, sed.javaClass.simpleName)

        assertPensjon(sed)

        JSONAssert.assertEquals( sed.toJsonSkipEmpty(), json, false)

    }

    fun assertPensjon(sed: SED) {
        println("assert detaljert på nav og pensjon type: ${sed.type}")
        when(sed) {
            is P5000 -> {
                val p5000 = sed
                assertEquals(P5000::class.java.name, p5000.javaClass.name)
                assertNotNull(p5000.p5000Pensjon)
                assertEquals("231", p5000.p5000Pensjon?.medlemskapboarbeid?.gyldigperiode)
            }
            is R005 -> {
                val r005 = sed
                assertEquals(R005::class.java.name, r005.javaClass.name)
                assertNotNull(r005.nav?.brukere)
                assertEquals("TRANE", r005.nav?.brukere?.first()?.person?.etternavn)
            }
            is X005 -> {
                val x005 = sed
                assertEquals(X005::class.java.name, x005.javaClass.name)
                assertNotNull(x005.nav?.sak)
                assertEquals("Duck", x005.nav?.sak?.kontekst?.bruker?.person?.etternavn)
                assertEquals("Dummy", x005.nav?.sak?.kontekst?.bruker?.person?.fornavn)
            }
            is X010 -> {
                val x010 = sed
                assertEquals(X010::class.java.name, x010.javaClass.name)
                assertNotNull(x010.nav?.sak)
                assertEquals("BJELLEKLANGEN", x010.nav?.sak?.kontekst?.bruker?.person?.etternavn)
                assertEquals("Opplysningenenenenen", x010.nav?.sak?.paaminnelse?.svar?.informasjon?.kommersenere?.first()?.opplysninger)
            }
            is P6000 -> {
                val p6000 = sed
                assertEquals(P6000::class.java.name, p6000.javaClass.name)
                assertNotNull(p6000.p6000Pensjon)
                assertEquals("asdffsdaf", p6000.p6000Pensjon?.gjenlevende?.person?.etternavn)
                assertEquals("sdafsdf", p6000.p6000Pensjon?.gjenlevende?.person?.fornavn)
            }
            is P8000 -> {
                val p8000 = sed
                assertEquals(P8000::class.java.name, p8000.javaClass.name)
                assertNotNull(p8000.p8000Pensjon)
                assertNotNull(p8000.nav?.annenperson)
                assertEquals("02", p8000.p8000Pensjon?.anmodning?.referanseTilPerson)
                assertEquals("Gjenlev", p8000.nav?.annenperson?.person?.etternavn)
            }
            is P15000 -> {
                val p15000 = sed
                assertEquals(P15000::class.java.name, p15000.javaClass.name)
                assertNotNull(p15000.p15000Pensjon)
                assertEquals("322", p15000.p15000Pensjon?.gjenlevende?.person?.fornavn)
                assertEquals("321", p15000.p15000Pensjon?.gjenlevende?.person?.etternavn)
            }
            else -> println("Ikke noe detaljert assert på ${sed.type}") //
        }
    }


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