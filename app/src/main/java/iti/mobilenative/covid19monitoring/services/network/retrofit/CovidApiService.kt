package iti.mobilenative.covid19monitoring.services.network.retrofit

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.pojo.Statistics
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApiService {

    @GET("countries")
    suspend fun getCasesByAllCountries() : List<Country>

    @GET("countries/{countries}")
    suspend fun getCasesBySelectedCountries(@Path("countries") params: String) : List<Country>

    @GET("countries/{country}")
    suspend fun getCasesBySelectedCountry(@Path("country") param: String) : Country

    @GET("all")
    suspend fun getStatistics() : Statistics

}
