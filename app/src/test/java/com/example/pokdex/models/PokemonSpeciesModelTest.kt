package com.example.pokdex.models

import junit.framework.TestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo

class PokemonSpeciesModelTest : TestCase() {

    fun testGetChainId_EmptyUrl_ReturnsMinusOne()
    {
        var model= PokemonSpeciesModel.EvolutionChain("")

       var result =  model.getChainId()

        assertThat(result, equalTo(-1))
    }

    fun testGetChainId_CorrectUrl_ReturnsTen()
    {
        var model= PokemonSpeciesModel.EvolutionChain("https://pokeapi.co/api/v2/evolution-chain/10")

        var result =  model.getChainId()

        assertThat(result, equalTo(10))
    }

    //last character cant be a letter or a sign
    fun testGetChainId_IncorrectUrl_ReturnsMinusOne()
    {
        var model= PokemonSpeciesModel.EvolutionChain("https://pokeapi.co/api/v2/evolution-chain/f")

        var result =  model.getChainId()

        assertThat(result, equalTo(-1))
    }


}