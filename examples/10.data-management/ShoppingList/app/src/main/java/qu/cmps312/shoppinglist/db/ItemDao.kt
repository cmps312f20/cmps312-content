package qu.cmps312.shoppinglist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import qu.cmps312.shoppinglist.entity.Item

@Dao
interface ItemDao {
    /* App will be notified of any changes of the Item table data
       Whenever Room detects Item table data change our LiveData
       observer will be called with the new list of items
       No need for suspend function as LiveData is already asynchronous
    */
    // suspend fun getAll() : List<Item>
    // p.name || ' ' || p.image : means concatenate name and image
    @Transaction
    @Query("select i.*, (p.name || ' ' || p.image) as productName from Item i join Product p on i.productId = p.id")
    fun getAll() : LiveData<List<Item>>

    @Query("select i.*, (p.name || ' ' || p.image) as productName from Item i join Product p on i.productId = p.id where i.id = :id")
    suspend fun getItem(id: Long) : Item?

    @Query("select i.*, (p.name || ' ' || p.image) as productName from Item i join Product p on i.productId = p.id where p.name = :productName")
    suspend fun getItem(productName: String) : Item?

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