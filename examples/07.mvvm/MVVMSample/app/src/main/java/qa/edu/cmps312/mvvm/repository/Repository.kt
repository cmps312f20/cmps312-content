package qa.edu.cmps312.mvvm.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

// https://kotlinlang.org/docs/reference/coroutines/flow.html
object Repository {
    fun fetchNewsFlow(): Flow<String> =
        flow {
            var counter = 0
            while (true) {
                counter++
                delay(20000)
                emit(DummyDb.getNews())
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
        return "$m mins $s secs"
    }
}

object DummyDb {
    val weatherConditions = listOf("Sunny", "Windy", "Rainy", "Snowy")
    val clubs = listOf(
        "Bayern München",
        "Liverpool FC",
        "Paris Saint-Germain",
        "Barcelona",
        "Real Madrid",
        "Manchester City",
        "Atlético Madrid",
        "Inter Milan",
        "Sevilla",
        "Atalanta",
        "AC Milan",
        "Juventus"
    )

    fun getNews() : String {
        val clubs = clubs.shuffled().take(2)
        val scores = (0..4).shuffled().take(2)
        return "${clubs[0]}: ${scores[0]} \n${clubs[1]}: ${scores[1]}"
    }
}