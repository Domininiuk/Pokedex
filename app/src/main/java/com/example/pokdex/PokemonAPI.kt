package com.example.pokdex


import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.Models.PokemonSpecies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface  PokemonAPI
{

    // Get the ditto pokemon (Test method)
    @GET("pokemon/ditto")
    fun getDitto() : Call<PokemonModel>

    // Get the pokemon with the given id
    @GET("pokemon/{id}")
    fun getPokemon(@Path ("id") id : Int) : Call<PokemonModel>

    //Get a list of all pokemon
    @GET("pokemon?limit={limit}&offset={offset}")
    fun getListOfPokemon(limit : Int, offset : Int) : Call<List<PokemonModel>>
    // Get the pokemon with the given string
    @GET("pokemon-species/")
    fun getPokemon(name : String) : Call<PokemonSpecies>
    // Create model class forthis
    @GET("pokemon-species/?limit=0")
    fun getPokemonCount() : Call<PokemonSpecies>

    @GET("pokemon-species/{id}")
    fun getPokemonSpecies(id : Int) : Call<PokemonSpecies>
}