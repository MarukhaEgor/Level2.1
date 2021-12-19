package com.example.level21.data.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactsEntity(
    @PrimaryKey
    @NonNull
    val userName: String,
    val career: String?,
    val email: String?,
    val phone: String?,
    val address: String?,
    val birthDay: String?,
    val avatar: String?,
)