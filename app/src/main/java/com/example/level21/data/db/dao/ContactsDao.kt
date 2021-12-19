package com.example.level21.data.db.dao


import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.level21.data.db.entity.ContactsEntity

@Dao
interface ContactsDao {

    @Query("SELECT * FROM ContactsEntity ORDER BY userName ASC")
    fun getAllContacts(): Flow<List<ContactsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactsEntity)

    @Delete
    suspend fun deleteContact(contact: ContactsEntity?)

    @Update
    suspend fun updateContact(contact: ContactsEntity)

    @Query("SELECT * FROM ContactsEntity ORDER BY userName ASC")
    suspend fun getRawList(): List<ContactsEntity>

}