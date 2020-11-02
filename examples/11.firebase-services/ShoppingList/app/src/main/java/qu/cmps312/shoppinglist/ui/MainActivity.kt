package qu.cmps312.shoppinglist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.entity.Address
import qu.cmps312.shoppinglist.entity.User
import qu.cmps312.shoppinglist.ui.viewmodel.AuthViewModel
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingListViewModel


private val TAG = "MainActivity.Auth"
class MainActivity : AppCompatActivity() {
    companion object {
        // Choose an arbitrary request code value
        private const val RC_SIGN_IN = 123
    }

    private val authViewModel by viewModels<AuthViewModel>()
    private val shoppingListViewModel by viewModels<ShoppingListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        authViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        // Get further details from Firestore about current user
        authViewModel.setCurrentUser()

        // Every time the Auth state changes display a message
        Firebase.auth.addAuthStateListener {
            println(">> Debug: Firebase.auth.addAuthStateListener: ${it.currentUser?.email}")
            val message = if (it.currentUser != null)
                              "Welcome ${it.currentUser!!.displayName}"
                          else
                              "Sign out successful"

            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
        }

        /*
        if (Firebase.auth.currentUser == null) {
            startSignIn()
        }*/
    }

    private fun startSignIn() {
        // You can add more providers such as Facebook, Twitter, Github, etc.
        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        // Sign in with FirebaseUI
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.img_shopping_list_logo)
            .setIsSmartLockEnabled(false)
            .build()
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = Firebase.auth.currentUser
                // Notify all the observers watching the current user change
                authViewModel.setCurrentUser()

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.

                Toast.makeText(this, response?.error?.message, Toast.LENGTH_LONG).show()
                println(">> Debug: login failed: ${response?.error?.errorCode} - ${response?.error?.message}")
                return
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        authViewModel.currentUser.observe(this) {
            val isSignedIn = (it != null)
            val displayName = it?.toString() ?: ""
            customizeMenu(menu, isSignedIn, displayName)
        }

        return super.onCreateOptionsMenu(menu)
    }

    private fun customizeMenu(menu: Menu, isSignedIn: Boolean, displayName: String = "") {
        var menuItem = menu.findItem(R.id.mi_welcome)
        menuItem.title = "\uD83D\uDC4B $displayName"
        menuItem.isVisible = isSignedIn

        menuItem = menu.findItem(R.id.mi_sign_out)
        menuItem.isVisible = isSignedIn

        menuItem = menu.findItem(R.id.mi_sign_in)
        menuItem.isVisible = !isSignedIn

        menuItem = menu.findItem(R.id.mi_sign_up)
        menuItem.isVisible = !isSignedIn

        menuItem = menu.findItem(R.id.mi_sign_in_with_firebase_auth_ui)
        menuItem.isVisible = !isSignedIn
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_init_firebase_db -> onInitFirestoreDBClicked()

            R.id.mi_sign_in_with_firebase_auth_ui -> startSignIn()

            R.id.mi_sign_up -> {
                val user = User(
                    uid = "", firstName = "Abdelkarim",
                    lastName = "Erradi", email = "erradi@live.com",
                    password = "pass123", role = "Admin",
                    Address("345", "Test St", "Doha")
                )
                authViewModel.signUp(user)
            }

            R.id.mi_sign_in -> authViewModel.signIn("erradi@live.com", "pass123")

            R.id.mi_sign_out -> {
                authViewModel.signOut()
                //AuthUI.getInstance().signOut(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onInitFirestoreDBClicked() {
        try {
            shoppingListViewModel.initDB().observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}