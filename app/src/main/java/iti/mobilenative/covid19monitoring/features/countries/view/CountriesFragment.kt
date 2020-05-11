package iti.mobilenative.covid19monitoring.features.countries.view

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Case
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.workmanager.CountriesWorker
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import iti.mobilenative.covid19monitoring.utils.WORK_MANAGER_TAG
import kotlinx.android.synthetic.main.fragment_countries.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class CountriesFragment : Fragment(),CommunicatorOfAdapterAndFragment {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    private lateinit var countriesList:List<Country>
    private lateinit var countriesAdapter: CountriesAdapter

    private  var caseAdapter: CaseAdapter? = null
    private lateinit var casesGridView : GridView
    private var case = Case()
    private var death = Case()
    private var recoverd = Case()
    var casesList: ArrayList<Case> = ArrayList(3)

    private lateinit var countriesRecyclerView : RecyclerView
    lateinit var countriesViewModel : CountriesViewModel

    lateinit var navController : NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity?.application as App).appComponent.provideActivity(ActivityModule(FragmentActivity(this.id))).inject(this)
        countriesViewModel = ViewModelProvider(this, viewModelProvidersFactory)[CountriesViewModel::class.java]
        val view =  inflater.inflate(R.layout.fragment_countries, container, false)
        countriesRecyclerView = view.findViewById(R.id.countriesRecyclerView)
        countriesRecyclerView.layoutManager = LinearLayoutManager(context)
        casesGridView = view.findViewById(R.id.grid_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        if(casesList.size==0){
            casesList.add(case)
            casesList.add(recoverd)
            casesList.add(death)
        }
    }

    override fun onResume() {
        super.onResume()
        val subscribedCountries = countriesViewModel.getAllSubscribedCountries()
        Log.i("mainactivity","Subscribed countries are  :"+ subscribedCountries + "\nsize = "+ subscribedCountries.size)
        countriesViewModel.getStatistics().observe(requireActivity(), Observer {
           case.title="Cases"
           case.numbers = it.cases.toString()
           case.color =  Color.rgb(166,221,241)
            casesList.set(0,case)

            recoverd.title="Recovered"
            recoverd.numbers = it.recovered.toString()
            recoverd.color =  Color.rgb(160,231,160)
            casesList.set(1,recoverd)

            death.title="Deaths"
            death.numbers = it.deaths.toString()
            death.color =  Color.rgb(255,153,148)
            casesList.set(2,death)

            caseAdapter = CaseAdapter(casesList,requireContext())
            grid_view.numColumns = 3
            grid_view.horizontalSpacing = 15

            grid_view.adapter = caseAdapter

        })
        setupWorkManager()
        countriesViewModel.getAllCountries().observe(requireActivity(), Observer {
            countriesList = it
            countriesAdapter = CountriesAdapter(this,countriesList, context,isFromSubscribedCountries = false)
            countriesRecyclerView.adapter = countriesAdapter
        })
    }

    override fun updateCountryList(indexOfCountry: Int, subscriptionValue : Boolean) {
        if(subscriptionValue){
            countriesViewModel.subscribeToCountry( countriesList[indexOfCountry].country)
        }else{
            countriesViewModel.unsubscribeFromCountry( countriesList[indexOfCountry].country)
        }

        //countriesList[indexOfCountry].isSubscribed = subscriptionValue
    }

    fun setupWorkManager(){

        val request = PeriodicWorkRequestBuilder<CountriesWorker>(15, TimeUnit.MINUTES)
            //.setInitialDelay(15,TimeUnit.MINUTES)
            .setConstraints(getWorkManagerConstraints())
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(WORK_MANAGER_TAG, ExistingPeriodicWorkPolicy.REPLACE, request)

    }
    private fun getWorkManagerConstraints() : Constraints {
        return  Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.app_bar_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
            val searchManager = getSystemService(requireContext(),SearchManager::class.java)
            searchView.setSearchableInfo(searchManager!!.getSearchableInfo(requireActivity().componentName))

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //countriesAdapter.filter.filter(query)
                    Toast.makeText(context, "Seach Query: " + query, Toast.LENGTH_SHORT).show()
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    //countriesAdapter.filter.filter(newText)
                    Toast.makeText(context, "Seach Query: " + newText, Toast.LENGTH_SHORT).show()
                    return false
                }
            })
        }
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            return true
        }
        if (item.itemId == R.id.settings) {
            navController.navigate(R.id.settingsFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
