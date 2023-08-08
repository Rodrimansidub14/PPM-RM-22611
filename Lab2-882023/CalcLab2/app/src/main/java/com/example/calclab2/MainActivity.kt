// Rodrigo Mansilla 22611
// Universidad del Valle de Guatemala
// Programación de Plataformas Móviles
// Laboratorio 2


package com.example.calclab2

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Variables para controlar el estado del último carácter ingresado
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    // Instancia del evaluador de expresiones
    private val evaluator = evaluateExpression()

    // Almacenamiento del último resultado calculado
    private var lastResult: String? = null

    // TextView para mostrar el resultado
    lateinit var result: TextView

    // Historial de cálculos realizados
    private val history = mutableListOf<Pair<String, String>>()

    // Máximo número de entradas en el historial
    private val MAX_HISTORY_ENTRIES = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el TextView de resultado
        result = findViewById(R.id.result)
    }

    // Función para manejar la entrada de dígitos
    fun onDigit(view: View) {
        // Si el texto termina con ")", añade un "*" para la multiplicación
        if (result.text.endsWith(")")) {
            result.append("*")
        }
        result.append((view as Button).text)
        lastNumeric = true
    }

    // Función para limpiar la entrada
    fun onClear(view: View) {
        result.text = ""
        lastNumeric = false
        lastDot = false
    }

    // Función para manejar la entrada del punto decimal
    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            result.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    // Función para manejar la entrada de operadores
    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(result.text.toString())) {
            result.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        } else if (!lastNumeric && result.text.isNotEmpty() && (view as Button).text == "-") {
            result.append("-") // permite números negativos
        }
    }

    // Función para eliminar el último carácter ingresado
    fun onBackspace(view: View) {
        if(result.text.isNotEmpty()) {
            val newText = result.text.substring(0, result.text.length - 1)
            result.text = newText
        }
    }

    // Función para comprobar si ya hay un operador en la entrada
    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

    // Función para evaluar la expresión ingresada cuando se pulsa el botón "="
    fun onEqual(view: View) {
        if (lastNumeric) {
            val infixExpression = result.text.toString().replace(" ", "")
            try {
                // Convertir la expresión infija a postfija
                val postfixExpression = evaluator.infixToPostfix(infixExpression)

                // Evaluar la expresión postfija
                val evaluationResult = evaluator.evaluatePostfix(postfixExpression)

                // Convertir el resultado a formato exponencial si es muy grande o muy pequeño
                var displayResult = evaluationResult.toString()
                if (displayResult.toDouble() > 1e10 || displayResult.toDouble() < 1e-10) {
                    displayResult = String.format("%.2e", displayResult.toDouble())
                }

                // Actualizar la pantalla y el historial
                lastResult = displayResult
                result.text = lastResult
                history.add(Pair(infixExpression, lastResult!!))
                lastDot = true // Después de la evaluación, se puede agregar un punto
            } catch (e: ArithmeticException) {
                // Manejar errores aritméticos
                if (e.message == "/ by zero") {
                    result.text = "División por cero"
                } else {
                    result.text = "Math ERR"
                }
                lastResult = null
            } catch (e: Exception) {
                // Manejar otros errores
                result.text = "Error"
                lastResult = null
            }

            // Mantener el historial al tamaño máximo definido
            if(history.size > MAX_HISTORY_ENTRIES) {
                history.removeAt(0)
            }
        }
    }

    // Función para usar el último resultado calculado
    fun useLastResult(view: View) {
        lastResult?.let {
            result.text = it
            lastNumeric = true
        }
    }

    // Función para manejar la entrada de teclado físico o virtual
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

    // Función para mostrar el historial de cálculos
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

    // Función para comprobar si una cadena es un número válido
    fun isValidNumber(input: String): Boolean {
        return if(input.count { it == '.' } > 1) false else !input.endsWith('.')
    }
}





