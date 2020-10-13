import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time = measureTimeMillis {
            val symbol = getStockSymbol("Microsoft")
            println(">> received getStockSymbol result: $symbol")

            val price = getStrockPrice()
            println(">> received getStrockPrice result: $price")
        }
        println("Execution duration: ${time}ms")
    }
}

suspend fun getStockSymbol(name: String): String {
    delay(2000)
    //val result = Random.nextInt(100);
    val symbol = companies[name]
    println("getStockSymbol result: $symbol")
    return symbol!!
}

suspend fun getStrockPrice(): Int {
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