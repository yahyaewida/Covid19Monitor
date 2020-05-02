package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.repository

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Statistics
import iti.mobilenative.covid19monitoring.services.network.retrofit.RetrofitHandler

class StatisticsRepository {
    private val retrofitHandler = RetrofitHandler()

    /*fun getStatistics(): Single<Statistics> {
        return retrofitHandler.getStatistics()
    }*/
}