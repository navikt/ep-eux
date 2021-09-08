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
    val tilbakekreving: TilbakekrevingBrukere? = null // Kun brukt av R005
)

//R005
@JsonIgnoreProperties(ignoreUnknown = true)
data class Tilbakekreving(
    val feilutbetaling: Feilutbetaling? = null,
    val anmodning: AnmodningItem? = null,
    val anmodningommotregning: AnmodningMotregningItem? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TilbakekrevingBrukere(
    val status: Status? = null,
)

data class AnmodningMotregningItem(
    val bank: Bank?
)

data class AnmodningItem( val type: String? = null)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Status(
    val type: String? = null,
    val annen: String? = null
)

data class Feilutbetaling(
    val ytelse: Ytelse? = null,
    val valuta: String? = null,
    val beloep: String? = null,
    val periode: TilbakekrevingsPeriode? = null
)

data class TilbakekrevingsPeriode(
    val sluttdato: String? = null,
    val startdato: String? = null
)