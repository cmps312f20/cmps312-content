package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    var text: String,
    var definitions: MutableList<Definition> = mutableListOf(),
    var sentences: MutableList<Sentence> = mutableListOf()
)