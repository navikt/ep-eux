package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(P5000::class.java)


data class P5000(
    @JsonProperty("sed")
    override var type: SedType = SedType.P5000,
    override val sedGVer: String? = "4",
    override var sedVer: String? = "1",
    override var nav: Nav? = null,
    @JsonProperty("pensjon")
    val p5000Pensjon: P5000Pensjon? = null
) : SED(type, sedGVer, sedVer, nav) {

    //punkt 5.2.1.3.1 i settes til "0" når gyldigperiode == "0"
    fun updateFromUI(): P5000 {
        val pensjon = this.p5000Pensjon
        val medlemskapboarbeid = pensjon?.medlemskapboarbeid
        val gyldigperiode = medlemskapboarbeid?.gyldigperiode
        val erTom = medlemskapboarbeid?.medlemskap.let { it?.isEmpty() == true }
        if (gyldigperiode == "0" && erTom) {
            logger.debug("gyldigperiode = $gyldigperiode setter totalaar til 0")
            val newPensjon = P5000Pensjon(
                trygdetid = listOf(MedlemskapItem(
                    sum = TotalSum(aar = "0")
                )),
                medlemskapboarbeid = medlemskapboarbeid,
            )
            return this.copy(p5000Pensjon = newPensjon)
        }
        return this
    }

}