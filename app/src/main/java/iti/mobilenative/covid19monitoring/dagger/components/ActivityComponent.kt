package iti.mobilenative.covid19monitoring.dagger.components

import dagger.Subcomponent
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ActivityModule
import iti.mobilenative.covid19monitoring.dagger.modules.activity.ViewModelProvider
import iti.mobilenative.covid19monitoring.dagger.scopes.ActivityScope
import iti.mobilenative.covid19monitoring.features.HomeActivity
import iti.mobilenative.covid19monitoring.features.countries.view.CountriesFragment
import iti.mobilenative.covid19monitoring.features.countries.view.MainActivity
import iti.mobilenative.covid19monitoring.features.settings.view.SettingsFragment
import iti.mobilenative.covid19monitoring.features.statistics.StatisticsFragment
import iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.view.CurrentStatisticsFragment
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.view.HistoricalStatisticsFragment
import iti.mobilenative.covid19monitoring.features.subscribed_countries.view.SubscribedCountriesFragment

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, ViewModelProvider::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(subscribedCountriesFragment: SubscribedCountriesFragment)
    fun inject(historicalStatisticsFragment: HistoricalStatisticsFragment)
    fun inject(currentStatisticsFragment: CurrentStatisticsFragment)
    fun inject(countriesFragment: CountriesFragment)
    fun inject(statisticsFragment: StatisticsFragment)
    fun inject(settingsFragment: SettingsFragment)
}