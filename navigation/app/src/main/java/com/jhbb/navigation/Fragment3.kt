package com.jhbb.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class Fragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btFragment3).setOnClickListener {
            findNavController().navigate(Fragment3Directions.actionFragment3ToFragment1())
        }

        val args: Fragment3Args by navArgs()
        args.FRAGMENT3ARG.apply {
            Toast.makeText(activity, this, Toast.LENGTH_SHORT).show()
        }
    }
}