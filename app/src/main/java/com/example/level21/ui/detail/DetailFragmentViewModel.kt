package com.example.level21.ui.detail

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.level21.utils.SingleLiveEvent

class DetailFragmentViewModel: ViewModel() {

    private val _navigationEvent = SingleLiveEvent<NavDirections>()
    val navigationEvent: LiveData<NavDirections> = _navigationEvent

    fun goBack(activity: Activity) {
        activity.onBackPressed()
    }
}