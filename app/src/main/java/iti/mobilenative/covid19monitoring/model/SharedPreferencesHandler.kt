package iti.mobilenative.covid19monitoring.model

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class SharedPreferencesHandler @Inject constructor(var sharedPreferences: SharedPreferences){

    private lateinit var prefSubject: BehaviorSubject<SharedPreferences>


    val prefChangeListener = OnSharedPreferenceChangeListener { sharedPreferences, _ ->
        prefSubject.onNext(sharedPreferences)
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(prefChangeListener)
        prefSubject = BehaviorSubject.createDefault(sharedPreferences)
    }


     fun readFromSharedPrefrences() : Observable<SharedPreferences>{

        return prefSubject
    }

     fun writeInSharedPreferences(userName: String?) {
        val editor = sharedPreferences.edit()
        editor.putString("username", userName)
        editor.apply()
    }
    fun unregisterListener(){
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(prefChangeListener)
    }
}