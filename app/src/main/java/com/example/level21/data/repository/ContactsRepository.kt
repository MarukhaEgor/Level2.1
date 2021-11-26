package com.example.level21.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone.*
import androidx.lifecycle.LiveData
import com.example.level21.data.db.ContactsDataBase
import com.example.level21.data.db.dao.ContactsDao
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import org.koin.core.KoinComponent

class ContactsRepository(
    private val context: Context,
    private val db: ContactsDataBase
) :
    KoinComponent {

    private var cols = listOf(
        DISPLAY_NAME,
        NUMBER,
        PHOTO_URI,
        _ID
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

    fun getcontactOne(): LiveData<ContactsEntity?> {
        return db.coordinatesDao().getContactById(1)
    }

    suspend fun insertContact(contact: ContactsEntity){
        db.coordinatesDao().insertContact(contact)
    }

    fun getContactList(): List<ContactsEntity>? {
        return db.coordinatesDao().getAllContacts()
    }

}