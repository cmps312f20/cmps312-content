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

    fun updateItem(index: Int, item: Item) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.updateItem(item)
    }

    fun deleteItem(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        shoppingRepository.deleteItem(item)
    }
}

/*
class ShoppingViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val shoppingRepository = ShoppingRepository(appContext)

    private var _shoppingList = MutableLiveData(listOf<Item>()) //shoppingRepository.getItems()

    val shoppingList = _shoppingList as LiveData<List<Item>>

   init {
       //_shoppingList = shoppingRepository.getItems() as MutableLiveData
       //viewModelScope.launch {
           //shoppingRepository.initDB()
           getItems()
       //}
    }

    fun getItems() = viewModelScope.launch {
        val items = shoppingRepository.getItems() //as MutableLiveData
        _shoppingList.value?.let {
            _shoppingList.value = items as MutableList<Item>
        }
    }

    fun addItem(item: Item) = viewModelScope.launch {
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
                val itemId = shoppingRepository.addItem(item)
                item.id = itemId

                // Add item to the in-memory list used by UI
                //it.add(item)
                // This is needed to notify the observers
                _shoppingList.value = it + item
            }
        }
    }

    fun updateItem(index: Int, item: Item) = viewModelScope.launch {
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

    fun deleteItem(item: Item) = viewModelScope.launch {
        // Delete from data source
        shoppingRepository.deleteItem(item)

        // Delete from the in-memory list used by UI
        // Alternatively you can reset the the whole list using getItems()
        _shoppingList.value?.let {
            //it.remove(item)
            _shoppingList.value = it - item
        }
    }
} */