package iti.mobilenative.covid19monitoring.features.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
//import iti.mobilenative.covid19monitoring.dagger.components.DaggerApplicationComponent
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.model.CountryDao
import iti.mobilenative.covid19monitoring.model.SharedPreferencesHandler
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.services.network.retrofit.CovidApiService
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var countryDao : CountryDao
    @Inject lateinit var retrofitClient : CovidApiService
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory
    lateinit var countriesViewModel: CountriesViewModel
    @Inject
    lateinit var sharedPreferencesHandler: SharedPreferencesHandler
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as App).appComponent.provideActivity(ActivityModule(this)).inject(this)

        countriesViewModel = ViewModelProviders.of(this, viewModelProvidersFactory)[CountriesViewModel::class.java]

        //Log.i("mainactivity","llocalDB is :"+ countryDao.getAllCountries())
        getCountriesObservable()
        //Log.i("mainactivity","llocalDB is :"+ countryDao.getAllCountries())
        sharedPreferencesHandler.writeInSharedPreferences("test")

       val disposable =  sharedPreferencesHandler.readFromSharedPrefrences()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer {
                Log.i("mainactivity","shared prefrences value :"+ it.getString("username","empty"))

            })

        compositeDisposable.add(disposable)


    }
    fun getCountriesObservable(){
        val d = countriesViewModel.getCasesByAllCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                   // displayCountries(it)
                    sharedPreferencesHandler.writeInSharedPreferences("test2")
                },{
                    displayError(it.message!!)
                }
            )
        compositeDisposable.add(d)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
            sharedPreferencesHandler.unregisterListener()
        }
    }
    fun displayCountries(list: List<Country>){
        Log.i("mainactivity","size is :"+ list.size)
        Log.i("mainactivity","first element :"+ list.get(0))
        Log.i("mainactivity","last element  :"+ list.get((list.size)-1))

        //countryDao.insertNewCountry(list.get(0))
    }

    fun displayError(error: String){
        Log.i("mainactivity",error)
    }

    var counter =0
    fun changeValueAction(view: View) {

       sharedPreferencesHandler.writeInSharedPreferences("test "+(counter++))
    }
}
