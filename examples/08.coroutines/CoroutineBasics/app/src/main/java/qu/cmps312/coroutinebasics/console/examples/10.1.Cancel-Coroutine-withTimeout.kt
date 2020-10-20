package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel


fun main() = runBlocking {
    val JOB_TIMEOUT = 5000L
    // Cancel the job after 5 seconds timeout
    val job = withTimeoutOrNull(JOB_TIMEOUT) {
        fibonacci().collect {
            print("$it, ")
        }
    }

    if(job == null){
        val cancelMessage = "\nJob cancelled...Job took longer than ${JOB_TIMEOUT/1000}s"
        println(cancelMessage)
    } else {
        println("\n>>> Job done <<<")
    }

}
