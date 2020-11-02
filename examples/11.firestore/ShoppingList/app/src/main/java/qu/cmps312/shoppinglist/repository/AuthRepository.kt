package qu.cmps312.shoppinglist.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import qu.cmps312.shoppinglist.entity.User
import java.lang.Exception

class AuthRepository {

    private val userCollectionRef by lazy {
        Firebase.firestore.collection("users")
    }

    suspend fun signUp(user: User) : User? = withContext(Dispatchers.IO) {
        val authResult = Firebase.auth.createUserWithEmailAndPassword(user.email, user.password).await()

        // Add the user details to Firestore
        authResult?.user?.let {
            user.uid = it.uid
            println(">> Debug: signUp.user.uid : ${user.uid}")
            userCollectionRef.document(user.uid).set(user).await()
            return@withContext user
        }
        return@withContext null
    }

    suspend fun signIn(email: String, password: String) : User? = withContext(Dispatchers.IO) {
        val authResult = Firebase.auth.signInWithEmailAndPassword(email, password).await()
        println(">> Debug: signIn.authResult : ${authResult.user?.uid}")
        var user : User? = null
        // Get the user details from Firestore
        authResult?.user?.let {
            val uid = it.uid
            user = getUser(uid)
        }
        println(">> Debug: signIn.user : $user")
        return@withContext user
    }

    suspend fun getUser(uid: String) = withContext(Dispatchers.IO) {
        return@withContext userCollectionRef.document(uid)
                                            .get().await().toObject(User::class.java)
    }
}