package qa.edu.cmps312.countryexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import json.country.CountryRepository
import kotlinx.android.synthetic.main.activity_country_cards.*
import kotlinx.android.synthetic.main.list_item_country.view.*

class CountryInfoActivity : AppCompatActivity() {
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_cards)
        CountryRepository.getCountries(this)
        displayStadium(currentIndex)
        displayCurrentIndex(currentIndex)

        nextBtn.setOnClickListener { onClick( it )  }
        prevBtn.setOnClickListener { onClick( it ) }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.nextBtn -> ++currentIndex
            R.id.prevBtn -> --currentIndex
        }
        //If current index becomes == count then it will be assigned 0
        currentIndex = (currentIndex + CountryRepository.count) % CountryRepository.count
        Log.i("Main Activity", "currentIndex: $currentIndex")
        displayStadium(currentIndex)
        displayCurrentIndex(currentIndex)
    }

    private fun displayCurrentIndex(index: Int) {
          currentIndexTv.text = "${currentIndex + 1} of ${CountryRepository.count}"
    }

    private fun displayStadium(index: Int) {
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
    }
}