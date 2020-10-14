package qu.cmps312.coroutinebasics.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

private const val TAG = "ViewModel.Coroutines"
class MainViewModel : ViewModel() {
    var nextValue = MutableLiveData<Long>()
    var stockPrice = MutableLiveData<Int>()

    var job : Job? = null

    fun startFibonacci() {
        stopFibonacci()
        job = viewModelScope.launch {
            fibonacci()
        }
    }

    fun stopFibonacci() {
        job?.let {
            if (it.isActive)
                it.cancel()
        }
    }

    /*
     The Fibonacci series is a series where the next term is the sum of pervious two terms.
     Fn = Fn-1 + Fn-2
     The first two terms of the Fibonacci sequence is 0 followed by 1.
    The Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21, ...
    */
    suspend fun fibonacci() = withContext(Dispatchers.Default) {
        try {
            var terms = Pair(0L, 1L)
            // this sequence is infinite
            while (true) {
                //yield()  // periodic check - if job cancelled exit the loop
                //ensureActive()
                if (!isActive) return@withContext
                //println("${terms.first}")
                withContext(Dispatchers.Main) {
                    nextValue.value = terms.first
                }
                terms = Pair(terms.second, terms.first + terms.second)
                // Suspend the function for 400ms
                delay(400)
            }
            println("Job done!")
        } catch (e: CancellationException) {
            println("Job cancelled!")
        }
    }

    // When add is called the main thread will block
    // This is a Blocking call = Synchronous call
    // val sum = add(20, 30)
    fun add(x: Int, y: Int) = x + y

    private suspend fun getStockSymbol(name: String): String {
        Log.d(TAG, "I am going to to getStockSymbol for $name")
        //Suspend for 2000ms
        delay(2000)
        val symbol = companies[name]
        Log.d(TAG,"getStockSymbol result: $symbol")
        return symbol!!
    }

    private suspend fun getPrice(symbol: String): Int {
        Log.d(TAG, "I am going to getStrockPrice for $symbol")
        //Suspend for 3000ms
        delay(3000)
        val price = (50..500).random()
        Log.d(TAG, "getStockPrice result: $price")
        return price
    }

    suspend fun getStockPrice(name: String) = withContext(Dispatchers.IO) {
        val symbol = getStockSymbol("Microsoft")
        val price = getPrice(symbol)
        return@withContext price
    }

    suspend fun processInParallel(companies : List<String>) = flow {
        companies.forEach {
            coroutineScope {
                val deferred = async { getStockPrice(it) }
                val price = deferred.await()
                emit("$it = $price")
            }
        }
    }

    suspend fun processSequentially(companies : List<String>) = flow {
        companies.forEach {
            coroutineScope {
                val price = getStockPrice(it)
                emit("$it = $price")
            }
        }
    }

    /*
    // ToDo convert to a Flow and move to viewModel
    private suspend fun processInParallel(companies : List<String>) = coroutineScope {
        companies.map {
            val deferred = async { viewModel.getStockPrice(it) }
            deferred.await()
        }
    }

    // ToDo convert to a Flow and move to viewModel
    private suspend fun processSequentially(companies : List<String>) = coroutineScope {
        companies.map {
            viewModel.getStockPrice(it)
        }
    }
*/

    val companies = mapOf(
        "Apple" to "AAPL",
        "Amazon" to  "AMZN",
        "Alibaba Group" to "BABA",
        "Salesforce"  to "CRM",
        "Facebook" to "FB",
        "Google" to "GOOGL",
        "Johnson & Johnson" to "JNJ",
        "Microsoft" to "MSFT",
        "Tesla" to "TSLA"
    )
}