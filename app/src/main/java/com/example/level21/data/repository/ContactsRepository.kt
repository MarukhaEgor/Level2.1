package com.example.level21.data.repository

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.Flow
import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Phone.*
import androidx.annotation.WorkerThread
import com.example.level21.data.db.ContactsDataBase
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.utils.Constants
import org.koin.core.KoinComponent

class ContactsRepository(
    private val context: Context,
    private val db: ContactsDataBase,
) :
    KoinComponent {

    val allContacts: Flow<List<ContactsEntity>> = db.coordinatesDao().getAllContacts()

    @SuppressLint("Range")
    fun readContacts(): MutableList<ContactsModel> {
        val contacts = context.contentResolver.query(
            CONTENT_URI,
            Constants.CR_COLUMNS,
            null,
            null,
            null
        )
        val contactList = ArrayList<ContactsModel>()
        while (contacts?.moveToNext() == true) {
            val obj = ContactsModel(
                userName = contacts.getString(contacts.getColumnIndex(DISPLAY_NAME)),
                phone = contacts.getString(contacts.getColumnIndex(NUMBER)),
                avatar = contacts.getString(contacts.getColumnIndex(PHOTO_URI)),
                address = null,
                birthDay = null,
                career = null,
                email = null
                )
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