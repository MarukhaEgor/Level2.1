package com.example.level21.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.level21.data.models.ContactsModel

class DiffUtils: DiffUtil.ItemCallback<ContactsModel>() {
    override fun areItemsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ContactsModel, newItem: ContactsModel): Boolean {
        return oldItem.equals(newItem)
    }
}