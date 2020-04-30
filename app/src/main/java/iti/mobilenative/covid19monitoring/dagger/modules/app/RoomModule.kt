package iti.mobilenative.covid19monitoring.dagger.modules.app

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import iti.mobilenative.covid19monitoring.model.CountryDao
import iti.mobilenative.covid19monitoring.model.LocalDatabase
import iti.mobilenative.covid19monitoring.utils.DB_NAME

@Module(includes = [AppModule::class])
class RoomModule {

    @ApplicationScope
    @Provides
    fun getRoom(application: Application):LocalDatabase{
        return Room.databaseBuilder(
            application,
            LocalDatabase::class.java, DB_NAME
        )
            .allowMainThreadQueries()
            .build()
    }
    @ApplicationScope
    @Provides
    fun getCountryDao(database: LocalDatabase) : CountryDao{
        return database.getCountryDao()
    }
}