package com.example.level21.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ContactsModel(
    val userName: String,
    val career: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthDay: String?,
    val avatar: String?,
): Parcelable
