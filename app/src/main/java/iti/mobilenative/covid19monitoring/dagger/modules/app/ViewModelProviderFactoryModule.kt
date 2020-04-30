package iti.mobilenative.covid19monitoring.dagger.modules.app

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import iti.mobilenative.covid19monitoring.utils.ViewModelProvidersFactory

@Module
abstract class ViewModelProviderFactoryModule{
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProvidersFactory): ViewModelProvider.Factory
}