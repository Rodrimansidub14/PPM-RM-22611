

import android.app.Application
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import android.Manifest

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationClient = LocationServices.getFusedLocationProviderClient(application)
    val locationData = MutableLiveData<String>()

    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    locationData.value = "Lat: ${location.latitude}, Lng: ${location.longitude}"
                }
            }
        } else {
            locationData.value = "Permisos no otorgados"
        }
    }
}
