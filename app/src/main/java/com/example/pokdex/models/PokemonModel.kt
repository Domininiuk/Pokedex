package com.example.pokdex.models

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

data class PokemonModel(
    var id : Int = -1, val base_experience : Int = 0, val name : String = "",
    val weight : Float = 0.0f,
    val sprites : PokemonSprites? = null, val height : Int = 0, val abilities
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

    internal fun getListOfAbilityIds(): List<Int>
    {
        var list = mutableListOf<Int>()
        abilities.forEach {
            list.add(it.ability.getIdFromUrl())
        }
        return list;
    }
}






data class PokemonOther(
    val dream_world : PokemonDreamWorld,
    val home : PokemonHome,
                        @SerializedName("official-artwork")
                        val official_artwork: PokemonOfficialArtwork,)
data class PokemonHome(val front_default: String)

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
        list.add(other.dream_world.front_default)
        list.add(other.home.front_default)
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
{
    fun getColour() : Color
    {
        var color : Color? = null
        when(name)
        {
                 "normal" -> color =  Color(0xFFA8A77A)
                 "fire" -> color = Color(0xFFEE8130)
                 "water" -> color = Color(0xFF6390F0)
                 "electric" -> color = Color(0xFFF7D02C)
                 "grass" -> color = Color(0xFF7AC74C)
                 "ice" -> color = Color(0xFF96D9D6)
                 "fighting" -> color = Color(0xFFC22E28)
                 "poison" -> color = Color(0xFFA33EA1)
                 "ground" -> color =Color(0xFFE2BF65)
                 "flying" -> color =Color(0xFFA98FF3)
                 "psychic" -> color = Color(0xFFF95587)
                 "bug" -> color =Color(0xFFA6B91A)
                 "rock" -> color = Color(0xFFB6A136)
                 "ghost" -> color = Color(0xFF735797)
                 "dragon" -> color =Color(0xFF6F35FC)
                 "unknown" -> color = Color(0xFFD685AD)
        }
        return color!!
    }
}

data class PokemonAbilityHolder(val ability : PokemonAbilityName = PokemonAbilityName("",""))
data class PokemonAbilityName(val name : String, val url: String)
{
    fun getIdFromUrl() : Int
    {
        return url.subSequence(35, url.length - 1).toString().filter { c: Char -> c != '/' }.toInt()
    }
}
