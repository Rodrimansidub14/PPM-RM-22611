package com.example.freeprgm



import java.lang.ArithmeticException
import java.lang.IllegalArgumentException

interface Operaciones{
    fun ejecutar(num1:Double, num2:Double):Double
}

class Suma:Operaciones{
    override fun ejecutar(num1: Double, num2: Double):Double{
        return num1 + num2
    }
}

class Resta:Operaciones{
    override fun ejecutar(num1: Double, num2: Double):Double{
        return num1 - num2
    }
}

class Product:Operaciones{
    override fun ejecutar(num1: Double, num2: Double):Double{
        return num1 * num2
    }
}

class Div:Operaciones{
    override fun ejecutar(num1: Double, num2: Double):Double{
        if (num2 != 0.0){
            return num1/num2
        }
        else {
            throw ArithmeticException("DIV POR 0, INTENTE OTRA VEZ")
        }
    }
}

class Calculadora {
    val operaciones = listOf(
        Suma(),
        Resta(),
        Product(),
        Div()

    )

    fun calculate(nomOperaciones: String, num1: Double, num2: Double): Double {
        for (operacion in operaciones) {
            if (operacion.javaClass.simpleName == nomOperaciones)
                return operacion.ejecutar(num1, num2)
        }
        throw IllegalArgumentException("Err.Nombre Ilegal\nIntente de Nuevo")
    }
}

fun main(){
    val calculadora= Calculadora()

    while (true) {
        println("Bienvenido a la Calculadora, selecciona una opcion:\n1.Suma\n2.Resta\n3.Multiplicacion\n4.Division")

        val choice = readLine()


        if (choice == null || choice == "5") {
            break
        }


        println("Ingresa el primer numero")
        val num1String = readLine()
        if(num1String == null){
            println("Intenta de nuevo")
            continue
        }
        val num1 = num1String.toDoubleOrNull()
        if(num1==null){
            println("intenta de nuevo")
        }
        println("Ingresa el segundo numero")
        val num2String = readLine()
        if(num2String == null){
            println("Intenta de nuevo")
            continue
        }
        val num2 = num2String.toDoubleOrNull()
        if(num2==null){
            println("intenta de nuevo")
        }

        val result = when (choice){
            "1"-> calculadora.calculate("Suma",num1!!,num2!!)
            "2"-> calculadora.calculate("Resta",num1!!,num2!!)
            "3"-> calculadora.calculate("Multiplicacion",num1!!,num2!!)
            "4"-> calculadora.calculate("Division",num1!!,num2!!)

            else -> {
                println("Operacion no valida")
                continue
            }
        }
        println("El resultado es:$result")

    }
    println("Has terminado la operacion gracias por usar el programa")
}