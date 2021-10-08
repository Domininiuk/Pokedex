package com.example.pokdex
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
   private var pokemonCount : MutableLiveData<Int> = MutableLiveData()
    private val request = ServiceBuilder.buildService(PokemonAPI::class.java)
    private  var currentPokemon : MutableLiveData<PokemonModel> = MutableLiveData(PokemonModel(""))
    private val allPokemon = MutableLiveData<List<PokemonModel>>()

  init {
    pokemonCount.value = 1118

  }

    fun getRandomId() : Int
    {
        return Random.nextInt(0, pokemonCount.value!!.minus(1))
    }
    //
    //Send a request for the number of pokemon
    fun getPokemonCount() : LiveData<Int>
    {
            val call = request.getPokemonCount()

            call.enqueue(object : Callback<PokemonSpecies>{
                override fun onResponse(
                    call: Call<PokemonSpecies>,
                    response: Response<PokemonSpecies>
                ) {
                    pokemonCount.value = response.body()!!.count
                }
                override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

            return pokemonCount

    }
/*
    fun getAllPokemon() : LiveData<List<PokemonModel>>
    {

    }

 */
    fun getPokemon(id : Int) : LiveData<PokemonModel>
    {
        val call = request.getPokemon(id)
        call.enqueue(object : Callback<PokemonModel> {
            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
                if (response.isSuccessful) {
                    currentPokemon.value = response.body()!!
                }
            }

            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return currentPokemon
    }
    // Send a request to get count of pokemon
    //Generate an integer from 0 to 897 (or 1 to 898
    //Send a request to pokemon-species/{int}
    //Get the name of the pokemon
    // Then send a request to pokemon/{ name}
    fun getRandomPokemon() : LiveData<PokemonModel>
    {
        //Add observers to getPokemonCount?
        if(pokemonCount.value == 0)
        {
            getPokemonCount()
            return currentPokemon
        }
        else {
            val id: Int = Random.nextInt(0, pokemonCount.value!!)


            val call = request.getPokemon(id)

            call.enqueue(object : Callback<PokemonModel> {
                override fun onResponse(
                    call: Call<PokemonModel>,
                    response: Response<PokemonModel>
                ) {

                    //If the response is sucessful and the pokemon object isnt null,
                    // set it as the currentPokemon variable
                    if (response.isSuccessful) {
                        currentPokemon.value = response.body()!!
                    }
                }

                override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                }
            })

            return currentPokemon
        }

}

}