package com.example.level21.ui.addContactDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.level21.databinding.DialogFragmentAddContactBinding
import org.koin.android.ext.android.inject

class AddContactDialogFragment: DialogFragment() {
    private val viewModel: AddContactDialogFragmentViewModel by inject()
    private lateinit var binding: DialogFragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentAddContactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            btnSave.setOnClickListener {
                viewModel.saveContact(viewModel.createContact(
                    etUserNameAddContact.text.toString(),
                    etCareerAddContact.text.toString(),
                    etEmailAddContact.text.toString(),
                    etPhoneAddContact.text.toString(),
                    etAddressAddContact.text.toString(),
                    etBDataAddContact.text.toString(),
                    null
                ))
                dialog?.dismiss()
            }
            icArrowBack.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params: WindowManager.LayoutParams? = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.onWindowAttributesChanged(params)
    }


}