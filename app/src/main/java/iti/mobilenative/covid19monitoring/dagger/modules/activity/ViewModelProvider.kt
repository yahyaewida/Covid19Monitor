package iti.mobilenative.covid19monitoring.dagger.modules.activity

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import iti.mobilenative.covid19monitoring.dagger.scopes.ViewModelKey
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel
import iti.mobilenative.covid19monitoring.features.settings.viewmodel.SettingsViewModel
import iti.mobilenative.covid19monitoring.features.statistics.currentStatistics.viewmodel.StatisticsViewModel
import iti.mobilenative.covid19monitoring.features.statistics.historicalStatistics.viewmodel.HistoricalStatisticsViewmodel
import iti.mobilenative.covid19monitoring.features.subscribed_countries.viewmodel.SubscribedCountriesViewModel


@Module
abstract class ViewModelProvider {
    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    internal abstract fun countriesViewModel(viewModel: CountriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StatisticsViewModel::class)
    internal abstract fun statisticsViewModel(viewModel: StatisticsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoricalStatisticsViewmodel::class)
    internal abstract fun historicalStatisticsViewmodel(viewModel: HistoricalStatisticsViewmodel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(SubscribedCountriesViewModel::class)
    internal abstract fun selectedCountriesViewModel(viewModel: SubscribedCountriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel

}