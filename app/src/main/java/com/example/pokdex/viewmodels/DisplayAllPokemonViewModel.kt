package com.example.pokdex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.PokemonRepository
import kotlinx.coroutines.Dispatchers

class DisplayAllPokemonViewModel : ViewModel()
{
    val pokemonList = liveData(Dispatchers.IO)
    {
        val retrievedList = PokemonRepository.getPokemonList()
        retrievedList.attachIdsToPokemons()
        emit(retrievedList)
    }


}