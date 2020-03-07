package com.brunocortez.pokemon.repository

import com.brunocortez.pokemon.model.Pokemon

interface PokemonRepository {

    fun checkHealth(
        onComplete: () -> Unit,
        onError: (Throwable?) -> Unit
    )

    fun getPokemons(
        size: Int,
        sort: String,
        onComplete: (List<Pokemon>) -> Unit,
        onError: (Throwable) -> Unit
    )
}