package com.example.pokdex


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // goToRandomPokemonButton = findViewById(R.id.go_to_random_pokemon_button)

       val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
       val navController = navHostFragment!!.findNavController()

    }


}
