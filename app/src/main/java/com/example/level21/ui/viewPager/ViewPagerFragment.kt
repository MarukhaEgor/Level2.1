package com.example.level21.ui.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.level21.databinding.FragmentViewPagerBinding
import com.example.level21.ui.contacts.ContactsFragment
import com.example.level21.ui.profile.ProfileFragment


class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(layoutInflater, container, false)

//        val adapter = ViewPagerAdapter(
//            fragmentList,
//            parentFragmentManager,
//            lifecycle
//        )
//
//        val adapter = ViewPagerAdapter(childFragmentManager)
//        view.viewPager.adapter = adapter
//
//        val bundle = this.arguments
//        val page: Int? = bundle?.getInt("page")
//        if (bundle != null) {
//            view.viewPager.currentItem = page!!
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf(
            ProfileFragment(),
            ContactsFragment()
        )
        binding.viewPager.adapter = ViewPagerAdapter(this, fragmentList)
    }
}