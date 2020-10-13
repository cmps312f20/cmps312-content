import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    // When add is called -> main will BLOCK
    // Blocking call = synchronous call
    val sum = add(20, 30)
    // Main can resume
    //println(sum)

    //val symbol = getStockSymbol("Microsoft")

    //val symbol = getStockSymbol("Microsoft")

    runBlocking {

        val job = GlobalScope.launch {
           // val time = measureTimeMillis {
                val symbol = getStockSymbol("Microsoft")
                println(">> received getStockSymbol result: $symbol")

                val price = getStrockPrice(symbol)
                println(">> received getStrockPrice result: $price")
           // }
           // println("Execution duration: ${time}ms")
       }

        job.cancel()
    }
}

fun add(x: Int, y: Int) = x + y

suspend fun getStockSymbol(name: String): String {
    println("I am going to getStockSymbol for $name")
    //Suspend for 2000ms
    delay(2000)
    val symbol = companies[name]
    println("getStockSymbol result: $symbol")
    return symbol!!
}

suspend fun getStrockPrice(symbol: String): Int {
    println("I am going to getStrockPrice for $symbol")
    //Suspend for 3000ms
    delay(3000)
    val price = (50..500).random()
    println("getStrockPrice result: $price")
    return price
}

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