package iti.mobilenative.covid19monitoring.features.subscribed_countries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject

class SubscribedCountriesViewModel @Inject constructor(val countriesRepository: CountriesRepository)  : ViewModel(){


    fun  getSpecificCountriesData(countries: String): LiveData<List<Country>> {
        return countriesRepository.getSpecificCountriesData(countries)
    }
    fun getAllSubscribedCountriesObservable(): LiveData<List<Country>>{
        return  countriesRepository.getAllSubscribedCountriesObservable()
    }


    fun subscribeToCountry(countryName :String){
        countriesRepository.subscribeToCountry(countryName)
    }

    fun unsubscribeFromCountry(countryName :String){
        countriesRepository.unsubscribeFromCountry(countryName)
    }


}