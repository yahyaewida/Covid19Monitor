package iti.mobilenative.covid19monitoring.dagger.modules.activity

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import iti.mobilenative.covid19monitoring.dagger.scopes.ViewModelKey
import iti.mobilenative.covid19monitoring.features.countries.viewmodel.CountriesViewModel


@Module
abstract class ViewModelProvider {
    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    internal abstract fun loginViewModel(viewModel: CountriesViewModel): ViewModel
}