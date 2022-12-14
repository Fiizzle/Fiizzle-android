package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fiizzle.R
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {


    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)

        clickHandler()


        return binding.root
    }

    private fun clickHandler() {
        binding.loginSignInBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
        }

        binding.loginLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_allFragment)
        }
    }
}