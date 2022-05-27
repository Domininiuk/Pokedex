package com.example.pokdex.data.retrofit


import com.example.pokdex.models.*
import retrofit2.http.GET
import retrofit2.http.Path

interface  PokemonAPI
{
    // Get the pokemon with the given id
    @GET("pokemon/{id}")
   suspend fun getPokemonId(@Path ("id") id : Int) : PokemonModel

   @GET("pokemon/{name}")
   suspend fun getPokemonName(@Path("name") name : String) : PokemonModel

   //Get a list of pokemon names
   @GET("pokemon?limit=1118")
   suspend fun getPokemonList() : PokemonListModel

   //Get evolution chain with the given id
   @GET("evolution-chain/{id}")
   suspend fun getEvolutionChain(@Path("id") id : Int) : EvolutionModel

   //Get pokemon species with the given id
   @GET("pokemon-species/{id}")
   suspend fun getPokemonSpecies(@Path("id") id : Int ) : PokemonSpeciesModel

   @GET("ability/{id}")
   suspend fun getPokemonAbility(@Path("id") id : Int) : PokemonAbilityModel
}