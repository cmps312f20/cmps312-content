package qu.cmps312.coroutinebasics.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import qu.cmps312.coroutinebasics.ui.viewmodel.StockQuote
import retrofit2.Retrofit

private const val API_KEY = "Jjtxe7HOP_ZjzWK3kwYQu2ovpzxTPEIp"
object StockQuoteRepository {
    private const val BASE_URL = "https://api.polygon.io/v1/"
    private val contentType = "application/json".toMediaType()

    private val jsonConverterFactory = Json { ignoreUnknownKeys = true
                        coerceInputValues = true }.asConverterFactory(contentType)

    val stockQuoteService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
            .create(StockQuoteService::class.java)
    }
}

/*
class TokenInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("apiKey", API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
*/