package parrallel

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            coroutineScope {
                val deferred1 = async { networkCall1() }
                val deferred2 = async { networkCall2() }

                println("Waiting for values")

                val first = deferred1.await()
                val second = deferred2.await()

                println("$first + $second = ${first + second}")
            }
        }
        println("Execution duration: $time")
    }
}

suspend fun networkCall1(): Int {
    delay(3000)
    val value1 = Random.nextInt(100);
    println("First value1 is $value1")
    return value1
}

suspend fun networkCall2(): Int {
    delay(3000)
    val value2 = Random.nextInt(100);
    println("Second value is $value2")
    return value2
}