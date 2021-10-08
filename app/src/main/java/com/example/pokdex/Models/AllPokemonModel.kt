package com.example.pokdex.Models

data class AllPokemonModel(var results : MutableList<PokemonModel>)
{
    constructor(model : PokemonModel) : this(results = mutableListOf(model))
    constructor() : this(results = mutableListOf(PokemonModel()))
}