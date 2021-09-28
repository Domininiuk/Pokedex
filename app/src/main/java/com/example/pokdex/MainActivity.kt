package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pokdex.Models.Pokemon
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{

    lateinit var pokemonImageview : ImageView
    lateinit var pokemonNameTextview : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonImageview = findViewById(R.id.pokemon_image)
        pokemonNameTextview = findViewById(R.id.pokemon_name)

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
                        Picasso.get().load(pokemon.getOfficialArtworkFrontDefault()).into(pokemonImageview)
                        pokemonNameTextview.text = pokemon.name
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

