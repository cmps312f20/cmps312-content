package qu.cmps312.lingosnacks.ui


import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import coil.Coil
import coil.ImageLoader
import coil.clear
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import qu.cmps312.lingosnacks.R
import qu.cmps312.lingosnacks.model.User
import qu.cmps312.lingosnacks.ui.viewmodel.AuthViewModel
import qu.cmps312.lingosnacks.ui.viewmodel.PackageViewModel


class MainActivity : AppCompatActivity() {
    private val authViewModel by viewModels<AuthViewModel>()
    private val packageViewModel by viewModels<PackageViewModel>()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.navHostFragment)
        // Connect the drawerNavView to the NavController
        drawerNavView.setupWithNavController(navController)
        drawerNavView.setNavigationItemSelectedListener {
            onNavigationItemSelected(it)
        }

        // Show Drawer button on the action bar for top level destinations
        // and the Nav Up button on child screens
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerlayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //navController.addOnDestinationChangedListener(::onDestinationChanged)

        // Configure image loader to support SVG images
        val imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                add(SvgDecoder(this@MainActivity))
            }
            .build()
        Coil.setImageLoader(imageLoader)

        // Whenever the current user changes then customize the menu
        authViewModel.currentUser.observe(this) {
            println(">> Debug: authViewModel.currentUser.observe: $it")
            customizeMenu(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /*override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(::onDestinationChanged)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(::onDestinationChanged)
    }


    private fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        /*when(destination.id){
            R.id.transferConfirmationFragment or  R.id.transactionListFragment
            -> Toast.makeText(this, destination.label, Toast.LENGTH_SHORT).show()
        }*/
    }*/

    private fun customizeMenu(user: User?) {
        val isSignedIn = (user != null)
        val displayName = user?.toString() ?: ""
        val photoUri =  user?.photoUri ?: ""

        println(">> Debug: customizeMenu.photoUri: $photoUri")
        val headerView = drawerNavView.getHeaderView(0)
        val userNameTv = headerView.findViewById(R.id.userNameTv) as TextView
        val userRoleTv = headerView.findViewById(R.id.userRoleTv) as TextView
        val userPhotoIv = headerView.findViewById(R.id.userPhotoIv) as ImageView

        userNameTv.text = "\uD83D\uDC4B $displayName"
        userRoleTv.text = user?.role ?: ""


        if (photoUri.isEmpty()) {
            userPhotoIv.clear()
            val defaultImg = ContextCompat.getDrawable(this, R.drawable.ic_user)
            userPhotoIv.setImageDrawable(defaultImg)
        } else {
            userPhotoIv.load(photoUri)
        }

        // Workaround to refresh the menu
        drawerNavView.menu.clear()
        //drawerNavView.menu.removeGroup(0)
        drawerNavView.inflateMenu(R.menu.menu_drawer_nav)
        val menu = drawerNavView.menu

        var menuItem = menu.findItem(R.id.mi_sign_out)
        menuItem.isVisible = isSignedIn

        menuItem = menu.findItem(R.id.mi_sign_in)
        menuItem.isVisible = !isSignedIn

        menuItem = menu.findItem(R.id.mi_sign_up)
        menuItem.isVisible = !isSignedIn

        menuItem = menu.findItem(R.id.mi_my_scores)
        menuItem.isVisible = isSignedIn

        drawerNavView.invalidate()
    }

    private fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.mi_init_firebase_db -> onInitFirestoreDBClicked()

            R.id.mi_sign_up -> navController.navigate(R.id.signUpFragment)

            R.id.mi_sign_in -> navController.navigate(R.id.signInFragment)

            R.id.mi_sign_out -> authViewModel.signOut()

            R.id.mi_my_scores -> showScores()

            R.id.mi_leader_board -> navController.navigate(R.id.leaderBoardFragment)
        }
        drawerlayout.closeDrawer(GravityCompat.START) //Closing the drawer
        return true // because you handled the clicks
    }

    // Too lazy to create a fragment for this!
    private fun showScores() {
        val uid = authViewModel.getCurrentUserInfo().uid
        val scores = packageViewModel.getScores(uid)
        val items = scores.map { it.toString() }.toTypedArray()
        MaterialAlertDialogBuilder(this)
            .setTitle("Your scores")
            .setItems(items) { _, _ -> }
            .show()
    }

    private fun onInitFirestoreDBClicked() {
        packageViewModel.initFirestoreDB()
    }

}