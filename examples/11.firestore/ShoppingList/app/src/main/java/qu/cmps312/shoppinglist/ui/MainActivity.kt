package qu.cmps312.shoppinglist.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import qu.cmps312.shoppinglist.R
import qu.cmps312.shoppinglist.entity.Address
import qu.cmps312.shoppinglist.entity.User
import qu.cmps312.shoppinglist.ui.viewmodel.ShoppingListViewModel


private val TAG = "MainActivity.Auth"
class MainActivity : AppCompatActivity() {
    private val shoppingListViewModel by viewModels<ShoppingListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shoppingListViewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        /*if (Firebase.auth.currentUser == null) {
            startSignIn()
        }*/


        /*
        // Access a Cloud Firestore instance
        val db = Firebase.firestore

        // Create a new user with a first and last name
        var user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentRef ->
                println(">> Debug: Document added with ID: ${documentRef.id}")
            }
            .addOnFailureListener { e ->
                println(">> Debug: Error adding document $e")
            }

        // Create a new user with a first, middle, and last name
        user = hashMapOf(
            "first" to "Alan",
            "middle" to "Mathison",
            "last" to "Turing",
            "born" to 1912
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentRef ->
                println(">> Debug: Document added with ID: ${documentRef.id}")
            }
            .addOnFailureListener { e ->
                println(">> Debug: Error adding document $e")
            }

        db.collection("users").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println(">> Debug: ${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { e ->
                println(">> Debug: Error getting documents $e")
            }*/

    }

    private fun startSignIn() : Boolean {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )
        val RC_SIGN_IN = 9001
        // Sign in with FirebaseUI
        val intent = AuthUI.getInstance()
                           .createSignInIntentBuilder()
                           .setAvailableProviders(providers)
                           .setIsSmartLockEnabled(false)
                           .build()
        startActivityForResult(intent, RC_SIGN_IN)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        shoppingListViewModel.currentUser.observe(this) {
            var menuItem = menu.findItem(R.id.mi_welcome)
            it?.let {
                menuItem.title = "Welcome ${it.firstName} ${it.lastName}"
            }
            menuItem.isVisible = (it != null)

            menuItem = menu.findItem(R.id.mi_sign_out)
            menuItem.isVisible = (it != null)

            menuItem = menu.findItem(R.id.mi_sign_in)
            menuItem.isVisible = (it == null)

            menuItem = menu.findItem(R.id.mi_sign_up)
            menuItem.isVisible = (it == null)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_init_db -> onInitDBClicked()
            R.id.mi_sign_up -> {
                val user = User(
                    uid = "", firstName = "Abdelkarim",
                    lastName = "Erradi", email = "erradi@live.com",
                    password = "pass123", role = "Admin",
                    Address("345", "Test St", "Doha")
                )
                try {
                    shoppingListViewModel.signUp(user)
                } catch (e: Exception) {
                    println(">> Debug: SignUp failed $e")
                }
            }
            R.id.mi_sign_in -> shoppingListViewModel.signIn("erradi@live.com", "pass123")
            R.id.mi_sign_out -> {
                shoppingListViewModel.signOut()
                //AuthUI.getInstance().signOut(this)
                //startSignIn()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onInitDBClicked() {
        try {
            shoppingListViewModel.initDB().observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    /*fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                    // ...
                }

                // ...
            }
    }*/
}