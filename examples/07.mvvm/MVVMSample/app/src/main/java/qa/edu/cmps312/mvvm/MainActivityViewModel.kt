package qa.edu.cmps312.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val team1Score = MutableLiveData<Int>(0)
    val team2Score = MutableLiveData<Int>(0)

    fun incrementTeam1Score() = team1Score.postValue(team1Score.value?.inc())
    fun incrementTeam2Score() = team2Score.postValue(team2Score.value?.inc())
    /*
    var team1Score = 0
    var team2Score = 0

    fun incrementTeam1Score() = team1Score++
    fun incrementTeam2Score() = team2Score++
   */
}