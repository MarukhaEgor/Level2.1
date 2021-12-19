package com.example.level21.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.level21.data.db.dao.ContactsDao
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.utils.Constants

@Database(entities = [ContactsEntity::class], version = 1, exportSchema = false)
abstract class ContactsDataBase : RoomDatabase() {

    abstract fun coordinatesDao(): ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsDataBase? = null
        fun getDataBase(context: Context): ContactsDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDataBase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}