package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(val countriesRepository: CountriesRepository) :ViewModel(){


    fun getStatistics(): LiveData<Statistics> {
        return countriesRepository.getStatisticsFromApi()
    }
}