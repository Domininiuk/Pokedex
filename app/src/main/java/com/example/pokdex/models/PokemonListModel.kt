package com.example.pokdex.models

import com.example.pokdex.Utility


data class PokemonListModel(var results : MutableList<PokemonModel>,
                            var originalResults : MutableList<PokemonModel> = mutableListOf()
)
{

    fun attachIdsToPokemon()
    {
        var id = 1

        for (pokemon in results)
        {
            pokemon.id = id
            // Normal pokemon end at 898, and mega pokemon ids start
            if(id == 898)
            {
                id = 10000
            }
            id++

        }

    }
    fun sortPokemon(sortBy : String)
    {
        if(sortBy == "Alphabetically" && (originalResults.isNullOrEmpty() || originalResults == results))
        {
            originalResults = results
            results =  results.sortedBy { it.name }.toMutableList()
        }
        else if(sortBy == "Generation" && !originalResults.isNullOrEmpty()){
            results = originalResults
        }
    }
}
