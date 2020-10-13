package qa.edu.cmps312.countryexplorer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import json.country.Country
import qa.edu.cmps312.countryexplorer.model.CountryRepository.countryService

class CountryViewModel : ViewModel() {
    val _countries = liveData {
        emit( countryService.getCountries() )
    } as MutableLiveData

    val countries = _countries as LiveData<List<Country>>

    val continents: List<String> get() = _countries.value!!.map { it.region }.distinct().sorted()
    val countryNames: List<String> get() = _countries.value!!.map { it.name }.sorted()

    fun getRegions(continent: String) =
        _countries.value!!.filter { it.region.equals(continent, true) }
            .map { it.subregion }.distinct().sorted()


    fun addCountry(country: Country) {
        _countries.value?.let {
            // This is needed to notify the observers
            _countries.value = it + country
        }
        // In reality we should call the service to add
        // but the selected service does not support this operation
    }

    fun deleteCountry(country: Country) {
        _countries.value?.let {
            _countries.value = it - country
        }
        // In reality we should call the service to delete
        // but the selected service does not support this operation
    }
}