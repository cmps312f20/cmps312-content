package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
 // flow .... emit .... collect
fun main() {
    runBlocking {
        println("Receiving primes")
        primesFlow().collect{ // like observer
            println("Receiving $it")
        }
        println("Receiving end")

        symbolsFlow().collect{ // like observer
            println("Receiving $it")
        }
    }
}

//like observable
//flow builder
fun primesFlow() : Flow<Int> = flow {
    val primes = listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    primes.forEach {
        delay(it * 100L)
        emit(it)
    }
}

fun symbolsFlow(): Flow<String> = flow {
    emit("🌊") // Emits the value upstream ☝
    emit("⚽")
    emit("🎉")
}