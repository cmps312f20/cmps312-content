package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.*
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel
import java.lang.Exception

fun main() = runBlocking {
    /*  By default, if one child failed the whole job is cancelled
        and all incomplete sibling jobs are cancelled.
        Unless supervisorScope is used (see example 12)
     */
    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("Exception thrown somewhere within parent or child: $exception.")
    }

    val startTime = System.currentTimeMillis()
    val parentJob = GlobalScope.launch(exceptionHandler) {
        val viewModel = MainViewModel()
        val deferred1 = async() { viewModel.getStockQuote("Tesla") }
        try {
            val quote1 = deferred1.await()
            println(">> ${quote1.name} (${quote1.symbol}) = ${quote1.price}")
        } catch (e: Exception) {
            println("Request failed : $e.")
        }

        val deferred2 = async() { viewModel.getStockQuote("Aple") }
        try {
            val quote2 = deferred2.await()
            println(">> ${quote2.name} (${quote2.symbol}) = ${quote2.price}")
        } catch (e: Exception) {
            println("Request failed : $e.")
        }

        val deferred3 = async() { viewModel.getStockQuote("Google") }
        try {
            val quote3 = deferred3.await()
            println(">> ${quote3.name} (${quote3.symbol}) = ${quote3.price}")
        } catch (e: Exception) {
            println("Request failed : $e.")
        }
    }

    parentJob.invokeOnCompletion {
        if (parentJob.isCancelled) {
            println(">>> Job cancelled. ${it?.message}<<<")
        }
        else {
            val executionDuration = System.currentTimeMillis() - startTime
            println(">>> Job done. Total elapse time ${executionDuration / 1000}s <<<")
        }
    }
    // Wait for the job to finish otherwise main will exit
    parentJob.join()
}
