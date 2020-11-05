package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable

@Serializable
data class LearningPackage(
        val packageId: String,
        val author: String,
        val category: String,
        val description: String,
        val iconUrl: String = "",
        val keywords: MutableList<String>,
        val language: String,
        val lastUpdatedDate: String,
        val level: String,
        val title: String,
        val version: Int,
        var words: MutableList<Word>,
        val avgRating: Double = 0.0,
        internal var numRatings: Int = 0) {

    fun isOwner(currentUserEmail: String) = currentUserEmail.equals(author, true)
    fun isSignedIn(currentUserEmail: String) = currentUserEmail.isNotEmpty()
}