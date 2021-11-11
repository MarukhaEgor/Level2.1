package com.example.level21.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.level21.data.model.LoginModel
import com.example.level21.utils.Constants
import org.koin.core.KoinComponent

class Repository(context: Context) : KoinComponent {
    private val key = "KEY_SHARED_PREFS"

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(key, Context.MODE_PRIVATE)

    fun saveDataToSharedPrefs(data: LoginModel) {
        with(sharedPref.edit()) {
            putString(Constants.SP_NAME, data.name)
            putString(Constants.SP_SECOND_NAME, data.secondName)
            apply()
        }
    }

    fun saveState(autoLogin: Boolean) {
        sharedPref.edit().putBoolean(Constants.SP_IS_REMEMBER, autoLogin).apply()
    }

    fun getState(): Boolean {
        return sharedPref.getBoolean(Constants.SP_IS_REMEMBER, false)
    }

    fun getDataFromSharedPrefs(): LoginModel {
        val name = sharedPref.getString(Constants.SP_NAME, "")
        val secondName = sharedPref.getString(Constants.SP_SECOND_NAME, "")
        return LoginModel(
            name = name,
            secondName = secondName
        )
    }
}