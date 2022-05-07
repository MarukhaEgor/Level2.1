package com.example.level21.ui.viewPager

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.level21.base.BaseFragment

class ViewPagerAdapter(
    fm: Fragment,
    list: ArrayList<BaseFragment<out ViewBinding>>
) :
    FragmentStateAdapter(fm) {
    private val fragmentList = list
    override fun getItemCount(): Int = fragmentList.size
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}