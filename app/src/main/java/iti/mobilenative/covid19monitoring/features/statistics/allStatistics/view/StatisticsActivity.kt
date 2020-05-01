package iti.mobilenative.covid19monitoring.features.statistics.allStatistics.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.statistics.allStatistics.viewmodel.StatisticsViewModel
import iti.mobilenative.covid19monitoring.pojo.Statistics

class StatisticsActivity : AppCompatActivity() {
    private val TAG : String? = StatisticsActivity::class.simpleName

    lateinit var statisticsViewModel: StatisticsViewModel
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        getStatisticsObservable()
    }

    fun getStatisticsObservable(){
        val d = statisticsViewModel.getStatistics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayStatistics(it)
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

    fun displayStatistics(statistics: Statistics){
        log(statistics.toString())

    }

    fun displayError(error: String){
        log(error)
    }

    fun log(msg: String){
        Log.i(TAG,msg)
    }
}
