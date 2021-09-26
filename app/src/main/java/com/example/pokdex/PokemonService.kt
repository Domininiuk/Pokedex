package com.example.pokdex


import com.example.pokdex.Models.Pokemon
import retrofit2.Call
import retrofit2.http.GET

interface  PokemonService
{

    @GET("ditto")
    fun getDitto() : Call<Pokemon>
}