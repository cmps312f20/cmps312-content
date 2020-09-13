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
        initRecyclerView()
    }

    private fun initRecyclerView(){
        val countries = CountryRepository.getCountries(this)
        println(countries)

        countriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            countryRecyclerAdapter = CountryRecyclerAdapter()
            countryRecyclerAdapter.countries = countries
            adapter = countryRecyclerAdapter
        }
    }
}