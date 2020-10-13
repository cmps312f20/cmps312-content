package parrallel

import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        //withContext(Dispatchers.Default) {
            val time = measureTimeMillis {
                //  val sum = getSum()
                //  println(">> received sum: $sum")

                val deferred1 = networkCall1()
                val deferred2 = networkCall2()
            }
            println("Execution duration: ${time}ms")
        //}
    }

}


/*
coroutineScope can be used for parallel execution of tasks (i.e., child coroutines).
When any child coroutine in this scope fails, this scope fails and all the rest of the
children are cancelled.
*/
suspend fun getSum() = coroutineScope {
    try {
        val deferred1 = async { networkCall1() }
        val deferred2 = async { networkCall2() }

        println("Waiting for results from network calls")
        val result1 = deferred1.await()
        println(">> received networkCall1 result: $result1")

        val result2 = deferred2.await()
        println(">> received networkCall2 result: $result2")
        //println("$result1 + $result2 = ${result1 + result2}")
        return@coroutineScope result1 //+ result2
    } catch (e: Exception) {
        println(e.message)
    }
}

suspend fun networkCall1(): Int {
    delay(2000)
    val result = Random.nextInt(100);
    println("networkCall1 result: $result")
    return result
}

suspend fun networkCall2(): Int {
    delay(3000)
    val result = Random.nextInt(500);
    println("networkCall2 result: $result")
    return result
}