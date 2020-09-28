package qa.edu.cmps312.mvvm.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import qa.edu.cmps312.mvvm.R
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get current language and screen orientation
        val language = Locale.getDefault().language
        val orientation = when(resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> "Landscape"
            else -> "Portrait"
        }

        Log.d(TAG,
            "*** MainActivity.onCreate. Language = $language - Orientation = $orientation ***"
        )

        navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(this, navController)

        // Connect the bottomNavBar with the navController to auto-handle OnNavigationItemSelected
        bottomNavBar.setupWithNavController(navController)

        /* To hide the 'back' button on your toolbar inside loginFragment you can use
           AppBarConfiguration, and customize which destinations are considered top-level destinations.
           This way loginFragment and homeFragment will be considered top-level destinations,
           and won't have back button on toolbar.
         */
        //val appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.loginFragment, R.id.homeFragment)).build()
        //setupWithNavController(bottomNavBar, navController)

    }

    // Handle Navigate Up event (triggered when clicking the arrow button on the Top App Bar
    override fun onSupportNavigateUp() =  navController.navigateUp() || super.onSupportNavigateUp()

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"--- onPause ---")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy. Is Finishing: $isFinishing")
    }
}