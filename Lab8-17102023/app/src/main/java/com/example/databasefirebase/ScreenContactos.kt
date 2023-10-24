package com.example.databasefirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContactos() {
    var newContactoNombre by remember { mutableStateOf("") }
    var newContactoTel by remember { mutableStateOf("") }
    var newContactoEmail by remember { mutableStateOf("") }
    var newContactoId by remember { mutableStateOf("") }

    val contactos = remember { mutableStateListOf<Contacto>() }

    Column {
        TextField(
            value = newContactoNombre,
            onValueChange = { newContactoNombre = it },
            label = { Text("Nombre") }
        )
        TextField(
            value = newContactoTel,
            onValueChange = { newContactoTel = it },
            label = { Text("Telefono") }
        )
        TextField(
            value = newContactoEmail,
            onValueChange = { newContactoEmail = it },
            label = { Text("Email") }
        )
        TextField(
            value = newContactoId,
            onValueChange = { newContactoId = it },
            label = { Text("Id") }
        )
        Button(onClick = {
            addContacto(
                Contacto(

                    id = newContactoId,
                    nombre = newContactoNombre,
                    telefono = newContactoTel,
                    correo = newContactoEmail
                )
            )
        }) {
            Text("Agregar Contacto")
        }

        }
    }

@Composable
fun UpdateScreen(
    contact: Contacto, // Pass the contact to update as a parameter
    onUpdateClick: (Contacto) -> Unit // Callback to handle the update action
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Add input fields for updating contact details
        // For example, you can use TextFields for name, phone, email, etc.

        // Add a button to trigger the update action
        Button(
            onClick = {
                // Create a new Contacto object with updated data
                val updatedContact = Contacto(
                    id = contact.id,
                    nombre = "",
                    telefono = "",
                    correo = ""
                )

                // Call the callback function to update the contact
                onUpdateClick(updatedContact)
            }
        ) {
            Text("Update Contact")
        }
    }
}

@Composable
fun DeleteScreen(
    contact: Contacto, // Pass the contact to delete as a parameter
    onDeleteClick: (Contacto) -> Unit // Callback to handle the delete action
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display contact details
        Text("Name: ${contact.nombre}")
        Text("Phone: ${contact.telefono}")
        Text("Email: ${contact.correo}")

        // Add a button to trigger the delete action
        Button(
            onClick = {
                // Call the callback function to delete the contact
                onDeleteClick(contact)
            }
        ) {
            Text("Delete Contact")
        }
    }
}











