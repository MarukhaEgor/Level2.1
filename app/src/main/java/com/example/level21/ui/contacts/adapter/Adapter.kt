package com.example.level21.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.R
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.databinding.ItemsContactBinding
import com.example.level21.utils.DiffUtils
import com.example.level21.utils.loadCircleImage

class Adapter(
    private val itemClickListener: ItemClickListener,
) :
    ListAdapter<ContactsEntity, Adapter.ContactsViewHolder>(DiffUtils()) {

    private val _liveData = MutableLiveData<ContactsEntity>()
    val delLiveData: LiveData<ContactsEntity>
        get() = _liveData

    var rvList = ArrayList<ContactsEntity>()

    fun setListData(data: ArrayList<ContactsEntity>) {
        this.rvList = data
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_contact, parent, false)
        return ContactsViewHolder(ItemsContactBinding.bind(itemView))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = rvList[position]
        contact.let { holder.bind(it) }
        holder.delBtn.setOnClickListener {
            itemClickListener.onClick(holder.adapterPosition)
        }
    }

    fun deleteItem(pos: Int) {
        val list = rvList.toMutableList()
        val tempVal = list[pos]
        list.removeAt(pos)
        rvList = list as ArrayList<ContactsEntity>
        submitList(rvList)
        _liveData.value = tempVal
    }

    interface ItemClickListener {
        fun onClick(pos: Int)
    }
}