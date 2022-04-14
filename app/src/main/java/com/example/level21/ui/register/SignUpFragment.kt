package com.example.level21.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.level21.R
import com.example.level21.arch.BaseFragment
import com.example.level21.databinding.ProfileFragmentBinding
import com.example.level21.databinding.SignUpFragmentBinding
import com.example.level21.utils.Validator
import org.koin.android.ext.android.inject

class SignUpFragment : BaseFragment<SignUpFragmentBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SignUpFragmentBinding =
        SignUpFragmentBinding::inflate

    private val viewModel: SignUpViewModel by inject()

    override fun onResume() {
        super.onResume()
        if (viewModel.isAutoLogin()) navigate(SignUpFragmentDirections.actionSignUpFragmentToProfileFragment())
    }

    override fun setObservers() = viewModel.navigationEvent.observe(viewLifecycleOwner, ::navigate)
    override fun setListeners() = binding.btnRegister.setOnClickListener { goToProfile() }

    private fun goToProfile() {
        val email = binding.etSignUpViewEmail.text.toString()
        val pass = binding.etSignUpViewPass.text.toString()
        if (viewModel.validChecker(
                email,
                pass
            )
        ) {
            viewModel.goToProfile()
        } else {
            if (!Validator.isValidMail(email)) {
                binding.tilSignUpViewEmail.error = getString(R.string.wrong_mail_msg)
            }
            if (!Validator.isValidPass(pass)) {
                binding.tilSignUpViewPass.error = getString(R.string.wrong_pass_msg)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveLoginData(binding.chBxSignUpView.isChecked)
    }
}