package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.repository.StatisticsRepository
import iti.mobilenative.covid19monitoring.pojo.Statistics

class StatisticsViewModel :ViewModel(){

    var repo : StatisticsRepository
    init {
        repo = StatisticsRepository()
    }
    /*fun getStatistics(): Single<Statistics> {
        return repo.getStatistics()
    }*/
}