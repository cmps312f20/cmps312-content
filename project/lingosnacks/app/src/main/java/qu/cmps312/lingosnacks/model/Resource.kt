package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Resource(
    var title: String,
    var url: String,
    var type: ResourceTypeEnum
)