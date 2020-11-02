package qu.cmps312.shoppinglist.repository

import android.content.Context
import qu.cmps312.shoppinglist.db.ShoppingDB
import qu.cmps312.shoppinglist.entity.ShoppingItem

// Repository, abstracts access to multiple data sources
class ShoppingRepository(private val context: Context) {
    private val shoppingDB by lazy {
        ShoppingDB.getInstance(context)
    }

    private val shoppingItemDao by lazy {
        shoppingDB.getShoppingItemDao()
    }

    private val productDao by lazy {
        shoppingDB.getProductDao()
    }

    fun getShoppingItems() = shoppingItemDao.getAll()

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: ShoppingItem) {
        val dbItem = shoppingItemDao.getItem(item.productId)
        return if (dbItem == null) {
            shoppingItemDao.insert(item)
        } else {
            val quantity = dbItem.quantity + item.quantity
            shoppingItemDao.updateQuantity(dbItem.id, quantity)
        }
    }

    suspend fun updateQuantity(id: String, quantity: Int) = shoppingItemDao.updateQuantity(id,quantity)
    suspend fun deleteItem(item: ShoppingItem) = shoppingItemDao.delete(item)

    suspend fun getProducts(categoryId: String) = productDao.getProducts(categoryId)
    fun getCategories() = productDao.getCategories()
}