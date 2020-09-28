package qa.edu.cmps312.mvvm

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// https://kotlinlang.org/docs/reference/coroutines/flow.html
object Repository {
    private val weatherConditions = listOf("Sunny", "Windy", "Rainy", "Snowy")
    fun fetchWeatherFlow(): Flow<String> =
        flow {
            var counter = 0
            while (true) {
                counter++
                delay(20000)
                emit(weatherConditions[counter % weatherConditions.size])
            }
        }

    fun countDownTimer(minutes: Int): Flow<String> =
        flow {
            var remainingMilliSeconds = minutes * 60 * 1000L
            val step = 10000L // every 10 seconds
            emit("Remaining: ${convertToMmSs(remainingMilliSeconds)} ")
            while (true) {
                if (remainingMilliSeconds <= 0) break
                remainingMilliSeconds -= step
                delay(step)
                emit("Remaining: ${convertToMmSs(remainingMilliSeconds)} ")
            }
        }

    private fun convertToMmSs(milliSeconds: Long): String {
        val seconds = milliSeconds / 1000L
        val s = seconds % 60
        val m = seconds / 60 % 60
        //val h = seconds / (60 * 60) % 24
        return "$m mins $s secs"
    }
}

/*
    val timeRemainingFlow: LiveData<String> =
           Repository.countDownTimer(5).asLiveData() //viewModelScope.coroutineContext
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_main.*
import qa.edu.cmps312.mvvm.databinding.ActivityMainBinding
import java.util.*
/*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val selected = MutableLiveData<Item>()

    fun select(item: Item) {
        selected.value = item
    }
}

class MasterFragment : Fragment() {

    private lateinit var itemSelector: Selector

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemSelector.setOnClickListener { item ->
            // Update the UI
        }
    }
}

class DetailFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate
    // from the fragment-ktx artifact
    private val model: SharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.selected.observe(viewLifecycleOwner, Observer<Item> { item ->
            // Update the UI
        })
    }
}
*/