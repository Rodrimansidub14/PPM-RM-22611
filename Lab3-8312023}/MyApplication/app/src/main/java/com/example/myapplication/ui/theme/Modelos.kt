// Rodrigo Mansilla  22611
// 29/09/2021
// Programación de Plataformas Móviles
// Lab 3
// Universidad del Valle de Guatemala
// Ciclo 2 año 2



package com.example.myapplication.ui.theme

import android.content.res.Resources
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

data class Card(val design: Int, val festivity: String)

data class UserInput(val name: String, val surname: String, val message: String)
//

// Class: FestivitySelectionScreen permite seleccionar una festividad
@Composable

fun FestivitySelectionScreen(onFestivitySelected: (String) -> Unit) {
    val festivities = listOf("Cumpleaños", "Amor", "Bodas", "Graduaciones","Halloween")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selecciona una Festividad", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        festivities.forEach { festivity ->
            Button(onClick = { onFestivitySelected(festivity) }) {
                Text(festivity)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
// Class: DesignSelectionScreen permite seleccionar un diseño
@Composable
fun DesignSelectionScreen(festivity: String, onDesignSelected: (Int) -> Unit) {
    val designs = getDesignsForFestivity(festivity)

    // Log the fetched designs
    Log.d("DesignSelectionScreen", "Fetched designs for $festivity: $designs")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Selecciona un Diseño para $festivity", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn {
            items(designs) { design ->
                Image(
                    painter = painterResource(id = design),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            // Log the clicked design
                            Log.d("DesignSelectionScreen", "Design $design clicked")
                            onDesignSelected(design)
                        }
                        .size(200.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
// Class: PersonalizationScreen permite personalizar la tarjeta
sealed class Screen(val route: String) {
    object FestivitySelection : Screen("festivitySelection")
    object DesignSelection : Screen("designSelection/{festivity}")
    object Personalization : Screen("personalization/{design}")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalizationScreen(design: Int, onSubmit: (UserInput) -> Unit) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showPreview by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showPreview) {
            CardPreview(UserInput(name, surname, message), design)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { showPreview = false }) {
                    Text("Editar")
                }
                Button(onClick = {
                    onSubmit(UserInput(name, surname, message))
                }) {
                    Text("Finalizar")
                }
            }
        } else {
            if (design != 0) {
                Image(painter = painterResource(id = design), contentDescription = null, modifier = Modifier.size(100.dp))
            } else {
                Log.e("PersonalizationScreen", "Invalid design ID: $design")
                Text(text = "Invalid design selected", color = Color.Red)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Personaliza tu Tarjeta", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = surname, onValueChange = { surname = it }, label = { Text("Apellido") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text("Mensaje") })
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { showPreview = true }) {
                Text("Vista previa")
            }
        }
    }
}


// Class: CardPreview permite previsualizar la tarjeta
@Composable
fun CardPreview(userInput: UserInput, design: Int?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (design != null && resourceExists(design)) {
            Image(
                painter = painterResource(id = design),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
            Text(
                text = "${userInput.message}\n\n- ${userInput.name} ${userInput.surname}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        } else {
            Log.e("CardPreview", "Diseño invalido ")// Diseño Invalido
            Text("Diseño invalido seleccionado", color = Color.Red)
        }
    }
}
// Class: resourceExists permite verificar si un recurso existe
@Composable
fun resourceExists(resourceId: Int): Boolean {
    val resources = LocalContext.current.resources
    try {
        resources.getResourceName(resourceId)
        return true
    } catch (e: Resources.NotFoundException) {
        return false
    }
}
// Class: getDesignsForFestivity permite obtener los diseños para una festividad
fun getDesignsForFestivity(festivity: String): List<Int> {
    return when (festivity) {
        "Cumpleaños" -> listOf(
            R.drawable.bday1,
            R.drawable.bday2,
            R.drawable.bday3,
            R.drawable.bday4
        )
        "Amor" -> listOf(
            R.drawable.love1,
            R.drawable.love2,
            R.drawable.love3,
            R.drawable.love4
        )
        "Bodas" -> listOf(
            R.drawable.wedding1,
            R.drawable.wedding2,
            R.drawable.wedding3,
            R.drawable.wedding4
        )
        "Graduaciones" -> listOf(
            R.drawable.grad1,
            R.drawable.grad2,
            R.drawable.grad3,
            R.drawable.grad4
        )
        "Halloween" -> listOf(
            R.drawable.halloween1,
            R.drawable.halloween2,
            R.drawable.halloween3,
            R.drawable.halloween4
        )

        else -> emptyList()
    }
}