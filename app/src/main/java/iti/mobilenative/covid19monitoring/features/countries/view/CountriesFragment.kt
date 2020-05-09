package iti.mobilenative.covid19monitoring.features.countries.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.model.pojo.Country
import iti.mobilenative.covid19monitoring.model.workmanager.CountriesWorker
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import iti.mobilenative.covid19monitoring.utils.WORK_MANAGER_TAG
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

    private lateinit var countriesRecyclerView : RecyclerView

    lateinit var worldWideConfirmedCasesTextView: TextView
    lateinit var worldWideRecoveredCasesTextView: TextView
    lateinit var worldWideDeathsCasesTextView: TextView

    lateinit var countriesViewModel : CountriesViewModel
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
        worldWideConfirmedCasesTextView = view.findViewById(R.id.confirmedCasesTextView)
        worldWideRecoveredCasesTextView = view.findViewById(R.id.recoveredCasesTextView)
        worldWideDeathsCasesTextView = view.findViewById(R.id.deathsCasesTextView)

        return view
    }

    override fun onResume() {
        super.onResume()
        val subscribedCountries = countriesViewModel.getAllSubscribedCountries()
        Log.i("mainactivity","Subscribed countries are  :"+ subscribedCountries + "\nsize = "+ subscribedCountries.size)
        countriesViewModel.getStatistics().observe(requireActivity(), Observer {
            worldWideConfirmedCasesTextView.text = it.cases.toString()
            worldWideRecoveredCasesTextView.text = it.recovered.toString()
            worldWideDeathsCasesTextView.text = it.deaths.toString()
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

}
