package qu.cmps312.shoppinglist.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val image: String,
    val categoryId: Long = 0,
    /*
    Unfortunate limitation in Room:
      If category is annotated with @Ignore Room will ignore when writing 👍 but
      it will also ignore it when reading👎 even if category column is explicitly
      returned in a join query.
      So, as a workaround we store it as a null value but never read it
    */
    //@Ignore
    val category: String? = null
) {
    constructor(name: String, image: String, categoryId: Long)
            : this(0, name, image, categoryId)

    override fun toString(): String {
        return "$name $image"
    }
}