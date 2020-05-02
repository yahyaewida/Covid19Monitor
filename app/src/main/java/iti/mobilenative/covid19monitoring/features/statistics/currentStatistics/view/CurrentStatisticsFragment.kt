package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.viewmodel.StatisticsViewModel
import iti.mobilenative.covid19monitoring.pojo.Statistics
import kotlinx.android.synthetic.main.fragment_current_statistics.*

/**
 * A simple [Fragment] subclass.
 */
class CurrentStatisticsFragment : Fragment() {
    val TAG: String = "CStatisticsFragment"
    lateinit var currentStatisticsViewmodel: StatisticsViewModel
    val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //createPieChart()
        currentStatisticsViewmodel = ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        getStatisticsObservable()
    }
    fun getStatisticsObservable(){
        val d = currentStatisticsViewmodel.getStatistics()
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
        createPieChart(statistics.deaths.toFloat(),statistics.recovered.toFloat(),statistics.active.toFloat())
        log(statistics.toString())

    }

    fun displayError(error: String){
        log(error)
    }

    fun log(msg: String){
        Log.i(TAG,msg)
    }

    fun createPieChart(deaths: Float,recovered: Float,active: Float){
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
        pieChart.setExtraOffsets(5.0f,10.0f,5.0f,5.0f)
        pieChart.dragDecelerationFrictionCoef = 0.99f
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61.0f

        val list = mutableListOf<PieEntry>()
        list.add(PieEntry(deaths,"deaths"))
        list.add(PieEntry(recovered,"recovered"))
        list.add(PieEntry(active,"active"))

        val dataSet = PieDataSet(list,"cases")
        dataSet.sliceSpace = 3.0f
        dataSet.selectionShift = 5.0f

        val percentValuesClr = ArrayList<Int>()
        for(i in 1..3){
            percentValuesClr.add(Color.rgb(255,255,255))
        }
        dataSet.setValueTextColors(percentValuesClr)
        dataSet.valueTypeface = Typeface.DEFAULT_BOLD

        pieChart.getLegend().setTextColor(Color.BLACK);
        pieChart.getLegend().textSize = 14f
        pieChart.getLegend().typeface = Typeface.DEFAULT_BOLD
        pieChart.getLegend().formSize = 16.0f
        val colors = ArrayList<Int>()
        /*for (c in ColorTemplate.VORDIPLOM_COLORS){
            colors.add(c)
        }*/
        colors.add(Color.rgb(255,153,148))
        colors.add(Color.rgb(160,231,160))
        colors.add(Color.rgb(255, 247, 140))
        dataSet.setColors(colors)

        val data = PieData(dataSet)
        data.setValueTextSize(14f)

        data.setValueFormatter(PercentFormatter())
        pieChart.data= data
    }
}
