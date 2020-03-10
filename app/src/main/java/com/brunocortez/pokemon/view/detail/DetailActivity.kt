package com.brunocortez.pokemon.view.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import androidx.lifecycle.Observer
import com.brunocortez.pokemon.BuildConfig
import com.brunocortez.pokemon.R
import com.brunocortez.pokemon.view.ViewState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class DetailActivity : AppCompatActivity() {

    val detailViewModel: DetailViewModel by viewModel()
    val picasso: Picasso by inject()
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initTTS()

        Handler().postDelayed({detailViewModel.getPokemon(intent.getStringExtra("POKEMON_NUMBER"))}, 5000)
        detailViewModel.viewState.observe(this, Observer { state ->
            when (state) {
                is ViewState.Success -> {
                    val pokemon = state.data
                    ivPokemon?.pauseAnimation()
                    picasso.load("https://pokedexdx.herokuapp.com${pokemon.imageUrl}").into(ivPokemon)
                    tvPokemonName.text = "${pokemon.number} ${pokemon.name}"
                    tvPokemonDescription?.text = pokemon.description
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tts.speak(pokemon.description, TextToSpeech.QUEUE_FLUSH, null, null)
                    }
                    else {
                        tts.speak(pokemon.description, TextToSpeech.QUEUE_FLUSH, null)
                    }
                }
                is ViewState.Failed -> ivPokemon?.pauseAnimation()
            }
        })

//        detailViewModel.pokemon.observe(this, Observer {
//            ivPokemon?.pauseAnimation()
//            picasso.load("https://pokedexdx.herokuapp.com${it.imageUrl}").into(ivPokemon)
//            tvPokemonName.text = "${it.number} ${it.name}"
//            tvPokemonDescription?.text = it.description
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                tts.speak(it.description, TextToSpeech.QUEUE_FLUSH, null, null)
//            }
//            else {
//                tts.speak(it.description, TextToSpeech.QUEUE_FLUSH, null)
//            }
//        })

    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) { tts.language = Locale.US }
        })
    }
}
