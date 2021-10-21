package com.example.pokdex.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.Models.EvolutionChainLinkModel
import com.example.pokdex.Models.EvolutionModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.PokemonRepository
import kotlinx.coroutines.Dispatchers

class DisplayPokemonViewModel : ViewModel() {

   private var pokemon : LiveData<PokemonModel> = MutableLiveData<PokemonModel>(PokemonModel())
   lateinit var evolutionChain : LiveData<EvolutionModel>
   fun getPokemon(id : Int) : LiveData<PokemonModel> {
      pokemon = liveData(Dispatchers.IO)
      {
         val retrievedPokemon = PokemonRepository.getPokemonId(id)

         emit(retrievedPokemon)
      }
      return pokemon
   }

   fun getEvolutionChain(id : Int ) : LiveData<EvolutionModel>
   {
      evolutionChain = liveData(Dispatchers.IO)
      {
         val retrievedEvolutionChain =  PokemonRepository.getEvolutionChain(id)

         emit(retrievedEvolutionChain)
      }

      return evolutionChain
   }







}