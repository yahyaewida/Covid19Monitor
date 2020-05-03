package iti.mobilenative.covid19monitoring.utils

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import iti.mobilenative.covid19monitoring.dagger.components.ApplicationComponent
import iti.mobilenative.covid19monitoring.dagger.components.DaggerApplicationComponent
import iti.mobilenative.covid19monitoring.dagger.modules.app.AppModule
import iti.mobilenative.covid19monitoring.model.workmanager.WorkerManagerFactory
import javax.inject.Inject

class App : Application() {
     /*companion object{
           lateinit var database: LocalDatabase
    }
*/
     @Inject
     lateinit var workerManagerFactory: WorkerManagerFactory



    lateinit var appComponent :ApplicationComponent
    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder().appModule(
            AppModule(
                this
            )
        ).build()
        appComponent.inject(this)


        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerManagerFactory)
                .build()
        )
    }
}