package com.example.level21.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.data.models.ContactsModel
import com.example.level21.databinding.ContactsFragmentBinding
import com.example.level21.ui.contacts.adapter.Adapter
import com.example.level21.utils.SwipeToDel
import org.koin.android.ext.android.inject

class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by inject()
    private lateinit var binding: ContactsFragmentBinding

    private val rvList: MutableList<ContactsModel>? by lazy{
        getContactList()
    }

    private val rvAdapter: Adapter by lazy {
        Adapter(
            itemClickListener = object : Adapter.ItemClickListener {
                override fun onClick(pos: Int) {
                    rvAdapter.deleteItem(pos)
                }
            }, rvList = rvList
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.initList()
        initRv()
        rvAdapter.submitList(rvList)
    }

    private fun getContactList(): MutableList<ContactsModel>? {
        return viewModel.getList()?.toMutableList()
    }

    private fun setObservers() {
        viewModel.contactList.observe(viewLifecycleOwner, {
            binding.rvContactsList.adapter = rvAdapter
        })
    }

    private fun initRv() {
        binding.rvContactsList.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        val itemTouchHelper = ItemTouchHelper(SwipeToDel(rvAdapter))
        itemTouchHelper.attachToRecyclerView(binding.rvContactsList)
    }

}