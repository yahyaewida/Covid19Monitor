package iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel.HistoricalStatisticsViewmodel
import iti.mobilenative.covid19monitoring.pojo.HistoricalStatistics

/**
 * A simple [Fragment] subclass.
 */
class HistoricalStatisticsFragment : Fragment() {
    val TAG: String = "HStatisticsFragment"

    lateinit var historicalStatisticsViewmodel: HistoricalStatisticsViewmodel
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historical_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historicalStatisticsViewmodel = ViewModelProviders.of(this).get(HistoricalStatisticsViewmodel::class.java)
        getHistoricalStatisticsObservable("7")
    }
    fun getHistoricalStatisticsObservable(lastDays: String){
        val d = historicalStatisticsViewmodel.getHistoricalStatistics(lastDays)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                {
                    displayHistoricalStatistics(it)
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

    fun displayHistoricalStatistics(list: List<HistoricalStatistics>){
        log("Historical statistics size is "+list.size)
        log("first statistics : "+list.first().toString())
        log("last statistics : "+list.last().toString())
    }

    fun displayError(error: String){
        log(error)
    }

    fun log(msg: String){
        Log.i(TAG,msg)
    }

}
