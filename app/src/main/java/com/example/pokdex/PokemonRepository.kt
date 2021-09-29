package com.example.pokdex
import android.widget.Toast
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.Models.PokemonSpecies
import com.example.pokdex.ServiceBuilder.buildService
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PokemonRepository
{
    private var pokemonCount : Int = 0
    private val request = ServiceBuilder.buildService(PokemonAPI::class.java)
    private lateinit var currentPokemon : PokemonModel


  init {


  }

    //If the pokemon count is 0
    // Send a request for  the correct amount
    fun getPokemonCount() : Int
    {
        if(pokemonCount == 0)
        {
            // TODO: Try to make  the code more readable?
                //TODO: To the livedata stuff because otherwise this wont work (its done on the backthread)
            val call = request.getPokemonCount()

            call.enqueue(object : Callback<PokemonSpecies>{
                override fun onResponse(
                    call: Call<PokemonSpecies>,
                    response: Response<PokemonSpecies>
                ) {
                    pokemonCount = response.body()!!.count
                }

                override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

            return pokemonCount
        }
        else
        {
           return 0
        }
    }
    fun getRandomPokemon() : PokemonModel
    { val call = request.getDitto()

    call.enqueue(object : Callback<PokemonModel> {
        override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {

            //If the response is sucessfull and the pokemon object isnt null,
            // set it as the currentPokemon variable
            if (response.isSuccessful)
            {
              currentPokemon = response.body()!!
            }
        }

        override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
        }
    })

        return currentPokemon
}
    private fun <T> addToRequestQueue(call : Call<T>)
    {

    }
}