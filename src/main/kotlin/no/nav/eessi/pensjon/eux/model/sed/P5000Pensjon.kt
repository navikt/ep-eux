package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


data class P5000Pensjon(
    override val gjenlevende: Bruker? = null,
    val separatP5000sendes: String? = "0",  //Kap 7 denne verdien settes default til nei  = "0"
    val trygdetid: List<MedlemskapItem>? = null,
    // Benyttes for visning av "Se annen" siden for P5000 i fontend
    val medlemskapAnnen: List<MedlemskapItem>? = null,
    val medlemskapTotal: List<MedlemskapItem>? = null,

    // Benyttes for visning av "Se oversikt" siden for 5000 i frontend
    @Deprecated("se medlemskapboarbeid", ReplaceWith("medlemskapboarbeid"))
    val medlemskap: List<MedlemskapItem>? = null,
    val medlemskapboarbeid: Medlemskapboarbeid? = null
) : Pensjon()

data class Medlemskapboarbeid(
    val enkeltkrav: KravtypeItem? = null,
    val gyldigperiode: String? = null,
    val medlemskap: List<MedlemskapItem>? = null
)

/**
 * Benyttes både ved prefill og ved visning av trygdetidsperioder til frontend
 *
 * Flere felter her er ubrukt i javakoden men benyttes av frontend
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class MedlemskapItem(
    val relevans: String? = null,
    val ordning: String? = null,
    val land: String? = null,
    val sum: TotalSum? = null,
    val yrke: String? = null,
    val gyldigperiode: String? = null,
    val type: String? = null,
    val beregning: String? = null,
    val informasjonskalkulering: String? = null,
    val periode: Periode? = null,
    val enkeltkrav: KravtypeItem? = null
)

data class Dager(
    val nr: String? = null,
    val type: String? = null
)

data class TotalSum(
    val kvartal: String? = null,
    val aar: String? = null,
    val uker: String? = null,
    val dager: Dager? = null,
    val maaneder: String? = null
)