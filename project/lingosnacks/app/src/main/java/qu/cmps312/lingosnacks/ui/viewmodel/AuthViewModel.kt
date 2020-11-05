package qu.cmps312.lingosnacks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import qu.cmps312.lingosnacks.model.User
import qu.cmps312.lingosnacks.repositories.AuthRepository

class AuthViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val authRepository = AuthRepository(appContext)

    // ToDo: Initialize _currentUser by calling userRepository.getCurrentUser to get cached authenticated user
    private val _currentUser = MutableLiveData<User?>(null)
    val currentUser = _currentUser as LiveData<User?>

    fun signIn(email: String, password: String): User? {
        val user = authRepository.signIn(email, password)
        if (user != null) {
            _currentUser.value = user
        }
        return user
    }

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun signUp(user: User) {
        authRepository.signUp(user)
        _currentUser.value = user
    }

    fun signOut() {
        authRepository.signOut()
        _currentUser.value = null
    }
}