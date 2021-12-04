package com.example.level21.utils

import android.util.Patterns

object Validator {

    fun isValidData(email: String, pass: String): Boolean {
        return isValidMail(email) && isValidPass(pass)
    }

    fun isValidMail(email: String): Boolean {
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.contains(
                "."
            )
        ) return true
        return false
    }

    fun isValidPass(pass: String): Boolean {
        if (pass.isNotEmpty() && pass.length > Constants.PASS_MIN_LENGTH) {
            return true
        }
        return false
    }

}