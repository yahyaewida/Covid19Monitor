package iti.mobilenative.covid19monitoring.features.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.model.LocalDatabase
import iti.mobilenative.covid19monitoring.pojo.Country
import iti.mobilenative.covid19monitoring.utils.App

class MainActivity : AppCompatActivity() {
    lateinit var countriesViewModel: CountriesViewModel
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countriesViewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        getCountriesObservable()
    }
    fun getCountriesObservable(){
        val d = countriesViewModel.getCasesByAllCountries()
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

    override fun onDestroy() {
        super.onDestroy()
        if(!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }
    fun displayCountries(list: List<Country>){
        Log.i("mainactivity","size is :"+ list.size)
        Log.i("mainactivity","first element :"+ list.get(0))
        Log.i("mainactivity","last element  :"+ list.get((list.size)-1))

    }

    fun displayError(error: String){
        Log.i("mainactivity",error)
    }
}
