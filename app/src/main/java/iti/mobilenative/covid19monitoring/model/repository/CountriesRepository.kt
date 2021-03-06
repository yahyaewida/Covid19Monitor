package iti.mobilenative.covid19monitoring.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import iti.mobilenative.covid19monitoring.model.local_database.CountryDao
import iti.mobilenative.covid19monitoring.model.local_database.SharedPreferencesHandler
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import iti.mobilenative.covid19monitoring.model.remote_api.CovidApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class CountriesRepository @Inject constructor(val retrofit : CovidApiService) {
    @Inject lateinit var countryDao : CountryDao
    @Inject lateinit var sharedPreferencesHandler: SharedPreferencesHandler

    //API functions

    suspend fun getAllCountriesFromApi() : List<Country>{
        return retrofit.getCasesByAllCountries()
    }

    fun getSpecificCountriesData(countriesSeparatedByCommas  : String): LiveData<List<Country>>{
        return liveData(Dispatchers.IO) {
            val liveDataValue = retrofit.getCasesBySelectedCountries(countriesSeparatedByCommas)
            emit(liveDataValue)
        }
    }

    suspend fun getStatisticsFromApi() : Statistics{
            return retrofit.getStatistics()
    }

    fun getHistoricalStatisticsFromApi(forLastNumberOfDays : String): LiveData<HistoricalStatistics>{
        return liveData(Dispatchers.IO) {
            emit(retrofit.getHistoricalStatistics(forLastNumberOfDays))
        }
    }

    //LocalDB functions

    fun getAllCountriesLocalData(): LiveData<List<Country>>{
        return liveData(Dispatchers.IO) {
            val liveDataValue = countryDao.getAllCountries()
            emitSource(liveDataValue)
        }
    }

    fun getAllSubscribedCountriesObservable(): LiveData<List<Country>>{
        return liveData(Dispatchers.IO) {
            emitSource( countryDao.getAllSubscribedCountriesObservable())
        }
    }

    fun getAllSubscribedCountries() :List<Country> {
        return countryDao.getAllSubscribedCountries()
    }


    fun insertAllCountries(countriesList: List<Country>){
        CoroutineScope(Dispatchers.IO).launch {
            countryDao.insertAllCountries(countriesList)
        }
    }

    fun subscribeToCountry(countryName :String){
        CoroutineScope(Dispatchers.IO).launch {
            countryDao.subscribeToCountry(countryName)
        }
    }

    fun unsubscribeFromCountry(countryName :String){
        CoroutineScope(Dispatchers.IO).launch {
            countryDao.unsubscribeFromCountry(countryName)
        }
    }

    // shared Preferences
    fun getStatisticsFromSharedPreferences() : LiveData<Statistics>{
        return liveData(Dispatchers.IO) {
            emitSource(sharedPreferencesHandler.getDataFromSharedPreferences())
        }
    }

    fun writeStatisticsToSharedPreferences(statistics: Statistics){
        CoroutineScope(Dispatchers.IO).launch {
            sharedPreferencesHandler.writeInSharedPreferences(statistics)
        }

    }

    fun writeSettingsInSharedPreferences(numberOfHours : Long){
       sharedPreferencesHandler.writeSettingsInSharedPreferences(numberOfHours)
    }
    fun readSettingsFromSharedPreferences() : Long{
        return sharedPreferencesHandler.readSettingsFromSharedPreferences()
    }

    //Data synchronization

     fun syncData() = CoroutineScope(Dispatchers.IO).launch{
        val apiList = getAllCountriesFromApi()
        val subscribedCountries = getAllSubscribedCountries()
        val worldStatistics = getStatisticsFromApi()
        writeStatisticsToSharedPreferences(worldStatistics)

        if(subscribedCountries.count() > 0){
            updateApiCountriesWithSubscribedCountries(apiList,subscribedCountries)
        }

        insertAllCountries(apiList)
    }

    private fun  updateApiCountriesWithSubscribedCountries(apiList : List<Country>, subscribedCountries: List<Country>){
        apiList.map {apiCountry ->
            subscribedCountries.forEach {subscribedCountry ->
                if(apiCountry.country == subscribedCountry.country){
                    apiCountry.isSubscribed = true
                }
            }
        }
    }



}