package com.brunocortez.pokemon.view.form

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.brunocortez.pokemon.R
import com.brunocortez.pokemon.model.Pokemon
import com.brunocortez.pokemon.view.ViewState
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_form_pokemon.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class FormPokemonActivity : AppCompatActivity() {
    val formPokemonViewModel: FormPokemonViewModel by viewModel()
    val picasso: Picasso by inject()
    lateinit var pokemon : Pokemon
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_pokemon)
        setValues()

        formPokemonViewModel.viewState.observe(this, Observer {
            when (it) {
                is ViewState.Success -> {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                is ViewState.Failed -> {
                    Toast.makeText(this, it?.throwable.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

//        formPokemonViewModel.messageResponse.observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
//        })
        btSaveForm.setOnClickListener {
            pokemon.attack = sbAttack.progress
            pokemon.defense = sbDefense.progress
            pokemon.velocity = sbVelocity.progress
            pokemon.ps = sbPS.progress
            formPokemonViewModel.updatePokemon(pokemon)
        }
    }

    private fun setValues() {
        pokemon = intent.getParcelableExtra<Pokemon>("POKEMON")
        tvPokemonNameForm.text = pokemon.name

        picasso.load("https://pokedexdx.herokuapp.com${pokemon.imageUrl}").into(ivPokemonForm)
        sbAttack.progress = pokemon.attack
        sbDefense.progress = pokemon.defense
        sbPS.progress = pokemon.ps
        sbVelocity.progress = pokemon.velocity
        tvAttackValue.text = pokemon.attack.toString()
        tvDefenseValue.text = pokemon.defense.toString()
        tvPSValue.text = pokemon.ps.toString()
        tvVelocityValue.text = pokemon.velocity.toString()
        setListener(sbAttack, tvAttackValue)
        setListener(sbDefense, tvDefenseValue)
        setListener(sbVelocity, tvVelocityValue)
        setListener(sbPS, tvPSValue)
    }

    private fun setListener(seekBar: SeekBar, textView: TextView) {
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser:
            Boolean) {
                textView.text = progress.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}
