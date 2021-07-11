package com.example.converterlib.di

import com.example.converterlib.data.repo.Constant
import com.example.converterlib.network.ErrorInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    open fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://data.fixer.io/api/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    open fun provideGson(): Gson {
        return GsonBuilder().setPrettyPrinting().create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideErrorInterceptor(): ErrorInterceptor {
        return ErrorInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        errorInterceptor: ErrorInterceptor,
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
        builder
            .addInterceptor(loggingInterceptor)
            .addInterceptor(errorInterceptor)
            .connectTimeout(Constant.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(Constant.CONNECTION_READ_TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }

}