package iti.mobilenative.covid19monitoring.dagger.modules.activity

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import iti.mobilenative.covid19monitoring.dagger.scopes.ActivityScope

@Module
class ActivityModule (private val context: AppCompatActivity){

    @ActivityScope
    @Provides
    fun getContext() = context
}
