package qa.edu.cmps312.countryexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import json.country.Country
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var countryRecyclerAdapter : CountryRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countriesJson = resources.openRawResource(R.raw.countries)
                                .bufferedReader().use { it.readText() }
        //println(countriesJson)
        val countryRepository = CountryRepository(countriesJson)
        val countries = countryRepository.getCountries()

        println(countries)

        initRecyclerView(countries)
    }

    private fun initRecyclerView(countries: List<Country>){
        countriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            countryRecyclerAdapter = CountryRecyclerAdapter()
            countryRecyclerAdapter.countries = countries
            adapter = countryRecyclerAdapter
        }
    }
}