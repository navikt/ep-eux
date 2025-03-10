package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import no.nav.eessi.pensjon.eux.model.SedType

@JsonIgnoreProperties(ignoreUnknown = true)
class P8000Frontend(
    @JsonProperty("sed")
    type: SedType = SedType.P8000,
    nav: Nav? = null,
    @JsonProperty("pensjon")
    p8000Pensjon: P8000Pensjon?,
    var options: Options? = null,

) : P8000(type, nav, p8000Pensjon)

data class Options(
    val type: Type?,
    val ofteEtterspurtInformasjon: OfteEtterspurtInformasjon?,
    val informasjonSomKanLeggesInn: InformasjonSomKanLeggesInn?
)

data class Type(
    val bosettingsstatus: String?,
    val spraak: String?,
    val ytelse: String?
)

data class OfteEtterspurtInformasjon(
    val tiltak: BooleanValue?,
    val inntektFoerUfoerhetIUtlandet: InntektFoerUfoerhetIUtlandet?,
    val brukersAdresse: BooleanValue?,
    val medisinskInformasjon: BooleanValue?,
    val naavaerendeArbeid: BooleanValue?,
    val dokumentasjonPaaArbeidINorge: BooleanValue?,
    val ytelseshistorikk: BooleanValue?,
    val ibanSwift: BooleanValue?,
    val folkbokfoering: BooleanValue?,
    val brukersSivilstand: BooleanValue?,
    val opplysningerOmEPS: OpplysningerOmEPS?,
    val personUtenPNRDNR: BooleanValue?
)

data class InformasjonSomKanLeggesInn(
    val saksbehandlingstid: Saksbehandlingstid?,
    val P5000ForP5000NO: BooleanValue?
)

data class BooleanValue(
    val value: Boolean?
)

data class InntektFoerUfoerhetIUtlandet(
    val value: Boolean?,
    val landkode: String?,
    val periodeFra: String?,
    val periodeTil: String?
)

data class OpplysningerOmEPS(
    val value: Boolean?,
    val landkode: String?
)

data class Saksbehandlingstid(
    val value: Boolean?,
    val antallMaaneder: String?
)
