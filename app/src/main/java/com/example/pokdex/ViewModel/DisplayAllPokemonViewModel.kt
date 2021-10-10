package com.example.pokdex.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.Models.PokemonListModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.PokemonRepository
import kotlinx.coroutines.Dispatchers

class DisplayAllPokemonViewModel : ViewModel()
{
    val pokemonList = liveData(Dispatchers.IO)
    {
        val retrievedList = PokemonRepository.getPokemonList()
        emit(retrievedList)
    }


}