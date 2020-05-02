package iti.mobilenative.covid19monitoring.model

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import iti.mobilenative.covid19monitoring.pojo.Statistics
import iti.mobilenative.covid19monitoring.utils.*
import javax.inject.Inject

@ApplicationScope
class SharedPreferencesHandler @Inject constructor(var sharedPreferences: SharedPreferences){

    private lateinit var sharedPreferencesDataObservable: BehaviorSubject<SharedPreferences>


    private val changeListenerSharedPreferences = OnSharedPreferenceChangeListener { sharedPreferences, _ ->

        sharedPreferencesDataObservable.onNext(sharedPreferences)

    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(changeListenerSharedPreferences)
        sharedPreferencesDataObservable = BehaviorSubject.createDefault(sharedPreferences)
    }

    fun getDataFromSharedPreferences() : Observable<Statistics>{
        return sharedPreferencesDataObservable.map {prepareObjectForReading(it) }
    }

    fun unregisterListener(){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(changeListenerSharedPreferences)
    }


    fun writeInSharedPreferences(statisticsObject: Statistics) {
        val editor = sharedPreferences.edit()
        editor.putLong(UPDATED_KEY_SP, statisticsObject.updated)
        editor.putLong(CASES_KEY_SP, statisticsObject.cases)
        editor.putLong(DEATHS_KEY_SP, statisticsObject.deaths)
        editor.putLong(RECOVERED_KEY_SP, statisticsObject.recovered)
        editor.putLong(ACTIVE_KEY_SP, statisticsObject.active)
        editor.putLong(CRITICAL_KEY_SP, statisticsObject.critical)
        editor.putLong(TODAY_CASES_KEY_SP, statisticsObject.todayCases)
        editor.putLong(TODAY_DEATHS_KEY_SP, statisticsObject.todayDeaths)
        editor.putInt(AFFECTED_COUNTRIES_KEY_SP, statisticsObject.affectedCountries)
        editor.apply()
    }

    private fun prepareObjectForReading(sharedPreferences: SharedPreferences) : Statistics{

        return Statistics(
            updated =  sharedPreferences.getLong(UPDATED_KEY_SP,0),
            cases =  sharedPreferences.getLong(CASES_KEY_SP,0),
            deaths = sharedPreferences.getLong(DEATHS_KEY_SP,0),
            recovered = sharedPreferences.getLong(RECOVERED_KEY_SP,0),
            active = sharedPreferences.getLong(ACTIVE_KEY_SP,0),
            critical = sharedPreferences.getLong(CRITICAL_KEY_SP,0),
            todayCases = sharedPreferences.getLong(TODAY_CASES_KEY_SP,0),
            todayDeaths = sharedPreferences.getLong(TODAY_DEATHS_KEY_SP,0),
            affectedCountries = sharedPreferences.getInt(AFFECTED_COUNTRIES_KEY_SP,0)
        )
    }
}