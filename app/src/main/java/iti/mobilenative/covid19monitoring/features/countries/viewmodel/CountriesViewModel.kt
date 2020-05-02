package iti.mobilenative.covid19monitoring.features.countries.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.features.countries.repository.CountriesRepository
import iti.mobilenative.covid19monitoring.pojo.Country
import javax.inject.Inject

class CountriesViewModel @Inject constructor(val repository: CountriesRepository): ViewModel() {

    /*fun  getCasesByAllCountries(): Single<List<Country>>{
        return repository.getCasesByAllCountries()
    }
*/
}