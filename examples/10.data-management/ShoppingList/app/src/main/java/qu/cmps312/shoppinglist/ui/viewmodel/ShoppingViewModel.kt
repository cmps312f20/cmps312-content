package qu.cmps312.shoppinglist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import qu.cmps312.shoppinglist.db.ShoppingDB
import qu.cmps312.shoppinglist.entity.Item
import qu.cmps312.shoppinglist.repository.ShoppingRepository

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)
    val shoppingList = shoppingRepository.getItems()

    fun addItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.addItem(item)
    }

    fun updateQuantity(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateQuantity(item.id, item.quantity)
    }

    fun deleteItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(item)
    }
}