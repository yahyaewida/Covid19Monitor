package iti.mobilenative.covid19monitoring.model

import androidx.lifecycle.LiveData
import androidx.room.*
import iti.mobilenative.covid19monitoring.pojo.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM countryTable")
    suspend fun getAllCountries() : List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewCountry(country: Country)

    @Query("DELETE FROM countryTable WHERE country = :countryName")
    suspend fun deleteCountry(countryName :String)

    @Query("DELETE FROM countryTable")
    suspend fun deleteAllCountries()

    @Update
    suspend fun updateCountry(country : Country)
}