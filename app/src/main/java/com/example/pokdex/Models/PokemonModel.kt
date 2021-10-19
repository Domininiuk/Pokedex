package com.example.pokdex.Models

import com.google.gson.annotations.SerializedName

data class PokemonModel(val id : Int = -1, val base_experience : Int = 0,  val name : String = "",
                           val weight : Float = 0.0f,
                           val sprites : PokemonSprites? = null, val height : Int = 0,val abilities
                           : MutableList<PokemonAbilityHolder>
                           = mutableListOf())
{

    /*
    Height is in decimeters
    Weight is in hectograms (tenth of kilogram)
     */
      constructor(name: String) : this( -1, -1, name, 0.0f, null, 0, mutableListOf(
        PokemonAbilityHolder()
    ) ) {}

    constructor() : this( -1, -1, "", 0.0f, null, 0,  mutableListOf(PokemonAbilityHolder()) ) {}
    fun getOfficialArtworkFrontDefault() : String
    {
        if (sprites != null) {
            return sprites.getOfficialArtworkFrontDefault()
        }
        return ""
    }
    //Return the height in centimeters
    fun getHeightInCentimeters() : Int
    {
        return height * 10
    }
    //Return the weight in kilograms
    fun getWeightInKilograms() : Float
    {
        return weight / 10.0f
    }
}






data class PokemonOther(val dream_world : PokemonDreamWorld,
                        @SerializedName("official-artwork")
                        val official_artwork: PokemonOfficialArtwork)
{

}
data class PokemonDreamWorld(val front_default: String, val front_female: String)
{

}
data class PokemonOfficialArtwork(val front_default : String)
{

}
data class PokemonSprites(val back_default : String, val back_female : String, val back_shiny : String,
val back_shiny_female: String, val front_default: String, val front_female : String, val front_shiny :String,
val front_shiny_female : String, val other: PokemonOther)
{
    //Get default art from the PokemonOther data classs
    internal fun getOfficialArtworkFrontDefault() : String
    {
        return other.official_artwork.front_default
    }
    
}
data class PokemonAbilityHolder(val ability : PokemonAbility = PokemonAbility(""))
data class PokemonAbility(val name : String)
{

}