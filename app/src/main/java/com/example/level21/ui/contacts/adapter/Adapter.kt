package com.example.level21.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.R
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.databinding.ItemsContactBinding
import com.example.level21.utils.DiffUtils
import com.example.level21.utils.extensions.loadCircleImage

class Adapter(
    private val deleteItem: (position: Int) -> Unit,
    private val ShowToast: () -> Unit,
) :
    ListAdapter<ContactsEntity, Adapter.ContactsViewHolder>(DiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_contact, parent, false)
        return ContactsViewHolder(ItemsContactBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = getItem(position)
        contact.let { holder.bind(it) }
    }

    inner class ContactsViewHolder(
        private val binding: ItemsContactBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val delBtn = binding.ivDelBtn
        fun bind(contact: ContactsEntity) {
            binding.apply {
                ivAvatar.loadCircleImage(contact.avatar)
                tvUserName.text = contact.userName
                tvCarrier.text = contact.phone
                itemView.setOnClickListener {
                    showToast()
                }
            }
            delBtn.setOnClickListener {
                delItem(adapterPosition)
            }
        }
    }

    private fun showToast() = ShowToast()

    fun delItem(position: Int) = deleteItem(position)
}