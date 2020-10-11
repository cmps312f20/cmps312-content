package qu.cmps312.shoppinglist.model

import kotlinx.serialization.Serializable

@Serializable
class Item(val name: String, var quantity: Int)