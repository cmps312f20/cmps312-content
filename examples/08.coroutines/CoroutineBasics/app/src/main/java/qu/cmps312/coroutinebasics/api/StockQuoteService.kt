package qu.cmps312.coroutinebasics.api

import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = "Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp"
interface StockQuoteService {
    // This method will be used to access
    //https://api.polygon.io/v1/open-close/IBM/2020-10-19?apiKey=Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp
    @GET("open-close/{symbol}/{date}?apiKey=$API_KEY")
    // Example call: getStockQuote("IBM", "2020-10-19")
    suspend fun getStockQuote(@Path("symbol") symbol: String, @Path("date") date: String): MarketStockQuote
}