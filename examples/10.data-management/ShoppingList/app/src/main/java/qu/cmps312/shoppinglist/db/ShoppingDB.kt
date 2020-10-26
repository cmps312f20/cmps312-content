package qu.cmps312.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.shoppinglist.entity.Category
import qu.cmps312.shoppinglist.entity.Item
import qu.cmps312.shoppinglist.entity.Product

// If you change the version the DB will be dropped and recreated
@Database(entities = [Product::class, Category::class, Item::class], version = 2, exportSchema = false)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun getShoppingDao(): ItemDao
    abstract fun getProductDao(): ProductDao

    // Create a singleton dbInstance
    companion object {
        private var dbInstance: ShoppingDB? = null

        fun getInstance(context: Context): ShoppingDB {
            if (dbInstance == null) {
                // Use Room.databaseBuilder to open( or create) the database
                dbInstance = Room.databaseBuilder(
                    context,
                    ShoppingDB::class.java, "shopping.db"
                ).fallbackToDestructiveMigration().build()
            }
            // After the DB instance is created load the data from json files
            // into the Category and Product tables if the Category table is empty
            GlobalScope.launch {
                initDB(dbInstance!!, context)
            }
            return dbInstance as ShoppingDB
        }

        private suspend fun initDB(shoppingDB: ShoppingDB, context: Context) {
            val productDao = shoppingDB.getProductDao()
            val categoryCount = productDao.getCategoryCount()
            // If categoryCount = 0 then means the DB is empty
            if (categoryCount == 0) {
                val json = Json { ignoreUnknownKeys = true
                                  coerceInputValues = true }
                // Read from json file and write to db
                // 1. Insert categories
                var data = context.assets.open("product_categories.json")
                    .bufferedReader().use { it.readText() }
                val categories = json.decodeFromString<List<Category>>(data)
                val categoryIds = productDao.insertCategories(categories)

                println(">> Debug: categoryIds = productDao.insertCategories(categories) $categoryIds")

                // 2. Insert products
                data = context.assets.open("products.json")
                    .bufferedReader().use { it.readText() }
                var products = json.decodeFromString<List<Product>>(data)
                println(">> Debug: initDB products $products")

                products = products.map {
                    // Lookup the category id
                    val category = productDao.getCategory(it.category!!)
                    Product(it.name, it.image, category!!.id)
                }
                val productIds = productDao.insertProducts(products)
                println(">> Debug: productIds = productDao.insertProducts(products) $productIds")
            }
        }
    }
}
