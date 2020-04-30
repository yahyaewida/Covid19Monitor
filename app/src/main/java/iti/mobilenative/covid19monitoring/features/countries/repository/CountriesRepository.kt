package iti.mobilenative.covid19monitoring.features.countries.repository

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.services.network.contract.Remotable
import iti.mobilenative.covid19monitoring.services.network.retrofit.CovidApiService
import iti.mobilenative.covid19monitoring.services.network.retrofit.RetrofitHandler
import javax.inject.Inject


class CountriesRepository @Inject constructor(val retrofit : CovidApiService) {

    
    fun getCasesByAllCountries(): Single<List<Country>>{
        return retrofit.getCasesByAllCountries()
    }
}