package com.example.level21.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel

class DiffUtils: DiffUtil.ItemCallback<ContactsEntity>() {
    override fun areItemsTheSame(oldItem: ContactsEntity, newItem: ContactsEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ContactsEntity, newItem: ContactsEntity): Boolean {
        return oldItem == newItem
    }
}