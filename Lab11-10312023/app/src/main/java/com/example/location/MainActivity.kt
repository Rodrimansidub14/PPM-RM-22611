package com.example.location

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.location.ui.theme.LocationScreen
import com.example.location.ui.theme.LocationTheme

// Define el CompositionLocal para el contexto
val LocalAppContext = staticCompositionLocalOf<Context> { error("No context provided") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Proporciona el contexto a los Composables hijos
                    CompositionLocalProvider(LocalAppContext provides this@MainActivity) {
                        LocationScreen()
                    }
                }
            }
        }
    }
}


