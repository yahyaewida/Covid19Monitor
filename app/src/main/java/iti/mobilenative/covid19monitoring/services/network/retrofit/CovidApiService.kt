package iti.mobilenative.covid19monitoring.services.network.retrofit

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import retrofit2.http.GET

interface CovidApiService {

    @GET("countries")
    fun getCasesByAllCountries() : Single<List<Country>>
}
