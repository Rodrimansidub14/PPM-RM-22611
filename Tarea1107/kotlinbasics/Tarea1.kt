package com.example.kotlinbasics

import kotlin.system.exitProcess
/*
Universidad del Valle de Guatemala
Programacion de Plataformas Moviles
Agenda Electronica
Rodrigo Mansilla
7/11/2023
 */
class Contacto(val name: String, val phone: Int, val email: String) {
    fun getInfo(): String {
        return "Nombre: $name\nTeléfono: $phone\nEmail: $email"
    }
}

class AgendaElectronica {
    private val contactos = mutableListOf<Contacto>()

    fun agregarContacto(contacto: Contacto) {
        contactos.add(contacto)
        println("Contacto agregado correctamente.")
    }

    fun mostrarContactos() {
        if (contactos.isNotEmpty()) {
            println("Lista de contactos:")
            contactos.forEachIndexed { index, contacto ->
                println("Contacto ${index + 1}:")
                println(contacto.getInfo())
                println()
            }
        } else {
            println("No hay contactos en la agenda.")
        }
    }
}

class LoginInfo(private val username: String, private val password: String) {
    fun esValido(inputUsername: String, inputPassword: String): Boolean {
        return username == inputUsername && password == inputPassword
    }
}

fun main() {
    val agenda = AgendaElectronica()
    val username = "Usuario"
    val password = "contraseña"
    val loginInfo = LoginInfo(username, password)

    println("Ingrese su Nombre:")
    var inputUsername: String = readLine()!!
    println("Ingrese su Contraseña:")
    var inputPassword = readLine()!!

    if (loginInfo.esValido(inputUsername, inputPassword))
        println("¡Hola!\nBienvenido a tu Agenda Electrónica\nInformación Personal:\n$inputUsername\n")
    while (true) {
        println("\nAgenda")
        println("1. Agregar Contacto")
        println("2. Mostrar Contactos")
        println("3. Calculadora")
        println("4. Salir")
        println("Ingresa una opción:")
        val opcion = readLine()!!

        when (opcion) {
            "1" -> {
                println("\nAgregar contacto:")
                println("Ingrese el nombre:")
                val nombre = readLine()!!
                println("Ingrese el teléfono:")
                val telefono = readLine()!!.toInt()
                println("Ingrese el email:")
                val email = readLine()!!
                val nuevoContacto = Contacto(nombre, telefono, email)
                agenda.agregarContacto(nuevoContacto)
            }
            "2" -> {
                println("\nMostrar Contactos")
                agenda.mostrarContactos()
            }
            "3" -> {
                println("Calculadora")
                SimpleCalc()
            }
            "4" -> {
                println("Exiting...")
                exitProcess(0)
            }
            else -> {
                println("Has ingresado una opción incorrecta. Por favor, vuelve a intentarlo.")
            }
        }
    }
}

fun SimpleCalc() {
    while (true) {
        println("\nCalculadora")
        println("1. Suma")
        println("2. Resta")
        println("3. Multiplicación")
        println("4. División")
        println("5. Regresar al menú principal")
        println("Ingresa una opción:")
        val opcion = readLine()!!

        when (opcion) {
            "1" -> {
                val numbers =Inputnumbrs()
                val result = numbers.first + numbers.second
                println("El resultado de la suma es: $result")
            }
            "2" -> {
                val numbers = Inputnumbrs()
                val result = numbers.first - numbers.second
                println("El resultado de la resta es: $result")
            }
            "3" -> {
                val numbers = Inputnumbrs()
                val result = numbers.first * numbers.second
                println("El resultado de la multiplicación es: $result")
            }
            "4" -> {
                val numbers = Inputnumbrs()
                if (numbers.second != 0) {
                    val result = numbers.first.toDouble() / numbers.second.toDouble()
                    println("El resultado de la división es: $result")
                } else {
                    println("No es posible dividir entre 0.")
                }
            }
            "5" -> return
            else -> {
                println("Has ingresado una opción incorrecta. Por favor, vuelve a intentarlo.")
            }
        }
    }
}

fun Inputnumbrs(): Pair<Int, Int> {
    println("Ingrese el primer número:")
    val num1 = readLine()!!.toInt()
    println("Ingrese el segundo número:")
    val num2 = readLine()!!.toInt()
    return num1 to num2
}
