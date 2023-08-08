/*
Rodrigo Mansilla 22611
Universidad del Valle de Guatemala
Programación de Plataformas Móviles
Laboratorio 2



 */


package com.example.calclab2

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private val evaluator = evaluateExpression()
    private var lastResult: String? = null
    lateinit var result: TextView
    private val history = mutableListOf<Pair<String, String>>()
    private val MAX_HISTORY_ENTRIES = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el texto
        result = findViewById(R.id.result)
    }

    fun onDigit(view: View) {
        if (result.text.endsWith(")")) {
            result.append("*")
        }
        result.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {
        result.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            result.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(result.text.toString())) {
            result.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        } else if (!lastNumeric && result.text.isNotEmpty() && (view as Button).text == "-") {
            result.append("-") //permite numeros negativos
        }
    }

    fun onBackspace(view: View) {
        if(result.text.isNotEmpty()) {
            val newText = result.text.substring(0, result.text.length - 1)
            result.text = newText
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            val infixExpression = result.text.toString().replace(" ", "")
            try {
                // Convert the infix expression to postfix
                val postfixExpression = evaluator.infixToPostfix(infixExpression)

                // Evaluate the postfix expression
                val evaluationResult = evaluator.evaluatePostfix(postfixExpression)

                // Convert result to exponential format if it's too large or too small
                var displayResult = evaluationResult.toString()
                if (displayResult.toDouble() > 1e10 || displayResult.toDouble() < 1e-10) {
                    displayResult = String.format("%.2e", displayResult.toDouble())
                }

                // Update the display and history
                lastResult = displayResult
                result.text = lastResult
                history.add(Pair(infixExpression, lastResult!!))
                lastDot = true // After evaluation, dot can be added
            } catch (e: ArithmeticException) {
                if (e.message == "/ by zero") {
                    result.text = "División por cero"
                } else {
                    result.text = "Math ERR"
                }
                lastResult = null
            } catch (e: Exception) {
                result.text = "Error"
                lastResult = null
            }

            if(history.size > MAX_HISTORY_ENTRIES) {
                history.removeAt(0)
            }
        }
    }

    fun useLastResult(view: View) {
        lastResult?.let {
            result.text = it
            lastNumeric = true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (event != null) {
            val keyChar = event.unicodeChar.toChar()
            if (keyChar.isDigit() || keyChar == '.' || "+-*/".contains(keyChar)) {
                result.append(keyChar.toString())
                lastNumeric = true
            } else if (keyChar == '\n' || keyCode == KeyEvent.KEYCODE_ENTER) {
                onEqual(result)
            } else if (keyCode == KeyEvent.KEYCODE_DEL) {
                onBackspace(result)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    fun showHistory(view: View) {
        val historyString = history.joinToString("\n") { "${it.first} = ${it.second}" }

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Historial")
            .setMessage(historyString)
            .setPositiveButton("Cerrar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    fun isValidNumber(input: String): Boolean {
        return if(input.count { it == '.' } > 1) false else !input.endsWith('.')
    }
}




