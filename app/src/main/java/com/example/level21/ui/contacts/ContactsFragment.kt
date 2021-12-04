package com.example.level21.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.databinding.ContactsFragmentBinding
import com.example.level21.ui.contacts.adapter.Adapter
import com.example.level21.utils.SwipeToDel
import org.koin.android.ext.android.inject

class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by inject()
    private lateinit var binding: ContactsFragmentBinding

    private val rvAdapter: Adapter by lazy {
        Adapter(
            itemClickListener = object : Adapter.ItemClickListener {
                override fun onClick(pos: Int) {
                    rvAdapter.deleteItem(pos)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!viewModel.isDataBaseEmpty()) {
            viewModel.initBase()
        }
        viewModel.getAllContacts()
        binding = ContactsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        setObservers()
    }

    private fun setObservers() {
        viewModel.liveData.observe(viewLifecycleOwner, {
            rvAdapter.setListData(it as ArrayList<ContactsEntity>)
            rvAdapter.submitList(it)
        })
        rvAdapter.delLiveData.observe(viewLifecycleOwner, {
            viewModel.deleteItem(it)
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