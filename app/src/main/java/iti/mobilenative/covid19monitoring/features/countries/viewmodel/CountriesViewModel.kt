package iti.mobilenative.covid19monitoring.features.countries.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.features.countries.repository.CountriesRepository
import iti.mobilenative.covid19monitoring.pojo.Country

class CountriesViewModel : ViewModel() {

    var repo : CountriesRepository
    init {
        repo = CountriesRepository()
    }

    fun  getCasesByAllCountries(): Single<List<Country>>{
        return repo.getCasesByAllCountries()
    }

}