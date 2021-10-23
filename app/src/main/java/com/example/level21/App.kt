package com.example.level21

import android.app.Application
import com.example.level21.di.repository
import com.example.level21.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        koinStart()
    }

    private fun koinStart() {
        startKoin {
            androidContext(this@App)
            modules(viewModel, repository)
        }
    }
}