package com.example.level21.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone.*
import com.example.level21.data.models.ContactsModel
import org.koin.core.KoinComponent

class ContactsRepository(private val context: Context) : KoinComponent {

    private var cols = listOf(
        DISPLAY_NAME,
        NUMBER,
        PHOTO_URI,
        ContactsContract.Contacts._ID
    ).toTypedArray()

    @SuppressLint("Range")
    fun readContacts(): MutableList<ContactsModel> {
        val contacts = context.contentResolver.query(
            CONTENT_URI,
            cols,
            null,
            null,
            null
        )
        val contactList: MutableList<ContactsModel> = ArrayList()
        while (contacts?.moveToNext() == true) {
            val name =
                contacts.getString(contacts.getColumnIndex(DISPLAY_NAME))
            val number =
                contacts.getString(contacts.getColumnIndex(NUMBER))
            val uriImg =
                contacts.getString(contacts.getColumnIndex(PHOTO_URI))
            val obj = ContactsModel()
            obj.name = name
            obj.number = number
            obj.image = uriImg
            contactList.add(obj)
        }
        contacts?.close()
        return contactList
    }
}