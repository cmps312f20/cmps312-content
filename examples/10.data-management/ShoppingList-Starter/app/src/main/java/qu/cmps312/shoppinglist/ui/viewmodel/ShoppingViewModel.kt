package qu.cmps312.shoppinglist.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import qu.cmps312.shoppinglist.entity.Item
import qu.cmps312.shoppinglist.repository.ShoppingRepository

class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)

    private var _shoppingList = MutableLiveData<List<Item>>()
    val shoppingList = _shoppingList as LiveData<List<Item>>

    init {
        getItems()
    }

    fun getItems() {
        val items = shoppingRepository.getItems()
        _shoppingList.value = items as MutableList<Item>
    }

    fun addItem(item: Item) {
        // Add/update the in-memory list used by UI
        // Alternatively update the data source then reset the the whole list using getItems()
        _shoppingList.value?.let {
            // If item exists just increase the quantity
            val foundAt = it.indexOfFirst { li -> li.name == item.name }
            if (foundAt >= 0) {
                // Update item in the in-memory list used by UI
                it[foundAt].quantity += item.quantity

                // Update item in the data source
                shoppingRepository.updateItem(it[foundAt])

                // This is needed to notify the observers
                _shoppingList.value = it
            } else {
                // Add item to the data source
                shoppingRepository.addItem(item)
                // Add item to the in-memory list used by UI
                // This is needed to notify the observers
                _shoppingList.value = it + item
            }
        }
    }

    fun updateItem(index: Int, item: Item) {
        // Update the data source
        shoppingRepository.updateItem(item)

        // Update the in-memory list used by UI
        // Alternatively you can reset the the whole list using getItems()
        _shoppingList.value?.let {
            val items = (it as MutableList)
            items[index] = item
            _shoppingList.value = items
        }
    }

    fun deleteItem(item: Item) {
        // Delete from data source
        shoppingRepository.deleteItem(item)

        // Delete from the in-memory list used by UI
        // Alternatively you can reset the the whole list using getItems()
        _shoppingList.value?.let {
            //it.remove(item)
            _shoppingList.value = it - item
        }
    }
}