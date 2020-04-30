package iti.mobilenative.covid19monitoring.dagger.modules.app

import dagger.Module
import dagger.Provides
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import iti.mobilenative.covid19monitoring.services.network.retrofit.CovidApiService
import iti.mobilenative.covid19monitoring.utils.BASEURL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @ApplicationScope
    @Provides
     fun createRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASEURL)
            .build()
    }
    @ApplicationScope
    @Provides
    fun getRetrofit(retrofit: Retrofit) : CovidApiService{
        return retrofit.create(CovidApiService::class.java)
    }

}