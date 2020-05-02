package iti.mobilenative.covid19monitoring.features.countries.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import io.reactivex.Single
import iti.mobilenative.covid19monitoring.model.CountryDao
import iti.mobilenative.covid19monitoring.model.SubscriptionsDao
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.services.network.contract.Remotable
import iti.mobilenative.covid19monitoring.services.network.retrofit.CovidApiService
import iti.mobilenative.covid19monitoring.services.network.retrofit.RetrofitHandler
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class CountriesRepository @Inject constructor(val retrofit : CovidApiService) {
    @Inject lateinit var countryDao : CountryDao

    @Inject lateinit var subscriptionsDao: SubscriptionsDao

    fun getAllCountriesLocalData(): LiveData<List<Country>>{
        return liveData(Dispatchers.IO) {
            val liveDataValue = countryDao.getAllCountries()
            emit(liveDataValue)
        }
    }
    fun getAllCountriesFromApi(): LiveData<List<Country>>{
        return liveData(Dispatchers.IO) {
            val liveDataValue = retrofit.getCasesByAllCountries()
            emit(liveDataValue)
            Log.i("mainactivity", "inside Second call function : "+Thread.currentThread().name)
        }
    }



    fun getSubscribedCountries() : List<Country>{

        return subscriptionsDao.getAllSubscribedCountries()


    }



}