package com.example.pokdex


import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.Models.PokemonSpecies
import retrofit2.Call
import retrofit2.http.GET

interface  PokemonAPI
{

    // Get the ditto pokemon (Test method)
    @GET("pokemon/ditto")
    fun getDitto() : Call<PokemonModel>

    // Get the pokemon with the given id
    @GET("pokemon/{id}")
    fun getPokemon(id : Int) : Call<PokemonModel>

    // Create model class forthis
    @GET("pokemon-species/?limit=0")
    fun getPokemonCount() : Call<PokemonSpecies>
}