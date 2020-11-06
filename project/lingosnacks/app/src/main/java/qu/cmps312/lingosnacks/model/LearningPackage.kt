package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable
import qu.cmps312.lingosnacks.ui.viewmodel.UserInfo

@Serializable
data class LearningPackage(
        val packageId: String,
        val title: String,
        val description: String,
        val category: String,
        val iconUrl: String = "",
        val language: String,
        val level: String,
        val author: String,
        val keywords: MutableList<String>,
        val lastUpdatedDate: String,
        val version: Int,
        val avgRating: Double = 0.0,
        internal var numRatings: Int = 0,
        var words: MutableList<Word>) {
    fun isOwner(userInfo: UserInfo) = userInfo.email.equals(author, true)
    fun isTeacher(userInfo: UserInfo) = userInfo.role.equals("Teacher", true)
}