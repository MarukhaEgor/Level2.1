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
import com.example.level21.ui.addContactDialog.AddContactDialogFragment
import com.example.level21.ui.contacts.adapter.Adapter
import com.example.level21.utils.Constants
import com.example.level21.utils.ContactListItemDecoration
import com.example.level21.utils.SwipeToDel
import com.example.level21.utils.dpToPx
import org.koin.android.ext.android.inject

class ContactsFragment : Fragment(), ItemClickListener {

    private val viewModel: ContactsViewModel by inject()
    private lateinit var binding: ContactsFragmentBinding

    private val rvAdapter: Adapter by lazy {
        Adapter(
            itemClickListener = this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ContactsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
        setDbObserve()
        setListeners()
    }

    private fun setListeners(){
        binding.tvAddContact.setOnClickListener {
            val addContactDialogFragment = AddContactDialogFragment()
            addContactDialogFragment.show(parentFragmentManager,"addContact")
        }
    }

    private fun setDbObserve() {
        viewModel.allContacts.observe(viewLifecycleOwner, { contacts ->
            if (contacts.isEmpty()) {
                viewModel.initBase()
            }
            rvAdapter.setListData(contacts as ArrayList<ContactsEntity>)
            contacts.let { rvAdapter.submitList(it) }
        })
    }

    private fun initRv() {
        binding.rvContactsList.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
        binding.rvContactsList.addItemDecoration(
            ContactListItemDecoration(
                requireContext().dpToPx(Constants.RV_ITEM_MARGIN)
            )
        )
        val itemTouchHelper = ItemTouchHelper(SwipeToDel(rvAdapter))
        itemTouchHelper.attachToRecyclerView(binding.rvContactsList)
    }

    override fun onClick(pos: Int) {
        viewModel.deleteItem(pos)
    }
}