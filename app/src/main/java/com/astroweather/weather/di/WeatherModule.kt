package com.astroweather.weather.di

import android.content.Context
import com.astroweather.BuildConfig
import com.astroweather.common.di.CommonModule
import com.astroweather.weather.domain.service.WeatherService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [CommonModule::class])
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    fun provideFusedLocationClient(@ApplicationContext applicationContext: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    @Provides
    fun provideCancellationTokenSource(): CancellationTokenSource {
        return CancellationTokenSource()
    }

    @Provides
    @Singleton
    fun provideService(gson: Gson, client: OkHttpClient): WeatherService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(WeatherService::class.java)
    }
}