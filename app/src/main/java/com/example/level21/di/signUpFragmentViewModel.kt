package com.example.level21.di

import com.example.level21.ui.register.SignUpViewModel
import org.koin.dsl.module

val viewModel = module {
    single { SignUpViewModel(get()) }
}