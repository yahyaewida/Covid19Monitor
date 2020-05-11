package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.view

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.viewmodel.StatisticsViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Statistics
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import kotlinx.android.synthetic.main.fragment_current_statistics.*
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class CurrentStatisticsFragment : Fragment() {
    val TAG: String = "CStatisticsFragment"
    lateinit var currentStatisticsViewmodel: StatisticsViewModel

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).appComponent.provideActivity(ActivityModule(FragmentActivity(this.id))).inject(this)
        currentStatisticsViewmodel = ViewModelProvider(this, viewModelProvidersFactory)[StatisticsViewModel::class.java]

        getStatistics()
    }
    fun getStatistics(){
        currentStatisticsViewmodel.getStatistics()
            .observe(requireActivity(), Observer {
                if(it.updated != 0.toLong()){
                    displayStatistics(it)
                }else{
                    displayError("error in loading statistics")
                }
            })
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
        if(pieChart!=null){
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

            val colors = ArrayList<Int>()
            colors.add(Color.rgb(255,153,148))
            colors.add(Color.rgb(160,231,160))
            colors.add(Color.rgb(166,221,241))
            dataSet.setColors(colors)

            pieChart.legend.setEnabled(true)
            val legends = ArrayList<LegendEntry>()
            legends.add(LegendEntry("deaths [${deaths.toInt()}]",Legend.LegendForm.SQUARE,16.0f,1.0f,
                null,Color.rgb(255,153,148)
            ))
            legends.add(LegendEntry("recovered [${recovered.toInt()}]",Legend.LegendForm.SQUARE,16.0f,1.0f,
                null,Color.rgb(160,231,160)
            ))
            legends.add(LegendEntry("active [${active.toInt()}]",Legend.LegendForm.SQUARE,16.0f,1.0f,
                null,Color.rgb(166,221,241)
            ))

            pieChart.legend.setCustom(legends)
            pieChart.legend.setTextColor(Color.BLACK)
            pieChart.legend.textSize = 14f
            pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
            pieChart.legend.typeface = Typeface.DEFAULT_BOLD

            pieChart.extraBottomOffset = 20.0f

            val data = PieData(dataSet)
            data.setValueTextSize(14f)

            //val total = active+deaths+recovered
            //pieChart.centerText = "total cases: "+total
            data.setValueFormatter(PercentFormatter(pieChart))
            pieChart.setUsePercentValues(true)
            pieChart.data= data

            pieChart.invalidate()
        }

    }
}

