package iti.mobilenative.covid19monitoring.model.local_database

import androidx.lifecycle.LiveData
import androidx.room.*

import iti.mobilenative.covid19monitoring.model.pojo.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM countryTable")
    fun getAllCountries() : LiveData<List<Country>>

    @Query("SELECT * FROM countryTable where isSubscribed = 1")
    fun getAllSubscribedCountriesObservable() : LiveData<List<Country>>

    @Query("SELECT * FROM countryTable where isSubscribed = 1")
    fun getAllSubscribedCountries() : List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCountries(countriesList: List<Country>)

    @Query("update countryTable set isSubscribed = 1 where country = :countryName")
    suspend fun subscribeToCountry(countryName :String)

    @Query("DELETE FROM countryTable WHERE country = :countryName")
    suspend fun deleteCountry(countryName :String)

    @Query("DELETE FROM countryTable")
    suspend fun deleteAllCountries()

    @Update
    suspend fun updateCountry(country : Country)

}