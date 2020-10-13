package qa.edu.cmps312.countryexplorer.ui

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_register.*
import qa.edu.cmps312.countryexplorer.R
import qa.edu.cmps312.countryexplorer.ui.common.toast

// You can pass the layoutId to the constructor
/*
Must add this dependency to app build.gradle
    def activity_version = "1.1.0"
    implementation "androidx.activity:activity-ktx:$activity_version"
*/
class RegisterActivity : AppCompatActivity(R.layout.activity_register) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)

        // Read data sent by the caller
        val id = intent.getIntExtra("id", 0)
        val name = intent.getStringExtra("name")

        toast("Received id: $id & name: $name", Toast.LENGTH_LONG)

        initUniversitiesSpinner()
        initCountriesAutoCompleteTv()
    }

    private fun initUniversitiesSpinner() {
        val universities = listOf("Qatar University",
            "Carnegie Mellon University in Qatar",
            "Texas A&M University at Qatar",
            "Hamad Bin Khalifa University (HBKU)")

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, universities
        )
        universitiesSp.adapter = adapter
    }

    private fun initCountriesAutoCompleteTv() {
        CountryRepository.initCountries(this)
        // It tells the AutoCompleteTextView what layout to use for individual suggestions
        // android.R.layout.simple_list_item_1 => it a built-in layout. It has 1 TextView
        // List of all android built-in layout available @
        // https://github.com/aosp-mirror/platform_frameworks_base/tree/master/core/res/res/layout
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, CountryRepository.countryNames
        )
        // You can set the completionThreshold to 1 in the layout to start
        // searching once the user enters 1 character
        continentAcTv.setAdapter(adapter)

        // When the user clicks a continent
        continentAcTv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, position, _ ->
            val selectedCountry = adapterView?.getItemAtPosition(position) as String
            toast("You have select $selectedCountry")
        }
    }
}