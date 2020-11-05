package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    val text: String,
    val resources: MutableList<Resource> = mutableListOf()
)