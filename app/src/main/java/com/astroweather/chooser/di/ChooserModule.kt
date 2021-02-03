package com.astroweather.chooser.di

import com.astroweather.chooser.domain.source.CitySource
import com.astroweather.chooser.domain.source.local.LocalCitySource
import com.astroweather.common.di.CommonModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module(includes = [CommonModule::class])
@InstallIn(SingletonComponent::class)
object ChooserModule {

    @Provides
    @Named("localCitySource")
    fun provideLocalCitySource(): CitySource {
        return LocalCitySource()
    }
}