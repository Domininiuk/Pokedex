package com.example.pokdex


import com.example.pokdex.Models.PokemonListModel
import com.example.pokdex.Models.PokemonModel
import retrofit2.http.GET
import retrofit2.http.Path

interface  PokemonAPI
{
    // Get the pokemon with the given id
    @GET("pokemon/{id}")
   suspend fun getPokemonId(@Path ("id") id : Int) : PokemonModel

   //Get a list of pokemon names
   @GET("pokemon?limit=1118")
   suspend fun getPokemonList() : PokemonListModel
}