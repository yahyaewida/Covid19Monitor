package iti.mobilenative.covid19monitoring.services.network.contract

import io.reactivex.Single
import iti.mobilenative.covid19monitoring.pojo.Country

interface Remotable {
        fun  getCasesByAllCountries() : Single<List<Country>>
}