package com.brunocortez.pokemon.di

import android.content.Context
import com.brunocortez.pokemon.api.AuthInterceptor
import com.brunocortez.pokemon.api.PokemonService
import com.brunocortez.pokemon.repository.PokemonRepository
import com.brunocortez.pokemon.repository.PokemonRepositoryImpl
import com.brunocortez.pokemon.view.list.ListPokemonsViewModel
import com.brunocortez.pokemon.view.splash.SplashViewModel
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun createNetworkClient(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
private fun createOkhttpClientAuth(authInterceptor: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}
private fun createPicassoAuth(context: Context, client: OkHttpClient): Picasso {
    return Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()
}

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { ListPokemonsViewModel(get()) }
}
val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
}
val networkModule = module {
    single<Interceptor> { AuthInterceptor() }
    single { createNetworkClient(get(), get(named("baseUrl"))).create(PokemonService::class.java) }
    single { createOkhttpClientAuth(get()) }
    single(named("baseUrl")) { "https://pokedexdx.herokuapp.com" }
    single { createPicassoAuth(get(), get()) }
}