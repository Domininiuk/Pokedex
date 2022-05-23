package com.example.pokdex.models

import com.google.gson.annotations.SerializedName

data class PokemonModel(val id : Int = -1, val base_experience : Int = 0,  val name : String = "",
                           val weight : Float = 0.0f,
                           val sprites : PokemonSprites? = null, val height : Int = 0,val abilities
                           : MutableList<PokemonAbilityHolder>
                           = mutableListOf(), val types : MutableList<PokemonTypeHolder> = mutableListOf())
{

    /*
    Fetched height is in decimeters
    Fetched weight is in hectograms (tenth of a kilogram)
     */
      constructor(name: String) : this( -1, -1, name, 0.0f, null, 0, mutableListOf(
        PokemonAbilityHolder()), mutableListOf(PokemonTypeHolder(PokemonType("", ""))))


    constructor() : this( -1, -1, "", 0.0f, null, 0,
        mutableListOf(PokemonAbilityHolder()),  mutableListOf(PokemonTypeHolder(PokemonType("", "")))
    )

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

data class PokemonDreamWorld(val front_default: String, val front_female: String)
data class PokemonOfficialArtwork(val front_default : String)
data class PokemonSprites(val back_default : String, val back_female : String, val back_shiny : String,
val back_shiny_female: String, val front_default: String, val front_female : String, val front_shiny :String,
val front_shiny_female : String, val other: PokemonOther)
{
    //Get default art from the PokemonOther data classs
    internal fun getOfficialArtworkFrontDefault() : String
    {
        return other.official_artwork.front_default
    }
    internal fun getListOfUrls() : List<String>
    {
        var list : MutableList<String> = mutableListOf()

        //Add all the elements to the list
        list.add(other.official_artwork.front_default)
        list.add(back_default)
        list.add(back_female)
        list.add(back_shiny)
        list.add(back_shiny_female)
        list.add(front_default)
        list.add(front_female)
        list.add(front_shiny_female)

        //Iterate through the list and remove all null elements
        var returnList : MutableList<String> = mutableListOf()
        for(url in list)
        {
            if(url != null)
            {
                returnList.add(url)
            }
        }
        return returnList
    }

}

/*
Pokemon have upwards to two types (dual type Pokemon)
 */
data class PokemonTypeHolder(val type : PokemonType){}
data class PokemonType(val name : String, val url : String)

data class PokemonAbilityHolder(val ability : PokemonAbility = PokemonAbility(""))
data class PokemonAbility(val name : String)
