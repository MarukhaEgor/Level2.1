package com.example.level21.data.repository

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.Flow
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone.*
import androidx.annotation.WorkerThread
import com.example.level21.data.db.ContactsDataBase
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import org.koin.core.KoinComponent

class ContactsRepository(
    private val context: Context,
    private val db: ContactsDataBase,
) :
    KoinComponent {

    val allContacts: Flow<List<ContactsEntity>> = db.coordinatesDao().getAllContacts()

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

    suspend fun insertContact(contact: ContactsEntity) {
        db.coordinatesDao().insertContact(contact)
    }

    suspend fun deleteContact(contact: ContactsEntity?) {
        db.coordinatesDao().deleteContact(contact)
    }

}