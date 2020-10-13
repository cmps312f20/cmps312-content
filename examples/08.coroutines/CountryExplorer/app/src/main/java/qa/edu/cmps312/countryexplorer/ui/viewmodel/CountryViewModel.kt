package qa.edu.cmps312.countryexplorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import json.country.CountryRepository

class CountryViewModel : ViewModel() {
    val countries = liveData {
        emit(CountryRepository.countryService.getCountries())
    }
}