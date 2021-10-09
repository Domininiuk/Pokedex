package com.example.pokdex.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.PokemonRepository
import kotlinx.coroutines.Dispatchers

class DisplayPokemonViewModel : ViewModel() {

   var pokemon : LiveData<PokemonModel> = MutableLiveData<PokemonModel>(PokemonModel())

   fun getPokemon(id : Int) : LiveData<PokemonModel> {
      pokemon = liveData(Dispatchers.IO)
      {
         val retrievedPokemon = PokemonRepository.getPokemonId(1)

         emit(retrievedPokemon)
      }
      return pokemon
   }
    private var pokemonCount : MutableLiveData<Int>

   init {

      pokemonCount = MutableLiveData(0)
   }





}