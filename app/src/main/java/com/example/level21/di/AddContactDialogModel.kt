package com.example.level21.di

import com.example.level21.ui.addContactDialog.AddContactDialogFragmentViewModel
import org.koin.dsl.module

val addContactViewModel = module {
    single { AddContactDialogFragmentViewModel(get()) }
}