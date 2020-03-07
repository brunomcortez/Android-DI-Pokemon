package com.brunocortez.pokemon.repository

interface PokemonRepository {
    fun checkHealth(
        onComplete: () -> Unit,
        onError: (Throwable?) -> Unit
    )
}