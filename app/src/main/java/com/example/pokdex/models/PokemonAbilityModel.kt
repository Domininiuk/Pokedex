package com.example.pokdex.models

data class PokemonAbilityModel(val effect_entries: List<PokemonAbilityEffect>,val name: String)
data class PokemonAbilityEffect(val effect: String, val language: PokemonAbilityEffectLanguage)
data class PokemonAbilityEffectLanguage(val name : String)
