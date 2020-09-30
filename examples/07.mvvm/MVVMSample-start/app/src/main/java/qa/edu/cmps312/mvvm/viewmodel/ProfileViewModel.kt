package qa.edu.cmps312.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import qa.edu.cmps312.mvvm.model.Profile


class ProfileViewModel : ViewModel() {
    private val TAG = "LifeCycle->FootballViewModel \uD83D\uDEC2\uD83D\uDE00\uD83D\uDE00"
    private val _profile = MutableLiveData<Profile>(Profile("Fiona", "Shrek"))
    val profile : LiveData<Profile> = _profile

    fun save(profile: Profile) {
        Log.d("$TAG.save", profile.toString())
        //ToDo: pass it in DB or remote service
    }

    init {
        Log.d(TAG, "Created")
    }

    override fun onCleared() {
        Log.d(TAG, "☠️☠️ onCleared ☠️☠️")
        super.onCleared()
    }
}