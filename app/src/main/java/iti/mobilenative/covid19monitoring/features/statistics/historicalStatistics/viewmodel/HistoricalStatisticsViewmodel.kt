package iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import iti.mobilenative.covid19monitoring.model.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject

class HistoricalStatisticsViewmodel @Inject constructor(val countriesRepository: CountriesRepository) : ViewModel(){


    fun getHistoricalStatistics(lastDays: String): LiveData<HistoricalStatistics> {
        return countriesRepository.getHistoricalStatisticsFromApi(lastDays)
    }
}