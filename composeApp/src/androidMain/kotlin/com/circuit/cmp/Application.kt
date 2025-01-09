package com.circuit.cmp

import android.app.Application
import com.circuit.cmp.di.module.initKoin
import org.koin.android.ext.koin.androidContext

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@Application)
        }
    }
}