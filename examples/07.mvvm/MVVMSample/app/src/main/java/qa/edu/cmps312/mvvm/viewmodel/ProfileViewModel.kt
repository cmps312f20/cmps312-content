package qa.edu.cmps312.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import qa.edu.cmps312.mvvm.model.Profile


class ProfileViewModel : ViewModel() {
    //private val fionaProfile = Profile("Fiona", "Shrek", ObservableInt(0))
    private val _profile = MutableLiveData<Profile>(Profile("Fiona", "Shrek"))

    // When you need to rebind the whole profile UI object.
    //_profile.setValue(newProfile)

    // When you need to update a specific part of the UI.
    // This will trigger the notifyPropertyChanged method on the bindable field "age" and
    // hence notify the UI elements that are observing it to update.
    //_profile.getValue().likes = 2

/*    private val _name = MutableLiveData("Ada")
    private val _lastName = MutableLiveData("Lovelace")
    private val _likes =  MutableLiveData(0)

    val name: LiveData<String> = _name
    val lastName: LiveData<String> = _lastName
    val likes: LiveData<Int> = _likes*/

    val profile : LiveData<Profile> = _profile
    //val profile = _profile

    // popularity is exposed as LiveData using a Transformation instead of a @Bindable property.
    /*val popularity: LiveData<Popularity> = Transformations.map(_likes) {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }*/

/*    fun onLike() {
        _profile.value?.onLike()
        // call postValue to notify Observers
        _profile.postValue(_profile.value)
    }*/
}