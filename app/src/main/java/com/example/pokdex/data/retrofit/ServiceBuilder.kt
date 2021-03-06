package com.example.pokdex.data.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val BASE_URL : String = "https://pokeapi.co/api/v2/"
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(
        GsonBuilder().create()
    )).client(client).
            build()

    fun<T> buildService (service : Class<T>) : T
    {
        return retrofit.create(service)
    }
}