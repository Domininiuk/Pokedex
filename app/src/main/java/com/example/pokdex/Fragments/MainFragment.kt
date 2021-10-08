package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.pokdex.PokemonRepository
import com.example.pokdex.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        go_to_random_pokemon_button.setOnClickListener()
        {
            /*
            val id : Int = PokemonRepository.getRandomId()
            val action =  MainFragmentDirections.actionMainFragmentToDisplayPokemonFragment(id)
            findNavController().navigate(action)

             */
            val action = MainFragmentDirections.actionMainFragmentToDisplayAllPokemonFragment()
            findNavController().navigate(action)

        }


        super.onViewCreated(view, savedInstanceState)
    }

}