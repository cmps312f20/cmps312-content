package com.example.coroutinebasics

import kotlinx.coroutines.*

fun main() {
    /*
    isActive: Do an action before finishing the coroutine
    ensureActive(): Instantaneously stop work
    yield(): CPU heavy computation that may exhaust thread-pool
     */
    val job =GlobalScope.launch(Dispatchers.Default) {
        try {
            repeat(5) {
                if (!isActive) return@launch
                //ensureActive()  //yield
                println("Hello $it")
                delay(450)
            }
        } catch (e: CancellationException) {
            println("Job cancelled!")
        }
    }

    runBlocking {
        delay(1000)
        println("Cancel!")
        job.cancel()
        println("Done!")
    }
}