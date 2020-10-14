package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel

fun main() = runBlocking<Unit> {

    val startTime = System.currentTimeMillis()
    val job = GlobalScope.launch {
        val viewModel = MainViewModel()
        val quote = viewModel.getStockQuote("Apple")
        println(">> ${quote.name} (${quote.symbol}) = ${quote.price}")

        val quote2 = viewModel.getStockQuote("Tesla")
        println(">> ${quote2.name} (${quote2.symbol}) = ${quote2.price}")

        val quote3 = viewModel.getStockQuote("Google")
        println(">> ${quote3.name} (${quote3.symbol}) = ${quote3.price}")
    }

    job.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total elapse time $executionDuration <<<")
    }
    // Wait for the job to finish otherwise main will exit
    job.join()
}
