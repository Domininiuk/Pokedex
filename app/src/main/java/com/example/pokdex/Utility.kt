package com.example.pokdex

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import java.io.IOException
import java.net.URL

object Utility
{
    // Turn a string url into a bitmap
    fun toBitmap(urlString : String): Bitmap?
    {
        //Create a URL variable
        val url: URL = URL(urlString)
        return url.toBitmap()
    }
    private fun URL.toBitmap() : Bitmap?
    {
        return try{
            BitmapFactory.decodeStream(openStream())
        }
        catch (e: IOException)
        {
            null
        }
    }
}
