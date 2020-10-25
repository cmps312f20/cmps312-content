package qu.cmps312.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import qu.cmps312.shoppinglist.entity.Item

@Dao
interface ShoppingDao {
    @Query("select * from Item")
    // suspend fun getAll() : List<Item>
    // App will be notified of any changes of the Item table data
    // No need for suspend function as LiveData is already asynchronous
    fun getAll() : LiveData<List<Item>>

    @Query("select * from Item where id = :id")
    suspend fun getItem(id: Long) : Item?

    @Query("select * from Item where name = :name")
    suspend fun getItem(name: String) : Item?

    @Query("select count(*) from Item")
    fun getCount() : Long

    // Returns id of newly added item
    @Insert
    suspend fun insert(item: Item): Long

    // Return ids of newly added item
    @Insert
    fun insertList(items: List<Item>) : List<Long>

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}