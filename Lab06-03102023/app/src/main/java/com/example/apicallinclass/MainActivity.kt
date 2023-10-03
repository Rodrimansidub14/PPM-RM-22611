package com.example.apicallinclass
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.google.accompanist.coil.rememberImagePainter
import kotlinx.coroutines.delay
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


//////////////////////////////////////////////
//Interfaz de API
interface TeleportApi {
    @GET("api/urban_areas/")
    suspend fun getUrbanAreas(): Response<UrbanAreasResponse>

    @GET("api/urban_areas/{slug}/images/")
    suspend fun getUrbanAreaImages(@Path("slug") slug: String): Response<UrbanAreaImagesResponse>
}

//////INICIALIZACION DE API ///////////////////////
data class UrbanAreasResponse(val urban_areas: List<String>)
data class UrbanAreaImagesResponse(val photos: List<Photo>)
data class Photo(val image: Image)
data class Image(val mobile: String)

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.teleport.org/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(TeleportApi::class.java)
////////////Screens /////////////////////

sealed class Screen(val route: String) {
    object CityList : Screen("cityList")
    object CityDetails : Screen("cityDetails/{citySlug}") {
        fun withArgs(citySlug: String): String = "cityDetails/$citySlug"
        const val ARG_CITY_SLUG = "citySlug"
    }
    object CityImage : Screen("cityImage/{citySlug}") {
        fun withArgs(citySlug: String): String = "cityImage/$citySlug"
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.CityList.route) {
        composable(Screen.CityList.route) {
            CityListScreen(navController)
        }
        composable(Screen.CityDetails.route) { backStackEntry ->
            val citySlug = backStackEntry.arguments?.getString(Screen.CityDetails.ARG_CITY_SLUG)
            CityDetailsScreen(navController, citySlug)
        }
        composable(Screen.CityImage.route) { backStackEntry ->
            val citySlug = backStackEntry.arguments?.getString(Screen.CityDetails.ARG_CITY_SLUG)
            CityImageScreen(citySlug)
        }
    }
}

@Composable
fun CityListScreen(navController: NavController, cityRepo: CityRepository = CityRepository()) {
    var loading by remember { mutableStateOf(true) }
    var urbanAreas by remember { mutableStateOf<List<String>?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val areas = cityRepo.fetchUrbanAreas()
            if (areas != null) {
                urbanAreas = areas
            } else {
                error = "Failed to fetch urban areas. Please check your internet connection."
            }
        } catch (e: Exception) {
            error = "An error occurred: ${e.message}"
        } finally {
            loading = false
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                urbanAreas?.let { areas ->
                    LazyColumn {
                        items(areas.size) { index ->
                            val city = areas[index]
                            Text(
                                text = city,
                                modifier = Modifier.clickable {
                                    val slug = extractSlugFromUrl(city)
                                    navController.navigate(Screen.CityDetails.withArgs(slug))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CityDetailsScreen(navController: NavController, citySlug: String?, cityRepo: CityRepository = CityRepository()) {
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var cityDetails by remember { mutableStateOf<String?>(null) }

    // This ensures that citySlug is never null
    val nonNullCitySlug = citySlug ?: ""

    LaunchedEffect(nonNullCitySlug) {
        try {
            // Fetch additional city details if needed
            // Example: val details = cityRepo.fetchCityDetails(nonNullCitySlug)
            // Replace this with your actual data fetching logic

            // For now, let's simulate a delay to show loading indicator
            delay(2000)

            // Example: cityDetails = details
            // Replace this with your actual data
            cityDetails = "City Details for $nonNullCitySlug"
        } catch (e: Exception) {
            error = "An error occurred: ${e.message}"
        } finally {
            loading = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Display city details here
                cityDetails?.let { details ->
                    Text(text = details)
                }
            }
        }
    }
}

@Composable
fun CityImageScreen(citySlug: String?, cityRepo: CityRepository = CityRepository()) {
    var loading by remember { mutableStateOf(true) }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    // This ensures that citySlug is never null
    val nonNullCitySlug = citySlug ?: ""

    LaunchedEffect(nonNullCitySlug) {
        try {
            val image = cityRepo.fetchCityImage(nonNullCitySlug)
            if (image != null) {
                imageUrl = image
            } else {
                error = "Failed to fetch city image. Please check your internet connection."
            }
        } catch (e: Exception) {
            error = "An error occurred: ${e.message}"
        } finally {
            loading = false
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            if (error != null) {
                Text(
                    text = error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                imageUrl?.let { url ->
                    Image(
                        painter = rememberImagePainter(data = url),
                        contentDescription = "Image of $nonNullCitySlug",
                        modifier = Modifier.fillMaxWidth().height(200.dp)
                    )
                }
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

// The extractSlugFromUrl function
fun extractSlugFromUrl(url: String): String {
    return url.split("/").dropLast(1).last()
}

class CityRepository {
    private val api = retrofit.create(TeleportApi::class.java)

    suspend fun fetchUrbanAreas(): List<String>? {
        return try {
            val response = api.getUrbanAreas()
            if (response.isSuccessful) {
                response.body()?.urban_areas
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchCityImage(slug: String): String? {
        return try {
            val response = api.getUrbanAreaImages(slug)
            if (response.isSuccessful) {
                response.body()?.photos?.firstOrNull()?.image?.mobile
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}