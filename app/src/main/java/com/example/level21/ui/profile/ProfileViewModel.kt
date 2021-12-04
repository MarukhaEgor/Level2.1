package com.example.level21.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        _navigationEvent.value = ProfileFragmentDirections.actionProfileFragmentToContactsFragment()
    }


}