package com.brunocortez.pokemon.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("content") val content: List<Pokemon>
)