package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


// Se utilizo ChatGPT4 para la lógica de las funciones de diferentes tableros y la implementación de la libreria intent.

class GameScreen:AppCompatActivity(){
    private lateinit var turnos: TextView
    private lateinit var Tablero: GridLayout
    private lateinit var Reslt: TextView
    private var currPlayer = "X"
    private var tamañoTablero: Int = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gamescreen)

        // Get the board size from the intent
        tamañoTablero = intent.getIntExtra("tamañoTablero", 3)

        turnos = findViewById(R.id.turnos)
        Tablero = findViewById(R.id.Tablero)
        Reslt = findViewById(R.id.Reslt)
        currPlayer = if ((0..1).random() == 0) "X" else "O"
        turnos.text = "Turno de $currPlayer"
        setupTablero()
    }


    //ChatGPT4 : configuraci[on de tablero programable.
    private fun setupTablero() {
        for (i in 0 until tamañoTablero) {
            for (j in 0 until tamañoTablero) {
                val button = Button(this)
                button.layoutParams = GridLayout.LayoutParams()
                button.setOnClickListener { onCellClicked(button) }
                Tablero.addView(button)
            }
        }


    }
    private fun onCellClicked(button: Button) {
        if (button.text == "") {
            button.text = currPlayer
            if (checkWin()) {
                Reslt.text = "$currPlayer Wins!"

                for (i in 0 until Tablero.childCount) {
                    (Tablero.getChildAt(i) as Button).isEnabled = false
                }
            } else if (checkDraw()) {
                Reslt.text = "It's a Draw!"
            } else {
                Switch()
            }
        }
    }
    private fun Switch(){
        currPlayer = if (currPlayer== "X") "O" else "X"
        turnos.text="turno de $currPlayer"

    }
    //Checkeo de victoria usanfo GPT4 , Prompt: How do i check for a win in the current code.

    private fun checkWin(): Boolean {
        // Check rows
        for (i in 0 until tamañoTablero) {
            var win = true
            for (j in 0 until tamañoTablero) {
                if ((Tablero.getChildAt(i * tamañoTablero + j) as Button).text != currPlayer) {
                    win = false
                    break
                }
            }
            if (win) return true
        }

        // Check columns
        for (i in 0 until tamañoTablero) {
            var win = true
            for (j in 0 until tamañoTablero) {
                if ((Tablero.getChildAt(j * tamañoTablero + i) as Button).text != currPlayer) {
                    win = false
                    break
                }
            }
            if (win) return true
        }

        // Check primary diagonal
        var winDiagonal1 = true
        for (i in 0 until tamañoTablero) {
            if ((Tablero.getChildAt(i * tamañoTablero + i) as Button).text != currPlayer) {
                winDiagonal1 = false
                break
            }
        }
        if (winDiagonal1) return true

        // Check secondary diagonal
        var winDiagonal2 = true
        for (i in 0 until tamañoTablero) {
            if ((Tablero.getChildAt(i * tamañoTablero + (tamañoTablero - 1 - i)) as Button).text != currPlayer) {
                winDiagonal2 = false
                break
            }
        }
        if (winDiagonal2) return true

        return false
    }

    private fun checkDraw(): Boolean {
        for (i in 0 until Tablero.childCount) {
            if ((Tablero.getChildAt(i) as Button).text == "") {
                return false
            }
        }
        return true
    }




}
