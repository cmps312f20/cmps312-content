package qu.cmps312.shoppinglist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import qu.cmps312.shoppinglist.model.Item
import qu.cmps312.shoppinglist.repository.ShoppingRepository

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)

    private var _shoppingList = liveData {
        val items = shoppingRepository.shoppingList
        emit(items)
    } as MutableLiveData

    val shoppingList: LiveData<List<Item>> = _shoppingList

    fun addItem(item: Item) {
        _shoppingList.value?.let {
            // If item exists just increase the quantity
            var foundAt = it.indexOfFirst { li -> li.name == item.name }
            if (foundAt >= 0)
            {
                it[foundAt].quantity += item.quantity
                // This is needed to notify the observers
                _shoppingList.value = it
            }
            else {
                // This is needed to notify the observers
                _shoppingList.value = it + item
            }
        }
    }

    fun deleteItem(item: Item) {
        _shoppingList.value?.let {
            _shoppingList.value = it - item
        }
    }
}