package com.example.myapplicationC

import java.util.*
import kotlin.collections.ArrayList

class evaluateExpression {

    fun infixToPostfix(infix: String): List<String> {
        val output = ArrayList<String>()
        val operators = Stack<Char>()

        var index = 0
        while (index < infix.length) {
            when {
                infix[index].isWhitespace() -> index++  // Skip whitespace
                infix[index].isDigit() || (infix[index] == '.' && index + 1 < infix.length && infix[index + 1].isDigit()) -> {  // Handle numbers and floating points
                    val start = index
                    while (index < infix.length && (infix[index].isDigit() || infix[index] == '.')) {
                        index++
                    }
                    output.add(infix.substring(start, index))
                }
                infix[index] == '(' -> {
                    operators.push(infix[index])
                    index++
                }
                infix[index] == ')' -> {
                    while (operators.peek() != '(') {
                        output.add(operators.pop().toString())
                    }
                    operators.pop()
                    index++
                }
                else -> {
                    while (operators.isNotEmpty() && precedence(infix[index]) <= precedence(operators.peek())) {
                        output.add(operators.pop().toString())
                    }
                    operators.push(infix[index])
                    index++
                }
            }
        }

        while (operators.isNotEmpty()) {
            output.add(operators.pop().toString())
        }

        return output
    }


    fun precedence(op: Char): Int {
        return when (op) {
            '+', '-' -> 1
            '*', '/' -> 2
            '^' -> 3
            else -> 0
        }
    }

    fun evaluatePostfix(postfix: List<String>): Double {
        val stack = Stack<Double>()
        for (token in postfix) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                else -> {
                    val right = stack.pop()
                    val left = stack.pop()
                    val result = when (token[0]) {
                        '+' -> left + right
                        '-' -> left - right
                        '*' -> left * right
                        '/' -> left / right
                        '^' -> Math.pow(left, right)
                        else -> throw IllegalArgumentException("Operador desconocido: $token")
                    }
                    stack.push(result)
                }
            }
        }
        return stack.pop()
    }

    fun evaluateInfix(infix: String): Double {
        val postfix = infixToPostfix(infix)
        return evaluatePostfix(postfix)
    }

}