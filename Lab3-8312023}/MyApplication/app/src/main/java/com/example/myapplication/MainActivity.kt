package com.example.myapplication
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.DesignSelectionScreen
import com.example.myapplication.ui.theme.FestivitySelectionScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PersonalizationScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingCardsApp()
                }
            }
        }
    }
}
sealed class Screen(val route: String) {
    object FestivitySelection : Screen("festivitySelection")
    object DesignSelection : Screen("designSelection/{festivity}")
    object Personalization : Screen("personalization/{design}")
}

@Composable
fun GreetingCardsApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.FestivitySelection.route) {
        composable(Screen.FestivitySelection.route) {
            FestivitySelectionScreen { festivity ->
                navController.navigate(Screen.DesignSelection.route.replace("{festivity}", festivity))
            }
        }

        composable("designSelection/{festivity}") { backStackEntry ->
            val festivity = backStackEntry.arguments?.getString("festivity")
            if (festivity != null) {
                DesignSelectionScreen(festivity) { selectedDesign ->
                    navController.navigate("personalization/$selectedDesign")
                }
            }
        }

        composable("personalization/{design}") { backStackEntry ->
            val selectedDesignString = backStackEntry.arguments?.getString("design")
            Log.d("Navigation", "Retrieved design string: $selectedDesignString")

            val selectedDesign = selectedDesignString?.toIntOrNull()
            Log.d("Navigation", "Converted design ID: $selectedDesign")

            if (selectedDesign != null) {
                PersonalizationScreen(selectedDesign) {
                    // Handle the submission or saving of the personalized card
                    navController.popBackStack(navController.graph.startDestinationId, false)
                }
            } else {
                Log.e("Navigation", "Failed to retrieve design ID or convert it to Int")
            }
        }
    }
}
