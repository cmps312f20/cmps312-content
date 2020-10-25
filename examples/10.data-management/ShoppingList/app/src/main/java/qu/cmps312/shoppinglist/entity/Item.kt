package qu.cmps312.shoppinglist.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity //(tableName = "shopping_items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    //@ColumnInfo(name="item_name")
    val name: String,
    var quantity: Int)
