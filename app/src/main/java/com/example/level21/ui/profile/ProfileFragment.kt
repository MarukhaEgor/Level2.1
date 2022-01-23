package com.example.level21.ui.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.level21.R
import com.example.level21.databinding.ProfileFragmentBinding
import com.example.level21.utils.extensions.loadCircleImage
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by inject()
    private lateinit var binding: ProfileFragmentBinding

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                viewModel.viewContacts()
            } else {
                Toast.makeText(requireContext(), "Разрешение нужно", Toast.LENGTH_LONG).show()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.setDataToProfile()
        setListeners()
    }

    private fun setListeners() {
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

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun setObservers() {
        with(viewModel) {
            loginModel.observe(viewLifecycleOwner, { it ->
                ("${it.name} ${it.secondName}").also { binding.tvProfileActivityName.text = it }
            })
            navigationEvent.observe(viewLifecycleOwner, ::navigate)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }
}