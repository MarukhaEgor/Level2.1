package com.example.level21.data.repository

import android.content.Context
import android.provider.ContactsContract

class ContactsRepository(context: Context) {

    private var cols = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
    ).toTypedArray()

    var rs = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
}