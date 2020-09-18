package qa.edu.cmps312.countryexplorer

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_country_list.*
import qa.edu.cmps312.countryexplorer.adapter.CountryAdapter
import qa.edu.cmps312.countryexplorer.adapter.SortBy

class CountryListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        initRecyclerView()

        // Replace the built legacy action bar with our toolbar
        // In style.xml you should use a theme with NoActionBar
        setSupportActionBar(toolbar as Toolbar)

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
        continentAcTv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            val selectedContinent = adapterView?.getItemAtPosition(position) as String
            toast("You have select $selectedContinent")
            // Set the filter on the recyclerView adapter
            val countryAdapter = countriesRv.adapter as CountryAdapter
            countryAdapter.filter(selectedContinent)
        }
    }

    private fun initRecyclerView() {
        val countries = CountryRepository.initCountries(this)
        //println(countries)

        countriesRv.apply {
            adapter = CountryAdapter(countries)
            layoutManager = LinearLayoutManager(this@CountryListActivity)
        }
    }

    // Load the sort options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sort_countries, menu)
        return true
    }

    // Handle sort menu
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        val countryRecyclerAdapter = countriesRv.adapter as CountryAdapter
        val sortBy = when (menuItem.itemId) {
            R.id.sortByNameMi -> SortBy.NAME
            R.id.sortByNameDescendingMi -> SortBy.NAME_DESC
            R.id.sortByPopulationMi -> SortBy.POPULATION
            R.id.sortByPopulationDescendingMi -> SortBy.POPULATION_DESC
            else -> null
        }
        return if (sortBy == null) {
            super.onOptionsItemSelected(menuItem)
        } else {
            countryRecyclerAdapter.sort(sortBy!!)
            true
        }
    }

    // For this to work you need to add this to the Manifest
    /*
        <activity android:name=".CountryListActivity"
            android:configChanges="orientation|screenSize" />
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
       countriesRv.layoutManager = when(newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                GridLayoutManager(this, 2)
            }
            else -> {
                LinearLayoutManager(this)
            }
        }
    }
}