package com.example.level21.ui.contacts


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.data.repository.ContactsRepository
import com.example.level21.utils.CoroutineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class ContactsViewModel(private val repository: ContactsRepository) : CoroutineViewModel(),
    KoinComponent {

    private val _liveData = MutableLiveData<List<ContactsEntity>>()

    val liveData: LiveData<List<ContactsEntity>>
        get() = _liveData

    private var contactsListSize = 0

    fun getAllContacts(): List<ContactsEntity>? {
        scope.launch {
            _liveData.postValue(getAllUsers())
        }
        return _liveData.value
    }

    fun isDataBaseEmpty(): Boolean{
        scope.launch {
            contactsListSize = repository.getFirstContact().size
        }
        if (contactsListSize > 0 ) return true
        return false
    }

    private fun getAllUsers(): List<ContactsEntity>? {
        return repository.getContactList()
    }

    fun deleteItem(contact: ContactsEntity){
        scope.launch {
            repository.deleteContact(contact)
        }
    }

    private fun insertContact(contact: ContactsEntity) {
        scope.launch {
            repository.insertContact(contact)
        }
    }

    fun initBase() {
        initDataBase(repository.readContacts())
    }

    private fun initDataBase(contactList: MutableList<ContactsModel>) {

        for (it in 0 until contactList.size) {
            val contact = ContactsEntity(
                id = it,
                userName = contactList[it].name,
                phone = contactList[it].number,
                avatar = contactList[it].image,
                career = "",
                address = "",
                birthDay = "",
                email = ""
            )
            insertContact(contact)
        }
    }
}