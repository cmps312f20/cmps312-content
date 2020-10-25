package qu.cmps312.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.shoppinglist.entity.Item

@Database(entities = [Item::class], version = 1)
abstract class ShoppingDB : RoomDatabase() {
    abstract fun getShoppingDao(): ShoppingDao

    // Create a singleton dbInstance
    companion object {
        private var dbInstance: ShoppingDB? = null

        fun getInstance(context: Context): ShoppingDB {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context,
                    ShoppingDB::class.java, "shopping.db"
                ).build()
            }
            println("Debug: dbInstance = $dbInstance")
            GlobalScope.launch {
                initDB(dbInstance!!, context)
            }
            return dbInstance as ShoppingDB
        }

        private suspend fun initDB(shoppingDB: ShoppingDB, context: Context) {
            val shoppingDao = shoppingDB.getShoppingDao()
            val itemsCount = shoppingDao.getCount()
            if (itemsCount == 0L) {
                // Read from json file and write to db
                val data = context.assets.open("shopping_list.json")
                    .bufferedReader().use { it.readText() }
                val itemsList = Json.decodeFromString<List<Item>>(data)
                shoppingDao.insertList(itemsList)
            }
        }
    }
}
