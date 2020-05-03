package iti.mobilenative.covid19monitoring.dagger.modules.app

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import iti.mobilenative.covid19monitoring.dagger.scopes.WorkerManagerkey
import iti.mobilenative.covid19monitoring.model.workmanager.ChildWorkerFactory
import iti.mobilenative.covid19monitoring.model.workmanager.CountriesWorker

@Module
abstract class WorkerManagerModule {

    @Binds
    @IntoMap
    @WorkerManagerkey(CountriesWorker::class)
    internal abstract fun bindWorkerFactory(worker: CountriesWorker.Factory): ChildWorkerFactory
}
