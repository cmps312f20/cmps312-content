package qa.edu.cmps312.countryexplorer.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.android.material.snackbar.Snackbar
import json.country.Country
import kotlinx.android.synthetic.main.activity_country_list.*
import qa.edu.cmps312.countryexplorer.R
import qa.edu.cmps312.countryexplorer.ui.adapter.CountryAdapter
import qa.edu.cmps312.countryexplorer.ui.adapter.SortBy
import qa.edu.cmps312.countryexplorer.ui.common.toast
import qa.edu.cmps312.countryexplorer.ui.viewmodel.CountryViewModel


class CountryListActivity : AppCompatActivity() {
    private val countryViewModel by viewModels<CountryViewModel>()
    lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        // Set the RecyclerView adapter and Layout Manager
        initRecyclerView()
        setRecyclerSwipeListener()

        // 2.1 Grab the searchView from the toolbar
        val searchView = topToolbar.findViewById<SearchView>(R.id.searchMi)
        searchView.setBackgroundColor(Color.WHITE)
        // 2.2. Handle search as the user types the search text
        searchView.setOnQueryTextListener(searchHandler)

        // Handle toolbar menu item clicked
        topToolbar.setOnMenuItemClickListener { onToolbarMenuItemClicked(it) }

        // Configure image loader
        val imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                add(SvgDecoder(this@CountryListActivity))
            }
            .build()

        Coil.setImageLoader(imageLoader)

        //Change color of the searchView EditText
        //val searchViewText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        //searchViewText.setTextColor(Color.WHITE)
    }

    private fun initRecyclerView() {
        //val countries = CountryRepository.initCountries(this).toMutableList()
        /* Pass a reference to onCountryClicked function to the adapter
           so that the adapter can call it when a country list item is clicked
        */
        countryAdapter = CountryAdapter(this, ::onCountryClicked, ::onCountryDeleted)
        //countryAdapter.countries = countries
        countryViewModel.countries.observe(this) {
            countryAdapter.countries = it as MutableList
        }
        countriesRv.apply {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(this@CountryListActivity)
        }
    }

    private fun onCountryClicked(country: Country) {
        toast("Clicked: ${country.name}", Toast.LENGTH_LONG)
    }

    private fun onCountryDeleted(country: Country) {
        countryViewModel.deleteCountry(country)

        Snackbar.make(countriesRv, "${country.name} removed", Snackbar.LENGTH_LONG).setAction(
            "UNDO"
        ) {
            countryViewModel.addCountry(country)
        }.show()
    }

    //region Handle search
    private val searchHandler = object : SearchView.OnQueryTextListener {
        // Ignore and do not perform any special behavior here
        override fun onQueryTextSubmit(query: String?) = false

        // As the user types filter the list based on the search text
        override fun onQueryTextChange(searchText: String): Boolean {
            Log.i("CountryListActivity", "Query: $searchText")
            countryAdapter.filter(searchText)
            return true
        }
    }
    //endregion

    //region Handle Toolbar Menu item clicked
    private fun onToolbarMenuItemClicked(menuItem: MenuItem): Boolean {
        val sortBy = when (menuItem.itemId) {
            R.id.sortByNameMi -> SortBy.NAME
            R.id.sortByNameDescendingMi -> SortBy.NAME_DESC
            R.id.sortByPopulationMi -> SortBy.POPULATION
            R.id.sortByPopulationDescendingMi -> SortBy.POPULATION_DESC
            else -> null
        }
        countryAdapter.sort(sortBy!!)
        return true
    }
    //endregion

    //region Handle swipe to delete
    private fun setRecyclerSwipeListener() {
        /*
        1. Create ItemTouchHelper.SimpleCallback and tell it what events to listen for.
        It takes two parameters: One for drag directions and one for swipe directions.
        We’re only interested in swipe. Pass 0 to inform the callback not to respond to drag events.
        */
        val swipeHandler = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            // Ignore and do not perform any special behavior here
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            // 2. onSwiped ask the RecyclerView adapter to delete the swiped item
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDirection: Int) {
                val position = viewHolder.adapterPosition
                val country = countryAdapter.countries[position]
                onCountryDeleted(country)
            }
        }

        /* 3. Initialize ItemTouchHelper with the swipeHandler you defined,
              and then attach it to the RecyclerView.
         */
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(countriesRv)
    }
    //endregion

    //region Change the layout manager to a Gridlayout when the screen orientation is Landscape
    // Change the layout manager to a Gridlayout when the screen orientation is Landscape
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
    //endregion
}