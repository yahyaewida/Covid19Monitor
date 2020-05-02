package iti.mobilenative.covid19monitoring.dagger.components

import dagger.Component
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.dagger.modules.app.RetrofitModule
import iti.mobilenative.covid19monitoring.dagger.modules.app.RoomModule
import iti.mobilenative.covid19monitoring.dagger.modules.app.ViewModelProviderFactoryModule
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import iti.mobilenative.covid19monitoring.utils.App


@ApplicationScope
@Component(modules = [RetrofitModule::class, RoomModule::class, ViewModelProviderFactoryModule::class])
interface ApplicationComponent {
    fun provideActivity(activityModule : ActivityModule) : ActivityComponent
    fun inject(app:App)
}