package com.example.level21.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.level21.R
import com.example.level21.databinding.SignUpFragmentBinding
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by inject()
    private lateinit var binding: SignUpFragmentBinding

    override fun onResume() {
        super.onResume()
        if (viewModel.isAutoLogin()){
            navigate(SignUpFragmentDirections.actionSignUpFragmentToProfileFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpFragmentBinding.inflate(inflater)
        setOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    private fun setObservers() {
        viewModel.navigationEvent.observe(viewLifecycleOwner, ::navigate)
    }

    private fun setOnClickListeners() {
        binding.BtnRegister.setOnClickListener {
            goToProfile()
        }
    }

    private fun goToProfile(){
        val email = binding.signUpViewEmailEt.text.toString()
        val pass = binding.signUpViewPassEt.text.toString()
        if (viewModel.validChecker(email,
            pass)){
            viewModel.goToProfile()
        } else {
            if (!viewModel.isValidMail(email)){
                binding.signUpViewEmailTIL.error = getString(R.string.wrong_mail_msg)
            }
            if (!viewModel.isValidPass(pass)){
                binding.signUpViewPassTIL.error = getString(R.string.wrong_pass_msg)
            }
        }
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveLoginData(binding.signUpViewRemmChBx.isChecked)
    }
}