package iti.mobilenative.covid19monitoring.utils

import android.app.Application
import iti.mobilenative.covid19monitoring.model.LocalDatabase

class App : Application() {
     companion object{
           lateinit var database: LocalDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = LocalDatabase.getInstance(this)!!
    }
}