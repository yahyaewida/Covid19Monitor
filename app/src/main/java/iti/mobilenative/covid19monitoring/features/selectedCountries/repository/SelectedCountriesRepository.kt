package iti.mobilenative.covid19monitoring.features.selectedCountries.repository

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.services.network.retrofit.RetrofitHandler

class SelectedCountriesRepository {
    private val retrofitHandler = RetrofitHandler()

    /*fun getCasesBySelectedCountries(countries: String): Single<List<Country>> {
        return retrofitHandler.getCasesBySelectedCountries(countries)
    }

    fun getCasesBySelectedCountry(country: String): Single<Country> {
        return retrofitHandler.getCasesBySelectedCountry(country)
    }*/
}