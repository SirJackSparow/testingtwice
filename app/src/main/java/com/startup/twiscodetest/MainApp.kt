package com.startup.twiscodetest

import android.app.Application
import com.squareup.picasso.Picasso
import com.startup.twiscodetest.di.moduleApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(moduleApp)
        }
        val builder = Picasso.Builder(this)
        Picasso.setSingletonInstance(builder.build())
    }
}