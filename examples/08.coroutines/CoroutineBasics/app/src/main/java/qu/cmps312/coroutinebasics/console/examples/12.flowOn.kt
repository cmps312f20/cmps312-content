package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.delay
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

/*
using a flowOn operator: emit values from one thread and collect them in another
*/
val flowNews = flow {
    println("Emitting coroutine -> $coroutineContext")
    emit("Dar")
    emit("Salam")
    delay(100)
    emit("Done")
}.flowOn(Dispatchers.IO) // The above stream will run in io dispatcher

fun main() = runBlocking {
    flowNews.collect {
        println("Collecting coroutine -> $coroutineContext and value $it")
    }
}