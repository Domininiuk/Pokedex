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

    fun formatName(name : String) : String
    {
        var tempNameSplit = name.split('-').reversed()
        var tempName = ""
        if(tempNameSplit.size > 1)
        {
            for(string in tempNameSplit)
            {
                if(string == "m")
                {
                    tempName += "Male "
                }
                else if(string == "f")
                {
                    tempName += "Female "
                }
                else
                {
                    tempName += firstToUpper(string) + " "
                }
            }
        }
        else
        {
            return firstToUpper(name)
        }

        return tempName
    }
}
