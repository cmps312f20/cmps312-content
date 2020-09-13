package json.country

import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

object CountryRepository {
    lateinit var countries : List<Country>
    val count : Int get() = countries.size

    fun getCountries(context: Context) : List<Country>{
        val countriesJson = context.assets
            .open("stadiums.json")
            .bufferedReader()
            .use { it.readText() }
        countries = Json.decodeFromString(countriesJson)
        return countries
    }

    fun getCountriesByContinent(continent: String) = countries.filter { it.continent.equals(continent, true) }
                                                              .sortedByDescending { it.population }

    fun getCountriesByRegion(region: String) = countries.filter { it.region.equals(region, true) }
                                                        .sortedBy { it.population }

    val countryCountByContinent = countries.groupingBy { it.continent }.eachCount()

    val populationByContinent = countries.groupingBy { it.continent }.fold(0) { sum: Long, country -> sum + country.population }

    fun getPopulousCountry() = countries.maxByOrNull { it.population }

    fun getLeastPopulatedCountry() = countries.filter { it.population > 0 }.minByOrNull { it.population }

    fun getLagestCountry() = countries.maxByOrNull { it.area }

    fun getSmallestCountry() = countries.filter { it.area > 0 } .minByOrNull { it.area }
}