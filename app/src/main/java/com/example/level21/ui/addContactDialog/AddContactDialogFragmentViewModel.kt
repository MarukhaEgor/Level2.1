package com.example.level21.ui.addContactDialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.repository.ContactsRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class AddContactDialogFragmentViewModel(private val repository: ContactsRepository) : ViewModel(), KoinComponent {

    fun saveContact(contact: ContactsEntity) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }

    fun createContact(
        username: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthDay: String,
        avatar: String?,
    ): ContactsEntity {
        return ContactsEntity(username, career, email, phone, address, birthDay, avatar)
    }
}