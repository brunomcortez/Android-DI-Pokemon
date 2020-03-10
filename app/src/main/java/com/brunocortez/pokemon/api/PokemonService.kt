package com.brunocortez.pokemon.api

import com.brunocortez.pokemon.model.HealthResponse
import com.brunocortez.pokemon.model.Pokemon
import com.brunocortez.pokemon.model.PokemonResponse
import retrofit2.Call
import retrofit2.http.*

interface PokemonService {
    @GET("/api/pokemon/health")
    fun checkHealth(): Call<HealthResponse>

    @GET("/api/pokemon")
    fun getPokemons(
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Call<PokemonResponse>

    @PUT("/api/pokemon")
    fun updatePokemon(
        @Body pokemon: Pokemon
    ) : Call<Pokemon>

    @GET("/api/pokemon/{number}")
    fun getPokemon(
        @Path("number") number: String) : Call<Pokemon>
}