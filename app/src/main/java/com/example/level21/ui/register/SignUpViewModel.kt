package com.example.level21.ui.register

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.level21.data.LoginModel
import com.example.level21.data.Repository
import com.example.level21.utils.Constants
import com.example.level21.utils.SingleLiveEvent
import org.koin.core.KoinComponent

class SignUpViewModel(private val repository: Repository) : ViewModel(), KoinComponent {

    private val _navigationEvent = SingleLiveEvent<NavDirections>()
    val navigationEvent: LiveData<NavDirections> = _navigationEvent

    private lateinit var name: String
    private lateinit var secondName: String

    fun goToProfile() {
        _navigationEvent.value =
            SignUpFragmentDirections.actionSignUpFragmentToProfileFragment()
    }

    fun saveLoginData(chkBoxResult: Boolean){
        saveLoginData(LoginModel(name = name, secondName = secondName))
        repository.saveState(chkBoxResult)
    }

    fun isAutoLogin(): Boolean{
        return repository.getState()
    }

    private fun saveLoginData(data: LoginModel) {
        repository.saveDataToSharedPrefs(data)
    }

    fun validChecker(email: String, pass: String): Boolean {
        return isValidMail(email) && isValidPass(pass)
    }

    fun isValidMail(email: String): Boolean {
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.contains(".")) {
            email.substring(0, email.indexOf(".")).also { name = it }
            email.substring(email.indexOf(".") + 1, email.indexOf("@")).also { secondName = it }
            return true
        }
        return false
    }

    fun isValidPass(pass: String): Boolean {
        if (pass.isNotEmpty() && pass.length > Constants.PASS_MIN_LENGTH) {
            return true
        }
        return false
    }
}