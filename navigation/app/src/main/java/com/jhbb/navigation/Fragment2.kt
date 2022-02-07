package com.jhbb.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import android.widget.Toast
import android.widget.ToggleButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.switchmaterial.SwitchMaterial

class Fragment2 : Fragment() {

    lateinit var navigateButton: Button
    lateinit var authenticatedSwitch: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: Fragment2Args by navArgs()
        args.FRAGMENT2ARG.apply {
            Toast.makeText(activity, this, Toast.LENGTH_SHORT).show()
        }

        navigateButton = view.findViewById(R.id.btFragment2)
        authenticatedSwitch = view.findViewById(R.id.swUserAuthenticated)

        setButtonClick()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setAuthenticationStatus()
    }

    private fun setButtonClick() {
        navigateButton.setOnClickListener {
            if (!authenticatedSwitch.isChecked) {
                findNavController().navigate(Fragment2Directions.actionFragment2ToAuthentication())
            } else {
                findNavController().navigate(Fragment2Directions.actionFragment2ToFragment3("Navigation from Fragment 2"))
            }
        }
    }

    private fun setAuthenticationStatus() {
        val isUserAuthenticated = findNavController()
            .currentBackStackEntry!!
            .savedStateHandle
            .get<Boolean>(LoginFragment.IS_USER_AUTHENTICATED) ?: false

        authenticatedSwitch.isChecked = isUserAuthenticated
    }
}