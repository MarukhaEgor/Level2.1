package com.example.level21.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.viewpager2.widget.ViewPager2
import com.example.level21.R
import com.example.level21.base.BaseFragment
import com.example.level21.databinding.ProfileFragmentBinding
import org.koin.android.ext.android.inject

class ProfileFragment(
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    ProfileFragmentBinding = ProfileFragmentBinding::inflate
) : BaseFragment<ProfileFragmentBinding>() {

    private val viewModel: ProfileViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setDataToProfile()
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.viewContacts()
            } else {
                Toast.makeText(requireContext(), "Разрешение нужно", Toast.LENGTH_LONG).show()
            }
        }

    override fun setListeners() {
        binding.btnProfileActivityViewContact.setOnClickListener {
                requestPermission.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    override fun setObservers() {
        with(viewModel) {
            loginModel.observe(viewLifecycleOwner) { it ->
                ("${it.name} ${it.secondName}").also { binding.tvProfileActivityName.text = it }
            }
            navigationEvent.observe(viewLifecycleOwner, ::navigateToContacts)
        }
    }

    private fun navigateToContacts(direction: Int) {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        viewPager?.currentItem = direction
    }

}