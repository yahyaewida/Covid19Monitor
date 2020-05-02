package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.viewmodel.StatisticsViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import javax.inject.Inject

class StatisticsActivity : AppCompatActivity() {
    private val TAG : String? = StatisticsActivity::class.simpleName

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var statisticsViewModel: StatisticsViewModel
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        statisticsViewModel = ViewModelProvider(this, viewModelProvidersFactory)[StatisticsViewModel::class.java]
        getStatisticsObservable()
    }

    fun getStatisticsObservable(){
       /* val d = statisticsViewModel.getStatistics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayStatistics(it)
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
