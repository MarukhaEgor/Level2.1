package com.example.level21.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.R
import com.example.level21.data.models.ContactsModel
import com.example.level21.utils.DiffUtils

class Adapter(private val itemClickListener: ItemClickListener, private var rvList: MutableList<ContactsModel>?) :
    ListAdapter<ContactsModel, Adapter.ContactsViewHolder>(DiffUtils()) {

    inner class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val delBtn: ImageView = view.findViewById(R.id.ivDelBtn) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.delBtn.setOnClickListener {
            itemClickListener.onClick(holder.adapterPosition)
        }
    }

    fun deleteItem(pos: Int) {
        val list = rvList?.toMutableList()
        list?.removeAt(pos)
        rvList = list
        submitList(rvList)
    }

    interface ItemClickListener {
        fun onClick(pos: Int)
    }
}