package com.example.pokdex.models

data class PokemonAbilityModel(val effect_entries: List<PokemonAbilityEffect>,val name: String)
{
    fun getEnglishVersion(): PokemonAbilityEffect
    {
        for(entry in effect_entries)
        {
            if(entry.language.name == "en")
            {
                return entry;
            }
        }
        return effect_entries[0]
    }
}
data class PokemonAbilityEffect(val effect: String, val language: PokemonAbilityEffectLanguage)
data class PokemonAbilityEffectLanguage(val name : String)
