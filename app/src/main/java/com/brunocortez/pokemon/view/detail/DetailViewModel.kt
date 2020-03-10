package com.brunocortez.pokemon.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunocortez.pokemon.model.Pokemon
import com.brunocortez.pokemon.repository.PokemonRepository
import com.brunocortez.pokemon.view.ViewState

class DetailViewModel(val pokemonRepository: PokemonRepository) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val pokemon = MutableLiveData<Pokemon>()

    var viewState: MutableLiveData<ViewState<Pokemon>> = MutableLiveData()

    fun getPokemon(number: String) {
//        isLoading.value = true
        viewState.value = ViewState.Loading
        pokemonRepository.getPokemon(
                number, onComplete = {
                it?.let {
                    viewState.value = ViewState.Success(it)
                }

//            isLoading.value = false
//            pokemon.value = it
            },
        onError = {
//            isLoading.value = false
            viewState.value = ViewState.Failed(Throwable("Fail to load pokemon"))
        } )
    }
}
