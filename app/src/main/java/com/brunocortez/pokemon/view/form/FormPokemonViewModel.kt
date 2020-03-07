package com.brunocortez.pokemon.view.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brunocortez.pokemon.model.Pokemon
import com.brunocortez.pokemon.repository.PokemonRepository
import com.brunocortez.pokemon.view.ViewState

class FormPokemonViewModel(val pokemonRepository: PokemonRepository) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
//    val messageResponse = MutableLiveData<String>()

    val viewState: MutableLiveData<ViewState<Pokemon>> = MutableLiveData()

    fun updatePokemon(pokemon: Pokemon) {
        viewState.value = ViewState.Loading
//        isLoading.value = true
        pokemonRepository.updatePokemon(
            pokemon = pokemon,
            onComplete = {
                viewState.value = if (it != null) ViewState.Success(it) else ViewState.Failed(Throwable("ERROR"))
            },
            onError = {
                viewState.value = ViewState.Failed(Throwable(it.message))
            }
        )
    }
}