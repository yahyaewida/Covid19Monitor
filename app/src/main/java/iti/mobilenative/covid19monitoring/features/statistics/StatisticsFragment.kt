package iti.mobilenative.covid19monitoring.features.statistics

import android.os.Bundle
import android.view.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
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
    private lateinit var searchItem : MenuItem
    lateinit var navControllermenu : NavController
    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var statisticsFragmentViewModel: HistoricalStatisticsViewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.application as App).appComponent.provideActivity(
            ActivityModule(
                FragmentActivity(
                    this.id
                )
            )
        ).inject(this)
        statisticsFragmentViewModel = ViewModelProvider(
            this,
            viewModelProvidersFactory
        )[HistoricalStatisticsViewmodel::class.java]
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navControllermenu = Navigation.findNavController(view)
        Navigation.setViewNavController(
            statisticsBottomNavigationView,
            requireActivity().findNavController(R.id.nav_statistics_host_fragment)
        )
        val navController = requireActivity().findNavController(R.id.nav_statistics_host_fragment)
        statisticsBottomNavigationView.setupWithNavController(navController)
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.app_bar_menu, menu)
        searchItem = menu.findItem(R.id.search)
        searchItem.setVisible(false)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            navControllermenu.navigate(R.id.settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}

