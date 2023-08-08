package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity : AppCompatActivity() {
    private lateinit var editTextP1: EditText
    private lateinit var editTextP2: EditText
    private lateinit var tamañoTablero: Spinner
    private lateinit var PlayButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // elementosUI
        editTextP1 = findViewById<EditText>(R.id.editTextP1)
        editTextP2 = findViewById<EditText>(R.id.editTextP2)
        tamañoTablero = findViewById<Spinner>(R.id.tamañoTablero)
        PlayButton = findViewById<Button>(R.id.PlayButton)

        PlayButton.setOnClickListener {
            val nombreJugador1 = editTextP1.text.toString()
            val nombreJugador2 = editTextP2.text.toString()
            val tamañoselecTab = tamañoTablero.selectedItem.toString()

            val intent = Intent(this, GameScreen::class.java)
            intent.putExtra("boardSize", tamañoselecTab.toInt())
            intent.putExtra("player1Name", nombreJugador1)
            intent.putExtra("player2Name", nombreJugador2)
            startActivity(intent)
        }
    }
}
