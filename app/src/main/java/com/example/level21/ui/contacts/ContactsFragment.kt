package com.example.level21.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.level21.R
import com.example.level21.R.id.mainNavHostFragment
import com.example.level21.base.BaseFragment
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

class ContactsFragment(
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    ContactsFragmentBinding = ContactsFragmentBinding::inflate
) : BaseFragment<ContactsFragmentBinding>() {

    private val viewModel: ContactsViewModel by inject()

    private val rvAdapter: Adapter by lazy {
        Adapter({ position -> deleteItem(position) }
        ) { contact ->
            showDetail(contact)
        }
    }

    override fun setListeners() {
        binding.apply {
            tvAddContact.setOnClickListener {
                val addContactDialogFragment = AddContactDialogFragment()
                addContactDialogFragment.show(parentFragmentManager, "addContact")
            }
            icArrowBack.setOnClickListener {
                viewModel.goBack()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRv()
    }

    override fun setObservers() {
        viewModel.allContacts.observe(viewLifecycleOwner) { contacts ->
            if (contacts.isEmpty()) viewModel.initDataBase()
            contacts.let { rvAdapter.submitList(it) }
        }
        viewModel.navigationEvent.observe(viewLifecycleOwner, ::navigateToProfile)
        viewModel.navigationEventDetail.observe(viewLifecycleOwner, ::navigateToDetail)
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

    private fun navigateDetailTransactions(contact: ContactsModel) {
        val detailFragment = DetailFragment()
        detailFragment.arguments = addToBundle(contact)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(mainNavHostFragment, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToDetail(pair: Pair<Int, ContactsModel>) {
        findNavController().navigate(pair.first, addToBundle(contact = pair.second))
    }

    private fun addToBundle(contact: ContactsModel): Bundle {
        return Bundle().apply { putParcelable("contact", contact) }
    }

    private fun showDetail(contact: ContactsModel) {
        if (GlobalConstants.IF_NAV_TRANSACTION) {
            addToBundle(contact)
            viewModel.goToDetailFragmentNavigate(contact)
        } else {
            navigateDetailTransactions(contact)
        }
    }

    private fun navigateToProfile(direction: Int) {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.currentItem = direction
    }

    private fun deleteItem(position: Int) = viewModel.deleteItem(position)
}