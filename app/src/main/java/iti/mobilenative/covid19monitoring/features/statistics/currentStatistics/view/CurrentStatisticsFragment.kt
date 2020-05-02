package iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import iti.mobilenative.covid19monitoring.R

/**
 * A simple [Fragment] subclass.
 */
class CurrentStatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_statistics, container, false)
    }

}
