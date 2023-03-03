package com.example.level21.ui.contacts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.R
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.databinding.ItemsContactBinding
import com.example.level21.utils.DiffUtils
import com.example.level21.utils.extensions.loadCircleImage

class Adapter(
    private val deleteItem: (position: Int) -> Unit,
    private val showDetail: (contact: ContactsModel) -> Unit,
    private val setDelView: (status: Boolean) -> Unit,
) :
    ListAdapter<ContactsEntity, Adapter.ContactsViewHolder>(DiffUtils()) {

    private var isEnable = false
    private var delList: MutableList<ContactsEntity>? = mutableListOf()

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
        fun bind(contact: ContactsEntity) {
            binding.apply {
                ivAvatar.loadCircleImage(contact.avatar)
                tvUserName.text = contact.userName
                tvCarrier.text = contact.phone
                if (isEnable) {
                    ivDelBtn.visibility = View.GONE
                    ivContactSelector.visibility = View.VISIBLE
                } else {
                    ivDelBtn.visibility = View.VISIBLE
                    ivContactSelector.visibility = View.GONE
                }
            }
            itemView.setOnClickListener {
                if (isEnable) {
                    if (isContain(contact)) {
                        changeState(contact)
                        delFromList(contact)
                    } else {
                        changeState(contact)
                        addToList(contact)
                    }
                } else {
                    showDetail(contact)
                }
            }
            binding.ivDelBtn.setOnClickListener {
                delItem(adapterPosition)
            }
            binding.root.setOnLongClickListener {
                updateView()
                true
            }
        }

        private fun changeState(contact: ContactsEntity) {
            if (!isContain(contact)) {
                binding.ivContactSelector.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_check_box_is_checked
                )
            } else {
                binding.ivContactSelector.background = ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_check_box_round
                )
            }
        }

        private fun isContain(contact: ContactsEntity): Boolean = delList?.contains(contact)!!
        private fun addToList(contact: ContactsEntity) = delList?.add(contact)
        private fun delFromList(contact: ContactsEntity) {
            delList?.remove(contact)
            if (delList?.size == 0) {
                updateView()
            }
        }




        private fun showDetail(contact: ContactsEntity) =
            showDetail(
                ContactsModel(
                    userName = contact.userName,
                    phone = contact.phone,
                    avatar = contact.avatar,
                    career = contact.career,
                    address = contact.address,
                    birthDay = "",
                    email = ""
                )
            )
    }
    private fun updateView(){
        isEnable = !isEnable
        setDelView(isEnable)
        notifyDataSetChanged()
    }

    fun delItem(position: Int) = deleteItem(position)
    fun delItems(): MutableList<ContactsEntity>? = delList
    fun clearList() {
        delList?.clear()
        updateView()
    }
}