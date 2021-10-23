package com.example.level21.di

import com.example.level21.data.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val repository: Module = module {
    single {
        Repository(androidContext())
    }
}