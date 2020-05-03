package iti.mobilenative.covid19monitoring.model.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject


class CountriesWorker @Inject constructor(
    val countriesRepository: CountriesRepository,
    appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        //THIS IS OUR AMAZING WORK
        val dataToBeSynced = countriesRepository.getAllCountriesFromApi()
        Log.i("mainactivity","inside Work Manager")
        //val result = networkService.sync(dataToBeSynced)

        // Return the output
        return Result.success()

    }
    class Factory @Inject constructor(
        val countriesRepository: CountriesRepository

        ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return CountriesWorker(countriesRepository, appContext, params)
        }
    }
}


interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): CoroutineWorker
}