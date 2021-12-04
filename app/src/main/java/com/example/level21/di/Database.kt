package com.example.level21.di

import com.example.level21.data.db.ContactsDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule  = module {
    single { ContactsDataBase.getDataBase(androidContext()) }
}