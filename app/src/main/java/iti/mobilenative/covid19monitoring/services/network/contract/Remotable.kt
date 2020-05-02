package iti.mobilenative.covid19monitoring.services.network.contract

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.pojo.Statistics

interface Remotable {
        fun getCasesByAllCountries() : Single<List<Country>>
        fun getCasesBySelectedCountries(countries: String) : Single<List<Country>>
        fun getCasesBySelectedCountry(country: String) : Single<Country>
        fun getStatistics() : Single<Statistics>
        fun getHistoricalStatistics(lastDays: String) : Single<HistoricalStatistics>
}