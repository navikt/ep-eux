package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SedClassConverting {

    @Test
    fun fromJsonToP2000Class() {
        val json = javaClass.getResource("/sed/P2000-NAV.json").readText()
        Assertions.assertEquals(GenericSED::class.java,  SED.fromJsonToClass<GenericSED>(json).javaClass)
        Assertions.assertEquals(GenericSED::class.java, SED.fromJson(json).javaClass)
    }

    @Test
    fun fromJsonToP2100Class() {
        val json = javaClass.getResource("/sed/P2100-PinNo.json").readText()
        Assertions.assertEquals(GenericSED::class.java,  SED.fromJsonToClass<GenericSED>(json).javaClass)
        Assertions.assertEquals(GenericSED::class.java, SED.fromJson(json).javaClass)
    }

    @Test
    fun fromJsonToP6000Class() {
        val json = javaClass.getResource("/sed/P6000-gjenlevende-NAV.json").readText()
        Assertions.assertEquals(P6000::class.java,  SED.fromJsonToClass<P6000>(json).javaClass)
        Assertions.assertEquals(P6000::class.java, SED.fromJson(json).javaClass)
    }

    @Test
    fun fromJsonToR005Class() {
        val json = javaClass.getResource("/sed/R005-avdod-enke-NAV.json").readText()
        Assertions.assertEquals(R005::class.java,  SED.fromJsonToClass<R005>(json).javaClass)
        Assertions.assertEquals(R005::class.java, SED.fromJson(json).javaClass)
    }

}