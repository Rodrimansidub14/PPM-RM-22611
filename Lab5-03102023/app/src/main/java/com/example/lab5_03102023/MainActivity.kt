package com.example.lab5_03102023


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ShibeApi {
    @GET("shibes?count=1&urls=true&httpsUrls=true")
    suspend fun getRandomShibe(): List<String>
}

private const val BASE_URL = "http://shibe.online/api/"

val shibeApi: ShibeApi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ShibeApi::class.java)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShibeScreen()
        }
    }
}

@Composable
fun ShibeScreen() {
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            coroutineScope.launch {
                try {
                    val response = shibeApi.getRandomShibe()
                    imageUrl = response.firstOrNull()
                    errorMessage = null  // reset the error message
                } catch (e: Exception) {
                    imageUrl = null  // reset the image
                    errorMessage = "Error fetching image. Please try again."
                }
            }
        }) {
            Text(text = "Mostrar Shibe")
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Text(text = it, color = Color.Red)
            Spacer(modifier = Modifier.height(16.dp))
        }

        imageUrl?.let {
            Image(
                painter = rememberImagePainter(data = it),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewShibeScreen() {
    ShibeScreen()
}