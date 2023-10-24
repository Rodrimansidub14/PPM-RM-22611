package com.example.databasefirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.databasefirebase.ui.theme.DatabaseFirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatabaseFirebaseTheme {
                // Create a NavController for navigation
                val navController = rememberNavController()

                // Display the main screen
                NavHost(
                    navController = navController,
                    startDestination = "main_screen"
                ) {
                    composable("main_screen") {
                        MainScreen(
                            onAddClick = {
                                // Navigate to the "Add Contact" screen or perform your desired action
                                navController.navigate("add_contact_screen")
                            },
                            onUpdateClick = {
                                // Navigate to the "Update User" screen or perform your desired action
                                navController.navigate("update_user_screen")
                            },
                            onDeleteClick = {
                                // Navigate to the "Delete User" screen or perform your desired action
                                navController.navigate("delete_user_screen")
                            }
                        )
                    }
                    composable("add_contact_screen") {
                        // Implement the "Add Contact" screen here
                    }
                    composable("update_user_screen") {
                        // Implement the "Update User" screen here
                    }
                    composable("delete_user_screen") {
                        // Implement the "Delete User" screen here
                    }
                }
            }
        }
    }
}
@Composable
fun MainScreen(
    onAddClick: () -> Unit,
    onUpdateClick: (Contacto) -> Unit, // Callback for updating contact
    onDeleteClick: (Contacto) -> Unit // Callback for deleting contact
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = onAddClick) {
            Text("Add Contact")
        }
        Button(onClick = {
            // Create a Contacto object with dummy data or retrieve it from your list
            val contact = Contacto(
                id = "12345",
                nombre = "John Doe",
                telefono = "1234567890",
                correo = "john.doe@example.com"
            )
            onUpdateClick(contact) // Call the callback to navigate to the UpdateScreen
        }) {
            Text("Update User")
        }
        Button(onClick = {
            // Create a Contacto object with dummy data or retrieve it from your list
            val contact = Contacto(
                id = "12345",
                nombre = "John Doe",
                telefono = "1234567890",
                correo = "john.doe@example.com"
            )
            onDeleteClick(contact) // Call the callback to navigate to the DeleteScreen
        }) {
            Text("Delete User")
        }
    }
}
