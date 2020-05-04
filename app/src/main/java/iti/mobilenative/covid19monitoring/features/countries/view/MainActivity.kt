package iti.mobilenative.covid19monitoring.features.countries.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import io.reactivex.disposables.CompositeDisposable
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.model.local_database.CountryDao
import iti.mobilenative.covid19monitoring.model.local_database.SharedPreferencesHandler
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import iti.mobilenative.covid19monitoring.model.remote_api.CovidApiService
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import iti.mobilenative.covid19monitoring.model.workmanager.CountriesWorker
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import iti.mobilenative.covid19monitoring.utils.WORK_MANAGER_TAG
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var countryDao : CountryDao
    @Inject lateinit var retrofitClient : CovidApiService
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory
    lateinit var countriesViewModel: CountriesViewModel
    @Inject
    lateinit var sharedPreferencesHandler: SharedPreferencesHandler


    @Inject
    lateinit var countriesRepository: CountriesRepository

    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).appComponent.provideActivity(ActivityModule(this)).inject(this)

        countriesViewModel = ViewModelProvider(this, viewModelProvidersFactory)[CountriesViewModel::class.java]



        /*lateinit var list : List<Country>

        countriesRepository.getAllCountriesFromApi().observe(this@MainActivity, Observer {

            list = countriesRepository.getSubscribedCountries()

            it.forEach {value ->
                list.forEach {
                    if (value.country == it.country && value.updated != it.updated){
                        Log.i("mainactivity","country  is :"+ value)
                    }
                }
            }
        })
*/

        setupWorkManagerTest()

        /*countriesRepository.getAllCountriesLocalData().observe(this, Observer {
            it.filter {
                it.isSubscribed
            }
                .forEach {
                    Log.i("mainactivity","Subscribed to  :"+ it.country)
                }
        })*/
        /*countriesRepository.subscribeToCountry("Egypt")
        countriesRepository.subscribeToCountry("Italy")
        countriesRepository.subscribeToCountry("France")*/
       /* countriesRepository.getAllCountriesLocalData()
            .observe(this, Observer {
                Log.i("mainactivity","LocalDB  result  is :"+ it)
                Log.i("mainactivity","LocalDB  size  is :"+ it.size)
            })*/

        /*CoroutineScope(Dispatchers.IO).launch{
            val apiList = countriesViewModel.getAllCountriesFromApi()
            val subscribedCountries = countriesViewModel.getAllSubscribedCountries()
            //subscribedCountries.get(0).active = 50
            if(subscribedCountries.count() > 0){
                apiList.map {apiCountry ->
                    subscribedCountries.forEach {subscribedCountry ->
                        if(apiCountry.country == subscribedCountry.country){
                            apiCountry.isSubscribed = true
                        }
                    }
                }
                val subscribedCountriesFromApi = apiList.filter { it.isSubscribed }
                subscribedCountriesFromApi.forEachIndexed { index, country ->
                    if(country.cases != subscribedCountries.get(index).cases || country.active != subscribedCountries.get(index).active  || country.recovered != subscribedCountries.get(index).recovered ){
                        Log.i("mainactivity","Data changed  For country  :"+ country.country)
                    }
                }
                Log.i("mainactivity","Comparison Ended  ")
            }

            countriesRepository.insertAllCountries(apiList)
            Log.i("mainactivity","Rows inserted :  "+apiList.size)

            //Need to get Statistics and historical statistics then update DB and shared preferences
        }
*/

