package qu.cmps312.shoppinglist.repository

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.shoppinglist.entity.Item

class ShoppingRepository(private val context: Context) {
    fun getItems() = run {
            val data = context.assets.open("shopping_list.json")
                                     .bufferedReader().use { it.readText() }
            Json.decodeFromString<List<Item>>(data)
    }

    fun addItem(item: Item) {}
    fun updateItem(item: Item) {}
    fun deleteItem(item: Item) {}
}