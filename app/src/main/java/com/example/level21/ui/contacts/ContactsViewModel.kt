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

class ContactsViewModel(private val repository: ContactsRepository) : CoroutineViewModel(), KoinComponent {

//    private var _contactList : LiveData<List<ContactsEntity>?>? = null
//    val contactList : LiveData<List<ContactsEntity>?>? = _contactList

    private val _liveData = MutableLiveData<List<ContactsEntity>>()

    val liveData: LiveData<List<ContactsEntity>>
        get() = _liveData

    fun initDB() {
        initBase()
    }

    private fun initBase() {
        initDataBase(repository.readContacts())
    }

    private fun initDataBase(contactList: MutableList<ContactsModel>) {

        for (i in 0 until contactList.size) {
            val contact = ContactsEntity(
                id = i,
                userName = contactList[i].name,
                phone = contactList[i].number,
                avatar = contactList[i].image,
                career = "",
                address = "",
                birthDay = "",
                email = ""
            )
            insertContact(contact)
        }
        getAll()
    }

    fun getAll() : List<ContactsEntity>?{
        scope.launch{
            _liveData.postValue(getAllUsers())
        }
        return _liveData.value
    }

    private fun getAllUsers(): List<ContactsEntity>? {
        return repository.getContactList()
    }


    private fun insertContact(contact: ContactsEntity) {
        scope.launch {
            repository.insertContact(contact)
        }
    }

//    fun getList(): MutableList<ContactsEntity>? {
//        return contactList.value
//    }

}