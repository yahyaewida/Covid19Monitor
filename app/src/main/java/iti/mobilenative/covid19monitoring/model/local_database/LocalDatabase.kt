package iti.mobilenative.covid19monitoring.model.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import iti.mobilenative.covid19monitoring.model.pojo.Country

@Database(entities = [Country::class],version = 1,exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun getCountryDao() : CountryDao
}