package com.example.pokdex.Models

data class AllPokemonModel(val results : List<PokemonModel>)
{
    constructor(model : PokemonModel) : this(results = listOf(model))
    constructor() : this(results = listOf(PokemonModel()))
}