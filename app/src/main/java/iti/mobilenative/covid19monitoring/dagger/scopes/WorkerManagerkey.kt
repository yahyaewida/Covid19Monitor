package iti.mobilenative.covid19monitoring.dagger.scopes

import androidx.work.CoroutineWorker
import dagger.MapKey
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class WorkerManagerkey(val value: KClass<out CoroutineWorker>)
