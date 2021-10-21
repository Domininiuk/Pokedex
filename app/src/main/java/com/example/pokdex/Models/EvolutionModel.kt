package com.example.pokdex.Models


data class EvolutionModel(val id : Int,  val chain : EvolutionChainLinkModel)
{
}
data class EvolutionChainLinkModel( val species : EvolutionSpeciesModel, val evolves_to : List<EvolutionChainLinkModel>)

data class EvolutionSpeciesModel(val name : String , val url : String)
{

}