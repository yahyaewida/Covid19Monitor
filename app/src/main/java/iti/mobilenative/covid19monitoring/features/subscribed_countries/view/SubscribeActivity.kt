package iti.mobilenative.covid19monitoring.features.subscribed_countries.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import io.reactivex.disposables.CompositeDisposable
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.subscribed_countries.viewmodel.SubscribedCountriesViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Country

class SubscribeActivity : AppCompatActivity() {
    private val TAG : String? = SubscribeActivity::class.simpleName

    lateinit var subscribedCountriesViewModel: SubscribedCountriesViewModel
    val compositeDisposable = CompositeDisposable()

    val countriesList = listOf("egypt","italy")
    val countriesParam = countriesList.joinToString (separator = ",") { it -> "${it}" }
    val countryParam = "Egypt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        subscribedCountriesViewModel = ViewModelProviders.of(this).get(SubscribedCountriesViewModel::class.java)
        getSelectedCountriesObservable(countriesParam)
        getSelectedCountryObservable(countryParam)
        Log.i(TAG,countriesParam)
    }
    fun getSelectedCountriesObservable(countries: String){
       /* val d = selectedCountriesViewModel.getCasesBySelectedCountries(countries)
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

    fun getSelectedCountryObservable(country: String){
        /*val d = selectedCountriesViewModel.getCasesBySelectedCountry(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayCountry(it)
                },{
                    displayError(it.message!!)
                }
            )
        compositeDisposable.add(d)*/
    }

    private fun displayCountry(country: Country) {
        log(country.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
    fun displayCountries(list: List<Country>){
        log("size is :"+ list.size)
        log("first element :"+ list.get(0))
        log("last element  :"+ list.get((list.size)-1))
    }

    fun displayError(error: String){
        log(error)
    }

    fun log(msg: String){
        Log.i(TAG,msg)
    }
}
