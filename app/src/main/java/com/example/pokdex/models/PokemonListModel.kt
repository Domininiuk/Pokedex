package com.example.pokdex.models


data class PokemonListModel(var results : MutableList<PokemonModel>)
{

    fun attachIdsToPokemons()
    {
        var id = 1

        for (pokemon in results)
        {
            pokemon.id = id
            id++
        }

    }
}
