package com.example.pokdex.models

import junit.framework.TestCase
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains


class PokemonSpritesTest : TestCase() {

    fun testGetOfficialArtworkFrontDefault() {}

    fun testGetListOfUrls_allEmpty_returns_allEmpty()
    {
        //Create Pokemon Sprites
        var sprites = PokemonSprites("", "", "","",
            "","","","",
            PokemonOther(PokemonDreamWorld("",""), PokemonOfficialArtwork(""),))

        var result = sprites.getListOfUrls()

        //result length 18
        assertThat(result, contains("", "", "", "", "","","","",))
    }
}