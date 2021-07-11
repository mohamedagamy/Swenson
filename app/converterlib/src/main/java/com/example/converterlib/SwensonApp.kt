package com.example.converterlib

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

@HiltAndroidApp
class SwensonApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }

    private fun initRxJavaError() {
        // If Java 8 lambdas are supported
        RxJavaPlugins.setErrorHandler { e: Throwable? ->
            Timber.d(e?.localizedMessage.toString())
        }

    }
}