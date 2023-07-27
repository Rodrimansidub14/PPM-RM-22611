package com.example.kotlinbasics

import java.util.Scanner

data class Contact(val name: String, val phone: Int, val email: String)

class Agenda(private val username: String, private val password: String) {
    private val contacts = mutableListOf<Contact>()

    fun login() {
        val scanner = Scanner(System.`in`)
        print("Enter username: ")
        val inputUsername = scanner.nextLine()
        print("Enter password: ")
        val inputPassword = scanner.nextLine()

        if (inputUsername == username && inputPassword == password) {
            println("Access granted.")
            menu()
        } else {
            println("Access denied.")
        }
    }

    private fun menu() {
        val scanner = Scanner(System.`in`)
        var option: Int
        do {
            println("1. Add contact")
            println("2. View contacts")
            println("3. Exit")
            print("Enter option: ")
            option = scanner.nextInt()

            when (option) {
                1 -> addContact()
                2 -> viewContacts()
                3 -> println("Exiting...")
                else -> println("Invalid option.")
            }
        } while (option != 3)
    }

    private fun addContact() {
        val scanner = Scanner(System.`in`)
        print("Enter name: ")
        val name = scanner.nextLine()
        print("Enter phone: ")
        val phone = scanner.nextInt()
        print("Enter email: ")
        val email = scanner.next()

        contacts.add(Contact(name, phone, email))
    }

    private fun viewContacts() {
        for (contact in contacts) {
            println(contact)
        }
    }
}

fun main() {
    val agenda = Agenda("admin", "password")
    agenda.login()
}
