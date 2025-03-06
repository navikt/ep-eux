package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue

class P8000Pensjon(
    val gjenlevende: Bruker? = null,
    val anmodning: AnmodningOmTilleggsInfo? = null,
    val ytterligeinformasjon: String? = null,
    val vedlegg: List<String> ? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class AnmodningOmTilleggsInfo(
    val referanseTilPerson: String? = null,
    val seder: List<Seder>? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
class Seder (
    val sendFolgendeSEDer: List<SedNummer>? = null,
    val andreEtterspurteSEDer: String? = null,
    val begrunnelse: String? = null
)

enum class SedNummer(val kode: String) {
    p1000("01"),
    p1100("02"),
    p2000("03"),
    p2100("04"),
    p2200("05"),
    p4000("06"),
    p5000("07"),
    p6000("08"),
    p7000("09"),
    andre_seder("99");

    // Ensure serialization returns the name
    @JsonValue
    override fun toString(): String {
        return name
    }

    // Allow deserialization from either the name or the kode
    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): SedNummer {
            return entries.find { it.name == value || it.kode == value }
                ?: throw IllegalArgumentException("Invalid SedNummer: $value")
        }
    }
}
