package iti.mobilenative.covid19monitoring.model.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import kotlinx.coroutines.*
import javax.inject.Inject


class CountriesWorker @Inject constructor(
    val countriesRepository: CountriesRepository,
    private val appContext: Context,
    workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        val asyncVariable = async {
            syncData()
        }
        Log.i("mainactivity","inside Work Manager")
        asyncVariable.await()
        Log.i("mainactivity","end Work Manager work")

        Result.success()

    }

    private fun syncData() = CoroutineScope(Dispatchers.IO).launch{
        val apiList = countriesRepository.getAllCountriesFromApi()
        val subscribedCountries = countriesRepository.getAllSubscribedCountries()
        val worldStatistics = countriesRepository.getStatisticsFromApi()
        countriesRepository.writeStatisticsToSharedPreferences(worldStatistics)

        if(subscribedCountries.count() > 0){

            updateApiCountriesWithSubscribedCountries(apiList,subscribedCountries)

            val notifiedCountriesList = getUpdatedSubscribedCountriesData(apiList , subscribedCountries)

            sendNotification(notifiedCountriesList)


            Log.i("mainactivity","Comparison Ended  ")
        }

        countriesRepository.insertAllCountries(apiList)
        Log.i("mainactivity","Rows inserted :  "+apiList.size)

        //Need to get Statistics and historical statistics then update DB and shared preferences

    }

    private fun  updateApiCountriesWithSubscribedCountries(apiList : List<Country>, subscribedCountries: List<Country>){
        apiList.map {apiCountry ->
            subscribedCountries.forEach {subscribedCountry ->
                if(apiCountry.country == subscribedCountry.country){
                    apiCountry.isSubscribed = true
                }
            }
        }
    }

    private fun getUpdatedSubscribedCountriesData(apiList : List<Country>, subscribedCountries: List<Country>) : List<Country>{

        val resultList = ArrayList<Country>()

        val subscribedCountriesFromApi = apiList.filter { it.isSubscribed }

        subscribedCountriesFromApi.forEach {apiCountry ->
            subscribedCountries.forEach {localCountry ->
                if(apiCountry.country == localCountry.country){
                    if(apiCountry.active != localCountry.active  || apiCountry.recovered != localCountry.recovered ){
                        Log.i("mainactivity","Data changed  For country  :"+ apiCountry.country)

                        apiCountry.active =  if ( apiCountry.active - localCountry.active  > 0 )   apiCountry.active - localCountry.active else 0
                        apiCountry.recovered = if(apiCountry.recovered - localCountry.recovered  > 0) apiCountry.recovered - localCountry.recovered  else 0
                        apiCountry.deaths =  if( apiCountry.deaths - localCountry.deaths  >0 ) apiCountry.deaths  - localCountry.deaths else 0

                        resultList.add(apiCountry)
                    }
                }
            }
        }
        return resultList
    }
    private fun sendNotification(notifiedCountryList : List<Country>){
        Log.i("mainactivity","Notified Country List :"+ notifiedCountryList)

        var content = ""

        notifiedCountryList.forEach {

            content += it.country +"-> "
            if(it.active > 0 ){
                content += "active : "+ it.cases
            }
            if(it.recovered > 0){
                content += " , recovered : " +it.recovered
            }
            if(it.deaths > 0){
                content += " , deaths : " +it.deaths
            }

            content += "\n"
        }

        if(content != ""){
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "cases"
            //If on Oreo then notification required a notification channel.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val  channel = NotificationChannel(channelId, "New Cases", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            val notification = NotificationCompat.Builder(applicationContext, channelId)
                .setContentTitle("Covid 19")
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_launcher_background)

            notificationManager.notify(1, notification.build())

        }


    }


    class Factory @Inject constructor(
        val countriesRepository: CountriesRepository

        ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return CountriesWorker(countriesRepository, appContext, params)
        }
    }
}

