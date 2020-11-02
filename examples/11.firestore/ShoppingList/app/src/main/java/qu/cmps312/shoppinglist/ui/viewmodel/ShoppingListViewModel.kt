package qu.cmps312.shoppinglist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.entity.User
import qu.cmps312.shoppinglist.repository.AuthRepository
import qu.cmps312.shoppinglist.repository.ShoppingFsRepository
import java.lang.Exception

class ShoppingListViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingFsRepository(appContext) //ShoppingRepository(appContext)
    private val authRepository = AuthRepository()
    private val _shoppingList = MutableLiveData<List<ShoppingItem?>>()
    val shoppingList = _shoppingList as LiveData<List<ShoppingItem>>
    private var shoppingListUpdateListener : ListenerRegistration? = null

    private val _currentUser = MutableLiveData<User?>()
    val currentUser = _currentUser as LiveData<User?>

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage = _errorMessage as LiveData<String>

    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        _errorMessage.value = exception.message
        println(">> Debug: Exception thrown: $exception.")
    }

    init {
        getShoppingItems()
        getCurrentUser()
    }

    fun getShoppingItems() {
        shoppingListUpdateListener?.remove()

        val query = shoppingRepository.getShoppingItems()
        if (query == null) {
            _shoppingList.value = emptyList<ShoppingItem>()
            return
        }
        shoppingListUpdateListener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                println(">> Debug: Shopping List Update Listener failed. ${e.message}")
                return@addSnapshotListener
            }
            _shoppingList.value = snapshot?.toObjects(ShoppingItem::class.java)
        }
    }

    fun addItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }

    fun updateQuantity(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateQuantity(item.id, item.quantity)
    }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(item)
    }

    fun signUp(user: User) = viewModelScope.launch(exceptionHandler) {
            _currentUser.value = authRepository.signUp(user)
    }

    fun signIn(email: String, password: String) = viewModelScope.launch(exceptionHandler) {
        _currentUser.value = authRepository.signIn(email, password)
    }

    fun getCurrentUser() = viewModelScope.launch(exceptionHandler) {
        val uid = Firebase.auth.currentUser?.uid
        if (uid == null)
            _currentUser.value = null
        else
            _currentUser.value = authRepository.getUser(uid)
    }

    fun signOut() {
        Firebase.auth.signOut()
        _currentUser.value = null
    }

    fun initDB() = liveData<String> {
        val result = shoppingRepository.initDB()
        emit(result)
    }

    override fun onCleared() {
        shoppingListUpdateListener?.remove()
        super.onCleared()
    }
}