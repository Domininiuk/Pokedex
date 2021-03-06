package com.example.pokdex.data.retrofit
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

object PokemonRepository
{
   private var pokemonCount : MutableLiveData<Int> = MutableLiveData(1118)
    private val request : PokemonAPI = ServiceBuilder.buildService(PokemonAPI::class.java)


    fun getRandomId() : Int
    {
        return Random.nextInt(0, pokemonCount.value!!.minus(1))
    }

 suspend fun getPokemonAbility(id : Int) = request.getPokemonAbility(id)
    suspend fun getPokemonName(name : String ) = request.getPokemonName(name)
    suspend fun getPokemonId(id : Int) = request.getPokemonId(id)
    suspend fun getPokemonList() = request.getPokemonList()
    suspend fun getEvolutionChain(id : Int ) = request.getEvolutionChain(id)
    suspend fun getPokemonSpecies(id : Int) = request.getPokemonSpecies(id)

}