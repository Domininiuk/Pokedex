package com.example.pokdex.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.R


class DisplayAllPokemonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContext(): Context? {
        return super.getContext()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_all_pokemon, container, false)
    }

/*
     class DisplayAllPokemonAdapter : RecyclerView.Adapter<PokemonHolder> {
        class PokemonHolder : RecyclerView.ViewHolder{

        }
    }

 */
}

