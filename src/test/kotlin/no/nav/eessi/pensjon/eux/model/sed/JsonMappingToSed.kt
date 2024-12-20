package no.nav.eessi.pensjon.eux.model.sed

import no.nav.eessi.pensjon.eux.model.SedType
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
        val json = jsonString(pair)
        val sed = SED.fromJsonToConcrete(json)
        println("*** ${sed.type} ***")

        assertEquals(type, sed.type)
        assertEquals(type.name, sed.javaClass.simpleName)
        assertPensjon(sed)

        JSONAssert.assertEquals( sed.toJsonSkipEmpty(), json, false)

    }

    @ParameterizedTest
    @MethodSource("listSedTypeFromRina")
    fun `Se at alle json fra RINA mapper korrekt`(pair: Pair<SedType, String>) {
        val json = jsonString(pair)
        val sed = SED.fromJsonToConcrete(json)

//        println(sed.toJsonSkipEmpty())

//        println("*".repeat(100))
//
//        println(json)
        print(json)

        JSONAssert.assertEquals( sed.toJsonSkipEmpty(), json, false)
    }

    private fun jsonString(pair: Pair<SedType, String>): String {
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
        return json
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
                assertNotNull(p5000.pensjon)
                assertEquals("231", p5000.pensjon?.medlemskapboarbeid?.gyldigperiode)
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

                assertEquals("NO:NAVT002", x005.xnav?.sak?.leggtilinstitusjon?.institusjon?.id)
                assertEquals("joijo joijo", x005.xnav?.sak?.leggtilinstitusjon?.grunn?.annet)

            }
            is X009 -> {
                assertEquals(X009::class.java.name, sed.javaClass.name)
                assertNotNull(sed.xnav?.sak)
                val x009: X009 = sed
                assertEquals("æøå", x009.xnav?.sak?.kontekst?.arbeidsgiver?.navn)

                assertEquals("æøå", x009.xnav?.sak?.kontekst?.refusjonskrav?.id)
                assertEquals("æøå", x009.xnav?.sak?.kontekst?.refusjonskrav?.antallkrav)

                assertEquals("dokument", x009.xnav?.sak?.paaminnelse?.sende?.firstOrNull()?.type)
                assertEquals("æøå", x009.xnav?.sak?.paaminnelse?.sende?.firstOrNull()?.detaljer)



            }
            is X010 -> {
                assertEquals(X010::class.java.name, sed.javaClass.name)
                assertNotNull(sed.xnav?.sak)
                assertEquals("æøå", sed.xnav?.sak?.kontekst?.bruker?.person?.etternavn)
                assertEquals("dokument", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.ikketilgjengelig?.first()?.type)
                assertEquals("æøå", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.ikketilgjengelig?.first()?.opplysninger)
                assertEquals("kan_ikke_fremlegge_etterspurt_informasjon", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.ikketilgjengelig?.first()?.grunn?.type)


                assertEquals("æøå", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.kommersenere?.first()?.opplysninger)
                assertEquals("dokument", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.kommersenere?.first()?.type)
                assertEquals("æøå", sed.xnav?.sak?.paaminnelse?.svar?.informasjon?.kommersenere?.first()?.forventetdato)

            }
            is P6000 -> {
                val p6000: P6000 = sed
                assertEquals(P6000::class.java.name, p6000.javaClass.name)
                assertNotNull(p6000.pensjon)
                assertEquals("asdffsdaf", p6000.pensjon?.gjenlevende?.person?.etternavn)
                assertEquals("sdafsdf", p6000.pensjon?.gjenlevende?.person?.fornavn)
                assertEquals("234", p6000.pensjon?.vedtak?.firstOrNull()?.delvisstans?.utbetaling?.beloepBrutto)

            }
            is P7000 -> {
                assertEquals(2, sed.pensjon?.samletVedtak?.tildeltepensjoner?.size)
                assertEquals(1, sed.pensjon?.samletVedtak?.avslag?.size)
            }
            is P8000 -> {
                assertEquals(P8000::class.java.name, sed.javaClass.name)
                assertNotNull(sed.p8000Pensjon)
                assertNotNull(sed.nav?.annenperson)
                assertEquals("02", sed.p8000Pensjon?.anmodning?.referanseTilPerson)
                assertEquals("Gjenlev", sed.nav?.annenperson?.person?.etternavn)
            }
            is P12000 -> {
                assertEquals(P12000::class.java.name, sed.javaClass.name)
                assertNotNull(sed.pensjon)
                assertEquals("01", sed.pensjon?.pensjoninfo?.firstOrNull()?.betalingsdetaljer?.pensjonstype)
                assertEquals("maaned_12_per_aar", sed.pensjon?.pensjoninfo?.firstOrNull()?.betalingsdetaljer?.utbetalingshyppighet)
                assertEquals("11111", sed.pensjon?.pensjoninfo?.firstOrNull()?.betalingsdetaljer?.belop)
                assertEquals("xx", sed.pensjon?.pensjoninfo?.firstOrNull()?.betalingsdetaljer?.effektueringsdato)
            }

            is P15000 -> {
                assertEquals(P15000::class.java.name, sed.javaClass.name)
                assertNotNull(sed.pensjon)
                assertEquals("322", sed.pensjon?.gjenlevende?.person?.fornavn)
                assertEquals("321", sed.pensjon?.gjenlevende?.person?.etternavn)
            }
            else -> {
                if(sed.type == SedType.H121) {
                    assertEquals("The Norwegian Labour and Welfare Administration", sed.nav?.bruker?.person?.pin?.first()?.institusjonsnavn)
                }
                println("Ikke noe detaljert assert på ${sed.type}")
            } //
        }
    }

    private fun sedVersion(sed: SED) = "${sed.sedGVer}.${sed.sedVer}"

    private fun readFile(file: String): String = javaClass.getResource(file).readText()

    companion object {
        @JvmStatic
        fun listSedType(): List<Pair<SedType,String>> {
            return SED.listSupportetConcreteClass()
                .map { Pair(it, "/sed/$it-NAV.json") }
        }
        @JvmStatic
        fun listSedTypeFromRina(): List<Pair<SedType,String>> {
            return SED.listSupportetConcreteClass()
                .map { Pair(it, "/sed/$it-RINA.json") }
        }
    }
}