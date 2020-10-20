package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val parentJob = GlobalScope.launch {
        val viewModel = MainViewModel()
        val deferred = async { viewModel.getStockQuote("Apple") }
        val quote = deferred.await()
        println(">> ${quote.name} (${quote.symbol}) = ${quote.price}")

        val deferred2 = async { viewModel.getStockQuote("Tesla") }
        val quote2 = deferred2.await()
        println(">> ${quote2.name} (${quote2.symbol}) = ${quote2.price}")

        val deferred3 = async { viewModel.getStockQuote("Google") }
        val quote3 = deferred3.await()
        println(">> ${quote3.name} (${quote3.symbol}) = ${quote3.price}")
    }

    parentJob.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total elapse time ${executionDuration/1000}s <<<")
    }
    // Wait for the job to finish otherwise main will exit
    parentJob.join()
}
