package com.example.level21.ui.addContactDialog

import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.repository.ContactsRepository
import com.example.level21.utils.CoroutineViewModel
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class AddContactDialogFragmentViewModel(private val repository: ContactsRepository) : CoroutineViewModel(), KoinComponent {

    fun saveContact(contact: ContactsEntity) {
        scope.launch {
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