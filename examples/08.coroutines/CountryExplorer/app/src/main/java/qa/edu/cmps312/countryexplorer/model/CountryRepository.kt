package json.country

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import qa.edu.cmps312.countryexplorer.model.CountryService
import retrofit2.Retrofit

object CountryRepository {
    private const val BASE_URL = "https://restcountries.eu/rest/v2/"
    private val contentType = "application/json".toMediaType()

    val jsonConverterFactory = Json { ignoreUnknownKeys = true
        coerceInputValues = true }.asConverterFactory(contentType)

    val countryService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .build()
            .create(CountryService::class.java)
    }

    var countries = listOf<Country>()
    val count : Int get() = countries.size

    fun initCountries(context: Context) : List<Country> {
        if (countries.isEmpty()) {
            val countriesJson = context.assets
                                    .open("countries.json")
                                    .bufferedReader()
                                    .use { it.readText() }
            val json = Json { ignoreUnknownKeys = true
                              coerceInputValues = true }
            countries = json.decodeFromString(countriesJson)
        }
        return countries
    }

    fun getCountriesByContinent(continent: String) = countries.filter { it.region.equals(continent, true) }
                                                              .sortedByDescending { it.population }

    fun getCountriesByRegion(region: String) = countries.filter { it.subregion.equals(region, true) }
                                                        .sortedBy { it.population }

    val continents : List<String> get() = countries.map { it.region}.distinct().sorted()
    val countryNames : List<String> get() = countries.map { it.name}.sorted()

    fun getRegions(continent : String) =
            countries.filter { it.region.equals(continent, true) }
                     .map { it.subregion }.distinct().sorted()
}