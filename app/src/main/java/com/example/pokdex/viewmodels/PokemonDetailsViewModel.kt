package com.example.pokdex.viewmodels

import androidx.lifecycle.*
import com.example.pokdex.data.retrofit.PokemonRepository
import com.example.pokdex.models.*
import kotlinx.coroutines.Dispatchers


class PokemonDetailsViewModel : ViewModel() {

   var pokemon : LiveData<PokemonModel> = MutableLiveData<PokemonModel>(PokemonModel())
   lateinit var evolutionChain : LiveData<EvolutionModel>
   lateinit var pokemonSpecies: LiveData<PokemonSpeciesModel>
   lateinit var pokemonAbility: LiveData<PokemonAbilityModel>
   lateinit var pokemonAbilities: LiveData<MutableList<PokemonAbilityModel>>

   // Dow
   fun getPokemon(id : Int) : LiveData<PokemonModel> {
      pokemon = liveData(Dispatchers.IO)
      {
         val retrievedPokemon = PokemonRepository.getPokemonId(id)

         emit(retrievedPokemon)
      }
      return pokemon
   }


   fun getPokemonAbility(id :Int) : LiveData<PokemonAbilityModel>
   {
      pokemonAbility = liveData(Dispatchers.IO)
      {
         val retrievedAbility = PokemonRepository.getPokemonAbility(id)
         emit(retrievedAbility)
      }

      return pokemonAbility
   }


   fun getPokemon(name: String) : LiveData<PokemonModel>
   {
      pokemon = liveData(Dispatchers.IO)
      {
         val retrievedPokemon = PokemonRepository.getPokemonName(name)

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


fun getPokemonSpecies(id : Int) : LiveData<PokemonSpeciesModel>
{
   pokemonSpecies = liveData(Dispatchers.IO)
   {
      val retrievedPokemonSpecies = PokemonRepository.getPokemonSpecies(id)
      emit(retrievedPokemonSpecies)
   }
   return pokemonSpecies
}




}