package com.jhbb.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    lateinit var authenticationButton: Button
    lateinit var passwordText: TextInputLayout

    companion object {
        const val IS_USER_AUTHENTICATED = "IS_USER_AUTHENTICATED"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        authenticationButton = view.findViewById(R.id.btAuthenticate)
        passwordText = view.findViewById(R.id.edPassword)

        authenticationButton.setOnClickListener {
            if (isPasswordValid()) {
                setAuthenticationStatus(true)
                findNavController().popBackStack()
            }
        }
        setAuthenticationStatus(false)
    }

    private fun setAuthenticationStatus(status: Boolean) {
        findNavController()
            .previousBackStackEntry!!
            .savedStateHandle
            .set(IS_USER_AUTHENTICATED, status)
    }

    private fun isPasswordValid(): Boolean {
        passwordText.apply {
            return if (editText?.text.isNullOrBlank()) {
                passwordText.error = "Cannot be empty"
                false
            } else {
                passwordText.error = null
                true
            }
        }
    }
}