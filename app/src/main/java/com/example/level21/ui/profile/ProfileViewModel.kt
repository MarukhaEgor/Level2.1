package com.example.level21.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.data.models.LoginModel
import com.example.level21.data.repository.ContactsRepository
import com.example.level21.data.repository.Repository
import com.example.level21.utils.CoroutineViewModel
import com.example.level21.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class ProfileViewModel(
    private val repository: Repository,
    private val repositoryContact: ContactsRepository
) : CoroutineViewModel(), KoinComponent {

    private val _loginModel = MutableLiveData<LoginModel>()
    val loginModel: MutableLiveData<LoginModel> = _loginModel

    private val _navigationEvent = SingleLiveEvent<NavDirections>()
    val navigationEvent: LiveData<NavDirections> = _navigationEvent


    fun setDataToProfile() {
        _loginModel.value = repository.getDataFromSharedPrefs()
    }

    fun viewContacts() {
        initBase()
        _navigationEvent.value = ProfileFragmentDirections.actionProfileFragmentToContactsFragment()
    }

    private fun initBase() {
        initDataBase(repositoryContact.readContacts())
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
    }

    private fun insertContact(contact: ContactsEntity) {
        scope.launch {
            repositoryContact.insertContact(contact)
        }
    }

}