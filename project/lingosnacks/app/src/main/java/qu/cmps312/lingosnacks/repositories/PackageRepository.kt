package qu.cmps312.lingosnacks.repositories

import android.content.Context
import qu.cmps312.lingosnacks.model.LearningPackage
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import qu.cmps312.lingosnacks.model.Rating

class PackageRepository(val context: Context) {
    // ToDo: Implement all PackageRepository methods to read/write from the online/local database
    fun getPackages() : List<LearningPackage> {
        val data = context.assets.open("packages.json")
            .bufferedReader().use { it.readText() }
        return Json{ ignoreUnknownKeys = true }.decodeFromString<List<LearningPackage>>(data)
    }

    fun getPackages(searchText: String) : List<LearningPackage> {
        var packages = getPackages()

        if (searchText.isEmpty())
            return packages

        packages = packages.filter {
            it.keywords.any { kw -> kw.contains(searchText, true) } ||
            it.description.contains(searchText, true)
        }
        return packages
    }

    fun addPackage(learningPackage: LearningPackage) {}
    fun updatePackage(learningPackage: LearningPackage) {}
    fun deletePackage(learningPackage: LearningPackage) {}

    fun getRatings(packageId: String) : List<Rating> {
        val data = context.assets.open("ratings.json")
            .bufferedReader().use { it.readText() }
        val ratings = Json{ ignoreUnknownKeys = true }.decodeFromString<List<Rating>>(data)
        return ratings.filter { it.packageId == packageId }
    }

    // ToDO: Whenever a new rating is added then recompute & update the package avgRating and numRatings
    // this is typically done to improve performance and avoid computing the rating upon request
    /*
        // Compute new number of ratings
        val newNumRatings = numRatings + 1

        // Compute new average rating
        val oldRatingTotal = avgRating * numRatings
        val newAvgRating = (oldRatingTotal + rating) / newNumRatings
     */
    fun addRating(rating: Rating) {
        println(">> Debug: PackageRepository.addRating: $rating")
    }
}