package iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel.HistoricalStatisticsViewmodel
import iti.mobilenative.covid19monitoring.pojo.HistoricalStatistics
import kotlinx.android.synthetic.main.fragment_historical_statistics.*


/**
 * A simple [Fragment] subclass.
 */
class HistoricalStatisticsFragment : Fragment() {
    val TAG: String = "HStatisticsFragment"
    lateinit var historicalStatisticsViewmodel: HistoricalStatisticsViewmodel
    val compositeDisposable = CompositeDisposable()


    var deaths = ArrayList<Entry>()
    var recovered = ArrayList<Entry>()
    var cases = ArrayList<Entry>()
    var casesDates = ArrayList<String>()


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

    fun displayHistoricalStatistics(statistics: HistoricalStatistics){
        log("historicalStatistics : "+statistics)
        createLineChart(statistics)
    }

    fun displayError(error: String){
        log(error)
    }

    fun log(msg: String){
        Log.i(TAG,msg)
    }

    fun createLineChart(statistics: HistoricalStatistics){
        val deathValues = statistics.deaths.values.toMutableList()
        for(i in 1..deathValues.size-1){
            deaths.add(Entry(i.toFloat(),deathValues.get(i).toFloat()))
        }
        val recoveredValues = statistics.recovered.values.toMutableList()
        for(i in 1..recoveredValues.size-1){
            recovered.add(Entry(i.toFloat(),recoveredValues.get(i).toFloat()))
        }
        val casesValues = statistics.cases.values.toMutableList()
        casesDates = statistics.cases.keys.toCollection(ArrayList())

        for(i in 1..casesValues.size-1){
            cases.add(Entry(i.toFloat(),casesValues.get(i).toFloat()))
        }

        val dataSets : ArrayList<ILineDataSet> = ArrayList()
        val deathsDataset = LineDataSet(deaths,"deaths")
        deathsDataset.setColor(Color.rgb(255,153,148))
        val recoveredDataset = LineDataSet(recovered,"recovered")
        recoveredDataset.setColor(Color.rgb(160,231,160))
        val casesDataset = LineDataSet(cases,"cases")
        casesDataset.setColor(Color.rgb(255, 247, 140))

        dataSets.add(deathsDataset)
        dataSets.add(recoveredDataset)
        dataSets.add(casesDataset)

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        val formatter: ValueFormatter =
            object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase): String {
                    return casesDates[value.toInt()]
                }
            }
        xAxis.granularity = 1f
        xAxis.valueFormatter = formatter


        lineChart.getAxisLeft().setDrawGridLines(false)
        lineChart.getXAxis().setDrawGridLines(false)


        val yAxisLeft = lineChart.axisLeft
        yAxisLeft.granularity = 3f
        //lineChart.setVisibleYRange(0f,2000000f,yAxisLeft.axisDependency)

        yAxisLeft.setAxisMaxValue(5000000f);
        yAxisLeft.setAxisMinValue(0f);
        yAxisLeft.setLabelCount(5000)

        deathsDataset.setAxisDependency(YAxis.AxisDependency.LEFT)
        recoveredDataset.setAxisDependency(YAxis.AxisDependency.LEFT)
        casesDataset.setAxisDependency(YAxis.AxisDependency.LEFT)

        lineChart.setData(LineData(dataSets))
        lineChart.animateX(2500);
        lineChart.invalidate();
    }
}
