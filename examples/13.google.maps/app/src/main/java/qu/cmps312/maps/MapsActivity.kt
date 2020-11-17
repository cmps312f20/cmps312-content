package qu.cmps312.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.coroutineScope
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.maps.android.ktx.addGroundOverlay
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

//This class allows you to interact with the map by adding markers, styling its appearance and
// displaying the user's device location.
class MapsActivity : AppCompatActivity() {

    private val mapViewModel by viewModels<MapViewModel>()
    private lateinit var map: GoogleMap

    private var poiMarker : Marker? = null

    // Register request permission callback, which handles the user's response to the
    // system permission dialog.
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
        // Callback for the result from requesting permission
        { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Enable My Location button on the map
                enableMyLocation()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision.
                val message = "The following features will be missing: (1) My Location button on the map (2) "
                Toast.makeText(this@MapsActivity, message, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the mapFragment from the activity layout
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        lifecycle.coroutineScope.launchWhenCreated {
            // Await for the map to be ready then customize it
            val googleMap = mapFragment.awaitMap()
            onMapReady(googleMap)
        }

        initPlacesAutocomplete()

        // Observe device location change
        mapViewModel.deviceLocation.observe(this@MapsActivity) {
            it?.let {
                val currentLocation = "Device location Lat: ${it.latitude} & Long=: ${it.longitude}"
                messageTv.text = currentLocation
                Toast.makeText(this@MapsActivity, currentLocation, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This function is called when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker for Doha.
     */
    private fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        /*
        1: World
        5: Continent
        10: City
        15: Streets
        20: Buildings
       */
        val zoomLevel = 10f
        val overlaySize = 100f

        // Doha geo coordinates
        // Sydney geo coorinates: LatLng(37.422160, -122.084270)
        val homeLatLng = LatLng(25.286106, 51.534817)
        val markerTitle = "Doha"
        // A Snippet is Additional text that's displayed below the title
        val snippetText = "$markerTitle is my City! Lat: ${homeLatLng.latitude}, Long: ${homeLatLng.longitude}"

        // Zoom the map to the home location
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))

        map.addMarker {
            position(homeLatLng)
            title(markerTitle)
            snippet(snippetText)
        }

        // Add overlay image at the current location
        map.addGroundOverlay {
            position(homeLatLng, overlaySize)
            image(BitmapDescriptorFactory.fromResource(R.drawable.android))
        }

        setMapLongClick(map)
        setPoiClick(map)
        enableMyLocation()

        // Show the zoom controls
        map.uiSettings.isZoomControlsEnabled = true

        // Optionally set a custom InfoWindow to show the Point of Interest (PoI) info
        setCustomInfoWindow()
    }

    // Initializes contents of Activity's standard options menu. Only called the first time options
    // menu is displayed.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_options, menu)
        return true
    }

    // Called whenever an item in your options menu is selected.
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Change the map type based on the user's selection.
        R.id.mi_normal_map -> {
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.mi_hybrid_map -> {
            map.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.mi_satellite_map -> {
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.mi_terrain_map -> {
            map.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        R.id.mi_reverse_geocode -> {
            // Hamad International Airport, Doha, Qatar (25.260 , 51.6138)
            val hiaLat = 25.2609 //25.37727951601785 //-33.8885751183869
            val hiaLng = 51.6138 //51.49117112159729 //151.18734866380692
            val hiaAddress = getAddress(hiaLat, hiaLng)
            val message = "Lat: $hiaLat & Long: $hiaLng is $hiaAddress"
            Toast.makeText(this@MapsActivity, message, Toast.LENGTH_LONG).show()
            true
        }
        R.id.mi_geocode -> {
            // Hamad International Airport, Doha, Qatar (25.260 , 51.6138)
            val locationName = "Hamad International Airport"
            val geoLocation = getGeoCoordinates(locationName)
            geoLocation?.let {
                val message = "$locationName @ Lat: ${it.latitude} & Long: ${it.longitude}"
                Toast.makeText(this@MapsActivity, message, Toast.LENGTH_LONG).show()
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    // Called when user makes a long press gesture on the map.
    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            // A Snippet is Additional text that's displayed below the title
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f, Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker {
                position(latLng)
                title(getString(R.string.dropped_pin))
                snippet(snippet)
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            }
        }
    }

    // Places a marker on the map and displays an info window that contains POI name.
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            println(">> Debug. Clicked PoI placeId: ${poi.placeId}. Name: ${poi.name}")
            // A Snippet is Additional text that's displayed below the title.
            val snippet = "Lat:${poi.latLng.latitude}, Long: ${poi.latLng.longitude}"

            // Remove previous poiMarker before creating a new one
            poiMarker?.remove()

            poiMarker = map.addMarker {
                position(poi.latLng)
                title(poi.name)
                snippet(snippet)
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
            }
            poiMarker?.showInfoWindow()
        }
    }

    // Checks that users have given permission to access the user's device current location
    private fun isLocationPermissionGranted() =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED


    // Checks if users have given their location and sets location enabled if so.
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled = true

            // Get the user's device current location
            mapViewModel.getCurrentLocation()
            // Subscribe to location updates
            mapViewModel.startLocationUpdates()
        } else {
            // Ask for the permission to access the user's device location
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    /*
        Reverse geocoding = converting geographic coordinates (lat, lng)
        into a human-readable address
    */
    private fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(lat, lng, 1)

        return if (addresses!= null && addresses.size > 0) {
            val address = addresses[0]?.getAddressLine(0) ?: ""
            //val city = addresses[0]?.locality ?: ""
            //val country = addresses[0]?.countryName ?: ""
            address
        } else {
            ""
        }
    }

    /*
       Geocoding = converting an address or location name (like a street address) into
       geographic coordinates (lat, lng)
   */
    private fun getGeoCoordinates(locationAddress: String): GeoLocation? {
        val geocoder = Geocoder(this)
        val coordinates = geocoder.getFromLocationName(locationAddress, 1)
        return if (coordinates != null && coordinates.size > 0) {
            val latitude = coordinates[0].latitude
            val longitude = coordinates[0].longitude
            GeoLocation(latitude, longitude)
        } else {
            null
        }
    }

    private fun initPlacesAutocomplete() {
        if (!Places.isInitialized()) {
            val apiKey = getString(R.string.google_maps_key)
            Places.initialize(applicationContext, apiKey, Locale.US);
        }

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocompleteFragment) as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // Zoom the map to the location of the selected place
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.latLng, 15F))
                println(">> Debug - Place: ${place.name}, ${place.id}, ${place.latLng?.latitude}, ${place.latLng?.longitude}")
            }

            override fun onError(status: Status) {
                println(">> Debug - An error occurred: $status")
            }
        })
    }

    private fun setCustomInfoWindow() {
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(maker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val info = LinearLayout(applicationContext)
                info.orientation = LinearLayout.VERTICAL
                val title = TextView(applicationContext)
                title.setTextColor(Color.BLACK)
                title.gravity = Gravity.CENTER
                title.setTypeface(null, Typeface.BOLD)
                title.text = marker.title
                val snippet = TextView(applicationContext)
                snippet.setTextColor(Color.GRAY)
                snippet.text = marker.snippet
                info.addView(title)
                info.addView(snippet)
                return info
            }
        })
    }
}