package com.example.pokdex.Models

import junit.framework.TestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class PokemonModelTest : TestCase() {

    fun testGetOfficialArtworkFrontDefault() {
        // Create a Pokemon Model with sprites
        //var model = PokemonModel(0, 0, "", 0.0f, PokemonSprites(), 0)

        // Call the function


        // Check the result
    }

    fun testGetHeightInCentimeters_Zero_ReturnsZero() {
        // Create a Pokemon Model
        var model = PokemonModel(0, 0 ,"", 0.0f, null, 0)

        // Call the function
        var result = model.getHeightInCentimeters()

        // Check the result

        assertThat(result, equalTo(0))
    }

    fun testGetHeightInCentimeters_One_returns_Ten() {
        // Create a Pokemon Model
        var model = PokemonModel(0, 0 ,"", 0.0f, null, 1)

        // Call the function
        var result = model.getHeightInCentimeters()

        // Check the result

        assertThat(result, equalTo(10))
    }
    fun testGetHeightInCentimeters_MinusTen_returns_MinusHundred() {
        // Create a Pokemon Model
        var model = PokemonModel(0, 0 ,"", 0.0f, null, -10)

        // Call the function
        var result = model.getHeightInCentimeters()

        // Check the result

        assertThat(result, equalTo(-100))
    }
    fun testGetWeightInKilograms_ZeroPointZero_ReturnsZeroPointZero() {
        // Create a Pokemon Model
        var model = PokemonModel(0, 0 ,"", 0.0f, null, 0)

        // Call the function
        var result = model.getWeightInKilograms()

        // Check the result
        assertThat(result, equalTo(0.0f))
    }

    fun testGetWeightInKilograms_OnePointZero_ReturnsZeroPointOne()
    {
        // Create a Pokemon Model
        var model = PokemonModel(0, 0 ,"", 1.0f, null, 0)

        // Call the function
        var result = model.getWeightInKilograms()

        // Check the result
        assertThat(result, equalTo(0.1f))
    }

}