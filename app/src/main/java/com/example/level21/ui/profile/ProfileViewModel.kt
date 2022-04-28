package com.example.level21.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.level21.data.models.LoginModel
import com.example.level21.data.repository.Repository
import com.example.level21.utils.SingleLiveEvent
import org.koin.core.KoinComponent

class ProfileViewModel(
    private val repository: Repository,
) : ViewModel(), KoinComponent {

    private val _loginModel = MutableLiveData<LoginModel>()
    val loginModel: MutableLiveData<LoginModel> = _loginModel

    private val _navigationEvent = SingleLiveEvent<Int>()
    val navigationEvent: LiveData<Int> = _navigationEvent

    fun setDataToProfile() {
        _loginModel.value = repository.getDataFromSharedPrefs()
    }

    fun viewContacts() {
        _navigationEvent.value = 1
    }
}