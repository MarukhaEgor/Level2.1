package com.example.level21.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.level21.databinding.ProfileFragmentBinding
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by inject()
    private lateinit var binding: ProfileFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.setDataToProfile()
        setListeners()
    }

    private fun setListeners() {
        binding.profileActivityViewContactBtn.setOnClickListener {
            viewModel.viewContacts()
        }
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    private fun setObservers() {
        viewModel.loginModel.observe(viewLifecycleOwner, { it ->
            ("${it.name} ${it.secondName}").also { binding.profileActivityNameTv.text = it }
        })
        viewModel.navigationEvent.observe(viewLifecycleOwner, ::navigate)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater)
        return binding.root
    }
}