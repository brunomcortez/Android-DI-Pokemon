package com.brunocortez.pokemon.view.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunocortez.pokemon.model.Pokemon
import com.brunocortez.pokemon.repository.PokemonRepository
import com.brunocortez.pokemon.view.ViewState

class ListPokemonsViewModel (val pokemonRepository: PokemonRepository) : ViewModel() {

//    val messageError: MutableLiveData<String> = MutableLiveData()
//    val pokemons: MutableLiveData<List<Pokemon>> = MutableLiveData()
//    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val viewState: MutableLiveData<ViewState<List<Pokemon>>> = MutableLiveData()

    fun getPokemons() {
        viewState.value = ViewState.Loading
        pokemonRepository.getPokemons(150, "number,asc", {
                viewState.value = ViewState.Success(it)
            }, {
                viewState.value = ViewState.Failed(it)
            }
        )
    }
}