package com.example.level21.ui.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.level21.R
import com.example.level21.data.db.entity.ContactsEntity
import com.example.level21.data.models.ContactsModel
import com.example.level21.databinding.ContactsFragmentBinding
import com.example.level21.ui.addContactDialog.AddContactDialogFragment
import com.example.level21.ui.contacts.adapter.Adapter
import com.example.level21.ui.detail.DetailFragment
import com.example.level21.utils.Constants
import com.example.level21.utils.ContactListItemDecoration
import com.example.level21.utils.GlobalConstants
import com.example.level21.utils.SwipeToDel
import com.example.level21.utils.extensions.dpToPx
import org.koin.android.ext.android.inject

class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by inject()
    private lateinit var binding: ContactsFragmentBinding

    private val rvAdapter: Adapter by lazy { Adapter({ position -> deleteItem(position) }
    ) {  contact ->
        showToast(contact)
    }
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
        binding.apply {
            tvAddContact.setOnClickListener {
                val addContactDialogFragment = AddContactDialogFragment()
                addContactDialogFragment.show(parentFragmentManager,"addContact")
            }
            icArrowBack.setOnClickListener {
                viewModel.goBack()
            }
        }
    }

    private fun setDbObserve() {
        viewModel.allContacts.observe(viewLifecycleOwner, { contacts ->
            if (contacts.isEmpty()) viewModel.initDataBase()
            contacts.let { rvAdapter.submitList(it) }
        })
        viewModel.navigationEvent.observe(viewLifecycleOwner, ::navigate)
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
        ItemTouchHelper(SwipeToDel(rvAdapter)).apply { attachToRecyclerView(binding.rvContactsList) }
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun navigateDetail(contact: ContactsEntity) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<DetailFragment>(R.id.contactsFragment)
        }
    }

    private fun showToast(contact: ContactsModel) {
        viewModel.goToDetailFragment(contact)
        Toast.makeText(activity?.applicationContext, contact.userName, Toast.LENGTH_LONG).show()
    }

    private fun deleteItem(position: Int) {
        viewModel.deleteItem(position)
    }
}