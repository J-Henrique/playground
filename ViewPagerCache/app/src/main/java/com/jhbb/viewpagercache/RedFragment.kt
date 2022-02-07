package com.jhbb.viewpagercache

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jhbb.viewpagercache.databinding.FragmentRedBinding

class RedFragment : Fragment() {
    private lateinit var binding: FragmentRedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentRedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}