package com.ciutacuclaudia.data.di

import com.ciutacuclaudia.data.BuildConfig
import com.ciutacuclaudia.data.di.Constants.REQUEST_TIMEOUT_TIME
import com.ciutacuclaudia.data.remote.api.WeatherAlertAPI
import com.ciutacuclaudia.data.remote.interceptor.LoggingInterceptor
import com.ciutacuclaudia.data.remote.repository.WeatherRepository
import com.ciutacuclaudia.data.remote.repository.WeatherRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor(LoggingInterceptor())

        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val builder = OkHttpClient.Builder()
            .readTimeout(REQUEST_TIMEOUT_TIME, TimeUnit.SECONDS)
            .connectTimeout(REQUEST_TIMEOUT_TIME, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)

        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    @Singleton
    @Provides
    fun provideWeatherAlertAPI(retrofit: Retrofit): WeatherAlertAPI {
        return retrofit.create(WeatherAlertAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(weatherAlertAPI: WeatherAlertAPI): WeatherRepository {
        return WeatherRepositoryImpl(weatherAlertAPI)
    }
}

object Constants {
    const val REQUEST_TIMEOUT_TIME = 6L
}