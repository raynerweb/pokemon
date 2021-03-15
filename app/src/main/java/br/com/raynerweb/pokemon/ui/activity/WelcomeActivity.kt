package br.com.raynerweb.pokemon.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.raynerweb.pokemon.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
}