package com.example.level21.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.level21.arch.BaseFragment
import com.example.level21.databinding.ProfileFragmentBinding
import org.koin.android.ext.android.inject

class ProfileFragment(
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) ->
    ProfileFragmentBinding = ProfileFragmentBinding::inflate
) : BaseFragment<ProfileFragmentBinding>() {

    private val viewModel: ProfileViewModel by inject()

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
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission.launch(Manifest.permission.READ_CONTACTS)
            } else {
                viewModel.viewContacts()
            }
        }
    }

    override fun setObservers() {
        with(viewModel) {
            loginModel.observe(viewLifecycleOwner) { it ->
                ("${it.name} ${it.secondName}").also { binding.tvProfileActivityName.text = it }
            }
            navigationEvent.observe(viewLifecycleOwner, ::navigate)
        }
    }

}