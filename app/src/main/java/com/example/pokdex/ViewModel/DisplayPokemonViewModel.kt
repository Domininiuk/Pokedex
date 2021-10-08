package com.example.pokdex.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.PokemonRepository

class DisplayPokemonViewModel : ViewModel() {

    var  pokemon : MutableLiveData<PokemonModel>
    private var pokemonCount : MutableLiveData<Int>

   init {
      pokemon = MutableLiveData(PokemonModel(""))
      pokemonCount = MutableLiveData(0)
      // pokemonCount.value = 0
   }
   // If the count is equal to 0, send a request to get the correct number
   fun getPokemonCount() : LiveData<Int>
   {
      if (pokemonCount.value == 0) {
         pokemonCount = PokemonRepository.getPokemonCount() as MutableLiveData<Int>
      }

      return pokemonCount
   }
   fun getPokemon(id : Int) : LiveData<PokemonModel>
   {
      pokemon = PokemonRepository.getPokemon(id) as MutableLiveData<PokemonModel>
      return pokemon
   }


   fun getRandomPokemon() : LiveData<PokemonModel>
   {
      if(pokemon.value!!.name == "")
         {
            val model : LiveData<PokemonModel> = PokemonRepository.getRandomPokemon() as MutableLiveData<PokemonModel>

               pokemon = model as MutableLiveData<PokemonModel>

         }
      return pokemon
   }


}