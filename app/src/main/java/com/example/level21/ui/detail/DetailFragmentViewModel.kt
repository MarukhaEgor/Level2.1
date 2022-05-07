package com.example.level21.ui.detail

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.level21.R
import com.example.level21.utils.SingleLiveEvent

class DetailFragmentViewModel: ViewModel() {

    private val _navigationEvent = SingleLiveEvent<NavDirections>()
    val navigationEvent: LiveData<NavDirections> = _navigationEvent

    fun goBack() {
        _navigationEvent.value = DetailFragmentDirections.actionDetailFragmentToViewPagerFragment(1)
    }

    //fun getDirection(): NavDirections = DetailFragmentDirections.actionDetailFragmentToViewPagerFragment(1)
}