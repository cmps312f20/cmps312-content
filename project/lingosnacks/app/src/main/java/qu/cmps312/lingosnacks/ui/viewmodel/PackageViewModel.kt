package qu.cmps312.lingosnacks.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import qu.cmps312.lingosnacks.model.LearningPackage
import qu.cmps312.lingosnacks.model.Rating
import qu.cmps312.lingosnacks.model.Sentence
import qu.cmps312.lingosnacks.model.Word
import qu.cmps312.lingosnacks.repositories.PackageRepository

class PackageViewModel(application: Application) : AndroidViewModel(application) {
    private val packageRepository = PackageRepository(application)
    private var _packages = MutableLiveData<MutableList<LearningPackage>>()
    var selectedPackage: LearningPackage? = null
    var selectedWord: Word? = null
    var selectedSentence: Sentence? = null

    val packages = _packages as LiveData<List<LearningPackage>>

    fun getPackages() {
        _packages.value = packageRepository.getPackages().toMutableList()
    }

    fun getPackages(searchText: String) {
        _packages.value = packageRepository.getPackages(searchText).toMutableList()
    }

    fun deletePackage(learningPackage: LearningPackage) {
        packageRepository.deletePackage(learningPackage)
        _packages.value?.let {
            it.remove(learningPackage)
            _packages.value = it
            selectedPackage = null
        }
    }

    fun addPackage(learningPackage: LearningPackage) {
        _packages.value?.let {
            // If item exists just increase the quantity
            var foundAt = it.indexOfFirst { pack -> pack.packageId == learningPackage.packageId }
            if (foundAt >= 0) {
                packageRepository.updatePackage(learningPackage)
                it[foundAt] = learningPackage
                // This is needed to notify the observers
                _packages.value = it
            } else {
                packageRepository.addPackage(learningPackage)
                // This is needed to notify the observers
                it.add(learningPackage)
                _packages.value = it
            }
        }
    }

    fun getRatings() = packageRepository.getRatings(selectedPackage?.packageId!!)
    fun addRating(rating: Rating) = packageRepository.addRating(rating)

     /*fun findItem(learningPackage: LearningPackage): Int {
      _packages.value?.let {
            var foundAt = it.indexOfFirst { pack -> pack.packageID == learningPackage.packageID }
            if (foundAt >= 0) {
                return foundAt
            }
        }
    }*/

//    fun addWord(word: Word) {
//        _packages.value?.let {
//            // If item exists just increase the quantity
//            var foundAt = it.indexOfFirst { pack -> pack.packageID == selectedPackage?.packageID ?: 0 }
//            if (foundAt >= 0) {
//                it[foundAt].words.let {
//
//                }
//                _packages.value = it
//            }
//        }
//    }

}