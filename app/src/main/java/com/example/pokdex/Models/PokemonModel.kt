package com.example.pokdex.Models

//FINISH THE MODELS
import com.google.gson.annotations.SerializedName

data class PokemonModel(val id : Int, val base_experience : Int,  val name : String ,
                           val weight : String,
                           val sprites : PokemonSprites?)
{

      constructor(name: String) : this( -1, -1, name, "", null ) {}

    constructor() : this( -1, -1, "", "", null ) {}
    fun getOfficialArtworkFrontDefault() : String
    {
        if (sprites != null) {
            return sprites.getOfficialArtworkFrontDefault()
        }
        return ""
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

data class PokemonSpecies(val count : Int, val name : String)
{
    
}