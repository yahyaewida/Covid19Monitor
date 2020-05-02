package iti.mobilenative.covid19monitoring.features.selectedCountries.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.features.selectedCountries.repository.SelectedCountriesRepository
import iti.mobilenative.covid19monitoring.pojo.Country

class SelectedCountriesViewModel : ViewModel(){
    var repo : SelectedCountriesRepository
    init {
        repo = SelectedCountriesRepository()
    }

    /*fun  getCasesBySelectedCountries(countries: String): Single<List<Country>> {
        return repo.getCasesBySelectedCountries(countries)
    }

    fun  getCasesBySelectedCountry(country: String): Single<Country> {
        return repo.getCasesBySelectedCountry(country)
    }*/
}