package iti.mobilenative.covid19monitoring.model

import androidx.lifecycle.LiveData
import androidx.room.*
import iti.mobilenative.covid19monitoring.pojo.Country

@Dao
interface CountryDao {

    @Query("SELECT * FROM countryTable")
    fun getAllCountries() : List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewCountry(country: Country)

    @Query("DELETE FROM countryTable WHERE country = :countryName")
    fun deleteCountry(countryName :String)

    @Query("DELETE FROM countryTable")
    fun deleteAllCountries()

    @Update
    fun updateCountry(country : Country)
}