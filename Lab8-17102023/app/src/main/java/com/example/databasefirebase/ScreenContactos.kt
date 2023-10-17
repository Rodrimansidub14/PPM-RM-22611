package com.example.databasefirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ContactRow(contacto: Contacto){
    Text(contacto.nombre)
    Text(contacto.telefono)
    Text(contacto.correo)

    Button(onClick = {updateContacto(contacto)}) {
        Text("Actualizar")
    }
    Button(onClick = {deleteContacto(contacto)}) {
        Text("Eliminar")
    }
}

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
        LazyColumn {
            items(contactos.size) { index ->
                ContactRow(contactos[index])
            }
        }
    }
}









