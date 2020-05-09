package iti.mobilenative.covid19monitoring.features.countries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject

class CountriesViewModel @Inject constructor(val repository: CountriesRepository): ViewModel() {

    fun  getAllCountries(): LiveData<List<Country>>{
        return repository.getAllCountriesLocalData()
    }

    fun getAllSubscribedCountries(): List<Country>{
        return repository.getAllSubscribedCountries()
    }

    fun getStatistics(): LiveData<Statistics> {
        return repository.getStatisticsFromApi()
    }

    fun subscribeToCountry(countryName :String){
       repository.subscribeToCountry(countryName)
    }

    fun unsubscribeFromCountry(countryName :String){
        repository.unsubscribeFromCountry(countryName)
    }


}