package iti.mobilenative.covid19monitoring.services.network.retrofit

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.services.network.contract.Remotable

class RetrofitHandler : Remotable {

    val service: CovidApiService
    init {
        service = RetrofitFactory.create()
    }

    override fun  getCasesByAllCountries(): Single<List<Country>> {
        return service.getCasesByAllCountries()
    }
}