package com.example.level21.di

import com.example.level21.ui.profile.ProfileViewModel
import org.koin.dsl.module

val profileViewModel = module {
    single { ProfileViewModel(get(),get()) }
}