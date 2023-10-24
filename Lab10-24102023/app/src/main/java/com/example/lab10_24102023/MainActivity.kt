package com.example.lab10_24102023

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab10_24102023.ui.theme.Lab1024102023Theme
import com.example.lab10_24102023.ui.HomeScreen
import com.example.lab10_24102023.ui.LoginScreen()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1024102023Theme {
                // A surface container using the 'background' color from the theme
                Surface {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    Lab1024102023Theme {
        // A surface container using the 'background' color from the theme
        Surface {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "loginScreen") {
                composable("loginScreen") { LoginScreen(navController) }
                composable("registerScreen") { RegisterScreen(navController) }
                // ... other composables
            }
        }
    }
}



