package com.example.level21.di

import android.app.Application
import androidx.room.Room
import com.example.level21.data.db.ContactsDataBase
import com.example.level21.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    fun provideDataBase(application: Application): ContactsDataBase {
        return Room.databaseBuilder(application,
            ContactsDataBase::class.java,
            Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
    single { provideDataBase(androidApplication()) }
}