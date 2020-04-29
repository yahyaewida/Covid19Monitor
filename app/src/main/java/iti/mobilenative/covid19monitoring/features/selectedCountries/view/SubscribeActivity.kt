package iti.mobilenative.covid19monitoring.features.selectedCountries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.features.selectedCountries.repository.SelectedCountriesRepository
import iti.mobilenative.covid19monitoring.features.selectedCountries.viewmodel.SelectedCountriesViewModel
import iti.mobilenative.covid19monitoring.pojo.Country

class SubscribeActivity : AppCompatActivity() {
    private val TAG : String? = SubscribeActivity::class.simpleName

    lateinit var selectedCountriesViewModel: SelectedCountriesViewModel
    val compositeDisposable = CompositeDisposable()

    val countriesList = listOf("egypt","italy")
    val countriesParam = countriesList.joinToString (separator = ",") { it -> "${it}" }
    val countryParam = "Egypt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        selectedCountriesViewModel = ViewModelProviders.of(this).get(SelectedCountriesViewModel::class.java)
        getSelectedCountriesObservable(countriesParam)
        getSelectedCountryObservable(countryParam)
        Log.i(TAG,countriesParam)
    }
    fun getSelectedCountriesObservable(countries: String){
        val d = selectedCountriesViewModel.getCasesBySelectedCountries(countries)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayCountries(it)
                },{
                    displayError(it.message!!)
                }
            )
        compositeDisposable.add(d)
    }

    fun getSelectedCountryObservable(country: String){
        val d = selectedCountriesViewModel.getCasesBySelectedCountry(country)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayCountry(it)
                },{
                    displayError(it.message!!)
                }
            )
        compositeDisposable.add(d)
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
