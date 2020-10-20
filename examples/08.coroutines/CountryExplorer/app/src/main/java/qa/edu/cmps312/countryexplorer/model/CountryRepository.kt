package qa.edu.cmps312.countryexplorer.model

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object CountryRepository {
    private const val BASE_URL = "https://restcountries.eu/rest/v2/"
    private val contentType = "application/json".toMediaType()

    private val jsonConverterFactory = Json { ignoreUnknownKeys = true
                   coerceInputValues = true }.asConverterFactory(contentType)

    // Generate a class to enable calling the service methods
    val countryService: CountryService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
            .create(CountryService::class.java)
    }
}