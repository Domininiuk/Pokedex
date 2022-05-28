package com.example.pokdex.models


data class PokemonListModel(var results : MutableList<PokemonModel>,
                            var listSortedByGeneration : MutableList<PokemonModel> = mutableListOf(),
                            var listSortedAlphabetically: MutableList<PokemonModel> = mutableListOf(),
                            private var sortBy : String = "Generation"
)
{

    fun attachIdsToPokemonAndFormatNames()
    {

        var id = 1

        for (pokemon in results)
        {
            pokemon.id = id
            pokemon.name = pokemon.getFormattedName()
            // Normal pokemon end at 898, and mega pokemon ids start
            if(id == 898)
            {
                id = 10000
            }
            id++

        }

    }


    private fun saveListSortedAlphabetically()
    {
        listSortedAlphabetically = results

    }
    fun restoreList()
    {
        if(sortBy == "Generation")
        {
            results = listSortedByGeneration
        }
        else if(sortBy == "Alphabetically")
        {
            results = listSortedAlphabetically
        }
    }

    fun saveListSortedByGeneration()
    {
        listSortedByGeneration = results
    }
    fun restoreListSortedByGeneration()
    {
        results = listSortedByGeneration
    }
    fun searchPokemon(text: String)
    {
        var tempList : MutableList<PokemonModel> = mutableListOf()

        if(sortBy == "Alphabetically")
        {
            for(pokemon in listSortedAlphabetically)
            {
                if(pokemon.name.lowercase().startsWith(text.lowercase()))
                {
                    tempList += pokemon
                }
            }
        }
        else if(sortBy == "Generation" || sortBy == null){
            for(pokemon in listSortedByGeneration)
            {
                if(pokemon.name.lowercase().startsWith(text.lowercase()))
                {
                    tempList += pokemon
                }
            }
        }

        results = tempList
    }
    fun sortPokemon(sortBy : String)
    {
        this.sortBy = sortBy
        if(sortBy == "Alphabetically")
        {
            results =  results.sortedBy { it.name }.toMutableList()
            saveListSortedAlphabetically()
        }
        else if(sortBy == "Generation"){
            restoreListSortedByGeneration()
            saveListSortedByGeneration()
        }
    }
}
