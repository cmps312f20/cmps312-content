package qa.edu.cmps312.countryexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_country_cards.*
import kotlinx.android.synthetic.main.list_item_country.view.*

class CountryCardsActivity : AppCompatActivity() {
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_cards)

        CountryRepository.initCountries(this)
        displayCountry(currentIndex)

        nextBtn.setOnClickListener { onButtonClicked( it ) }
        prevBtn.setOnClickListener { onButtonClicked( it ) }

        bottomNv.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.countriesListMi -> {
                    val intent = Intent(this, CountryListActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.registerMi -> {
                    //Passing the context object (this) into the intent allows the app to know what activity it’s leaving.
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)

                    // Pass student ID and student name with Intent so it can be used
                   // by RegisterActivity when it’s started
                    intent.putExtra("id", 235789)
                    intent.putExtra("name", "Peter Pan")
                    startActivity(intent)
                    true
                }
                else -> { super.onOptionsItemSelected(it) }
            }
        }
    }

    private fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.nextBtn -> ++currentIndex
            R.id.prevBtn -> --currentIndex
            else -> return
        }
        //If current index becomes == count then it will be assigned 0
        currentIndex = (currentIndex + CountryRepository.count) % CountryRepository.count
        displayCountry(currentIndex)
    }

    private fun displayCurrentIndex(index: Int) {
          currentIndexTv.text = "${currentIndex + 1} of ${CountryRepository.count}"
    }

    private fun displayCountry(index: Int) {
        val country = CountryRepository.countries[index]
        countryItemView.apply {
            nameTv.text = country.name
            capitalTv.text = country.capital
            populationTv.text = country.population.toString()

            // context is the root activity in which flagIv component is displayed
            val image = resources.getIdentifier(
                "flag_${country.code.toLowerCase()}",
                "drawable",
                packageName
            )
            flagIv.setImageResource(image)
        }

        displayCurrentIndex(currentIndex)
    }
}