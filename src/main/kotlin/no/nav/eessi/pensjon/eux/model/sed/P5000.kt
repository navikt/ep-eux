package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(P5000::class.java)


data class P5000(
    @JsonProperty("sed")
    override val type: SedType = SedType.P5000,
    override val sedGVer: String,
    override val sedVer: String,
    override val nav: Nav? = null,
    override val pensjon: P5000Pensjon? = null
) : SED {

    //punkt 5.2.1.3.1 i settes til "0" når gyldigperiode == "0"
    fun updateFromUI(): P5000 {
        val p5000 = this
        val medlemskapboarbeid = p5000.pensjon?.medlemskapboarbeid
        val gyldigperiode = medlemskapboarbeid?.gyldigperiode
        val erTom = medlemskapboarbeid?.medlemskap.let { it == null || it.isEmpty() }
        if (gyldigperiode == "0" && erTom) {
            logger.info("P5000 setter 5.2.1.3.1 til 0 ")
            val newPensjon = P5000Pensjon(
                trygdetid = listOf(MedlemskapItem(
                    sum = TotalSum(aar = "0")
                )),
                medlemskapboarbeid = medlemskapboarbeid,
            )
            return p5000.copy(pensjon = newPensjon)
        }
        return p5000
    }

}