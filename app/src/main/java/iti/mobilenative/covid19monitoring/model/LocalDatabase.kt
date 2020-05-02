package iti.mobilenative.covid19monitoring.model

import androidx.room.Database
import androidx.room.RoomDatabase
import iti.mobilenative.covid19monitoring.pojo.Country

@Database(entities = [Country::class,Subscriptions::class],version = 1,exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun getCountryDao() : CountryDao
    abstract fun getSubscriptionsDao() : SubscriptionsDao
}