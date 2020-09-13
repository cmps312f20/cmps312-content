package qa.edu.cmps312.countryexplorer

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_country_list.*


class CountryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)
        initRecyclerView()

        // It tells the AutoCompleteTextView what layout to use individual suggestions
        // android.R.layout.simple_list_item_1 => it a built-in layout. It has 1 TextView
        // List of all android built-in layout available @
        // https://github.com/aosp-mirror/platform_frameworks_base/tree/master/core/res/res/layout
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, CountryRepository.continents
        )
        // You can set the completionThreshold to 1 in the layout to start
        // searching once the user enters 1 character
        continentAcTv.setAdapter(adapter)

        // When the user clicks a continent
        continentAcTv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val selectedContinent = adapterView?.getItemAtPosition(i) as String
            //Toast.makeText(applicationContext,"You have select $selectedContinent", Toast.LENGTH_SHORT).show()
            // Set the filter on the recyclerView adapter
            val countryRecyclerAdapter = countriesRv.adapter as CountryRecyclerAdapter
            countryRecyclerAdapter.filter(selectedContinent)
        }
    }

    private fun initRecyclerView() {
        val countries = CountryRepository.getCountries(this)
        //println(countries)

        countriesRv.apply {
            layoutManager = LinearLayoutManager(this@CountryListActivity)
            adapter = CountryRecyclerAdapter(countries)
        }
    }

    // Load the sort options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort_countries, menu)
        return true
    }

    // Handle sort menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val countryRecyclerAdapter = countriesRv.adapter as CountryRecyclerAdapter
        val sortBy = when (item.itemId) {
            R.id.sortByNameMi -> SortBy.NAME
            R.id.sortByNameDescendingMi -> SortBy.NAME_DESC
            R.id.sortByPopulationMi -> SortBy.POPULATION
            R.id.sortByPopulationDescendingMi -> SortBy.POPULATION_DESC
            else -> null
        }
        return if (sortBy == null) {
            super.onOptionsItemSelected(item)
        } else {
            countryRecyclerAdapter.sort(sortBy!!)
            true
        }
    }
}