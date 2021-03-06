package qu.cmps312.lingosnacks.repositories

import android.content.Context
import qu.cmps312.lingosnacks.model.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AuthRepository(val context: Context) {
    private fun getUsers() : List<User> {
        val data = context.assets.open("users.json")
                          .bufferedReader().use { it.readText() }
        return Json.decodeFromString(data)
    }

    /* ToDo: Implement userRepository.signIn using FirebaseAuth (email and password authentication).
       ToDo: After successful sign in call userRepository.getUser to get the user details from Firstore.users collection
       ToDo: Then Call userRepository.addCurrentUser to add successfully authenticated user to lingosnacks local database
    */
    fun signIn(email: String, password: String): User? {
        val users = getUsers()
        return users.find { it.email.toLowerCase() == email.toLowerCase() && it.password == password }
    }

    // ToDo: UserRepository.addUser : Add the user to FirebaseAuth & Firestore users collection (password should NOT stored in users collection)
    fun signUp(user: User) {
        println(">> Debug: AuthRepository.signUp: $user")
    }

    // ToDo: Get the user details from Firstore.users collection
    private fun getUser(id: String) {

    }

    // ToDo: Add successfully authenticated user to lingosnacks local database
    private fun addCurrentUser(user: User) {

    }

    // ToDo: Delete authenticated user from lingosnacks local database
    private fun deleteCurrentUser(user: User) {

    }

    // ToDo: Get the authenticated user from lingosnacks local database. Return null if not found (which mean the user did not login before).
    fun getCurrentUser() {

    }

    // ToDo: signout from FirebaseAuth and delete the user from lingosnacks local database
    fun signOut() {

    }
}