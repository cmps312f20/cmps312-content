package qu.cmps312.shoppinglist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.entity.ShoppingItem
import qu.cmps312.shoppinglist.repository.ShoppingFsRepository

class ShoppingListViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingFsRepository(appContext) //ShoppingRepository(appContext)
    private val _shoppingList = MutableLiveData<List<ShoppingItem?>>()
    val shoppingList = _shoppingList as LiveData<List<ShoppingItem>>
    private var shoppingListUpdateListener : ListenerRegistration? = null

    init {
        getShoppingListItems()
    }

    fun getShoppingListItems() {
        shoppingListUpdateListener?.remove()

        val query = shoppingRepository.getShoppingListItems()
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

    fun initDB() = liveData<String> {
        val result = shoppingRepository.initDB()
        emit(result)
    }

    override fun onCleared() {
        shoppingListUpdateListener?.remove()
        super.onCleared()
    }
}