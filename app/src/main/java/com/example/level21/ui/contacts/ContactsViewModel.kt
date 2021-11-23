package com.example.level21.ui.contacts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.level21.data.models.ContactsModel
import com.example.level21.data.repository.ContactsRepository
import org.koin.core.KoinComponent

class ContactsViewModel(private val repository: ContactsRepository) : ViewModel(), KoinComponent {

    private val _contactList = MutableLiveData<List<ContactsModel>>()
    val contactList: MutableLiveData<List<ContactsModel>> = _contactList

    fun initList() {
        _contactList.value = repository.readContacts()
    }

    fun getList(): List<ContactsModel>? {
        return contactList.value
    }

}