package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Resource(
    val resourceUrl: String,
    val title: String,
    val type: ResourceTypeEnum
)