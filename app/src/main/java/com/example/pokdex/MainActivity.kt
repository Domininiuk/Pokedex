package com.example.pokdex


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.ViewModel.RandomPokemonViewModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}
/*




  pokemonImageview = findViewById(R.id.pokemon_image)
        pokemonNameTextview = findViewById(R.id.pokemon_name)
        randomPokemonVM = RandomPokemonViewModel()

        val test : LiveData<Int> = randomPokemonVM.getPokemonCount()
        randomPokemonVM.pokemonCount.observe(this, Observer
        { newPokemonCount ->pokemonNameTextview.text = newPokemonCount.toString()

             var pokemon : LiveData<PokemonModel> = randomPokemonVM.getRandomPokemon()
            randomPokemonVM.pokemon.observe(this, Observer { newPokemon ->
                //Picasso crashes the app because url is null or empty aaaaaaa
                var url : String? = pokemon.value?.getOfficialArtworkFrontDefault()
                //If froont default isnt empty
                //Load the url
                if(url != "")
                {
                    Picasso.get().load(url).into(pokemonImageview)
                    pokemonNameTextview.text = pokemon.value?.name.toString()
                }
           })
            })
 */