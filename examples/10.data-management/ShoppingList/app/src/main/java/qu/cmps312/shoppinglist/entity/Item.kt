package qu.cmps312.shoppinglist.entity

import androidx.room.*

@Entity(foreignKeys = [ForeignKey(entity = Product::class,
    parentColumns = ["id"],
    childColumns = ["productId"],
    onDelete = ForeignKey.CASCADE)])
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val productId: Long,
    var quantity: Int,
    //var addedDate = LocalDateTime.now()
    /*
    Unfortunate limitation in Room:
      If productName is annotated with @Ignore Room will ignore when writing 👍 but
      it will also ignore it when reading👎 even if productName column is explicitly
      returned in a join query.
      So, as a workaround we store it as a null value but never read it
    */
    //@Ignore // productName will NOT be stored in the database
    val productName: String? = null
) {
    constructor(productId: Long, quantity: Int) : this(0, productId, quantity)
}
