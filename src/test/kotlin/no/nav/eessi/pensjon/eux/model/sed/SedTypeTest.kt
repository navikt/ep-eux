package no.nav.eessi.pensjon.eux.model.sed

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class SedTypeTest {

    @ParameterizedTest
    @EnumSource(SedType::class)
    fun `Beskrivelse inneholder SedType`(type: SedType) {
        assertTrue(
            type.typeMedBeskrivelse().startsWith(type.name),
            "Beskrivelse skal begynne med SedType"
        )
    }
}