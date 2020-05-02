package iti.mobilenative.covid19monitoring.dagger.modules.activity

import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides
import iti.mobilenative.covid19monitoring.dagger.scopes.ActivityScope

@Module
class ActivityModule (private val context: FragmentActivity){

    @ActivityScope
    @Provides
    fun getContext() = context
}
