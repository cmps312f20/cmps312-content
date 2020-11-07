package qu.cmps312.lingosnacks.model

import kotlinx.serialization.Serializable
import qu.cmps312.lingosnacks.ui.viewmodel.UserInfo

@Serializable
data class LearningPackage(
        var packageId: String = "",
        var title: String = "",
        var description: String = "",
        var category: String = "",
        var iconUrl: String = "",
        var language: String = "",
        var level: String = "",
        var author: String = "",
        var keywords: String = "",
        var lastUpdatedDate: String = "",
        var version: Int = 0,
        var avgRating: Double = 0.0,
        internal var numRatings: Int = 0,
        var words: MutableList<Word> = mutableListOf()) {

    fun isOwner(userInfo: UserInfo) = userInfo.email.equals(author, true)
    fun isTeacher(userInfo: UserInfo) = userInfo.role.equals("Teacher", true)
}