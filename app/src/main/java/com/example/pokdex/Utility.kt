package com.example.pokdex

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.example.pokdex.models.PokemonModel
import java.io.IOException
import java.net.URL

object Utility
{
    fun firstToUpper(string : String) : String
    {
        if(string.length > 1)
        {
            return Character.toUpperCase(string[0]) + string.substring(1)
        }

        return string
    }


    fun attachIdsToPokemons(list : MutableList<PokemonModel>)
    {
        var id = 0

        for (pokemon in list)
        {
            pokemon.id = id
            id++
        }

    }
}
