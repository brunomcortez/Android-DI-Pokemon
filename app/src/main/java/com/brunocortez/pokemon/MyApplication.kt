package com.brunocortez.pokemon

import android.app.Application
import com.brunocortez.pokemon.di.networkModule
import com.brunocortez.pokemon.di.repositoryModule
import com.brunocortez.pokemon.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            // Start stetho
            Stetho.initializeWithDefaults(this)
        }

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    networkModule
                )
            )
        }
    }
}