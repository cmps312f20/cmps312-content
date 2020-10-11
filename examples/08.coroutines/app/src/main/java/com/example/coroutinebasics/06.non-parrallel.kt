import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            val first = networkCall1()
            val second = networkCall2()

            println("Waiting for values")
            println("$first + $second = ${first + second}")
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