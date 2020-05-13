package iti.mobilenative.covid19monitoring.features.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import iti.mobilenative.covid19monitoring.R
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.features.settings.viewmodel.SettingsViewModel
import iti.mobilenative.covid19monitoring.model.workmanager.CountriesWorker
import iti.mobilenative.covid19monitoring.utils.App
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory
import iti.mobilenative.covid19monitoring.utils.WORK_MANAGER_TAG
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SettingsFragment : Fragment(),View.OnClickListener{
    lateinit var selected_radio_btn: RadioButton;

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory
    private lateinit var settingsViewModel : SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        (activity?.application as App).appComponent.provideActivity(
            ActivityModule(
                FragmentActivity(this.id)
            )
        ).inject(this)
        settingsViewModel = ViewModelProvider(this, viewModelProvidersFactory)[SettingsViewModel::class.java]

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notify_btn.setOnClickListener(this)
        bk.setOnClickListener(this)

    }
    override fun onStop() {
        super.onStop()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.notify_btn -> getNotificationInterval(v)
            R.id.bk -> requireActivity().onBackPressed()
        }
    }

    private fun getNotificationInterval(v: View?) {
        var id: Int = radio_group.checkedRadioButtonId
        if (id!=-1){
            val radio :RadioButton = requireView().findViewById(id);
            Toast.makeText(context,"${radio.text}",
                Toast.LENGTH_SHORT).show()
            if(radio.text == "1 hour"){
                editWorkManager(1)
                settingsViewModel.writeSettingsInSharedPreferences(1)
            }else if(radio.text == "2 hours"){
                editWorkManager(2)
                settingsViewModel.writeSettingsInSharedPreferences(2)
            }
            else if(radio.text == "4 hours"){
                editWorkManager(4)
                settingsViewModel.writeSettingsInSharedPreferences(4)
            }
            else if(radio.text == "1 day"){
                editWorkManager(24)
                settingsViewModel.writeSettingsInSharedPreferences(24)
            }
        }else{
            Toast.makeText(context,"nothing selected",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun editWorkManager(numberOfHours : Long){

        val request = PeriodicWorkRequestBuilder<CountriesWorker>(numberOfHours, TimeUnit.HOURS)
            .setInitialDelay(numberOfHours, TimeUnit.HOURS)
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
