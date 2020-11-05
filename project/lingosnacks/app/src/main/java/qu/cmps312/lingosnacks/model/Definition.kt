package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    val text: String,
    val source: String
)