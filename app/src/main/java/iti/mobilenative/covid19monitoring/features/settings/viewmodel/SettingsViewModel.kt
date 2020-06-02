package iti.mobilenative.covid19monitoring.features.settings.viewmodel

import androidx.lifecycle.ViewModel
import iti.mobilenative.covid19monitoring.model.repository.CountriesRepository
import javax.inject.Inject

class SettingsViewModel @Inject constructor(val repository: CountriesRepository): ViewModel() {

    fun writeSettingsInSharedPreferences(numberOfHours : Long){
        repository.writeSettingsInSharedPreferences(numberOfHours)
    }
    fun readSettingsFromSharedPreferences() : Long{
        return repository.readSettingsFromSharedPreferences()
    }
}