package com.example.level21.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.level21.data.db.entity.ContactsEntity

@Dao
interface ContactsDao {

    @Query("SELECT * FROM ContactsEntity ORDER BY userName ASC")
    fun getAllContacts(): List<ContactsEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: ContactsEntity)

    @Delete
    suspend fun deleteContact(contact: ContactsEntity?)

    @Update
    suspend fun updateContact(contact: ContactsEntity)

    @Query("SELECT * FROM ContactsEntity")
    fun getRawList(): List<ContactsEntity>

}