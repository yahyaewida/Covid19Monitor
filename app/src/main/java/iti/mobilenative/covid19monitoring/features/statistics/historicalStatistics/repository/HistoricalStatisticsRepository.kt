package iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.repository

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.services.network.retrofit.RetrofitHandler

class HistoricalStatisticsRepository {
    private val retrofitHandler = RetrofitHandler()

    fun getHistoricalStatistics(lastDays: String): Single<HistoricalStatistics> {
        return retrofitHandler.getHistoricalStatistics(lastDays)
    }
}