package com.example.level21.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.level21.data.models.ContactsModel
import com.example.level21.databinding.DetailFragmentBinding
import com.example.level21.utils.extensions.loadCircleImage


class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        val contact: ContactsModel? = bundle?.getParcelable("contact")
        if(bundle != null){
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

}