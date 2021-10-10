package com.example.pokdex.Fragments

import android.os.Bundle
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.PokemonListModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayAllPokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_display_all_pokemon.*
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*


class DisplayAllPokemonFragment : Fragment() {
    lateinit var displayAllPokemonVM: DisplayAllPokemonViewModel
    lateinit var pokemonList : PokemonListModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        initializeVariables()


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_all_pokemon, container, false)
    }

    //Initialize member variables
    private fun initializeVariables() {
        displayAllPokemonVM = DisplayAllPokemonViewModel()
        displayAllPokemonVM.pokemonList.observe(viewLifecycleOwner, {newList ->
            pokemonList = newList
            displayRecyclerView()
        })
    }


    private fun displayRecyclerView()
    {
        display_all_recyclerview.adapter = DisplayAllPokemonAdapter(pokemonList)
        display_all_recyclerview.layoutManager = GridLayoutManager(context, 2)
    }


}


class DisplayAllPokemonAdapter(list : PokemonListModel) :
    RecyclerView.Adapter<PokemonHolder>()
{
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"
    val pokemonList = list
    val results = pokemonList.results
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_all,
        parent, false)

        return PokemonHolder(view)

    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {

       holder.bind(results[position],position.plus(1), imageUrl)



    }

    override fun getItemCount(): Int {
        return results.size
    }

}

class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    fun bind(pokemonModel: PokemonModel, position: Int, url: String)
    {
        itemView.display_all_pokemon_name.text = pokemonModel.name
        Picasso.get().load(url + position + ".png").into(itemView.display_all_pokemon_image)
    }
}