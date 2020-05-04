package iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel.HistoricalStatisticsViewmodel
import iti.mobilenative.covid19monitoring.model.pojo.HistoricalStatistics
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import kotlinx.android.synthetic.main.fragment_historical_statistics.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class HistoricalStatisticsFragment : Fragment() {
    val TAG: String = "HStatisticsFragment"
    lateinit var historicalStatisticsViewmodel: HistoricalStatisticsViewmodel

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historical_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.provideActivity(ActivityModule(FragmentActivity(this.id))).inject(this)
        historicalStatisticsViewmodel = ViewModelProvider(this, viewModelProvidersFactory)[HistoricalStatisticsViewmodel::class.java]
        getHistoricalStatisticsObservable("7")
    }
    fun getHistoricalStatisticsObservable(lastDays: String){
         historicalStatisticsViewmodel.getHistoricalStatistics(lastDays)
            .observe(requireActivity(), Observer {
                if(it.cases.count() != 0){
                    displayHistoricalStatistics(it)
                }else{
                    displayError("error in loading historical statistics")
                }
            })
    }

    fun displayHistoricalStatistics(statistics: HistoricalStatistics){
        log("historicalStatistics : "+statistics)

        val datesLabels= statistics.cases.keys.toCollection(ArrayList())
        val casesBarEntry = getCasesBarEntry(statistics.cases.values.toMutableList())
        val deathsBarEntry = getDeathsBarEntry(statistics.deaths.values.toMutableList())
        val recoveredBarEntry = getRecoveredBarEntry(statistics.recovered.values.toMutableList())

        createBarChart(casesBarEntry,deathsBarEntry,recoveredBarEntry,datesLabels)

    }

    fun displayError(error: String){
        log(error)
    }

    fun log(msg: String){
        Log.i(TAG,msg)
    }

    fun createBarChart(casesBarEntry: ArrayList<BarEntry>,deathsBarEntry: ArrayList<BarEntry>,
                        recoveredBarEntry: ArrayList<BarEntry>,datesLabeles: ArrayList<String>){
        val barData = setBarChartdata(casesBarEntry,deathsBarEntry,recoveredBarEntry)
        barChart.data = barData
        setupSomeStyle(barData,datesLabeles)
    }
    fun setBarChartdata(casesBarEntry: ArrayList<BarEntry>,deathsBarEntry: ArrayList<BarEntry>,
                        recoveredBarEntry: ArrayList<BarEntry>) : BarData{

        val casesBarSet = BarDataSet(casesBarEntry,"Cases")
        casesBarSet.setColor(Color.rgb(255, 247, 140))

        val deathsBarSet = BarDataSet(deathsBarEntry,"Deaths")
        deathsBarSet.setColor(Color.rgb(255,153,148))

        val recoveredBarSet = BarDataSet(recoveredBarEntry,"Recovered")
        recoveredBarSet.setColor(Color.rgb(160,231,160))

        return  BarData(casesBarSet,deathsBarSet,recoveredBarSet)

    }
    fun setupSomeStyle(barData: BarData,datesLabeles: ArrayList<String>){
        barChart.description.isEnabled = false

        //Dates Label
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(datesLabeles)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setGranularityEnabled(true)

        //Grouping
        barChart.isDragEnabled = true
        barChart.setVisibleXRangeMaximum(3f)
        val barSpace = 0.1f
        val groupSpace = 0.1f
        barData.barWidth = 0.18f
        xAxis.axisMinimum = 0f
        barChart.groupBars(0f,groupSpace,barSpace)

        xAxis.setCenterAxisLabels(true)


        //Legend Position
        val legend: Legend = barChart.getLegend()
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)

        //Hide Grid
        barChart.getAxisLeft().setDrawGridLines(false)
        barChart.getXAxis().setDrawGridLines(false)
        barChart.getAxisRight().setDrawLabels(false)

        barChart.invalidate()
    }
    fun getCasesBarEntry(casesList: MutableList<Long>): ArrayList<BarEntry>{
       val casesBarEntry : ArrayList<BarEntry> = ArrayList()
        for(i in 0..casesList.size-1){
            casesBarEntry.add(BarEntry(i.toFloat(),casesList.get(i).toFloat()))
        }
        return casesBarEntry
    }
    fun getDeathsBarEntry(deathsList: MutableList<Long>): ArrayList<BarEntry>{
        val deathsBarEntry : ArrayList<BarEntry> = ArrayList()
        for(i in 0..deathsList.size-1){
            deathsBarEntry.add(BarEntry(i.toFloat(),deathsList.get(i).toFloat()))
        }
        return deathsBarEntry
    }
    fun getRecoveredBarEntry(recoveredList: MutableList<Long>): ArrayList<BarEntry>{
        val recoveredBarEntry : ArrayList<BarEntry> = ArrayList()
        for(i in 0..recoveredList.size-1){
            recoveredBarEntry.add(BarEntry(i.toFloat(),recoveredList.get(i).toFloat()))
        }
        return recoveredBarEntry
    }
}
