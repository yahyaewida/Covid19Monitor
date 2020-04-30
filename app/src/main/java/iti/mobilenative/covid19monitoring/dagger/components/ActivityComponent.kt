package iti.mobilenative.covid19monitoring.dagger.components

import dagger.Subcomponent
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ViewModelProvider
import iti.mobilenative.covid19monitoring.dagger.scopes.ActivityScope
import iti.mobilenative.covid19monitoring.features.countries.view.MainActivity

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, ViewModelProvider::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}