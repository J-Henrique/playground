package com.jhbb.viewpagercache

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ColorAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 100

    override fun createFragment(position: Int): Fragment {
        return RedFragment()
    }
}