package com.example.pokdex.Models

data class PokemonSpeciesModel(var evolution_chain : EvolutionChain)
{
    data class EvolutionChain(val url : String)
    {
        
    }
}