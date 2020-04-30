package iti.mobilenative.covid19monitoring.services.network.retrofit

import iti.mobilenative.covid19monitoring.utils.BASEURL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object Factory{
        fun create() : CovidApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASEURL)
                .build()
            return retrofit.create(CovidApiService::class.java)
        }
    }
}