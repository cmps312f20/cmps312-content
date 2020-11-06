package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val text: String,
    val definitions: List<Definition>,
    val sentences: List<Sentence>
)