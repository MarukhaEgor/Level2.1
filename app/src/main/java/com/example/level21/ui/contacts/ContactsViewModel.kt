package com.example.level21.ui.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.navigation.NavDirections
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.data.repository.ContactsRepository
import com.example.level21.utils.CoroutineViewModel
import com.example.level21.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class ContactsViewModel(private val repository: ContactsRepository) : CoroutineViewModel(),
    KoinComponent {

    val allContacts: LiveData<List<ContactsEntity>> = repository.allContacts.asLiveData()

    private val _navigationEvent = SingleLiveEvent<NavDirections>()
    val navigationEvent: LiveData<NavDirections> = _navigationEvent

    fun initDataBase() {
        initDataBase(repository.readContacts())
    }

    private fun insertContact(contact: ContactsEntity) {
        scope.launch {
            repository.insertContact(contact)
        }
    }

    private fun initDataBase(contactList: MutableList<ContactsModel>) {
        for (it in 0 until contactList.size) {
            val contact = ContactsEntity(
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

    fun deleteItem(pos: Int) {
        deleteItemFromDB(allContacts.value?.get(pos))
    }

    private fun deleteItemFromDB(contact: ContactsEntity?) {
        scope.launch {
            repository.deleteContact(contact)
        }
    }

    fun goBack() {
        _navigationEvent.value = ContactsFragmentDirections.actionContactsFragmentToProfileFragment()
    }

}