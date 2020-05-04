package iti.mobilenative.covid19monitoring.model.remote_api

import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidApiService {

    @GET("countries?sort=cases")
    suspend fun getCasesByAllCountries() : List<Country>

    @GET("countries/{countries}")
    suspend fun getCasesBySelectedCountries(@Path("countries") params: String) : List<Country>

   /* @GET("countries/{country}")
    suspend fun getCasesBySelectedCountry(@Path("country") param: String) : List<Country>
*/



    @GET("all")
    suspend fun getStatistics() : Statistics

    @GET("historical/all")
    suspend fun getHistoricalStatistics(@Query("lastdays") lastDays: String) : HistoricalStatistics

}
