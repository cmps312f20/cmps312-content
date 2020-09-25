package qa.edu.cmps312.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    override fun onSupportNavigateUp() =  navController.navigateUp()
}