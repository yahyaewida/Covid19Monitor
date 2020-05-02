package iti.mobilenative.covid19monitoring.features.statistics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import iti.mobilenative.covid19monitoring.R
import kotlinx.android.synthetic.main.fragment_statistics.*


/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        return view
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Navigation.setViewNavController(statisticsBottomNavigationView,
            requireActivity().findNavController(R.id.nav_statistics_host_fragment))
       val navController = requireActivity().findNavController(R.id.nav_statistics_host_fragment)
       statisticsBottomNavigationView.setupWithNavController(navController)
    }

}
