package com.example.location.ui.theme
import LocationViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import pub.devrel.easypermissions.EasyPermissions
import android.app.Activity

@Composable
fun LocationScreen(
    viewModel: LocationViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    // Lanzador para manejar el resultado de solicitud de permisos
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            // Todos los permisos concedidos
            viewModel.getLastLocation()
        } else {
            if (activity != null && EasyPermissions.somePermissionPermanentlyDenied(activity, permissions.keys.toList())) {
                viewModel.locationData.value = "Permisos denegados permanentemente. Por favor, habilítalos desde la configuración."
            } else {
                viewModel.locationData.value = "Permisos no otorgados"
            }
        }
    }

    val locationData: String by viewModel.locationData.observeAsState(initial = "")



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = locationData)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Comprobamos y solicitamos permisos
            if (EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                viewModel.getLastLocation()
            } else {
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
            }
        }) {
            Text(text = "Obtener Ubicación")
        }
    }
}

