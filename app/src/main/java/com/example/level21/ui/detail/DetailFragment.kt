package com.example.level21.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.level21.base.BaseFragment
import com.example.level21.data.models.ContactsModel
import com.example.level21.databinding.DetailFragmentBinding
import com.example.level21.ui.contacts.ContactsFragment
import com.example.level21.utils.extensions.loadCircleImage
import org.koin.android.ext.android.inject


class DetailFragment(
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    DetailFragmentBinding = DetailFragmentBinding::inflate
) : BaseFragment<DetailFragmentBinding>() {

    private val viewModel: DetailFragmentViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val contact: ContactsModel? = bundle?.getParcelable("contact")
        if (bundle != null) {
            setUpData(contact)
        }
    }

    private fun setUpData(contact: ContactsModel?) {
        binding.apply {
            ivDetailAvatar.loadCircleImage(contact?.avatar)
            tvDetailName.text = contact?.userName
            tvDetailCarreer.text = contact?.career
            tvDetailAddress.text = contact?.address
        }
    }

    override fun setObservers() = viewModel.navigationEvent.observe(viewLifecycleOwner, ::navigate)
    override fun setListeners() = binding.icArrowBack.setOnClickListener {
        requireActivity().onBackPressed()
        //viewModel.goBack()
    }



//    private fun navigateToContacts(page: Int){
//        val contactsFragment = ContactsFragment()
//        contactsFragment.arguments = addToBundle(page)
//    }
//
//    private fun navigateToContacts(direction: NavDirections){
//        findNavController().navigate(direction)
//    }
//
//    private fun addToBundle(page: Int): Bundle {
//        return Bundle().apply { putInt("page", page) }
//    }

}