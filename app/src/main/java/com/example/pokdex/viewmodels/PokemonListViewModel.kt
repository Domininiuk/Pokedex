package com.example.pokdex.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.data.retrofit.PokemonRepository
import com.example.pokdex.models.PokemonListModel
import com.example.pokdex.models.PokemonModel
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

    var showSearchBar = mutableStateOf(false)
    var searchText = mutableStateOf("")
}