package com.arany.corona

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CoreApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: CoreApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getCoreApplication(): CoreApplication {
            return instance as CoreApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}