package com.brunocortez.pokemon.view.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.brunocortez.pokemon.R
import com.brunocortez.pokemon.view.form.FormPokemonActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list_pokemons.*
import kotlinx.android.synthetic.main.include_loading.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ListPokemonsActivity : AppCompatActivity() {

    private val listPokemonsViewModel: ListPokemonsViewModel by viewModel()
    private val picasso: Picasso by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pokemons)
        listPokemonsViewModel.getPokemons()

        listPokemonsViewModel.isLoading.observe(this, Observer {
            containerLoading?.visibility = if (it) View.VISIBLE else View.GONE
        })

        listPokemonsViewModel.messageError.observe(this, Observer {
            if (it.isNotEmpty()) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        })

        listPokemonsViewModel.pokemons.observe(this, Observer {
            rvPokemons.adapter = ListPokemonsAdapter(it, picasso) {
                val intent = Intent(this, FormPokemonActivity::class.java)
                intent.putExtra("pokemon", it)
                startActivity(intent)
            }
            rvPokemons.layoutManager = GridLayoutManager(this, 3)
        })
    }
}
