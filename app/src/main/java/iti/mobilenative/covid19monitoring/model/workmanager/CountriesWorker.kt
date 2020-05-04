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
    val appContext: Context,
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

        subscribedCountries.get(0).active = 100200 //21
        subscribedCountries.get(0).deaths = 28900 // 16
        subscribedCountries.get(0).recovered = 81700 // 46


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

        subscribedCountriesFromApi.forEachIndexed { index, country ->
            if(country.cases != subscribedCountries.get(index).cases || country.active != subscribedCountries.get(index).active  || country.recovered != subscribedCountries.get(index).recovered ){
                Log.i("mainactivity","Data changed  For country  :"+ country.country)

                //change condition values

                country.active =  if ( country.active - subscribedCountries[index].active  > 0 )   country.active - subscribedCountries[index].active else 0
                country.recovered = if(country.recovered - subscribedCountries[index].recovered  > 0) country.recovered - subscribedCountries[index].recovered  else 0
                country.deaths =  if( country.deaths - subscribedCountries[index].deaths  >0 ) country.deaths  - subscribedCountries[index].deaths else 0

                resultList.add(country)
            }
        }
        return resultList
    }
    private fun sendNotification(notifiedCountryList : List<Country>){
        Log.i("mainactivity","Notified Country List :"+ notifiedCountryList)

        var content = ""

        notifiedCountryList.forEach {
            content += it.country +" , "
        }

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


    class Factory @Inject constructor(
        val countriesRepository: CountriesRepository

        ): ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): CoroutineWorker {
            return CountriesWorker(countriesRepository, appContext, params)
        }
    }
}

