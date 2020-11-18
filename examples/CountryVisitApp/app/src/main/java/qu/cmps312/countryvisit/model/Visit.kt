package qu.cmps312.countryvisit.model

import kotlinx.serialization.Serializable

@Serializable
data class Visit (
    val code: String,
    val name: String,
    val rating: Float,
    val amount: Float
)