package qu.cmps312.shoppinglist.entity

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val name: String,
    var quantity: Int)