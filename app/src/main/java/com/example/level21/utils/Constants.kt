package com.example.level21.utils

import android.provider.ContactsContract

object Constants {
    const val SP_IS_REMEMBER = "IS_REMEMBER"
    const val SP_NAME = "NAME"
    const val SP_SECOND_NAME = "SP_SECOND_NAME"
    const val PASS_MIN_LENGTH = 7
    const val DATABASE_NAME = "Contacts database"
    const val RV_ITEM_MARGIN = 16
    const val CONTACT_DATA = "contact"
    val CR_COLUMNS = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.PHOTO_URI,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()
}