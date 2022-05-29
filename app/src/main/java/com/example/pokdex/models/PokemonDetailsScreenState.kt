package com.example.pokdex.models

data class PokemonDetailsScreenState(var abilities: MutableList<PokemonAbilityModel>, var pokemonModel: PokemonModel,
                                     var pokemonSpeciesModel: PokemonSpeciesModel, var evolutionModel: EvolutionModel)

{

}