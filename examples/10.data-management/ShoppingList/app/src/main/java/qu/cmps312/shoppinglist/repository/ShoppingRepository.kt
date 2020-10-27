package qu.cmps312.shoppinglist.repository

import android.content.Context
import qu.cmps312.shoppinglist.db.ShoppingDB
import qu.cmps312.shoppinglist.entity.Item

// Repository, abstracts access to multiple data sources
class ShoppingRepository(private val context: Context) {
    private val shoppingDB by lazy {
        ShoppingDB.getInstance(context)
    }

    private val itemDao by lazy {
        shoppingDB.getItemDao()
    }

    private val productDao by lazy {
        shoppingDB.getProductDao()
    }

    fun getItems() = itemDao.getAll()

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: Item) : Long {
        val dbItem = itemDao.getItem(item.productId)
        return if (dbItem == null) {
            // Ensure that the productName is always null
            item.productName = null
            itemDao.insert(item)
        } else {
            val quantity = dbItem.quantity + item.quantity
            itemDao.updateQuantity(dbItem.id, quantity)
            dbItem.id
        }
    }

    suspend fun updateQuantity(id: Long, quantity: Int) = itemDao.updateQuantity(id,quantity)
    suspend fun deleteItem(item: Item) = itemDao.delete(item)

    suspend fun getProducts(categoryId: Long) = productDao.getProducts(categoryId)
    fun getCategories() = productDao.getCategories()
}