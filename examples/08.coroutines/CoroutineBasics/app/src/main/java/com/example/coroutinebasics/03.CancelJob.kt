package com.example.coroutinebasics

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun main() {
    runBlocking {
        /*
        isActive: Do an action before finishing the coroutine.
        You may also use:
            ensureActive(): Instantaneously stop work
            yield(): for CPU heavy computation that may exhaust thread-pool
         */
        val job = GlobalScope.launch(Dispatchers.Default) {
            fibonacci()
        }

        delay(1000)
        println("Cancel Fibonacci!")
        job.cancel()
        //println("Done!")
    }
}

// withContext allow us to run this function in the IO Thread
suspend fun fibonacci() = withContext(Dispatchers.IO) {
    try {
        var terms = Pair(0L, 1L)
        // this sequence is infinite
        while (true) {
            //yield()  // periodic check - if job cancelled exit the loop
            //ensureActive()
            if (!isActive) return@withContext
            println("${terms.first}")
            terms = Pair(terms.second, terms.first + terms.second)
            // Suspend the function for 200ms
            delay(200)
        }
        println("Job done!")
    } catch (e: CancellationException) {
        println("Job cancelled!")
    }
}