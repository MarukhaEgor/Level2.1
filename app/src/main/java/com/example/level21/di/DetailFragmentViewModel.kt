package com.example.level21.di

import com.example.level21.ui.detail.DetailFragmentViewModel
import org.koin.dsl.module

val detailViewModel = module {
    single { DetailFragmentViewModel() }
}