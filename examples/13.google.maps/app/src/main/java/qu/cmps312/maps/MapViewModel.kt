package qu.cmps312.maps

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class GeoLocation(
    val latitude: Double,
    val longitude: Double
)

class MapViewModel(appContext: Application) : AndroidViewModel(appContext) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(appContext)

    private val _deviceLocation = MutableLiveData<GeoLocation>()
    val deviceLocation =  _deviceLocation as LiveData<GeoLocation>

    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        viewModelScope.launch {
            val lastLocation = fusedLocationClient.lastLocation.await()
            lastLocation?.let {
                val currentLocation = GeoLocation(it.latitude, it.longitude)
                if (_deviceLocation.value == null || _deviceLocation.value!! != currentLocation) {
                    _deviceLocation.value = GeoLocation(it.latitude, it.longitude)
                    val currentLocationMsg = "Lat: ${it.latitude} & Long: ${it.longitude}"
                    println(">> Debug: $currentLocationMsg")
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            locationResult.locations.forEach {
                val currentLocation = GeoLocation(it.latitude, it.longitude)
                if (_deviceLocation.value == null || _deviceLocation.value!! != currentLocation) {
                    _deviceLocation.value = GeoLocation(it.latitude, it.longitude)
                    val currentLocationMsg = "Lat: ${it.latitude} & Long: ${it.longitude}"
                    println(">> Debug: $currentLocationMsg")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}