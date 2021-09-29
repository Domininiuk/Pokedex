package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.pokdex.Models.PokemonModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    lateinit var pokemonRepository : PokemonRepository

    lateinit var pokemonImageview : ImageView
    lateinit var pokemonNameTextview : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var int : Int = PokemonRepository.getPokemonCount()
        pokemonImageview = findViewById(R.id.pokemon_image)
        pokemonNameTextview = findViewById(R.id.pokemon_name)

      //  val request = ServiceBuilder.buildService(PokemonAPI::class.java)
    //    val call = request.getDitto()
/*
        call.enqueue(object : Callback<PokemonModel>
        {
            override fun onResponse(call: Call<PokemonModel>, response: Response<PokemonModel>) {
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                if(response.isSuccessful)
                {
                    //Use live data and a view model for retorfit2 background downlaod requests
                        //And create an activity for a random pokemon
                    response.body()
                    val pokemon : PokemonModel = PokemonRepository.getRandomPokemon()

                    Picasso.get().load(pokemon.getOfficialArtworkFrontDefault()).into(pokemonImageview)
                    pokemonNameTextview.text = pokemon.name

                }
            }

            override fun onFailure(call: Call<PokemonModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failure", Toast.LENGTH_SHORT).show()
            }

        }
        )

 */
    }


}
