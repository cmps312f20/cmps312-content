package qu.cmps312.shoppinglist.repository

import android.content.Context
import androidx.room.*
import qu.cmps312.shoppinglist.db.ShoppingDB
import qu.cmps312.shoppinglist.entity.Item
import java.math.BigDecimal

class ShoppingRepository(private val context: Context) {
    private val shoppingDB by lazy {
        ShoppingDB.getInstance(context)
    }

    private val shoppingDao by lazy {
        shoppingDB.getShoppingDao()
    }

    //suspend fun getItems() = shoppingDao.getAll()
    fun getItems() = shoppingDao.getAll()

    // If item already exists just increase the quantity otherwise insert a new Item
    suspend fun addItem(item: Item) : Long {
        val dbItem = shoppingDao.getItem(item.name)
        return if (dbItem == null) {
            shoppingDao.insert(item)
        } else {
            dbItem.quantity += item.quantity
            shoppingDao.update(dbItem)
            dbItem.id
        }
    }
    suspend fun updateItem(item: Item) = shoppingDao.update(item)
    suspend fun deleteItem(item: Item) = shoppingDao.delete(item)


}

/*
// Entity and its relations are  fetched by Room
@Entity(foreignKeys = [
        ForeignKey(entity = Owner::class,
                parentColumns = ["userId"],
                childColumns = ["owner"])
        ])
data class Pet(@PrimaryKey val catId: Long,
               val name: String, val ownerId: Long)

@Entity
data class Owner(@PrimaryKey val id: Long, val name: String) {
    @Relation(parentColumn = "id", entityColumn = "ownerId")
    val pets = listOf<Pet>()
}

@Dao
public interface OwnerDao {
    @Query("SELECT id, name FROM Owner")
    suspend fun getAll() : List<Owner>
}

class Converter{
    companion object{
        @TypeConverter
        fun fromBigDecimal(value: BigDecimal):String{
            return value.toString()
        }

        @TypeConverter
        fun toBigDecimal(value:String):BigDecimal{
            return value.toBigDecimal()
        }
    }
}
*/