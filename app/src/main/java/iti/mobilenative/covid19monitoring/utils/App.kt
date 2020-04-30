package iti.mobilenative.covid19monitoring.utils

import android.app.Application
import iti.mobilenative.covid19monitoring.dagger.components.ApplicationComponent
import iti.mobilenative.covid19monitoring.dagger.components.DaggerApplicationComponent
import iti.mobilenative.covid19monitoring.dagger.modules.app.AppModule

class App : Application() {
     /*companion object{
           lateinit var database: LocalDatabase
    }
*/
    lateinit var appComponent :ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder().appModule(
            AppModule(
                this
            )
        ).build()
        appComponent.inject(this)
//        database = LocalDatabase.getInstance(this)!!
    }
}