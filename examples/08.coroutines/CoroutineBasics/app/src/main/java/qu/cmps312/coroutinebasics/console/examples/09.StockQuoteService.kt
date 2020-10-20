package qu.cmps312.coroutinebasics.console.examples

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import qu.cmps312.coroutinebasics.api.StockQuoteRepository
import qu.cmps312.coroutinebasics.ui.viewmodel.MainViewModel

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = GlobalScope.launch {
        val company = "Apple"
        val date = "2020-10-13"

        val viewModel = MainViewModel()
        val symbol = viewModel.getStockSymbol(company)

        val quote = StockQuoteRepository.stockQuoteService.getStockQuote(symbol, date)
        println(">> $company (${quote.symbol}) = ${quote.price}")
        println(">> MarketStockQuote: $quote")
    }

    job.invokeOnCompletion {
        val executionDuration = System.currentTimeMillis() - startTime
        println(">>> Job done. Total elapse time ${executionDuration/1000}s <<<")
    }
    // Wait for the job to finish otherwise main will exit
    job.join()
}