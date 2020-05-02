package iti.mobilenative.covid19monitoring.features.selectedCountries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.selectedCountries.viewmodel.SubscribedCountriesViewModel
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SubscribedCountriesFragment : Fragment() {

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var subscribedCountriesViewModel: SubscribedCountriesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity?.application as App).appComponent.provideActivity(ActivityModule(FragmentActivity(this.id))).inject(this)
        subscribedCountriesViewModel = ViewModelProvider(this, viewModelProvidersFactory)[SubscribedCountriesViewModel::class.java]
        return inflater.inflate(R.layout.fragment_subscribed_countries, container, false)
    }


}
