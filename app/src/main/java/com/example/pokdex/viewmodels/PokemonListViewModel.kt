package com.example.pokdex.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.data.retrofit.PokemonRepository
import kotlinx.coroutines.Dispatchers


class PokemonListViewModel : ViewModel()
{
    val pokemonList = liveData(Dispatchers.IO)
    {
        val retrievedList = PokemonRepository.getPokemonList()
        retrievedList.attachIdsToPokemons()
        emit(retrievedList)
    }


}