package qa.edu.cmps312.countryexplorer.model

import json.country.Country
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CountryService {
    //https://restcountries.eu/rest/v2/all
    @GET("all")
    suspend fun getCountries() : List<Country>

    //https://restcountries.eu/rest/v2/name/{name}
    @GET("name/{name}")
    suspend fun getCountry(@Path("name") name: String) : Country

    @POST("countries")
    suspend fun addCountry(@Body country: Country)
}