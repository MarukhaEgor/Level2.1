package com.example.level21.ui.contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.level21.R
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.utils.DiffUtils

class Adapter(
    private val itemClickListener: ItemClickListener,
//    private var rvList: List<ContactsEntity>?,
    private val context: Context
) :
    ListAdapter<ContactsEntity, Adapter.ContactsViewHolder>(DiffUtils()) {

    var rvList  = ArrayList<ContactsEntity>()

    fun setListData(data: ArrayList<ContactsEntity>) {
        this.rvList = data
    }

    inner class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val delBtn: ImageView = view.findViewById(R.id.ivDelBtn) as ImageView
        private val profile = view.findViewById<ImageView>(R.id.ivAvatar)!!
        private val name = view.findViewById<TextView>(R.id.tvUserName)!!
        private val carrier = view.findViewById<TextView>(R.id.tvCarrier)!!
        fun bind(contact: ContactsEntity) {
            Glide.with(context).load(contact.avatar).circleCrop()
                .placeholder(R.drawable.ic_android_black).into(profile)
            name.text = contact.userName
            carrier.text = contact.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = rvList?.get(position)
        contact?.let { holder.bind(it) }
        holder.delBtn.setOnClickListener {
            itemClickListener.onClick(holder.adapterPosition)
        }
    }

    fun deleteItem(pos: Int) {
        val list = rvList?.toMutableList()
        list?.removeAt(pos)
        rvList = list as ArrayList<ContactsEntity>
        submitList(rvList)
    }

    interface ItemClickListener {
        fun onClick(pos: Int)
    }
}