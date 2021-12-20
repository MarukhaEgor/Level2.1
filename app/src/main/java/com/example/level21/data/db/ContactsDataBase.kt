package com.example.level21.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.level21.data.db.dao.ContactsDao
import com.example.level21.data.db.entity.ContactsEntity

@Database(entities = [ContactsEntity::class], version = 1, exportSchema = false)
abstract class ContactsDataBase : RoomDatabase() {
    abstract fun coordinatesDao(): ContactsDao
}