package no.nav.eessi.pensjon.eux.model.sed

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class R005Nav(
        val eessisak: List<EessisakItem>? = null,
        @JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
        val brukere: List<Bruker>? = null,
        val ektefelle: Ektefelle? = null,
        val barn: List<BarnItem>? = null, //pkt 6 og 8
        val verge: Verge? = null,
        val krav: Krav? = null,

        //X005
        val sak: Navsak? = null,
        //P10000 hvordan få denne til å bli val?
        var annenperson: Bruker? = null,
)