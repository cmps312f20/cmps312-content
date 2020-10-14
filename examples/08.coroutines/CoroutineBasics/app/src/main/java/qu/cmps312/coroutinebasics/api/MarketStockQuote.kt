package qu.cmps312.coroutinebasics.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Daily Open / Close
// https://api.polygon.io/v1/open-close/IBM/2020-10-13?apiKey=Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp

//suspend fun PolygonStocksClient.getDailyOpenClose(symbol: String, date: String): DailyOpenCloseDTO =
    //polygonClient.fetchResult { path("v1", "open-close", symbol, date) }

@Serializable
data class MarketStockQuote (
    val status: String? = null,
    @SerialName("from") val date: String? = null,
    val symbol: String? = null,
    @SerialName("open") val price: Double? = null,
    val high: Double? = null,
    val low: Double? = null,
    val close: Double? = null,
)