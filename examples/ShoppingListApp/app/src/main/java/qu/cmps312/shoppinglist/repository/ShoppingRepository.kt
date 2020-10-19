package qu.cmps312.shoppinglist.repository

import android.content.Context
import kotlinx.serialization.decodeFromString
import qu.cmps312.shoppinglist.model.Item
import kotlinx.serialization.json.Json

class ShoppingRepository(private val context: Context) {
    /*
        lazy() is a function that takes a lambda to initialize shoppingItems
        The first call to get shoppingList executes the lambda passed to lazy()
        to initialize shoppingItems
        subsequent calls to get() simply return the remembered shoppingItems.
        shoppingItems is a lazy property!
    */
    val shoppingList by lazy {
        val data = context.assets.open("shopping_list.json")
                                 .bufferedReader().use { it.readText() }
        Json.decodeFromString<List<Item>>(data)
    }
}