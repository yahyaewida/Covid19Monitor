package iti.mobilenative.covid19monitoring.features.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel.HistoricalStatisticsViewmodel
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import kotlinx.android.synthetic.main.fragment_statistics.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : Fragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var statisticsFragmentViewModel: HistoricalStatisticsViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        (activity?.application as App).appComponent.provideActivity(ActivityModule(FragmentActivity(this.id))).inject(this)
        statisticsFragmentViewModel = ViewModelProvider(this, viewModelProvidersFactory)[HistoricalStatisticsViewmodel::class.java]
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
