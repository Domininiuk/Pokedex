package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pokdex.Models.Pokemon
import okhttp3.internal.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val request = ServiceBuilder.buildService(PokemonService::class.java)
        val call = request.getDitto()

        call.enqueue(object : Callback<Pokemon>
        {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                if(response.isSuccessful)
                {
                    response.body()
                    val pokemon : Pokemon? = response.body()
                    if (pokemon != null) {
                        findViewById<ImageView>(R.id.poke_sprite).setImageBitmap(Utility.toBitmap(pokemon.sprites.other.official_artwork.front_default))
                    }


                            //Add a mdc card for the pokemon
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failure", Toast.LENGTH_SHORT).show()
            }

        }
        )
    }
}

