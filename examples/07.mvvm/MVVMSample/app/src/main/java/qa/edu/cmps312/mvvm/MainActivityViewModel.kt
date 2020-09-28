package qa.edu.cmps312.mvvm

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*


class MainActivityViewModel : ViewModel() {
    private val _team1Score = MutableLiveData<Int>(0)
    private val _team2Score = MutableLiveData<Int>(0)

    // Expose read only LiveData that the View can observe or bind to
    val team1Score: LiveData<Int> get() = _team1Score
    val team2Score: LiveData<Int> get() = _team2Score

    fun incrementTeam1Score() {
        _team1Score.value = _team1Score.value?.inc()
    }

    fun incrementTeam2Score() {
        _team2Score.value = _team2Score.value?.inc()
    }

    val currentWeatherFlow: LiveData<String> =
            Repository.fetchWeatherFlow().asLiveData()

    val timeRemainingFlow: LiveData<String> =
           Repository.countDownTimer(5).asLiveData() //viewModelScope.coroutineContext

    /*
    var team1Score = 0
    var team2Score = 0

    fun incrementTeam1Score() = team1Score++
    fun incrementTeam2Score() = team2Score++
   */

    init {
        Log.d("MainActivityViewModel", ">>> MainActivityViewModel - Created <<<")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainActivityViewModel", ">>> MainActivityViewModel - onCleared <<<")
    }
}