package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fiizzle.R
import com.example.fiizzle.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {


    private lateinit var binding : FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSignInBinding>(inflater, R.layout.fragment_sign_in, container, false)

        clickHandler()

        return binding.root
    }

    private fun clickHandler() {
        binding.signInCompleteBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_allFragment)
        }
    }
}