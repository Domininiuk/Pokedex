package com.example.pokdex.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.data.retrofit.PokemonRepository
import kotlinx.coroutines.Dispatchers


class PokemonListViewModel : ViewModel()
{
    var sortBy = "Generation"
    val pokemonList = liveData(Dispatchers.IO)
    {
        val retrievedList = PokemonRepository.getPokemonList()
        retrievedList.attachIdsToPokemonAndFormatNames()
        retrievedList.saveListSortedByGeneration()
        emit(retrievedList)
    }
}