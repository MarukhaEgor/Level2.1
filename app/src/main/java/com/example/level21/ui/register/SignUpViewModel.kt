package com.example.level21.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.level21.data.models.LoginModel
import com.example.level21.data.repository.Repository
import com.example.level21.utils.SingleLiveEvent
import com.example.level21.utils.Validator
import org.koin.core.KoinComponent

class SignUpViewModel(private val repository: Repository,
                      private var name: String? = null,
                      private var secondName: String? = null
) : ViewModel(), KoinComponent {

    private val _navigationEvent = SingleLiveEvent<NavDirections>()
    val navigationEvent: LiveData<NavDirections> = _navigationEvent

    fun goToProfile() {
        _navigationEvent.value =
            SignUpFragmentDirections.actionSignUpFragmentToProfileFragment(name.toString(),
                secondName.toString())
    }

    fun saveLoginData(chkBoxResult: Boolean) {
        saveLoginData(LoginModel(name = name, secondName = secondName))
        repository.saveState(chkBoxResult)
    }

    fun isAutoLogin(): Boolean = repository.getState()

    private fun saveLoginData(data: LoginModel) = repository.saveDataToSharedPrefs(data)

    fun validChecker(email: String, pass: String): Boolean {
        return when {
            Validator.isValidData(email,pass) -> {
                email.substring(0, email.indexOf(".")).also { name = it }
                email.substring(email.indexOf(".") + 1, email.indexOf("@")).also { secondName = it }
                true
            }
            else -> false
        }
    }
}