package iti.mobilenative.covid19monitoring.dagger.modules.app

import dagger.Module
import dagger.Provides
import iti.mobilenative.covid19monitoring.dagger.scopes.ApplicationScope
import iti.mobilenative.covid19monitoring.model.remote_api.CovidApiService
import iti.mobilenative.covid19monitoring.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {

    @ApplicationScope
    @Provides
     fun createRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
    @ApplicationScope
    @Provides
    fun getRetrofit(retrofit: Retrofit) : CovidApiService {
        return retrofit.create(CovidApiService::class.java)
    }

}