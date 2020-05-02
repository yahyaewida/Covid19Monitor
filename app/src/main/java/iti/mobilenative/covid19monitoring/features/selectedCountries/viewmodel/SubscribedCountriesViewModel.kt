package iti.mobilenative.covid19monitoring.features.selectedCountries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject

class SubscribedCountriesViewModel @Inject constructor(val countriesRepository: CountriesRepository)  : ViewModel(){


    fun  getSpecificCountriesData(countries: String): LiveData<List<Country>> {
        return countriesRepository.getSpecificCountriesData(countries)
    }

}