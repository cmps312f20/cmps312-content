package qa.edu.cmps312.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import qa.edu.cmps312.mvvm.repository.Repository

class FootballViewModel : ViewModel() {
    private val TAG = "LifeCycle->FootballViewModel \uD83D\uDEC2⚽"
    private val _team1Score = MutableLiveData<Int>(0)
    private val _team2Score = MutableLiveData<Int>(0)

    // Expose read only LiveData that the View can observe or bind to
    val team1Score: LiveData<Int> get() = _team1Score
    val team2Score: LiveData<Int> get() = _team2Score

    fun incrementTeam1Score() {
        // call postValue to notify Observers
        _team1Score.postValue((_team1Score.value ?: 0) + 1)
    }

    fun incrementTeam2Score() {
        // call postValue to notify Observers
        _team2Score.postValue( _team2Score.value?.inc())
    }

    val newsFlow: LiveData<String> = Repository.fetchNewsFlow().asLiveData()

    val timeRemainingFlow: LiveData<String> =
           Repository.countDownTimer(5).asLiveData() //viewModelScope.coroutineContext

    init {
        Log.d(TAG, "Created")
    }

    override fun onCleared() {
        Log.d(TAG, "☠️☠️ onCleared ☠️☠️")
        super.onCleared()
    }
}