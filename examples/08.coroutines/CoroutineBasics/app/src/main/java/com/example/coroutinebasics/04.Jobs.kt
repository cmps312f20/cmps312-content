import kotlinx.coroutines.*

fun main() {
    val job = GlobalScope.launch {
        repeat(5) {
            println("Coroutine is still working...")
            delay(1000L)
        }
    }
    runBlocking {
        job.join()
        //delay(2000L)
        //job.cancel()
        println("Main Thread is continuing...")
    }
}



/*
fun main() {
    runBlocking {
        val job1 = launch {
            //delay(3000)
            println("job1 launched")
            val job2 = launch {
                println("job2 launched")
                delay(3000)
                println("job2 finished")
            }
            job2.invokeOnCompletion { "job2 completed "}
        }

        job1.invokeOnCompletion {
            println("job1 completed")
        }

        delay(500)
        //println("job1 will be cancelled")
        job1.cancel()
    }
}
*/