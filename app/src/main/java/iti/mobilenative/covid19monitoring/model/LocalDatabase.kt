package iti.mobilenative.covid19monitoring.model

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import iti.mobilenative.covid19monitoring.pojo.Country

@Database(entities = [Country::class],version = 1)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun getCountryDao() : CountryDao?

    companion object {
        private const val DB_NAME = "country_db"
        private var INSTANCE: LocalDatabase? = null
        fun getInstance(application: Application?): LocalDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    application!!,
                    LocalDatabase::class.java, DB_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}