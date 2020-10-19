package qu.cmps312.coroutinebasics.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.coroutinebasics.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        /* To hide the 'back' button on the toolbar for profileFragment you can use
           AppBarConfiguration, and customize which destinations are considered top-level destinations.
           This way footballFragment and profileFragment will be considered top-level destinations,
           and won't have back button on toolbar.
        */
        navController = findNavController(R.id.navHostFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.whyCoroutinesFragment,
                                      R.id.cancelCoroutineFragment,
                                      R.id.parallelCoroutinesFragment))
        setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Connect the bottomNavBar with the navController to auto-handle OnNavigationItemSelected
        bottomNavBar.setupWithNavController(navController)
    }

    // Handle Navigate Up event (triggered when clicking the arrow button on the Top App Bar
    override fun onSupportNavigateUp() =  navController.navigateUp() || super.onSupportNavigateUp()
}