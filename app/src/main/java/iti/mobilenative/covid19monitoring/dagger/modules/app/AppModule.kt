package iti.mobilenative.covid19monitoring.dagger.modules.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import iti.mobilenative.covid19monitoring.model.local_database.SharedPreferencesHandler
import iti.mobilenative.covid19monitoring.utils.SHARED_PREFERENCES_FILE_NAME

@Module
class AppModule(private val app: Application) {
    @ApplicationScope
    @Provides
    fun getApplication() = app

    @ApplicationScope
    @Provides
    fun getSharedPreferences() : SharedPreferences = app.getSharedPreferences(
            SHARED_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
    )
    @ApplicationScope
    @Provides
    fun getSharedPreferencesHandler() : SharedPreferencesHandler{
        return SharedPreferencesHandler(getSharedPreferences())
    }
}