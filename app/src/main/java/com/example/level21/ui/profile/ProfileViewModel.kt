package com.example.level21.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.level21.data.LoginModel
import com.example.level21.data.Repository
import org.koin.core.KoinComponent

class ProfileViewModel(private val repository: Repository) : ViewModel(), KoinComponent {

    private val _loginModel = MutableLiveData<LoginModel>()
    val loginModel: MutableLiveData<LoginModel> = _loginModel


    fun setDataToProfile(){
        _loginModel.value = repository.getDataFromSharedPrefs()
    }


}