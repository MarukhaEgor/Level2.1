package com.example.level21.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.R
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.databinding.ItemsContactBinding
import com.example.level21.ui.contacts.ItemClickListener
import com.example.level21.utils.DiffUtils
import com.example.level21.utils.loadCircleImage

class Adapter(
    private val itemClickListener: ItemClickListener,
) :
    ListAdapter<ContactsEntity, Adapter.ContactsViewHolder>(DiffUtils()) {

    var rvList = ArrayList<ContactsEntity>()

    fun setListData(data: ArrayList<ContactsEntity>) {
        this.rvList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_contact, parent, false)
        return ContactsViewHolder(ItemsContactBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = rvList[position]
        contact.let { holder.bind(it) }
        holder.delBtn.setOnClickListener {
            delItem(holder.adapterPosition)
        }
    }

    inner class ContactsViewHolder(private val binding: ItemsContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val delBtn = binding.ivDelBtn
        fun bind(contact: ContactsEntity) {
            binding.apply {
                ivAvatar.loadCircleImage(contact.avatar)
                tvUserName.text = contact.userName
                tvCarrier.text = contact.phone
            }
        }
    }

    fun delItem(position: Int) {
        itemClickListener.onClick(position)
    }
}