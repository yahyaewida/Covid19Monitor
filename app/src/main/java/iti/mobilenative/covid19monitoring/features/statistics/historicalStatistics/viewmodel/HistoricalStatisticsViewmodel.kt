package iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.repository.HistoricalStatisticsRepository
import iti.mobilenative.covid19monitoring.pojo.HistoricalStatistics

class HistoricalStatisticsViewmodel : ViewModel(){

    var repo : HistoricalStatisticsRepository
    init {
        repo = HistoricalStatisticsRepository()
    }
    fun getHistoricalStatistics(lastDays: String): Single<HistoricalStatistics> {
        return repo.getHistoricalStatistics(lastDays)
    }
}