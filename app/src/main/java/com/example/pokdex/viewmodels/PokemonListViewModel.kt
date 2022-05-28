package com.example.pokdex.viewmodels

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
        retrievedList.attachIdsToPokemon()
        emit(retrievedList)
    }

    fun sortPokemon(pokemonList: PokemonListModel) : MutableList<PokemonModel>
    {
        if(sortBy == "Alphabetically")
        {
            var list = pokemonList.results
            list.sortedBy { it.name }
            return list
        }
        else
        {

        }

        return pokemonList.results
    }

}