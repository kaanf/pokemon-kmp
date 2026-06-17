package com.kaanf.pokemon.presentation.util

import com.kaanf.pokemon.domain.model.UnitSystem
import kotlin.test.Test
import kotlin.test.assertEquals

class MeasurementFormatterTest {

    @Test
    fun formatsHeightInMeters() {
        assertEquals("0.7 m", formatHeight(7, UnitSystem.METRIC))
        assertEquals("1.7 m", formatHeight(17, UnitSystem.METRIC))
    }

    @Test
    fun formatsHeightInFeetAndInches() {
        assertEquals("2'04\"", formatHeight(7, UnitSystem.IMPERIAL))
    }

    @Test
    fun formatsWeightInKilograms() {
        assertEquals("6.9 kg", formatWeight(69, UnitSystem.METRIC))
    }

    @Test
    fun formatsWeightInPounds() {
        assertEquals("15.2 lbs", formatWeight(69, UnitSystem.IMPERIAL))
    }
}
