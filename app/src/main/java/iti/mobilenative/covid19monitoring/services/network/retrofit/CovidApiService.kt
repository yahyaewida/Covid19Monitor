package iti.mobilenative.covid19monitoring.services.network.retrofit

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.pojo.Statistics
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApiService {

    @GET("countries")
    fun getCasesByAllCountries() : Single<List<Country>>

    @GET("countries/{countries}")
    fun getCasesBySelectedCountries(@Path("countries") params: String) : Single<List<Country>>

    @GET("countries/{country}")
    fun getCasesBySelectedCountry(@Path("country") param: String) : Single<Country>

    @GET("all")
    fun getStatistics() : Single<Statistics>

}
