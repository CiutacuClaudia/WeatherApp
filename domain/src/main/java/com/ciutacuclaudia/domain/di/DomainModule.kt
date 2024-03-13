package com.ciutacuclaudia.domain.di

import com.ciutacuclaudia.data.remote.repository.WeatherRepository
import com.ciutacuclaudia.domain.WeatherUseCase
import com.ciutacuclaudia.domain.WeatherUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Singleton
    @Provides
    fun provideWeatherUseCase(weatherRepository: WeatherRepository): WeatherUseCase {
        return WeatherUseCaseImpl(weatherRepository)
    }
}