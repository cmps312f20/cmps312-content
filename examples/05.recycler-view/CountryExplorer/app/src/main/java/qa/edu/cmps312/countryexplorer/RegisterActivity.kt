package qa.edu.cmps312.countryexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_country_list.*
import kotlinx.android.synthetic.main.activity_country_list.continentAcTv
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        CountryRepository.loadCountries(this)

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, CountryRepository.countryNames
        )
        countriesSp.adapter = adapter
    }
}