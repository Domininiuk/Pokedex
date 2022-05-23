package com.example.pokdex.fragments


import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.adapters.DisplayAllPokemonAdapter
import com.example.pokdex.models.PokemonListModel
import com.example.pokdex.R
import com.example.pokdex.viewmodels.DisplayAllPokemonViewModel
import com.example.pokdex.databinding.FragmentDisplayAllPokemonBinding


class DisplayAllPokemonFragment : Fragment() {
    lateinit var displayAllPokemonVM: DisplayAllPokemonViewModel
    lateinit var pokemonList : PokemonListModel

    private var _binding : FragmentDisplayAllPokemonBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
            initializeBinding()
            initializeVariables()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeBinding()
    {
        _binding = FragmentDisplayAllPokemonBinding.inflate(layoutInflater)
    }
    //Initialize member variables
    private fun initializeVariables() {
        displayAllPokemonVM = DisplayAllPokemonViewModel()
        displayAllPokemonVM.pokemonList.observe(viewLifecycleOwner) { newList ->
            pokemonList = newList
            displayRecyclerView()
        }
    }


    private fun displayRecyclerView()
    {
        binding.displayAllRecyclerview.adapter = DisplayAllPokemonAdapter(pokemonList) { position ->
            onListItemClick(
                position
            )
        }
        binding.displayAllRecyclerview.layoutManager = GridLayoutManager(context, 2)
        binding.displayAllRecyclerview.addItemDecoration(MarginItemDecorator(20, 2))

    }

    //When clicking on a pokemon, go their specific pokemon fragment
    private fun onListItemClick(position : Int)
    {
        val id : Int = position + 1
        val action =  DisplayAllPokemonFragmentDirections.actionDisplayAllPokemonFragmentToDisplayPokemonFragment2(id)
       findNavController().navigate(action)
    }

}

class MarginItemDecorator(private val spaceSize: Int, private val spanCount: Int = 1,
private val orientation : Int  = GridLayoutManager.VERTICAL) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect)
        {
            if(orientation == GridLayoutManager.VERTICAL)
            {
                if(parent.getChildAdapterPosition(view) < spanCount)
                {
                    top = spaceSize
                }
                if(parent.getChildAdapterPosition(view) % spanCount == 0)
                {
                    left = spaceSize
                }
            }
            else
            {
                if(parent.getChildAdapterPosition(view) < spanCount)
                {
                    left = spaceSize
                }
                if(parent.getChildAdapterPosition(view) % spanCount == 0)
                {
                    top = spaceSize
                }
            }
            right = spaceSize
            bottom = spaceSize

        }
    }
}