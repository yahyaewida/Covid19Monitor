package iti.mobilenative.covid19monitoring.services.network.retrofit

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.pojo.Statistics
import iti.mobilenative.covid19monitoring.services.network.contract.Remotable

class RetrofitHandler : Remotable {

    val service: CovidApiService
    init {
        service = RetrofitFactory.create()
    }

    override fun  getCasesByAllCountries(): Single<List<Country>> {
        return service.getCasesByAllCountries()
    }

    override fun getCasesBySelectedCountries(countries: String): Single<List<Country>> {
        return service.getCasesBySelectedCountries(countries)
    }

    override fun getCasesBySelectedCountry(country: String): Single<Country> {
        return service.getCasesBySelectedCountry(country)
    }

    override fun getStatistics(): Single<Statistics> {
        return service.getStatistics()
    }

    override fun getHistoricalStatistics(lastDays: String): Single<HistoricalStatistics> {
        return service.getHistoricalStatistics(lastDays)
    }
}