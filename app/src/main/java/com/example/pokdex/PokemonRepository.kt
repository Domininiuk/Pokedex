package com.example.pokdex
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokdex.Models.AllPokemonModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.Models.PokemonSpecies
import com.example.pokdex.ServiceBuilder.buildService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

object PokemonRepository
{
   private var pokemonCount : MutableLiveData<Int> = MutableLiveData(1118)
    private val request : PokemonAPI = ServiceBuilder.buildService(PokemonAPI::class.java)


    fun getRandomId() : Int
    {
        return Random.nextInt(0, pokemonCount.value!!.minus(1))
    }
   
   suspend fun getPokemonId(id : Int) = request.getPokemonId(id)
}