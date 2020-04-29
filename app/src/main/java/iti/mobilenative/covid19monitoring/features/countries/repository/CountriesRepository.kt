package iti.mobilenative.covid19monitoring.features.countries.repository

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.services.network.contract.Remotable
import iti.mobilenative.covid19monitoring.services.network.retrofit.RetrofitHandler

class CountriesRepository() {
    private val retrofitHandler = RetrofitHandler()

    fun getCasesByAllCountries(): Single<List<Country>>{
        return retrofitHandler.getCasesByAllCountries()
    }
}