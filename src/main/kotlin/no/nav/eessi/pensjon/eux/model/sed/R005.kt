package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class R005(
    @JsonProperty("sed")
    override val type: SedType = SedType.R005,
    @JsonProperty("nav")
    val recoveryNav: RNav? = null,
    val tilbakekreving: Tilbakekreving? = null
) : SED(type)

//R005 nav
data class RNav(
    val brukere: List<Brukere>? = null,
    val eessisak: List<EessisakItem>? = null
)

//kun for R005
data class Brukere(
    val mor: Foreldre? = null,
    val far: Foreldre? = null,
    val person: Person? = null,
    @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY]) // i R005 kan det være flere adresser pr person
    val adresse: List<Adresse>? = null,
    val arbeidsforhold: List<ArbeidsforholdItem>? = null,
    val bank: Bank? = null,
    val tilbakekreving: Tilbakekreving? = null // Kun brukt av R005
)

//R005
@JsonIgnoreProperties(ignoreUnknown = true)
data class Tilbakekreving(
    val feilutbetaling: Feilutbetaling? = null,
    val status: Status? = null
)
