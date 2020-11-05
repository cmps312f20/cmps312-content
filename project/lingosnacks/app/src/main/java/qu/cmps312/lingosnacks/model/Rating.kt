package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Rating(
    val ratingId: String = "",
    val packageId: String,
    val comment: String,
    val doneOn: String,
    val doneBy: String,
    val rating: Double
)