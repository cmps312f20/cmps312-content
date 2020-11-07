package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Definition(
    var text: String,
    var source: String
)