/*
        sharedPreferencesHandler.getDataFromSharedPreferences().observe(this, Observer {
            Log.i("mainactivity","Shared preferences object  is :"+ it)
        })

        countriesRepository.getAllSubscribedCountries().observe(this, Observer {
            Log.i("mainactivity","subscribed countries are :"+ it)
            Log.i("mainactivity","subscribed countries size is :"+ it.size)
        })



        countriesRepository.getAllCountriesLocalData()
            .observe(this, Observer {
                Log.i("mainactivity","LocalDB  result  is :"+ it)
            })*/


        /* countriesRepository.getCasesByAllCountries().observe(this, Observer {

             it.forEach {
             Log.i("mainactivity","country  is :"+ it)
         }
         })*/

        /*subscriptionsDao.insertNewSubscription(Subscriptions("Egypt"))
        subscriptionsDao.insertNewSubscription(Subscriptions("Italy"))
        subscriptionsDao.insertNewSubscription(Subscriptions("France"))*/
        //Log.i("mainactivity","llocalDB is :"+ countryDao.getAllCountries())
        //getCountriesObservable()

     /*   subscriptionsDao.getAllSubscribedCountries().subscribe(Consumer {
            it.forEach{
                Log.i("mainactivity","country : "+ it)
            }
        })
*/

        //Log.i("mainactivity","llocalDB is :"+ countryDao.getAllCountries())

       /*val disposable =  sharedPreferencesHandler.getDataFromSharedPreferences()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .throttleLast(3,TimeUnit.SECONDS)
            .subscribe {
                Log.i("mainactivity","shared prefrences value  :"+it)
            }

        compositeDisposable.add(disposable)*/


    }
    fun getCountriesObservable(){
       /* val d = countriesViewModel.getCasesByAllCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayCountries(it)
                },{
                    displayError(it.message!!)
                }
            )
        compositeDisposable.add(d)*/
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
            sharedPreferencesHandler.unregisterListener()
        }
    }
    fun displayCountries(list: List<Country>){
       /* Log.i("mainactivity","size is :"+ list.size)
        Log.i("mainactivity","first element :"+ list.get(0))
        Log.i("mainactivity","last element  :"+ list.get((list.size)-1))
*/
        /*
        for(country in list){
            countryDao.insertNewCountry(country)
        }
*/
        Log.i("mainactivity","All Data inserted")
        //countryDao.insertNewCountry(list.get(0))
    }

    fun displayError(error: String){
        Log.i("mainactivity",error)
    }

    var counter =0
    lateinit var statistics : Statistics
    fun changeValueAction(view: View) {

         statistics = Statistics(
             updated = counter.toLong(),
             cases = 500 + counter.toLong(),
             deaths = 500 + counter.toLong(),
             recovered = 500 + counter.toLong(),
             active = 500 + counter.toLong(),
             critical = 500 + counter.toLong(),
             affectedCountries = 500 + counter,
             todayCases = 500 + counter.toLong(),
             todayDeaths = 500 + counter.toLong()
         )
       sharedPreferencesHandler.writeInSharedPreferences( statistics)
        counter++
    }

    /*fun simulateData() : LiveData<List<Country>> {
         var countryList = ArrayList<Country>()
        for (i in 0..10){
            countryList.add(Country(updated = i.toLong(),todayDeaths = 100))
        }


        countryList.add(Country(updated=1588368816064, country="Egypt", countryInfo= CountryInfo(_id=818, lat=27.0, long=0.0, flag="https://corona.lmao.ninja/assets/img/flags/eg.png"), continent="Africa", cases=5895, todayCases=358, deaths=406, todayDeaths=14, recovered=1460, active=4029, critical=0, casesPerOneMillion=58.0, deathsPerOneMillion=4.0, tests=90000, testsPerOneMillion=879.0))

        countryList[0].country = "Italy"
        countryList[1].country = "France"
        //return Observable.fromCallable { countryList }
    }*/
   /* fun simulateSubscriptions():  LiveData<List<Country>> {
        //return subscriptionsDao.getAllSubscribedCountries()

        return

    }*/

    fun setupWorkManagerTest(){
        val fetchDataRequest = OneTimeWorkRequestBuilder<CountriesWorker>()
            .build()
        WorkManager.getInstance(this).enqueue(fetchDataRequest)
    }
    fun setupWorkManager(){

        val request = PeriodicWorkRequestBuilder<CountriesWorker>(2, TimeUnit.HOURS)
            .setConstraints(getWorkManagerConstraints())
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(WORK_MANAGER_TAG, ExistingPeriodicWorkPolicy.KEEP, request)

        WorkManager.getInstance(this).getWorkInfosByTag(WORK_MANAGER_TAG).get().get(0)
    }


    fun editWorkManager(){

        val request = PeriodicWorkRequestBuilder<CountriesWorker>(20, TimeUnit.SECONDS)
            .setConstraints(getWorkManagerConstraints())
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(WORK_MANAGER_TAG, ExistingPeriodicWorkPolicy.REPLACE, request)
    }

    private fun getWorkManagerConstraints() : Constraints{
        return  Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }



}
