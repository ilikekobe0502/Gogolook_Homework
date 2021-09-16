package com.gogolook.homework

import android.app.Application
import com.gogolook.homework.utils.log.DebugLogTree
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var self: Application
        fun applicationContext(): Application {
            return self
        }
    }

    init {
        self = this
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugLogTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}