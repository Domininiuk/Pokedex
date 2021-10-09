package com.example.pokdex


import com.example.pokdex.Models.AllPokemonModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.Models.PokemonSpecies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface  PokemonAPI
{
    // Get the pokemon with the given id
    @GET("pokemon/{id}")
   suspend fun getPokemonId(@Path ("id") id : Int) : PokemonModel
}