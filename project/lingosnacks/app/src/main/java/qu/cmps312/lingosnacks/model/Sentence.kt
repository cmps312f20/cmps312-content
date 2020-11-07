package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    var text: String,
    var resources: MutableList<Resource> = mutableListOf()
)